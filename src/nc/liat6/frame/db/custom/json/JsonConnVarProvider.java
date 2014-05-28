package nc.liat6.frame.db.custom.json;

import nc.liat6.frame.db.DbType;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.impl.SuperConnVarProvider;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.ISetting;
import nc.liat6.frame.locale.L;

/**
 * JSON连接变量提供器
 * 
 * @author 6tail
 * 
 */
public class JsonConnVarProvider extends SuperConnVarProvider{

	/** JSON连接配置 */
	private JsonSetting setting;

	public ISetting getSetting(){
		return setting;
	}

	public void setSetting(ISetting setting){
		this.setting = (JsonSetting)setting;
		super.registDriver(this.setting.getDriver());
	}

	public ConnVar getConnVar(){
		ConnVar cv = new ConnVar();
		if(!DbType.JSON.toString().equalsIgnoreCase(setting.getDbType())){
			throw new DaoException(L.get("db.dbtype_not_support")+ setting.getDbType());
		}
		cv.setDbType(DbType.JSON);
		cv.setAlias(setting.getAlias());
		JsonConnection jc = new JsonConnection();
		jc.setConnVar(cv);
		cv.setConnection(jc);
		cv.setSetting(setting);
		return cv;
	}

}
