package nc.liat6.frame.db.custom.oracle;

import nc.liat6.frame.db.config.DbConfig;
import nc.liat6.frame.db.config.IDbConfigProvider;

/**
 * Oracle数据库配置提供器
 * @author 6tail
 *
 */
public class OracleConfigProvider implements IDbConfigProvider{

	@Override
	public DbConfig getDbConfig(){
		DbConfig dc = new DbConfig();
		dc.setDbType("ORACLE");
		dc.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dc.setUrl("jdbc:oracle:thin:@?:?:?");
		dc.setSuperInterfaceName(IOracle.class.getName());
		dc.setTestSql("SELECT 1 FROM DUAL");
		return dc;
	}

}
