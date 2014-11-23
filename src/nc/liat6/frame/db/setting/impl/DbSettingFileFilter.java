package nc.liat6.frame.db.setting.impl;

import java.io.File;
import nc.liat6.frame.db.setting.IDbSettingFileFilter;

/**
 * 配置文件过滤
 *
 * @author 6tail
 *
 */
public class DbSettingFileFilter implements IDbSettingFileFilter{

  public boolean accept(File f){
    if(f.isDirectory()){
      return false;
    }
    if(f.getName().startsWith(".")){
      return false;
    }
    return true;
  }
}
