package nc.liat6.frame.db.setting;

import java.io.File;
import java.io.FileFilter;

/**
 * ÅäÖÃÎÄ¼ş¹ıÂË
 * 
 * @author 6tail
 * 
 */
public class DbSettingFileFilter implements FileFilter{

  public boolean accept(File f){
    return !f.isDirectory();
  }
}
