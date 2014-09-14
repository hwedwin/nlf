package nc.liat6.frame.db.setting;

import java.io.Serializable;

/**
 * 连接设置接口
 * 
 * @author 6tail
 * 
 */
public interface IDbSetting extends Serializable{

  /**
   * 获取配置类型
   * 
   * @return 配置类型
   */
  public String getType();

  /**
   * 获取别名
   * 
   * @return 别名
   */
  public String getAlias();

  /**
   * 获取数据库实例名
   * 
   * @return 数据库实例名
   */
  public String getDbName();

  /**
   * 获取驱动
   * 
   * @return 驱动
   */
  public String getDriver();

  /**
   * 获取数据库类型
   * 
   * @return 数据库类型
   */
  public String getDbType();
}