package nc.liat6.frame.db.custom.xml;

import nc.liat6.frame.db.DbType;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.impl.SuperConnVarProvider;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.ISetting;
import nc.liat6.frame.locale.L;

/**
 * XML连接变量提供器
 * 
 * @author 6tail
 * 
 */
public class XmlConnVarProvider extends SuperConnVarProvider{

	/** XML连接配置 */
	private XmlSetting setting;

	public ISetting getSetting(){
		return setting;
	}

	public void setSetting(ISetting setting){
		this.setting = (XmlSetting)setting;
		super.registDriver(this.setting.getDriver());
	}

	public ConnVar getConnVar(){
		ConnVar cv = new ConnVar();
		if(!DbType.XML.toString().equalsIgnoreCase(setting.getDbType())){
			throw new DaoException(L.get("db.dbtype_not_support")+ setting.getDbType());
		}
		cv.setDbType(DbType.XML);
		cv.setAlias(setting.getAlias());
		XmlConnection jc = new XmlConnection();
		jc.setConnVar(cv);
		cv.setConnection(jc);
		cv.setSetting(setting);
		return cv;
	}

}
