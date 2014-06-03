package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.BeanComparator;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.Stringer;

/**
 * CSV²éÑ¯Æ÷
 * 
 * @author 6tail
 * 
 */
public class CsvSelecter extends CsvExecuter implements ISelecter{

	protected List<String> cols = new ArrayList<String>();
	protected List<String> orders = new ArrayList<String>();
	protected List<Rule> conds = new ArrayList<Rule>();

	public ISelecter table(String tableName){
		initTable(tableName);
		return this;
	}

	public ISelecter column(String... column){
		for(String c:column){
			cols.add(c);
		}
		return this;
	}

	public ISelecter where(String sql){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public ISelecter whereSql(String sql,Object[] values){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public ISelecter where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("=");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		params.add(value);
		return this;
	}

	public ISelecter whereLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLike"));
		return where(column,value);
	}

	public ISelecter whereLeftLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLeftLike"));
		return where(column,value);
	}

	public ISelecter whereRightLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereRightLike"));
		return where(column,value);
	}

	public ISelecter whereNq(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNq"));
		return where(column,value);
	}

	public ISelecter whereIn(String column,Object... value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereIn"));
		return where(column,value);
	}

	public ISelecter whereNotIn(String column,Object... value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNotIn"));
		return where(column,value);
	}

	public ISelecter asc(String... column){
		for(String c:column){
			orders.add(c+":ASC");
		}
		return this;
	}

	public ISelecter desc(String... column){
		for(String c:column){
			orders.add(c+":DESC");
		}
		return this;
	}
	
	private boolean contains(List<String> l,String s){
		for(String n:l){
			if(n.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}

	public List<Bean> select(){
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
		
		List<Bean> l = new ArrayList<Bean>();
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
				l.add(o);
			}
		}catch(IOException e){
			throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath(),e);
		}
		
		Collections.sort(l,new BeanComparator(BeanComparator.TYPE_MANU,orders));
		if(cols.size()>0){
			for(Bean o:l){
				for(String k:o.keySet()){
					if(!contains(cols,k)){
						o.remove(k.toUpperCase());
					}
				}
			}
		}
		reset();
		return l;
	}

	public void reset(){
		cols.clear();
		conds.clear();
		orders.clear();
		params.clear();
	}

	public PageData page(int pageNumber,int pageSize){
		if(null == tableName){
			throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
		}
		List<Bean> l = select();
		int fromIndex = (pageNumber - 1) * pageSize;
		int toIndex = fromIndex+pageSize;
		if(toIndex>l.size()){
			toIndex = l.size();
		}
		List<Bean> rl = l.subList(fromIndex,toIndex);
		reset();
		PageData pd = new PageData();
		pd.setPageNumber(pageNumber);
		pd.setPageSize(pageSize);
		pd.setRecordCount(rl.size());
		pd.setData(rl);
		return pd;
	}

	public Bean one(){
		List<Bean> l = select();
		if(l.size() > 1){
			throw new DaoException(L.get("sql.record_too_many"));
		}
		if(l.size() < 1){
			throw new DaoException(L.get("sql.record_not_found"));
		}
		return l.get(0);
	}

}
