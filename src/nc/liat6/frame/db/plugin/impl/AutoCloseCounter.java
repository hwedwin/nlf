package nc.liat6.frame.db.plugin.impl;

import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.util.IOHelper;

/**
 * 自动关闭连接的计数器
 * 
 * @author 6tail
 *
 */
public class AutoCloseCounter implements ICounter{
  /** 事务接口 */
  protected ITrans t = null;
  /** 内嵌的查询器 */
  protected ICounter counter = null;

  public AutoCloseCounter(ITrans t){
    this.t = t;
    counter = t.getCounter();
  }

  public String getSql(){
    return counter.getSql();
  }

  public Object[] getParam(){
    return counter.getParam();
  }

  public void setTemplate(ITemplate template){
    counter.setTemplate(template);
  }

  public ITemplate getTemplate(){
    return counter.getTemplate();
  }

  public ICounter table(String tableName){
    return counter.table(tableName);
  }

  public ICounter column(String... column){
    counter.column(column);
    return this;
  }

  public ICounter where(String sql){
    counter.where(sql);
    return this;
  }

  public ICounter whereSql(String sql,Object[] values){
    counter.whereSql(sql,values);
    return this;
  }

  public ICounter where(String column,Object value){
    counter.where(column,value);
    return this;
  }

  public ICounter whereLike(String column,Object value){
    counter.whereLike(column,value);
    return this;
  }

  public ICounter whereLeftLike(String column,Object value){
    counter.whereLeftLike(column,value);
    return this;
  }

  public ICounter whereRightLike(String column,Object value){
    counter.whereRightLike(column,value);
    return this;
  }

  public ICounter whereNq(String column,Object value){
    counter.whereNq(column,value);
    return this;
  }

  public ICounter whereIn(String column,Object... value){
    counter.whereIn(column,value);
    return this;
  }

  public ICounter whereNotIn(String column,Object... value){
    counter.whereNotIn(column,value);
    return this;
  }

  public int count(){
    try{
      return counter.count();
    }finally{
      IOHelper.closeQuietly(t);
    }
  }
}