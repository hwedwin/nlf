package nc.liat6.frame.db.custom.csv;

import nc.liat6.frame.Factory;
import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.DbConfigFactory;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.IDbSettingProvider;

/**
 * CSV数据库设置提供器
 *
 * @author 6tail
 *
 */
public class CsvSettingProvider implements IDbSettingProvider{

  public IDbSetting getDbSetting(Bean o){
    String type = o.getString("type","");
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String dbname = o.getString("dbname","");
    type = type.toUpperCase();
    dbType = dbType.toLowerCase();
    DbConfig dc = DbConfigFactory.getDbConfig(dbType);
    CsvSetting cs = new CsvSetting();
    cs.setAlias(alias);
    cs.setDriver(dc.getDriverClassName());
    cs.setDbType(dbType);
    cs.setDbName(dbname.replace("${PATH}",Factory.APP_PATH));
    return cs;
  }

  public boolean support(String type){
    return "csv".equalsIgnoreCase(type);
  }
}