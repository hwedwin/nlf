package nc.liat6.frame.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import nc.liat6.frame.Version;

/**
 * 路径工具
 * 
 * @author 6tail
 * 
 */
public class Pather{

  private Pather(){}
  /** 当前框架包所在路径 */
  public static String FRAME_JAR_PATH;
  static{
    try{
      String p = null;
      URL url = Version.class.getProtectionDomain().getCodeSource().getLocation();
      p = URLDecoder.decode(url.getPath(),"UTF-8");
      // class处理
      if(p.endsWith(".class")){
        p = p.replace(Version.PACKAGE.replace(".","/"),"");
        p = p.substring(0,p.lastIndexOf("/")+1);
      }
      FRAME_JAR_PATH = new File(p).getAbsolutePath();
    }catch(UnsupportedEncodingException e){
      throw new RuntimeException(e);
    }
  }

  /**
   * 指定类所在的包的绝对路径。如果类处于文件夹中，则返回类所在包的根路径;如果是jar，则根据jarEnabled参数确定返回jar所在的文件夹还是jar。
   * 
   * @param className 类名
   * @param jarEnabled 是否开启jar模式 true返回jar false返回jar所在目录
   * @return 路径
   */
  public static String getPath(String className,boolean jarEnabled){
    String p = null;
    Class<?> cls;
    try{
      cls = Class.forName(className);
    }catch(ClassNotFoundException e){
      throw new RuntimeException(e);
    }
    URL url = cls.getClassLoader().getResource("/");
    if(null==url){
      url = cls.getProtectionDomain().getCodeSource().getLocation();
    }
    try{
      p = URLDecoder.decode(url.getPath(),"UTF-8");
    }catch(UnsupportedEncodingException e){
      throw new RuntimeException(e);
    }
    if(jarEnabled){
      return new File(p).getAbsolutePath();
    }
    // jar处理
    if(p.endsWith(".jar")){
      p = p.substring(0,p.lastIndexOf("/")+1);
    }
    return new File(p).getAbsolutePath();
  }
}