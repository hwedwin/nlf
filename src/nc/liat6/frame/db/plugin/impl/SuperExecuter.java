package nc.liat6.frame.db.plugin.impl;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.db.plugin.IExecuter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.db.sql.ITemplate;

/**
 * 执行器超类
 * 
 * @author 6tail
 * 
 */
public abstract class SuperExecuter implements IExecuter{

  /** 执行模板 */
  protected ITemplate template;
  /** SQL语句 */
  protected StringBuffer sql = new StringBuffer();
  /** 列绑定变量 */
  protected List<Object> paramCols = new ArrayList<Object>();
  /** where绑定变量 */
  protected List<Object> paramWheres = new ArrayList<Object>();
  /** 列 */
  protected List<Rule> cols = new ArrayList<Rule>();
  /** 表 */
  protected List<String> tables = new ArrayList<String>();
  /** 条件 */
  protected List<Rule> wheres = new ArrayList<Rule>();
  /** 排序 */
  protected List<String> orders = new ArrayList<String>();

  public SuperExecuter(){}

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

  public ITemplate getTemplate(){
    return template;
  }

  public void setTemplate(ITemplate template){
    this.template = template;
  }

  /**
   * 全部重置
   */
  protected void reset(){
    resetSql();
    paramCols.clear();
    paramWheres.clear();
    cols.clear();
    tables.clear();
    wheres.clear();
    orders.clear();
  }

  protected void resetSql(){
    if(null==sql){
      sql = new StringBuffer();
    }else{
      sql.delete(0,sql.length());
    }
  }

  protected String getWhereSql(){
    StringBuffer sql = new StringBuffer();
    int l = wheres.size();
    for(int i = 0;i<l;i++){
      if(i<1){
        sql.append(" WHERE");
      }else{
        sql.append(" AND");
      }
      sql.append(" ");
      Rule r = wheres.get(i);
      sql.append(r.getColumn());
      sql.append(r.getOpStart());
      sql.append(r.getTag());
      sql.append(r.getOpEnd());
    }
    return sql.toString();
  }

  /**
   * 可变长参数转List，Object数组也会转入
   * 
   * @param value Object或者Object数组
   * @return List
   */
  protected List<Object> objectsToList(Object... value){
    List<Object> l = new ArrayList<Object>();
    for(int i = 0;i<value.length;i++){
      if(value[i] instanceof Object[]){
        Object[] ps = (Object[])value[i];
        for(Object p:ps){
          l.add(p);
        }
      }else{
        l.add(value[i]);
      }
    }
    return l;
  }

  public IExecuter whereSql(String sql,Object[] values){
    Rule r = new Rule();
    r.setColumn(sql);
    r.setOpStart("");
    r.setTag("");
    r.setOpEnd("");
    wheres.add(r);
    for(Object v:values){
      paramWheres.add(v);
    }
    return this;
  }

  protected IExecuter whereIn(String column,Object... value){
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
  
  protected IExecuter whereNotIn(String column,Object... value){
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
}