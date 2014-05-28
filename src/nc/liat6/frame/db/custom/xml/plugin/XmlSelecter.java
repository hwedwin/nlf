package nc.liat6.frame.db.custom.xml.plugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nc.liat6.frame.db.custom.bean.Table;
import nc.liat6.frame.db.custom.xml.XmlCache;
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
 * JSON²éÑ¯Æ÷
 * 
 * @author 6tail
 * 
 */
public class XmlSelecter extends XmlExecuter implements ISelecter{

	protected List<String> cols = new ArrayList<String>();
	protected List<String> orders = new ArrayList<String>();
	protected List<Rule> conds = new ArrayList<Rule>();
	private int order = BeanComparator.TYPE_DEFAULT;

	public ISelecter table(String tableName){
		initTable(tableName);
		return this;
	}

	public ISelecter column(String... column){
		for(String c:column){
			cols.add(c.toUpperCase());
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
		r.setColumn(column.toUpperCase());
		r.setOpStart("eq");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ISelecter whereLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column.toUpperCase());
		r.setOpStart("like");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ISelecter whereLeftLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column.toUpperCase());
		r.setOpStart("leftlike");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ISelecter whereRightLike(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column.toUpperCase());
		r.setOpStart("rightlike");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ISelecter whereNq(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column.toUpperCase());
		r.setOpStart("nq");
		r.setOpEnd("");
		r.setTag(String.valueOf(value));
		conds.add(r);
		return this;
	}

	public ISelecter whereIn(String column,Object... value){
		StringBuffer s = new StringBuffer();
		s.append("$$$");
		for(Object c:value){
			s.append(c);
			s.append("$$$");
		}
		Rule r = new Rule();
		r.setColumn(column.toUpperCase());
		r.setOpStart("in");
		r.setOpEnd("");
		r.setTag(s.toString());
		conds.add(r);
		return this;
	}

	public ISelecter whereNotIn(String column,Object... value){
		StringBuffer s = new StringBuffer();
		s.append("$$$");
		for(Object c:value){
			s.append(c);
			s.append("$$$");
		}
		Rule r = new Rule();
		r.setColumn(column.toUpperCase());
		r.setOpStart("notin");
		r.setOpEnd("");
		r.setTag(s.toString());
		conds.add(r);
		return this;
	}

	public ISelecter asc(String... column){
		order = BeanComparator.TYPE_ASC;
		for(String c:column){
			orders.add(c.toUpperCase());
		}
		return this;
	}

	public ISelecter desc(String... column){
		order = BeanComparator.TYPE_DESC;
		for(String c:column){
			orders.add(c.toUpperCase());
		}
		return this;
	}

	public List<Bean> select(){
		Table table = XmlCache.getTable(template.getConnVar().getAlias(),tableName);
		if(null==table){
			throw new DaoException(Stringer.print(L.get("sql.table_not_found")+"?.?",template.getConnVar().getAlias(),tableName));
		}
		List<Bean> l = table.getRows();
		if(BeanComparator.TYPE_DEFAULT != order){
			Collections.sort(l,new BeanComparator(order,orders));
		}
		List<Bean> nl = new ArrayList<Bean>();

		outer:for(Bean m:l){
			for(String k:m.keySet()){
				Object v = m.get(k);

				for(Rule r:conds){
					if(!r.getColumn().equalsIgnoreCase(k)){
						continue;
					}
					String param = r.getTag();
					if("eq".equals(r.getOpStart())){
						if(null == param&&null!=v){
							continue outer;
						}
						if(!param.equals(String.valueOf(v))){
							continue outer;
						}
					}else if("nq".equals(r.getOpStart())){
						if(null == param&&null==v){
							continue outer;
						}
						if(param.equals(String.valueOf(v))){
							continue outer;
						}
					}else if("in".equals(r.getOpStart())){
						if(null == param&&null!=v){
							continue outer;
						}
						if(!param.contains("$$$" + String.valueOf(v) + "$$$")){
							continue outer;
						}
					}else if("notin".equals(r.getOpStart())){
						if(null == param&&null==v){
							continue outer;
						}
						if(param.contains("$$$" + String.valueOf(v) + "$$$")){
							continue outer;
						}
					}else if("like".equals(r.getOpStart())){
						if(null == param&&null!=v){
							continue outer;
						}
						if(!String.valueOf(v).contains(param)){
							continue outer;
						}
					}else if("leftlike".equals(r.getOpStart())){
						if(null == param&&null!=v){
							continue outer;
						}
						if(!String.valueOf(v).startsWith(param)){
							continue outer;
						}
					}else if("rightlike".equals(r.getOpStart())){
						if(null == param&&null!=v){
							continue outer;
						}
						if(!String.valueOf(v).endsWith(param)){
							continue outer;
						}
					}
				}
			}
			if(cols.size() > 0){
				Bean n = new Bean();
				for(String k:m.keySet()){
					String ku = k.toUpperCase();
					if(cols.contains(ku)){
						n.set(ku,m.get(k));
					}
				}
				nl.add(n);
			}else{
				nl.add(m);
			}
		}
		reset();
		return nl;
	}

	public void reset(){
		cols.clear();
		conds.clear();
		orders.clear();
		order = BeanComparator.TYPE_DEFAULT;
	}

	public PageData page(int pageNumber,int pageSize){
		List<Bean> l = select();
		PageData pd = new PageData();
		pd.setPageNumber(pageNumber);
		pd.setPageSize(pageSize);
		int from = (pageNumber - 1) * pageSize;
		if(from < 0||from>=l.size()){
			pd.setData(new ArrayList<Bean>());
			pd.setRecordCount(0);
			return pd;
		}
		int to = pageNumber * pageSize;
		if(to > l.size()){
			to = l.size();
		}
		pd.setRecordCount(l.size());
		pd.setData(l.subList(from,to));
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
