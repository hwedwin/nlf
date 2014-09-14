package nc.liat6.frame.db.custom.csv;

import nc.liat6.frame.db.setting.impl.SuperDbSetting;

/**
 * CSV连接配置
 * 
 * @author 6tail
 * 
 */
public class CsvSetting extends SuperDbSetting{

  private static final long serialVersionUID = 8889847573386316725L;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "csv";
  /** 默认数据库类型 */
  public static final String DEFAULT_DB_TYPE = "csv";
  /** 默认驱动程序 */
  public static final String DEFAULT_DRIVER = CsvDriver.class.getName();

  public CsvSetting(){
    type = DEFAULT_TYPE;
    dbType = DEFAULT_DB_TYPE;
    driver = DEFAULT_DRIVER;
  }
}
