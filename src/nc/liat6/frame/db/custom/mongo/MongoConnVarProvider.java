package nc.liat6.frame.db.custom.mongo;

import java.net.UnknownHostException;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.impl.SuperConnVarProvider;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.Stringer;
import com.mongodb.Mongo;

/**
 * MONGO连接变量提供器
 * 
 * @author 6tail
 * 
 */
public class MongoConnVarProvider extends SuperConnVarProvider{

	public ConnVar getConnVar(){
	  MongoSetting setting = (MongoSetting)this.setting;
		ConnVar cv = new ConnVar();
		cv.setDbType(setting.getDbType());
		cv.setAlias(setting.getAlias());
		MongoConnection jc = new MongoConnection();
		String ip = Stringer.cut(setting.getUrl(),"",":").trim();
		int port = Integer.parseInt(Stringer.cut(setting.getUrl(),":").trim());
		try{
			Mongo mongo = new Mongo(ip,port);
			mongo.getMongoOptions().setAutoConnectRetry(true);
			jc.setDb(mongo.getDB(setting.getDbName()));
		}catch(UnknownHostException e){
			throw new DaoException(L.get("db.exception.dao")+":"+setting.getUrl(),e);
		}
		jc.setConnVar(cv);
		cv.setConnection(jc);
		cv.setSetting(setting);
		return cv;
	}

	@Override
	public boolean support(String connType){
		return "mongo".equalsIgnoreCase(connType);
	}

}
