package nc.liat6.frame.db.plugin.impl;

import java.util.Iterator;
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

  public ISelecter table(String tableName){
    tables.add(tableName);
    return this;
  }

  public ISelecter where(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    if(null != value){
      r.setOpStart("=");
      paramWheres.add(value);
    }else{
      r.setOpStart(" IS NULL");
      r.setTag("");
    }
    wheres.add(r);
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
    super.whereSql(sql,values);
    return this;
  }

  public ISelecter whereLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart(" like ");
    wheres.add(r);
    paramWheres.add("%"+value+"%");
    return this;
  }

  public ISelecter whereLeftLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart(" like ");
    wheres.add(r);
    paramWheres.add(value+"%");
    return this;
  }

  public ISelecter whereRightLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart(" like ");
    wheres.add(r);
    paramWheres.add("%"+value);
    return this;
  }

  public ISelecter whereIn(String column,Object... value){
    super.whereIn(column,value);
    return this;
  }

  public ISelecter whereNotIn(String column,Object... value){
    super.whereNotIn(column,value);
    return this;
  }

  public ISelecter whereNq(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    if(null != value){
      r.setOpStart("!=");
      paramWheres.add(value);
    }else{
      r.setOpStart(" IS NOT NULL");
      r.setTag("");
    }
    wheres.add(r);
    return this;
  }

  public ISelecter asc(String... column){
    for(String c:column){
      orders.add(c+" ASC");
    }
    return this;
  }

  public ISelecter desc(String... column){
    for(String c:column){
      orders.add(c+" DESC");
    }
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

  public String getSql(){
    // 重置SQL
    resetSql();
    // 重新构造
    sql.append(" SELECT");
    int l = cols.size();
    if(l>0){
      for(int i = 0;i<l;i++){
        if(i>0){
          sql.append(",");
        }
        sql.append(" ");
        sql.append(cols.get(i).getColumn());
      }
    }else{
      sql.append(" *");
    }
    sql.append(" FROM");
    l = tables.size();
    for(int i = 0;i<l;i++){
      if(i>0){
        sql.append(",");
      }
      sql.append(" ");
      sql.append(tables.get(i));
    }
    sql.append(getWhereSql());
    l = orders.size();
    for(int i = 0;i<l;i++){
      if(i<1){
        sql.append(" ORDER BY");
      }else{
        sql.append(",");
      }
      sql.append(" ");
      sql.append(orders.get(i));
    }
    return sql.toString();
  }

  public Bean one(){
    String sql = getSql();
    Bean o = template.oneEntity(sql,getParam());
    reset();
    return o;
  }

  public List<Bean> select(){
    String sql = getSql();
    List<Bean> l = template.queryEntity(sql,getParam());
    reset();
    return l;
  }

  public PageData page(int pageNumber,int pageSize){
    String sql = getSql();
    PageData pd = template.queryEntity(sql,pageNumber,pageSize,getParam());
    reset();
    return pd;
  }

  public Iterator<Bean> iterator(){
    String sql = getSql();
    Iterator<Bean> l = template.iterator(sql,getParam());
    reset();
    return l;
  }
}