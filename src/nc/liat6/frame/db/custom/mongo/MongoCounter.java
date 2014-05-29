package nc.liat6.frame.db.custom.mongo;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * MONGO¼ÆÊýÆ÷
 * 
 * @author 6tail
 * 
 */
public class MongoCounter extends MongoExecuter implements ICounter{

	protected List<String> cols = new ArrayList<String>();
	protected List<Rule> conds = new ArrayList<Rule>();

	public ICounter table(String tableName){
		initTable(tableName);
		return this;
	}

	public ICounter column(String... column){
		for(String c:column){
			cols.add(c);
		}
		return this;
	}

	public ICounter where(String sql){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public ICounter whereSql(String sql,Object[] values){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public ICounter where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		params.add(value);
		return this;
	}

	public ICounter whereLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLike"));
		return where(column,value);
	}

	public ICounter whereLeftLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLeftLike"));
		return where(column,value);
	}

	public ICounter whereRightLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereRightLike"));
		return where(column,value);
	}

	public ICounter whereNq(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNq"));
		return where(column,value);
	}

	public ICounter whereIn(String column,Object... value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereIn"));
		return where(column,value);
	}

	public ICounter whereNotIn(String column,Object... value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNotIn"));
		return where(column,value);
	}

	public void reset(){
		cols.clear();
		conds.clear();
		params.clear();
	}

	public int count(){
		if(null == tableName){
			throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
		}
		MongoConnection conn = (MongoConnection)template.getConnVar().getConnection();
		DBObject ref = new BasicDBObject();
		DBObject keys = new BasicDBObject();
		
		for(int i=0;i<cols.size();i++){
			keys.put(cols.get(i),1);
		}
		
		for(int i=0;i<conds.size();i++){
			Rule r = conds.get(i);
			Object v = params.get(i);
			ref.put(r.getColumn(),v);
		}
		
		DBCursor cur = conn.getDb().getCollection(tableName).find(ref,keys);
		return cur.count();
	}

}
