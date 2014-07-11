package nc.liat6.frame.db.setting.impl;

/**
 * JDBC连接配置
 * 
 * @author 6tail
 * 
 */
public class JdbcSetting extends SuperDbSetting{

  private static final long serialVersionUID = 5902760339352767337L;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "jdbc";

  public JdbcSetting(){
    type = DEFAULT_TYPE;
  }
}