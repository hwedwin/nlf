package nc.liat6.frame.db.config;

/**
 * 数据库配置
 * 
 * @author 6tail
 * 
 */
public class DbConfig{

  /** 数据库类型 */
  private String dbType;
  /** 驱动类名 */
  private String driverClassName;
  /** 超级接口名 */
  private String superInterfaceName;
  /** 连接字符串 */
  private String url;
  /** 用于测试的SQL语句 */
  private String testSql;

  public String getDbType(){
    return dbType;
  }

  public void setDbType(String dbType){
    this.dbType = dbType;
  }

  public String getDriverClassName(){
    return driverClassName;
  }

  public void setDriverClassName(String driverClassName){
    this.driverClassName = driverClassName;
  }

  public String getSuperInterfaceName(){
    return superInterfaceName;
  }

  public void setSuperInterfaceName(String superInterfaceName){
    this.superInterfaceName = superInterfaceName;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }

  public String getTestSql(){
    return testSql;
  }

  public void setTestSql(String testSql){
    this.testSql = testSql;
  }
}
