package nc.liat6.frame.db.setting.impl;

import nc.liat6.frame.db.setting.ConnType;
import nc.liat6.frame.db.setting.ISetting;

/**
 * PROXOOL¡¨Ω”≥ÿ≈‰÷√
 * 
 * @author 6tail
 * 
 */
public class ProxoolSetting implements ISetting{
	private static final long serialVersionUID = -1965324522415398901L;
	private String type = ConnType.PROXOOL;
	private String alias;
	private String url;
	private String user;
	private String password;
	private String driver;
	private String dbType;
	private String dbName;
	private int prototypeCount = -1;
	private int houseKeepingSleepTime = -1;
	private long maximumActiveTime = -1;
	private int maximumConnectionLifeTime = -1;
	private int maximumConnectionCount = -1;
	private int minimumConnectionCount = -1;
	private int simultaneousBuildThrottle = -1;
	private boolean testBeforeUse = true;
	private boolean testAfterUse = true;

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

	public int getPrototypeCount(){
		return prototypeCount;
	}

	public void setPrototypeCount(int prototypeCount){
		this.prototypeCount = prototypeCount;
	}

	public int getHouseKeepingSleepTime(){
		return houseKeepingSleepTime;
	}

	public void setHouseKeepingSleepTime(int houseKeepingSleepTime){
		this.houseKeepingSleepTime = houseKeepingSleepTime;
	}

	public long getMaximumActiveTime(){
		return maximumActiveTime;
	}

	public void setMaximumActiveTime(long maximumActiveTime){
		this.maximumActiveTime = maximumActiveTime;
	}

	public int getMaximumConnectionLifeTime(){
		return maximumConnectionLifeTime;
	}

	public void setMaximumConnectionLifeTime(int maximumConnectionLifeTime){
		this.maximumConnectionLifeTime = maximumConnectionLifeTime;
	}

	public int getMaximumConnectionCount(){
		return maximumConnectionCount;
	}

	public void setMaximumConnectionCount(int maximumConnectionCount){
		this.maximumConnectionCount = maximumConnectionCount;
	}

	public int getMinimumConnectionCount(){
		return minimumConnectionCount;
	}

	public void setMinimumConnectionCount(int minimumConnectionCount){
		this.minimumConnectionCount = minimumConnectionCount;
	}

	public int getSimultaneousBuildThrottle(){
		return simultaneousBuildThrottle;
	}

	public void setSimultaneousBuildThrottle(int simultaneousBuildThrottle){
		this.simultaneousBuildThrottle = simultaneousBuildThrottle;
	}

	public boolean isTestBeforeUse(){
		return testBeforeUse;
	}

	public void setTestBeforeUse(boolean testBeforeUse){
		this.testBeforeUse = testBeforeUse;
	}

	public boolean isTestAfterUse(){
		return testAfterUse;
	}

	public void setTestAfterUse(boolean testAfterUse){
		this.testAfterUse = testAfterUse;
	}

	public void setType(String type){
		this.type = type;
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

}
