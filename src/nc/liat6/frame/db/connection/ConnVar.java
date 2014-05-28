package nc.liat6.frame.db.connection;

import nc.liat6.frame.db.DbType;
import nc.liat6.frame.db.setting.ISetting;

/**
 * 连接变量
 * 
 * @author 6tail
 * 
 */
public class ConnVar{

	private int level = 0;

	/** 连接设置 */
	private ISetting setting;

	/** 定制连接 */
	private IConnection connection;

	/** 数据库类型 */
	private DbType dbType;

	/** 别名 */
	private String alias;

	public int getLevel(){
		return level;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public ISetting getSetting(){
		return setting;
	}

	public void setSetting(ISetting setting){
		this.setting = setting;
	}

	public IConnection getConnection(){
		return connection;
	}

	public void setConnection(IConnection connection){
		this.connection = connection;
	}

	public DbType getDbType(){
		return dbType;
	}

	public void setDbType(DbType dbType){
		this.dbType = dbType;
	}

	public String getAlias(){
		return alias;
	}

	public void setAlias(String alias){
		this.alias = alias;
	}

	public boolean equals(Object o){
		if(null == o){
			return false;
		}
		if(!(o instanceof ConnVar)){
			return false;
		}
		ConnVar cv = (ConnVar)o;
		if(null == cv.getAlias()){
			if(null == alias){
				return true;
			}else{
				return false;
			}
		}
		return cv.getAlias().equals(alias);
	}

}
