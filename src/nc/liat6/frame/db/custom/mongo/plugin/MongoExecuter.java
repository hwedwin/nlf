package nc.liat6.frame.db.custom.mongo.plugin;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.IMongo;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.IExecuter;
import nc.liat6.frame.db.sql.ITemplate;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * MONGOÖ´ÐÐÆ÷
 * @author 6tail
 *
 */
public abstract class MongoExecuter implements IMongo,IExecuter{

	protected String tableName;
	protected ITemplate template;
	protected List<Object> params = new ArrayList<Object>();

	public Object[] getParam(){
		return params.toArray();
	}
	
	public String getSql(){
		return null;
	}

	public void setTemplate(ITemplate template){
		this.template = template;
	}

	public ITemplate getTemplate(){
		return template;
	}

	protected void initTable(String tableName){
		this.tableName = tableName;
	}
	
	protected DBObject bean2DBObject(Bean bean){
		if(null==bean){
			return null;
		}
		DBObject o = new BasicDBObject();
		for(String k:bean.keySet()){
			Object param = bean.get(k);
			if(null!=param&&param instanceof Bean){
				o.put(k,bean2DBObject((Bean)param));
			}else{
				o.put(k,param);
			}
		}
		return o;
	}
}
