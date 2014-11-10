package nc.liat6.frame.db.connection;

import nc.liat6.frame.db.setting.IDbSetting;

/**
 * 连接变量提供器接口
 *
 * @author 6tail
 *
 */
public interface IConnVarProvider{

  /**
   * 是否支持指定连接类型
   *
   * @param connType 连接类型
   * @return true/false 支持/不支持
   */
  boolean support(String connType);

  /**
   * 获取连接变量
   *
   * @return 连接变量
   */
  ConnVar getConnVar();

  /**
   * 设置连接设置
   *
   * @param setting 连接设置
   */
  void initSetting(IDbSetting setting);
}