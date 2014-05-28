package nc.liat6.frame.db.custom.xml.plugin;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.custom.bean.Table;
import nc.liat6.frame.db.custom.xml.XmlCache;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

public class XmlUpdater extends XmlExecuter implements IUpdater{

	private Bean row = new Bean();
	protected List<Rule> conds = new ArrayList<Rule>();

	public IUpdater table(String tableName){
		initTable(tableName);
		return this;
	}

	public IUpdater where(String sql){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public IUpdater whereSql(String sql,Object[] values){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public IUpdater where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("eq");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public IUpdater whereLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("like");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public IUpdater whereLeftLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("leftlike");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public IUpdater whereRightLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("rightlike");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public IUpdater whereNq(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("nq");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public IUpdater whereIn(String column,Object... value){
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

	public IUpdater whereNotIn(String column,Object... value){
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

	public IUpdater set(String column,Object value){
		row.set(column,value);
		return this;
	}

	public IUpdater set(String sql){
		Logger.getLog().warn(Stringer.print(L.get("sql.update_not_support")+"?",sql));
		return this;
	}

	public IUpdater setSql(String sql,Object[] values){
		Logger.getLog().warn(Stringer.print(L.get("sql.update_not_support")+"?",sql));
		return this;
	}

	public int update(){
		Table table = XmlCache.getTable(template.getConnVar().getAlias(),tableName);
		if(null==table){
			throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
		}
		List<Bean> l = table.getRows();
		int count = 0;
		outer:for(Bean m:l){
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

				count++;
				for(String key:row.keySet()){
					m.set(key,row.get(key));
				}
			}
		}
		reset();
		return count;
	}

	public void reset(){
		row.clear();
		conds.clear();
	}
	
	public IUpdater setBean(Bean bean){
		for(String col:bean.keySet()){
			set(col,bean.get(col));
		}
		return this;
	}

}
