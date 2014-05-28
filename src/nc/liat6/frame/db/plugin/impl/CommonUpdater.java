package nc.liat6.frame.db.plugin.impl;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.plugin.Rule;

/**
 * 通用执行器
 * @author 6tail
 *
 */
public class CommonUpdater extends SuperExecuter implements IUpdater{

	protected List<Rule> cols = new ArrayList<Rule>();
	protected List<String> tables = new ArrayList<String>();
	protected List<Rule> wheres = new ArrayList<Rule>();
	protected List<Object> paramCols = new ArrayList<Object>();
	protected List<Object> paramWheres = new ArrayList<Object>();

	public IUpdater table(String tableName){
		tables.add(tableName);
		return this;
	}

	public IUpdater where(String sql){
		Rule r = new Rule();
		r.setColumn(sql);
		// 空字符串替换占位符
		r.setTag("");
		wheres.add(r);
		return this;
	}

	public IUpdater whereSql(String sql,Object[] values){
		Rule r = new Rule();
		r.setColumn(sql);
		// 空字符串替换占位符
		r.setTag("");
		wheres.add(r);
		for(Object v:values){
			paramWheres.add(v);
		}
		return this;
	}

	public IUpdater where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("=");
		wheres.add(r);
		paramWheres.add(value);
		return this;
	}

	public IUpdater set(String column,Object value){
		//如果有重复的，替换值
		for(int i=0;i<cols.size();i++){
			if(cols.get(i).getColumn().equalsIgnoreCase(column)){
				paramCols.set(i,value);
				return this;
			}
		}
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("=");
		cols.add(r);
		paramCols.add(value);
		return this;
	}

	public IUpdater set(String sql){
		Rule r = new Rule();
		r.setColumn(sql);
		// 空字符串替换占位符
		r.setTag("");
		cols.add(r);
		return this;
	}

	public IUpdater setSql(String sql,Object[] values){
		Rule r = new Rule();
		r.setColumn(sql);
		// 空字符串替换占位符
		r.setTag("");
		cols.add(r);
		for(Object v:values){
			paramCols.add(v);
		}
		return this;
	}
	
	public void reset(){
		super.reset();
		cols.clear();
		tables.clear();
		wheres.clear();
		paramCols.clear();
		paramWheres.clear();
	}

	public int update(){
		String sql = getSql();
		int n = template.update(sql,getParam());
		//重置
		reset();
		return n;
	}

	public String getSql(){
		//重置SQL
		super.resetSql();
		//重新构造
		s.append(" UPDATE");
		int l = tables.size();
		for(int i = 0;i < l;i++){
			if(i>0){
				s.append(",");
			}
			s.append(" ");
			s.append(tables.get(i));
		}
		s.append(" SET");
		
		l = cols.size();
		for(int i = 0;i < l;i++){
			if(i>0){
				s.append(",");
			}
			s.append(" ");
			Rule r = cols.get(i);
			s.append(r.getColumn());
			s.append(r.getOpStart());
			s.append(r.getTag());
			s.append(r.getOpEnd());
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

	public IUpdater whereLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" like ");
		wheres.add(r);
		paramWheres.add("%" + value + "%");
		return this;
	}

	public IUpdater whereLeftLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" like ");
		wheres.add(r);
		paramWheres.add(value + "%");
		return this;
	}

	public IUpdater whereRightLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" like ");
		wheres.add(r);
		paramWheres.add("%" + value);
		return this;
	}

	public IUpdater whereIn(String column,Object... value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" in (");
		r.setOpEnd(")");
		StringBuilder tag = new StringBuilder();
		List<Object> l = objectsToList(value);
		for(Object o:l){
			tag.append(",?");
			paramWheres.add(o);
		}
		r.setTag(l.size()>0?tag.toString().substring(1):"");
		wheres.add(r);
		return this;
	}

	public IUpdater whereNotIn(String column,Object... value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart(" not in (");
		r.setOpEnd(")");
		StringBuilder tag = new StringBuilder();
		List<Object> l = objectsToList(value);
		for(Object o:l){
			tag.append(",?");
			paramWheres.add(o);
		}
		r.setTag(l.size()>0?tag.toString().substring(1):"");
		wheres.add(r);
		return this;
	}

	public IUpdater whereNq(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("!=");
		wheres.add(r);
		paramWheres.add(value);
		return this;
	}
	
	/**
	 * 重写了父类的getParam方法
	 * @see SuperExecuter
	 */
	public Object[] getParam(){
		List<Object> l = new ArrayList<Object>();
		for(Object o:paramCols){
			l.add(o);
		}
		for(Object o:paramWheres){
			l.add(o);
		}
		return l.toArray();
	}
	
	public IUpdater setBean(Bean bean){
		for(String col:bean.keySet()){
			set(col,bean.get(col));
		}
		return this;
	}

}
