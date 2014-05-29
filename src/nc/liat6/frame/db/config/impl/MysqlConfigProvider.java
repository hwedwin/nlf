package nc.liat6.frame.db.config.impl;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.IDbConfigProvider;
import nc.liat6.frame.db.config.IMysql;

/**
 * MySql数据库配置提供器
 * @author 6tail
 *
 */
public class MysqlConfigProvider implements IDbConfigProvider{

	@Override
	public DbConfig getDbConfig(){
		DbConfig dc = new DbConfig();
		dc.setDbType("MYSQL");
		dc.setDriverClassName("org.gjt.mm.mysql.Driver");
		dc.setUrl("jdbc:mysql://?:?/?");
		dc.setSuperInterfaceName(IMysql.class.getName());
		dc.setTestSql("SELECT 1");
		return dc;
	}

}
