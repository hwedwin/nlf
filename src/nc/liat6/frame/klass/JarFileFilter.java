package nc.liat6.frame.klass;

import java.io.File;
import java.io.FileFilter;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import nc.liat6.frame.util.IOHelper;

/**
 * jar文件过滤，允许.jar文件
 *
 * @author 6tail
 *
 */
public class JarFileFilter implements FileFilter{

  private static final String[] NO = {"Sun","Oracle","Apache","Apple","IBM","Signtool"};

  public boolean accept(File f){
    if(f.isDirectory()){
      return false;
    }
    if(!f.getName().endsWith(".jar")){
      return false;
    }
    JarFile jar = null;
    try{
      jar = new JarFile(f);
      Manifest mf = jar.getManifest();
      if(null==mf){
        return false;
      }
      Attributes attrs = mf.getMainAttributes();
      String author = attrs.getValue("Created-By");
      if(null==author){
        return true;
      }
      boolean inNo = false;
      for(String no:NO){
        if(author.contains(no+" ")){
          inNo = true;
        }
      }
      if(inNo){
        return false;
      }
    }catch(Throwable e){
      return false;
    }finally{
      IOHelper.closeQuietly(jar);
    }
    return true;
  }
}
