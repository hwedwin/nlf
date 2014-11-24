package nc.liat6.frame;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import nc.liat6.frame.aop.IAopInterceptor;
import nc.liat6.frame.exception.ImplNotFoundException;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.klass.ByteCodeReader;
import nc.liat6.frame.klass.ClassFileFilter;
import nc.liat6.frame.klass.ClassInfo;
import nc.liat6.frame.klass.ClassNameComparator;
import nc.liat6.frame.klass.ICaller;
import nc.liat6.frame.klass.JarFileFilter;
import nc.liat6.frame.klass.PkgNameComparator;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.locale.LocaleResource;
import nc.liat6.frame.util.IOHelper;
import nc.liat6.frame.util.Pather;
import nc.liat6.frame.web.WebContext;

/**
 * 工厂
 *
 * @author 6tail
 *
 */
public class Factory{

  /** 应用所在目录 */
  public static String APP_PATH = "";
  /** 应用路径 */
  public static String CALLER = "";
  /** NLF路径 */
  public static String NLF_PATH = "";
  /** 类信息缓存 */
  public static final Map<String,ClassInfo> CLASS = new HashMap<String,ClassInfo>();
  /** 接口实现缓存 */
  public static final Map<String,List<String>> IMPLS = new HashMap<String,List<String>>();
  /** AOP缓存 */
  public static final List<String> AOPS = new ArrayList<String>();
  /** 引入的包 */
  public static final Set<String> PKGS = new HashSet<String>();
  /** 扫描路径 */
  public static final Set<String> LIBS = new HashSet<String>();
  static{
    if(!WebContext.isWebApp){
      initApp(null,null);
    }
  }

  private Factory(){}

  private static void buildPkg(){
    List<String> l = new ArrayList<String>(PKGS.size());
    for(String s:PKGS){
      l.add(s);
    }
    Collections.sort(l,new PkgNameComparator());
    PKGS.clear();
    outer:for(String s:l){
      for(String p:PKGS){
        if(s.startsWith(p)){
          continue outer;
        }
      }
      PKGS.add(s);
    }
  }

  /**
   * 重建缓存
   */
  public static void reBuild(){
    buildInterface();
    buildImpls();
    buildAop();
    buildPkg();
  }

  public static void initApp(String callerPath,String libPath){
    JarFileFilter jarFilter = new JarFileFilter();
    // 所有需要扫描的路径
    Set<String> paths = new HashSet<String>();
    // 获取classpath引用路径
    String[] cps = System.getProperty("java.class.path").split(File.pathSeparator);
    for(String cp:cps){
      File f = new File(cp);
      if(!f.exists()){
        continue;
      }
      if(f.getName().endsWith(".jar")){
        if(jarFilter.accept(f)){
          paths.add(f.getAbsolutePath());
        }
      }
    }
    if(null==callerPath){
      // 获取调用者路径
      StackTraceElement[] sts = Thread.currentThread().getStackTrace();
      String caller = null;
      boolean isFrameClass = false;
      for(StackTraceElement t:sts){
        String s = t.getClassName();
        if(s.startsWith(Version.PACKAGE)){
          isFrameClass = true;
        }else{
          if(isFrameClass){
            caller = s;
            break;
          }
        }
      }
      callerPath = Pather.getPath(caller,true);
    }
    if(new File(callerPath).exists()){
      paths.add(callerPath);
    }
    // 获取调用者引用的路径
    if(callerPath.endsWith(".jar")){
      JarFile jar = null;
      try{
        jar = new JarFile(callerPath);
        Manifest mf = jar.getManifest();
        if(null!=mf){
          Attributes attrs = mf.getMainAttributes();
          String cp = attrs.getValue("Class-Path");
          if(null!=cp){
            String[] ps = cp.split(" ");
            for(String p:ps){
              p = p.trim();
              if(0==p.length()){
                continue;
              }
              // 当前目录，跳过
              if(".".equals(p)){
                continue;
              }
              File f = new File(p);
              if(jarFilter.accept(f)){
                paths.add(f.getAbsolutePath());
              }
            }
          }
        }
      }catch(IOException e){
        throw new RuntimeException(e);
      }finally{
        IOHelper.closeQuietly(jar);
      }
    }
    if(new File(callerPath).exists()){
      CALLER = callerPath;
      APP_PATH = callerPath.endsWith(".jar")?new File(callerPath).getParentFile().getAbsolutePath():callerPath;
      NLF_PATH = Pather.FRAME_JAR_PATH;
      System.out.println("CALLER   : "+CALLER);
      System.out.println("APP PATH : "+APP_PATH);
      System.out.println("NLF PATH : "+NLF_PATH);
    }
    // 如果调用者是jar，不扫描其所在目录的class
    if(CALLER.endsWith(".jar")){
      paths.remove(APP_PATH);
    }
    // 需要加载jar的目录
    Set<String> libs = new HashSet<String>();
    if(null!=libPath){
      libs.add(libPath);
    }
    for(String p:paths){
      if(p.endsWith(".jar")){
        continue;
      }
      if(p.equals(CALLER)){
        continue;
      }
      libs.add(p);
    }
    for(String p:libs){
      File dir = new File(p);
      if(!dir.exists()){
        continue;
      }
      File[] fs = dir.listFiles(jarFilter);
      for(File f:fs){
        paths.add(f.getAbsolutePath());
      }
      paths.remove(p);
    }
    // 扫描class
    for(String p:paths){
      if(!LIBS.contains(p)){
        LIBS.add(p);
        System.out.println("scan : "+p);
        scan(p);
      }
    }
    reBuild();
  }

  public static List<String> getImpls(String className){
    List<String> l = IMPLS.get(className);
    if(null==l){
      throw new ImplNotFoundException(className);
    }
    Collections.sort(l,new ClassNameComparator());
    return l;
  }

  public static void addImpl(String interfaceName,String implClassName){
    List<String> l = IMPLS.get(interfaceName);
    if(null==l){
      l = new ArrayList<String>();
      IMPLS.put(interfaceName,l);
    }
    if(!l.contains(implClassName)){
      l.add(implClassName);
    }
  }

  public static List<String> getAops(){
    return AOPS;
  }

  public static ClassInfo getClass(String className){
    return CLASS.get(className);
  }

  public static boolean contains(String className){
    return CLASS.containsKey(className);
  }

  private static void buildInterface(){
    for(ClassInfo c:CLASS.values()){
      List<String> l = new ByteCodeReader().getInterfaces(c);
      c.setInterfaces(l);
    }
  }

  private static void buildImpls(){
    IMPLS.clear();
    for(ClassInfo c:CLASS.values()){
      for(String it:c.getInterfaces()){
        if(!IMPLS.containsKey(it)){
          IMPLS.put(it,new ArrayList<String>());
        }
        List<String> l = IMPLS.get(it);
        if(!c.isAbstractClass()){
          l.add(c.getClassName());
        }
      }
    }
  }

  private static void scanDirClass(File file,FileFilter filter,String home){
    if(file.isDirectory()){
      for(File f:file.listFiles(filter)){
        scanDirClass(f,filter,home);
      }
      return;
    }
    String cn = file.getAbsolutePath().replace(home,"");
    while(cn.startsWith(File.separator)){
      cn = cn.substring(File.separator.length());
    }
    // 添加locale
    if(cn.endsWith(LocaleFactory.FILE_SUFFIX)){
      if(cn.contains("_")){
        LocaleResource lr = new LocaleResource();
        lr.setFileName(cn);
        lr.setHome(home);
        lr.setInJar(false);
        lr.setLocale(cn.substring(0,cn.indexOf(".")));
        LocaleFactory.addResource(lr);
        return;
      }
    }
    cn = cn.replace(File.separator,".").replace(".class","");
    ClassInfo ci = new ClassInfo();
    ci.setClassName(cn);
    ci.setHome(home);
    ci.setInJar(false);
    ci.setFileName(file.getName());
    ci.setLastModify(file.lastModified());
    CLASS.put(cn,ci);
  }

  private static void scanJarClass(File jarFile){
    ZipFile zip = null;
    try{
      zip = new ZipFile(jarFile);
      Enumeration<?> entries = zip.entries();
      while(entries.hasMoreElements()){
        ZipEntry entry = (ZipEntry)entries.nextElement();
        String cn = entry.getName();
        // 添加locale
        if(cn.endsWith(LocaleFactory.FILE_SUFFIX)){
          if(cn.contains("_")){
            LocaleResource lr = new LocaleResource();
            lr.setFileName(cn);
            lr.setHome(jarFile.getAbsolutePath());
            lr.setInJar(true);
            String nm = cn;
            if(nm.contains("/")){
              nm = nm.substring(nm.lastIndexOf("/")+1);
            }
            lr.setLocale(nm.substring(0,nm.indexOf(".")));
            LocaleFactory.addResource(lr);
            continue;
          }
        }
        if(!cn.endsWith(".class")){
          continue;
        }
        cn = cn.replace(".class","").replace("/",".");
        if(cn.contains(".")){
          String pkg = cn.substring(0,cn.lastIndexOf("."));
          PKGS.add(pkg);
        }
        ClassInfo ci = new ClassInfo();
        ci.setClassName(cn);
        ci.setHome(jarFile.getAbsolutePath());
        ci.setInJar(true);
        ci.setFileName(entry.getName());
        ci.setLastModify(entry.getTime());
        CLASS.put(cn,ci);
      }
    }catch(IOException e){
      throw new NlfException(jarFile.getAbsolutePath(),e);
    }finally{
      IOHelper.closeQuietly(zip);
    }
  }

  private static void scan(String path){
    File f = new File(path);
    if(f.isDirectory()){
      scanDirClass(f,new ClassFileFilter(),path);
    }else if(path.endsWith(".jar")){
      scanJarClass(f);
    }
  }

  private static void buildAop(){
    AOPS.clear();
    List<String> l = getImpls(IAopInterceptor.class.getName());
    AOPS.addAll(l.subList(0,l.size()));
  }

  public static ICaller getCaller(){
    List<String> l = getImpls(ICaller.class.getName());
    try{
      return (ICaller)Class.forName(l.get(0)).newInstance();
    }catch(Exception e){
      throw new NlfException(e);
    }
  }

  public static void doNothing(){}
}
