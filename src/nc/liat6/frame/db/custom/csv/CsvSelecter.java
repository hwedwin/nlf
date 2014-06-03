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
 * CSV查询器
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
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("like");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		params.add(value);
		return this;
	}

	public ISelecter whereLeftLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("left_like");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		params.add(value);
		return this;
	}

	public ISelecter whereRightLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("right_like");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		params.add(value);
		return this;
	}

	public ISelecter whereNq(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("!=");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		params.add(value);
		return this;
	}

	public ISelecter whereIn(String column,Object... value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("in");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		List<Object> l = objectsToList(value);
		params.add(l);
		return this;
	}

	public ISelecter whereNotIn(String column,Object... value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("not_in");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		List<Object> l = objectsToList(value);
		params.add(l);
		return this;
	}

	public ISelecter asc(String... column){
		for(String c:column){
			orders.add(c + ":ASC");
		}
		return this;
	}

	public ISelecter desc(String... column){
		for(String c:column){
			orders.add(c + ":DESC");
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
		File file = getTableFile();
		CSVFileReader cr = new CSVFileReader(file);
		List<Bean> l = new ArrayList<Bean>();
		try{
			String[] head = readHead(cr,file);
			outer:for(int i = 1;i < cr.getLineCount();i++){
				String[] data = cr.getLine(i);
				Bean o = new Bean();
				for(int j = 0;j < head.length;j++){
					String s = head[j].toUpperCase();
					if(data.length >= j){
						o.set(s,data[j]);
					}else{
						o.set(s,"");
					}
				}
				// 不满足条件的跳过，即不加入结果集
				for(int j = 0;j < conds.size();j++){
					Rule r = conds.get(j);
					// 操作类型
					String op = r.getOpStart();
					// 结果
					String v = o.getString(r.getColumn().toUpperCase(),"");
					// 参数
					String p = params.get(j) + "";
					if("=".equals(op)){
						if(!v.equals(p)){
							continue outer;
						}
					}else if("!=".equals(op)){
						if(v.equals(p)){
							continue outer;
						}
					}else if("like".equalsIgnoreCase(op)){
						if(v.indexOf(p) < 0){
							continue outer;
						}
					}else if("left_like".equalsIgnoreCase(op)){
						if(!v.startsWith(p)){
							continue outer;
						}
					}else if("right_like".equalsIgnoreCase(op)){
						if(!v.endsWith(p)){
							continue outer;
						}
					}else if("in".equalsIgnoreCase(op)){
						List<?> in = (List<?>)params.get(j);
						boolean isIn = false;
						in:for(Object m:in){
							if(v.equals(m + "")){
								isIn = true;
								break in;
							}
						}
						if(!isIn){
							continue outer;
						}
					}else if("not_in".equalsIgnoreCase(op)){
						List<?> in = (List<?>)params.get(j);
						boolean isIn = false;
						in:for(Object m:in){
							if(v.equals(m + "")){
								isIn = true;
								break in;
							}
						}
						if(isIn){
							continue outer;
						}
					}
				}
				l.add(o);
			}
			cr.close();
		}catch(IOException e){
			throw new DaoException(L.get("sql.file_read_error") + file.getAbsolutePath(),e);
		}finally{
			try{
				cr.close();
			}catch(IOException e){}
		}

		Collections.sort(l,new BeanComparator(BeanComparator.TYPE_MANU,orders));
		if(cols.size() > 0){
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
		int toIndex = fromIndex + pageSize;
		if(toIndex > l.size()){
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
