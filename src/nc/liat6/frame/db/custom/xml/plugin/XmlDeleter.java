package nc.liat6.frame.db.custom.xml.plugin;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.custom.bean.Table;
import nc.liat6.frame.db.custom.xml.XmlCache;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

public class XmlDeleter extends XmlExecuter implements IDeleter{

	protected List<Rule> conds = new ArrayList<Rule>();

	public IDeleter table(String tableName){
		initTable(tableName);
		return this;
	}

	public IDeleter where(String sql){
		Logger.getLog().warn(Stringer.print("??",L.get("sql.cond_not_support"),sql));
		return this;
	}

	public IDeleter whereSql(String sql,Object[] values){
		Logger.getLog().warn(Stringer.print("??",L.get("sql.cond_not_support"),sql));
		return this;
	}

	public IDeleter where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("eq");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public IDeleter whereIn(String column,Object... value){
		StringBuffer s = new StringBuffer();
		s.append("$$$");
		for(Object c:value){
			s.append(c);
			s.append("$$$");
		}
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("in");
		r.setOpEnd("");
		r.setTag(s.toString());
		conds.add(r);
		return this;
	}

	public IDeleter whereNotIn(String column,Object... value){
		StringBuffer s = new StringBuffer();
		s.append("$$$");
		for(Object c:value){
			s.append(c);
			s.append("$$$");
		}
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("notin");
		r.setOpEnd("");
		r.setTag(s.toString());
		conds.add(r);
		return this;
	}

	public int delete(){
		Table table = XmlCache.getTable(template.getConnVar().getAlias(),tableName);
		if(null == table){
			throw new DaoException(Stringer.print(L.get("sql.table_not_found")+"?.?",template.getConnVar().getAlias(),tableName));
		}
		List<Bean> l = table.getRows();
		List<Bean> nl = new ArrayList<Bean>();

		if(conds.size() > 0){
			outer:for(Bean m:l){
				Bean n = new Bean();
				for(String k:m.keySet()){
					Object v = m.get(k);

					for(Rule r:conds){
						if(!r.getColumn().equalsIgnoreCase(k)){
							continue;
						}
						if("eq".equals(r.getOpStart())){
							if(null == r.getTag()){
								if(v == null){
									continue outer;
								}
							}
							if(r.getTag().equals(String.valueOf(v))){
								continue outer;
							}
						}else if("in".equals(r.getOpStart())){
							if(null == r.getTag()){
								if(v == null){
									continue outer;
								}
							}
							if(r.getTag().contains("$$$" + String.valueOf(v) + "$$$")){
								continue outer;
							}
						}else if("notin".equals(r.getOpStart())){
							if(null == r.getTag()){
								if(v != null){
									continue outer;
								}
							}
							if(!r.getTag().contains("$$$" + String.valueOf(v) + "$$$")){
								continue outer;
							}
						}
					}
					n.set(k.toUpperCase(),v);
				}
				nl.add(n);
			}
		}
		reset();
		table.setRows(nl);
		return l.size() - nl.size();
	}

	public void reset(){
		conds.clear();
	}

}
