package nc.liat6.frame.db.custom.json.plugin;

import nc.liat6.frame.db.custom.bean.Table;
import nc.liat6.frame.db.custom.json.JsonCache;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

public class JsonInserter extends JsonExecuter implements IInserter{

	private Bean row = new Bean();
	
	public IInserter table(String tableName){
		initTable(tableName);
		return this;
	}

	public IInserter set(String column,Object value){
		row.set(column,value);
		return this;
	}

	public IInserter setSql(String column,String valueSql){
		Logger.getLog().warn(Stringer.print("??=?",L.get(LocaleFactory.locale,"sql.insert_not_support"),column,valueSql));
		return this;
	}

	public IInserter setSql(String column,String valueSql,Object[] values){
		Logger.getLog().warn(Stringer.print("??=?",L.get(LocaleFactory.locale,"sql.insert_not_support"),column,valueSql));
		return this;
	}
	
	public void reset(){
		row.clear();
	}

	public int insert(){
		Table table = JsonCache.getTable(template.getConnVar().getAlias(),tableName);
		if(null==table){
			throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
		}
		Bean nrow = new Bean();
		for(String k:row.keySet()){
			nrow.set(k,row.get(k));
		}
		table.addRow(nrow);
		reset();
		return 1;
	}

	public IInserter setBean(Bean bean){
		for(String col:bean.keySet()){
			set(col,bean.get(col));
		}
		return this;
	}

}
