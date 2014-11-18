package nc.liat6.frame.klass;

import java.io.File;
import java.io.FileFilter;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * jar文件过滤，允许.jar文件
 *
 * @author 6tail
 *
 */
public class JarFileFilter implements FileFilter{

  public boolean accept(File f){
    if(f.isDirectory()){
      return false;
    }
    if(!f.getName().endsWith(".jar")){
      return false;
    }
    try{
      JarFile file = new JarFile(f);
      Manifest mf = file.getManifest();
      if(null==mf){
        return false;
      }
      Attributes attrs = mf.getMainAttributes();
      String author = attrs.getValue("Created-By");
      if(null==author){
        return true;
      }
      if(author.contains("Sun Microsystems Inc.")){
        return false;
      }
      if(author.contains("Oracle Corporation")){
        return false;
      }
    }catch(Throwable e){
      return false;
    }
    return true;
  }
}
