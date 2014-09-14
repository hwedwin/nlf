package nc.liat6.frame;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.locale.LocaleResource;
import nc.liat6.frame.util.Pather;
import nc.liat6.frame.web.WebContext;

/**
 * 工厂
 * 
 * @author 6tail
 * 
 */
public class Factory{

  /** 应用包路径 */
  public static String APP_PATH = "";
  /** 类信息缓存 */
  public static final Map<String,ClassInfo> CLASS = new HashMap<String,ClassInfo>();
  public static final Map<String,List<String>> IMPLS = new HashMap<String,List<String>>();
  public static final List<String> AOPS = new ArrayList<String>();
  static{
    if(!WebContext.isWebApp){
      initApp();
    }
  }

  public static void reBuild(){
    buildInterface();
    buildImpls();
    buildAop();
  }

  private static void initApp(){
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
    String p = Pather.getPath(caller,true);
    APP_PATH = p.endsWith(".jar")?new File(p).getParentFile().getAbsolutePath():p;
    if(!Pather.FRAME_JAR_PATH.equals(APP_PATH)){
      scan(Pather.FRAME_JAR_PATH);
    }
    scan(p);
    reBuild();
    System.out.println("APP PATH:"+APP_PATH);
    System.out.println("NLF PATH:"+Pather.FRAME_JAR_PATH);
  }

  public static void initWebApp(String path){
    APP_PATH = path;
    if(!Pather.FRAME_JAR_PATH.equals(APP_PATH)){
      scan(Pather.FRAME_JAR_PATH);
    }
    scan(APP_PATH);
    reBuild();
    System.out.println("WEB PATH:"+APP_PATH);
    System.out.println("NLF PATH:"+Pather.FRAME_JAR_PATH);
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
    try{
      ZipFile zip = new ZipFile(jarFile);
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
        cn = cn.replace("/",".").replace(".class","");
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
    }
  }

  private static void scan(String path){
    File f = new File(path);
    if(!f.isDirectory()||path.endsWith(".jar")){
      scanJarClass(f);
    }else{
      scanDirClass(f,new ClassFileFilter(),path);
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
