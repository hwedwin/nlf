package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.BeanComparator;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.IOHelper;
import nc.liat6.frame.util.Stringer;

/**
 * CSV查询器
 *
 * @author 6tail
 *
 */
public class CsvSelecter extends CsvExecuter implements ISelecter{
  public ISelecter table(String tableName){
    initTable(tableName);
    return this;
  }

  public ISelecter column(String... column){
    for(String c:column){
      Rule rule = new Rule();
      rule.setColumn(c);
      cols.add(rule);
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
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public ISelecter whereLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("like");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public ISelecter whereLeftLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("left_like");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public ISelecter whereRightLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("right_like");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public ISelecter whereNq(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("!=");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public ISelecter whereIn(String column,Object... value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("in");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(objectsToList(value));
    return this;
  }

  public ISelecter whereNotIn(String column,Object... value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("not_in");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(objectsToList(value));
    return this;
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

  private boolean contains(List<Rule> l,String s){
    for(Rule n:l){
      if(n.getColumn().equalsIgnoreCase(s)){
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
      outer:for(int i = 1;i<cr.getLineCount();i++){
        String[] data = cr.getLine(i);
        Bean o = new Bean();
        for(int j = 0;j<head.length;j++){
          String s = head[j].toUpperCase();
          if(data.length>j){
            o.set(s,data[j]);
          }else{
            o.set(s,"");
          }
        }
        // 不满足条件的跳过，即不加入结果集
        for(int j = 0,n = wheres.size();j<n;j++){
          Rule r = wheres.get(j);
          // 操作类型
          String op = r.getOpStart();
          // 结果
          String v = o.getString(r.getColumn().toUpperCase(),"");
          // 参数
          String p = paramWheres.get(j)+"";
          if("=".equals(op)){
            if(!v.equals(p)){
              continue outer;
            }
          }else if("!=".equals(op)){
            if(v.equals(p)){
              continue outer;
            }
          }else if("like".equalsIgnoreCase(op)){
            if(v.indexOf(p)<0){
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
            List<?> in = (List<?>)paramWheres.get(j);
            boolean isIn = false;
            in:for(Object m:in){
              if(v.equals(m+"")){
                isIn = true;
                break in;
              }
            }
            if(!isIn){
              continue outer;
            }
          }else if("not_in".equalsIgnoreCase(op)){
            List<?> in = (List<?>)paramWheres.get(j);
            boolean isIn = false;
            in:for(Object m:in){
              if(v.equals(m+"")){
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
    }catch(IOException e){
      throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath(),e);
    }finally{
      IOHelper.closeQuietly(cr);
    }
    Collections.sort(l,new BeanComparator(BeanComparator.TYPE_MANU,orders));
    if(cols.size()>0){
      for(Bean o:l){
        List<String> removeCols = new ArrayList<String>();
        for(String k:o.keySet()){
          if(!contains(cols,k)){
            removeCols.add(k.toUpperCase());
          }
        }
        for(String s:removeCols){
          o.remove(s);
        }
      }
    }
    reset();
    return l;
  }

  public PageData page(int pageNumber,int pageSize){
    if(null==tableName){
      throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
    }
    if(pageNumber<1){
      pageNumber = 1;
    }
    List<Bean> l = select();
    int n = l.size();
    if(n<pageNumber*pageSize){
      pageNumber = (int)Math.ceil(n*1D/pageSize);
    }
    int fromIndex = (pageNumber-1)*pageSize;
    if(fromIndex<0){
      fromIndex = 0;
    }
    int toIndex = fromIndex+pageSize;
    if(toIndex>n){
      toIndex = n;
    }
    List<Bean> rl = l.subList(fromIndex,toIndex);
    reset();
    PageData pd = new PageData();
    pd.setPageNumber(pageNumber);
    pd.setPageSize(pageSize);
    pd.setRecordCount(n);
    pd.setData(rl);
    return pd;
  }

  public Bean one(){
    List<Bean> l = select();
    int n = l.size();
    if(n>1){
      throw new DaoException(L.get("sql.record_too_many"));
    }
    if(n<1){
      throw new DaoException(L.get("sql.record_not_found"));
    }
    return l.get(0);
  }

  public Iterator<Bean> iterator(){
    return select().iterator();
  }

  public <T>List<T> select(Class<?> klass){
    return select(klass,null);
  }

  public <T>List<T> select(Class<?> klass,IBeanRule rule){
    List<Bean> l = select();
    List<T> tl = new ArrayList<T>(l.size());
    for(Bean o:l){
      T t = o.toObject(klass,rule);
      tl.add(t);
    }
    return tl;
  }

  public PageData page(int pageNumber,int pageSize,Class<?> klass){
    return page(pageNumber,pageSize,klass,null);
  }

  public PageData page(int pageNumber,int pageSize,Class<?> klass,IBeanRule rule){
    PageData pd = page(pageNumber,pageSize);
    int size = pd.getSize();
    List<Object> l = new ArrayList<Object>(size);
    for(int i=0,j=pd.getSize();i<j;i++){
      l.add(pd.getBean(i).toObject(klass,rule));
    }
    pd.setData(l);
    return pd;
  }

  public <T>T one(Class<?> klass){
    return one(klass,null);
  }

  public <T>T one(Class<?> klass,IBeanRule rule){
    return one().toObject(klass,rule);
  }

  public <T>Iterator<T> iterator(Class<?> klass){
    return iterator(klass,null);
  }

  public <T>Iterator<T> iterator(Class<?> klass,IBeanRule rule){
    List<Bean> l = select();
    List<T> lo = new ArrayList<T>();
    for(Bean o:l){
      T t = o.toObject(klass,rule);
      lo.add(t);
    }
    return lo.iterator();
  }
}