package nc.liat6.frame.db.custom.csv;

import nc.liat6.frame.db.setting.IDbSetting;

/**
 * CSV¡¨Ω”≈‰÷√
 * 
 * @author 6tail
 * 
 */
public class CsvSetting implements IDbSetting{

	private static final long serialVersionUID = 8889847573386316725L;
	private String type = "csv";
	private String alias;
	private String driver;
	private String dbType;
	private String dbName;

	public String getAlias(){
		return alias;
	}

	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getDriver(){
		return driver;
	}

	public void setDriver(String driver){
		this.driver = driver;
	}

	public String getType(){
		return type;
	}

	public String getDbType(){
		return dbType;
	}

	public void setDbType(String dbType){
		this.dbType = dbType;
	}

	public String getDbName(){
		return dbName;
	}

	public void setDbName(String dbName){
		this.dbName = dbName;
	}

	public void setType(String type){
		this.type = type;
	}

}
