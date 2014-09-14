package nc.liat6.frame.db.custom.sqlserver;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.IDbConfigProvider;

/**
 * SqlServer数据库配置提供器
 * @author 6tail
 *
 */
public class SqlserverConfigProvider implements IDbConfigProvider{

	@Override
	public DbConfig getDbConfig(){
		DbConfig dc = new DbConfig();
		dc.setDbType("SQLSERVER");
		dc.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dc.setUrl("jdbc:sqlserver://?:?;DatabaseName=?");
		dc.setSuperInterfaceName(ISqlserver.class.getName());
		dc.setTestSql("SELECT 1");
		return dc;
	}

}
