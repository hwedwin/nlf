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
    String name = f.getName();
    String path = f.getAbsolutePath();
    boolean check = f.isDirectory()||name.endsWith(".class")||name.endsWith(LocaleFactory.FILE_SUFFIX);
    check = check&&(!path.contains("com.mongodb".replace(".",File.separator)))&&(!path.contains("org.bson".replace(".",File.separator)));
    return check;
  }
}