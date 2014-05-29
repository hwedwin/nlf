package nc.liat6.frame.db.plugin.impl.sqlserver;

import nc.liat6.frame.db.config.ISqlserver;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.db.plugin.impl.CommonSelecter;

/**
 * SQLSERVERÊý¾Ý¿â²éÑ¯Æ÷
 * @author 6tail
 *
 */
public class SqlserverSelecter extends CommonSelecter implements ISqlserver{

	public ISelecter where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		if(null!=value){
			r.setOpStart("=");
			params.add(value);
		}else{
			r.setOpStart(" IS NULL");
			r.setTag("");
		}
		wheres.add(r);
		return this;
	}
	
	public ISelecter whereNq(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		if(null!=value){
			r.setOpStart("!=");
			params.add(value);
		}else{
			r.setOpStart(" IS NOT NULL");
			r.setTag("");
		}
		wheres.add(r);
		
		return this;
	}

}
