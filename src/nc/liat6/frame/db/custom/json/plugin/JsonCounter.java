package nc.liat6.frame.db.custom.json.plugin;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.custom.bean.Table;
import nc.liat6.frame.db.custom.json.JsonCache;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

public class JsonCounter extends JsonExecuter implements ICounter{

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
		r.setOpStart("eq");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ICounter whereLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("like");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ICounter whereLeftLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("leftlike");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ICounter whereRightLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("rightlike");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ICounter whereNq(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("nq");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ICounter whereIn(String column,Object... value){
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

	public ICounter whereNotIn(String column,Object... value){
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

	public int count(){
		Table table = JsonCache.getTable(template.getConnVar().getAlias(),tableName);
		if(null==table){
			throw new DaoException(Stringer.print(L.get("sql.table_not_found")+"?.?",template.getConnVar().getAlias(),tableName));
		}
		List<Bean> l = table.getRows();
		List<Bean> nl = new ArrayList<Bean>();
		if(cols.size() > 0){
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
								if(v != null){
									continue outer;
								}
							}
							if(!r.getTag().equals(String.valueOf(v))){
								continue outer;
							}
						}else if("nq".equals(r.getOpStart())){
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
								if(v != null){
									continue outer;
								}
							}
							if(!r.getTag().contains("$$$" + String.valueOf(v) + "$$$")){
								continue outer;
							}
						}else if("notin".equals(r.getOpStart())){
							if(null == r.getTag()){
								if(v == null){
									continue outer;
								}
							}
							if(r.getTag().contains("$$$" + String.valueOf(v) + "$$$")){
								continue outer;
							}
						}else if("like".equals(r.getOpStart())){
							if(null == r.getTag()){
								if(v != null){
									continue outer;
								}
							}
							if(!String.valueOf(v).contains(r.getTag())){
								continue outer;
							}
						}else if("leftlike".equals(r.getOpStart())){
							if(null == r.getTag()){
								if(v != null){
									continue outer;
								}
							}
							if(!String.valueOf(v).startsWith(r.getTag())){
								continue outer;
							}
						}else if("rightlike".equals(r.getOpStart())){
							if(null == r.getTag()){
								if(v != null){
									continue outer;
								}
							}
							if(!String.valueOf(v).endsWith(r.getTag())){
								continue outer;
							}
						}
					}
				}
				nl.add(n);
			}
			reset();
			return nl.size();
		}
		reset();
		return l.size();
	}

	public void reset(){
		cols.clear();
		conds.clear();
	}

}
