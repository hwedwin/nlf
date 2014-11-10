package nc.liat6.frame.db.setting;

import java.util.List;

/**
 * 数据库连接设置管理接口
 *
 * @author 6tail
 *
 */
public interface IDbSettingManager{

  /**
   * 获取数据库连接设置列表
   * @return 数据库连接设置列表
   */
  List<IDbSetting> getDbSettings();
}