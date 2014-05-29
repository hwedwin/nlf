package nc.liat6.frame.db.connection.impl;

import java.sql.Connection;
import java.sql.SQLException;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.DbConfigFactory;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.SqlConnection;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.impl.ProxoolSetting;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Reflector;

/**
 * PROXOOL连接变量提供器
 * @author 6tail
 *
 */
public class ProxoolConnVarProvider extends SuperConnVarProvider {
	
	private ProxoolSetting setting;
	
	private static final String DEFAULT_TEST_SQL = "SELECT 1";
	

	public IDbSetting getSetting() {
		return setting;
	}

	public void setSetting(IDbSetting setting) {
		this.setting = (ProxoolSetting)setting;
		super.registDriver(this.setting.getDriver());
	}

	public ConnVar getConnVar() {
		ConnVar cv = new ConnVar();
		cv.setDbType(setting.getDbType());
		cv.setAlias(setting.getAlias());
		DbConfig dc = DbConfigFactory.getDbConfig(setting.getDbType());
		String testSql = dc.getTestSql();
		if(null==testSql||"".equals(testSql.trim())){
			testSql = DEFAULT_TEST_SQL;
		}
		
		Object ds = Reflector.newInstance("org.logicalcobwebs.proxool.ProxoolDataSource");
		Reflector.execute(ds,"setDriver",new Class[]{String.class},new Object[]{setting.getDriver()});
		Reflector.execute(ds,"setDriverUrl",new Class[]{String.class},new Object[]{setting.getUrl()});
		Reflector.execute(ds,"setUser",new Class[]{String.class},new Object[]{setting.getUser()});
		Reflector.execute(ds,"setPassword",new Class[]{String.class},new Object[]{setting.getPassword()});
		Reflector.execute(ds,"setAlias",new Class[]{String.class},new Object[]{setting.getAlias()});
		Reflector.execute(ds,"setHouseKeepingTestSql",new Class[]{String.class},new Object[]{testSql});
		Reflector.execute(ds,"setTestBeforeUse",new Class[]{boolean.class},new Object[]{setting.isTestBeforeUse()});
		
		Reflector.execute(ds,"setTestAfterUse",new Class[]{boolean.class},new Object[]{setting.isTestAfterUse()});
		
		if(-1!=setting.getHouseKeepingSleepTime()){
			Reflector.execute(ds,"setHouseKeepingSleepTime",new Class[]{int.class},new Object[]{setting.getHouseKeepingSleepTime()});
		}
		if(-1!=setting.getMaximumActiveTime()){
			Reflector.execute(ds,"setMaximumActiveTime",new Class[]{long.class},new Object[]{setting.getMaximumActiveTime()});
		}
		if(-1!=setting.getMaximumConnectionCount()){
			Reflector.execute(ds,"setMaximumConnectionCount",new Class[]{int.class},new Object[]{setting.getMaximumConnectionCount()});
		}
		if(-1!=setting.getMaximumConnectionLifeTime()){
			Reflector.execute(ds,"setMaximumConnectionLifeTime",new Class[]{int.class},new Object[]{setting.getMaximumConnectionLifeTime()});
		}
		if(-1!=setting.getMinimumConnectionCount()){
			Reflector.execute(ds,"setMinimumConnectionCount",new Class[]{int.class},new Object[]{setting.getMinimumConnectionCount()});
		}
		if(-1!=setting.getPrototypeCount()){
			Reflector.execute(ds,"setPrototypeCount",new Class[]{int.class},new Object[]{setting.getPrototypeCount()});
		}
		if(-1!=setting.getSimultaneousBuildThrottle()){
			Reflector.execute(ds,"setSimultaneousBuildThrottle",new Class[]{int.class},new Object[]{setting.getSimultaneousBuildThrottle()});
		}
		
		Connection conn = (Connection)Reflector.execute(ds,"getConnection");
		try{
			conn.setAutoCommit(false);
		}catch(SQLException e){
			Logger.getLog().error(L.get(LocaleFactory.locale,"db.commit_not_support"),e);
		}
		SqlConnection sc = new SqlConnection();
		sc.setSqlConnection(conn);
		cv.setConnection(sc);
		cv.setSetting(setting);
		return cv;
	}
	
	@Override
	public boolean support(String connType){
		return "proxool".equalsIgnoreCase(connType);
	}

}
