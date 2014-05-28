package nc.liat6.frame.db.plugin.impl;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.db.plugin.Rule;

/**
 * 通用插入器
 * @author 6tail
 *
 */
public class CommonInserter extends SuperExecuter implements IInserter{

	private List<Rule> cols = new ArrayList<Rule>();
	private List<String> tables = new ArrayList<String>();

	public IInserter table(String tableName){
		tables.add(tableName);
		return this;
	}

	public IInserter set(String column,Object value){
		//如果有重复的，替换值
		for(int i=0;i<cols.size();i++){
			if(cols.get(i).getColumn().equalsIgnoreCase(column)){
				params.set(i,value);
				return this;
			}
		}
		Rule r = new Rule();
		r.setColumn(column);
		cols.add(r);
		params.add(value);
		return this;
	}

	public IInserter setSql(String column,String valueSql){
		Rule r = new Rule();
		r.setColumn(column);
		r.setTag(valueSql);
		cols.add(r);
		return this;
	}

	public IInserter setSql(String column,String valueSql,Object[] values){
		Rule r = new Rule();
		r.setColumn(column);
		r.setTag(valueSql);
		cols.add(r);
		for(Object v:values){
			params.add(v);
		}
		return this;
	}
	
	public void reset(){
		super.reset();
		cols.clear();
		tables.clear();
	}

	public int insert(){
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
		s.append(" INSERT INTO");
		int l = tables.size();
		for(int i = 0;i < l;i++){
			if(i>0){
				s.append(",");
			}
			s.append(" ");
			s.append(tables.get(i));
		}
		
		l = cols.size();
		for(int i = 0;i < l;i++){
			if(i<1){
				s.append("(");
			}else{
				s.append(",");
			}
			s.append(" ");
			Rule r = cols.get(i);
			s.append(r.getColumn());
			if(i>l-2){
				s.append(")");
			}
		}
		
		s.append(" VALUES (");
		for(int i = 0;i < l;i++){
			if(i>0){
				s.append(",");
			}
			s.append(" ");
			Rule r = cols.get(i);
			s.append(r.getTag());
		}
		s.append(")");
		return s.toString();
	}

	public IInserter setBean(Bean bean){
		for(String col:bean.keySet()){
			set(col,bean.get(col));
		}
		return this;
	}

}
