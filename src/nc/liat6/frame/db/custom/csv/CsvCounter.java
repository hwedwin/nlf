package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * CSV¼ÆÊýÆ÷
 * 
 * @author 6tail
 * 
 */
public class CsvCounter extends CsvExecuter implements ICounter{

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
		r.setOpStart("=");
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
		if(null == tableName){
			throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
		}
		File file = getTableFile();
		CSVFileReader cr = new CSVFileReader(file);
		String[] head = null;
		try{
			if(cr.getLineCount()>0){
				head = cr.getLine(0);
			}
		}catch(IOException e){
			throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath(),e);
		}
		if(null==head){
			throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath());
		}
		
		int count = 0;
		try{
			outer:for(int i=1;i<cr.getLineCount();i++){
				String[] data = cr.getLine(i);
				Bean o = new Bean();
				for(int j=0;j<head.length;j++){
					String s = head[j].toUpperCase();
					if(data.length>=j){
						o.set(s,data[j]);
					}else{
						o.set(s,"");
					}
				}
				for(int j=0;j<conds.size();j++){
					Rule r = conds.get(j);
					if("=".equals(r.getOpStart())){
						if(!o.getString(r.getColumn().toUpperCase(),"").equals(""+params.get(j))){
							continue outer;
						}
					}
				}
				count++;
			}
		}catch(IOException e){
			throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath(),e);
		}
		reset();
		return count;
	}

}
