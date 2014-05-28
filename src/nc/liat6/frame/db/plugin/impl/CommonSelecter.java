package nc.liat6.frame.db.plugin.impl;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.paging.PageData;

/**
 * 通用查询器
 * 
 * @author 6tail
 * 
 */
public class CommonSelecter extends SuperExecuter implements ISelecter{

	protected List<String> cols = new ArrayList<String>();
	protected List<String> tables = new ArrayList<String>();
	protected List<Rule> wheres = new ArrayList<Rule>();
	protected List<String> orders = new ArrayList<String>();

	public ISelecter table(String tableName){
		tables.add(tableName);
		return this;
	}

	public ISelecter where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("=");
		wheres.add(r);
		params.add(value);
		return this;
	}

	public ISelecter where(String sql){
		Rule r = new Rule();
		r.setColumn(sql);
		r.setOpStart("");
		r.setTag("");
		r.setOpEnd("");
		wheres.add(r);
		return this;
	}

	public ISelecter whereSql(String sql,Object[] values){
		Rule r = new Rule();
		r.setColumn(sql);
		r.setOpStart("");
		r.setTag("");
		r.setOpEnd("");
		wheres.add(r);
		for(Object v:values){
			params.add(v);
		}
		return this;
	}

	public ISelecter whereLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" like ");
		wheres.add(r);
		params.add("%" + value + "%");
		return this;
	}

	public ISelecter whereLeftLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" like ");
		wheres.add(r);
		params.add(value + "%");
		return this;
	}

	public ISelecter whereRightLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" like ");
		wheres.add(r);
		params.add("%" + value);
		return this;
	}

	public ISelecter whereIn(String column,Object... value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" in (");
		r.setOpEnd(")");
		StringBuilder tag = new StringBuilder();
		List<Object> l = objectsToList(value);
		for(Object o:l){
			tag.append(",?");
			params.add(o);
		}
		r.setTag(l.size()>0?tag.toString().substring(1):"");
		wheres.add(r);
		return this;
	}

	public ISelecter whereNotIn(String column,Object... value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" not in (");
		r.setOpEnd(")");
		StringBuilder tag = new StringBuilder();
		List<Object> l = objectsToList(value);
		for(Object o:l){
			tag.append(",?");
			params.add(o);
		}
		r.setTag(l.size()>0?tag.toString().substring(1):"");
		wheres.add(r);
		return this;
	}

	public ISelecter whereNq(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("!=");
		wheres.add(r);
		params.add(value);
		return this;
	}

	public ISelecter asc(String... column){
		for(String c:column){
			orders.add(c + " ASC");
		}
		return this;
	}

	public ISelecter desc(String... column){
		for(String c:column){
			orders.add(c + " DESC");
		}
		return this;
	}

	public ISelecter column(String... column){
		for(String c:column){
			cols.add(c);
		}
		return this;
	}

	public String getSql(){
		// 重置SQL
		super.resetSql();
		// 重新构造
		s.append(" SELECT");
		int l = cols.size();
		if(l > 0){
			for(int i = 0;i < l;i++){
				if(i>0){
					s.append(",");
				}
				s.append(" ");
				s.append(cols.get(i));
			}
		}else{
			s.append(" *");
		}
		s.append(" FROM");
		
		l = tables.size();
		for(int i = 0;i < l;i++){
			if(i>0){
				s.append(",");
			}
			s.append(" ");
			s.append(tables.get(i));
		}
		
		l = wheres.size();
		for(int i = 0;i < l;i++){
			if(i<1){
				s.append(" WHERE");
			}else{
				s.append(" AND");
			}
			s.append(" ");
			Rule r = wheres.get(i);
			s.append(r.getColumn());
			s.append(r.getOpStart());
			s.append(r.getTag());
			s.append(r.getOpEnd());
		}
		
		l = orders.size();
		for(int i = 0;i < l;i++){
			if(i<1){
				s.append(" ORDER BY");
			}else{
				s.append(",");
			}
			s.append(" ");
			s.append(orders.get(i));
		}
		return s.toString();
	}
	
	public void reset(){
		super.reset();
		cols.clear();
		tables.clear();
		wheres.clear();
		orders.clear();
	}

	public Bean one(){
		String sql = getSql();
		Bean o = template.oneEntity(sql,params.toArray());
		//重置
		reset();
		return o;
	}

	public List<Bean> select(){
		String sql = getSql();
		List<Bean> l = template.queryEntity(sql,params.toArray());
		//重置
		reset();
		return l;
	}

	public PageData page(int pageNumber,int pageSize){
		String sql = getSql();
		PageData pd = template.queryEntity(sql,pageNumber,pageSize,params.toArray());
		//重置
		reset();
		return pd;
	}

}
