package nc.liat6.frame.klass;

import java.io.File;
import java.io.FileFilter;

/**
 * jar文件过滤，允许.jar文件
 *
 * @author 6tail
 *
 */
public class JarFileFilter implements FileFilter{

  public boolean accept(File f){
    return !f.isDirectory()&&f.getName().endsWith(".jar");
  }
}
