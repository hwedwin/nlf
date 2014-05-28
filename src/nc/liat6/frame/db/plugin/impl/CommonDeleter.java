package nc.liat6.frame.db.plugin.impl;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.Rule;

/**
 * 通用删除器
 * @author 6tail
 *
 */
public class CommonDeleter extends SuperExecuter implements IDeleter{

	private List<String> tables = new ArrayList<String>();
	protected List<Rule> wheres = new ArrayList<Rule>();

	public IDeleter table(String tableName){
		tables.add(tableName);
		return this;
	}

	public IDeleter where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("=");
		wheres.add(r);
		params.add(value);
		return this;
	}

	public IDeleter where(String sql){
		Rule r = new Rule();
		r.setColumn(sql);
		r.setOpStart("");
		r.setTag("");
		r.setOpEnd("");
		wheres.add(r);
		return this;
	}

	public IDeleter whereSql(String sql,Object[] values){
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
	
	public IDeleter whereIn(String column,Object... value){
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

	public IDeleter whereNotIn(String column,Object... value){
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
	
	public void reset(){
		super.reset();
		tables.clear();
		wheres.clear();
	}

	public int delete(){
		String sql = getSql();
		int n = template.update(sql,params.toArray());
		//重置
		reset();
		return n;
	}

	public String getSql(){
		//重置SQL
		super.resetSql();
		//重新构造
		s.append(" DELETE FROM");
		int l = tables.size();
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
		return s.toString();
	}

}
