package nc.liat6.frame.db.custom.json;

import nc.liat6.frame.db.setting.ConnType;
import nc.liat6.frame.db.setting.ISetting;

/**
 * JSON¡¨Ω”≈‰÷√
 * 
 * @author 6tail
 * 
 */
public class JsonSetting implements ISetting{

	private static final long serialVersionUID = 8889847513386316725L;
	private String type = ConnType.JSON;
	private String alias;
	private String url;
	private String user;
	private String password;
	private String driver;
	private String dbType;
	private String dbName;

	public String getAlias(){
		return alias;
	}

	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getUrl(){
		return url;
	}

	public void setUrl(String url){
		this.url = url;
	}

	public String getUser(){
		return user;
	}

	public void setUser(String user){
		this.user = user;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
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
