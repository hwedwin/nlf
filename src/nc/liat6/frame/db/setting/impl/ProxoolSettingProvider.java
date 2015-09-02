package nc.liat6.frame.db.setting.impl;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.DbConfigFactory;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.IDbSettingProvider;
import nc.liat6.frame.util.Stringer;

public class ProxoolSettingProvider implements IDbSettingProvider{

  public IDbSetting getDbSetting(Bean o){
    String type = o.getString("type","");
    String alias = o.getString("alias","");
    String dbType = o.getString("dbtype","");
    String user = o.getString("user","");
    String password = o.getString("password","");
    String server = o.getString("server","");
    String port = o.getString("port","");
    String dbname = o.getString("dbname","");
    type = type.toUpperCase();
    dbType = dbType.toLowerCase();
    DbConfig dc = DbConfigFactory.getDbConfig(dbType);
    ProxoolSetting ps = new ProxoolSetting();
    ps.setAlias(alias);
    ps.setDriver(dc.getDriverClassName());
    ps.setPassword(password);
    ps.setUrl(Stringer.print(dc.getUrl(),server,port,dbname));
    ps.setUser(user);
    ps.setDbType(dbType);
    ps.setDbName(dbname);
    ps.setPrototypeCount(o.getInt("prototypeCount",-1));
    ps.setHouseKeepingSleepTime(o.getLong("houseKeepingSleepTime",-1));
    ps.setMaximumActiveTime(o.getInt("maximumActiveTime",-1));
    ps.setMaximumConnectionLifeTime(o.getInt("maximumConnectionLifeTime",-1));
    ps.setMaximumConnectionCount(o.getInt("maximumConnectionCount",-1));
    ps.setMinimumConnectionCount(o.getInt("minimumConnectionCount",-1));
    ps.setSimultaneousBuildThrottle(o.getInt("simultaneousBuildThrottle",-1));
    ps.setTestBeforeUse(o.getBoolean("testBeforeUse",true));
    ps.setTestAfterUse(o.getBoolean("testAfterUse",true));
    return ps;
  }

  public boolean support(String type){
    return "proxool".equalsIgnoreCase(type);
  }
}