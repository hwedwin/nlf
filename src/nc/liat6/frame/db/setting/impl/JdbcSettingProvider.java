package nc.liat6.frame.db.setting.impl;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.DbConfigFactory;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.IDbSettingProvider;
import nc.liat6.frame.util.Stringer;

public class JdbcSettingProvider implements IDbSettingProvider{
	
	@Override
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
		
		JdbcSetting js = new JdbcSetting();
		js.setAlias(alias);
		js.setDriver(dc.getDriverClassName());
		js.setPassword(password);
		js.setUrl(Stringer.print(dc.getUrl(),server,port,dbname));
		js.setUser(user);
		js.setDbType(dbType);
		js.setDbName(dbname);
		return js;
	}

	@Override
	public boolean support(String type){
		return "jdbc".equalsIgnoreCase(type);
	}

}
