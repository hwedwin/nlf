package nc.liat6.frame.klass;

import java.io.File;
import java.io.FileFilter;
import nc.liat6.frame.locale.LocaleFactory;

/**
 * 类文件过滤，允许.class文件和文件夹
 * 
 * @author 6tail
 * 
 */
public class ClassFileFilter implements FileFilter{

  public boolean accept(File f){
    return f.isDirectory()||f.getName().endsWith(".class")||f.getName().endsWith(LocaleFactory.FILE_SUFFIX);
  }
}
