package nc.liat6.frame.db.plugin.impl;

import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.sql.ITemplate;

/**
 * 通用计数器
 * 
 * @author 6tail
 * 
 */
public class CommonCounter extends SuperExecuter implements ICounter{

  private ISelecter selecter;

  public ICounter table(String tableName){
    selecter.table(tableName);
    return this;
  }

  public ICounter where(String column,Object value){
    selecter.where(column,value);
    return this;
  }

  public ICounter column(String... column){
    selecter.column(column);
    return this;
  }

  public int count(){
    String sql = getSql();
    return template.count(sql,selecter.getParam());
  }

  public String getSql(){
    return selecter.getSql();
  }

  public ICounter whereLike(String column,Object value){
    selecter.whereLike(column,value);
    return this;
  }

  public ICounter whereLeftLike(String column,Object value){
    selecter.whereLeftLike(column,value);
    return this;
  }

  public ICounter whereRightLike(String column,Object value){
    selecter.whereRightLike(column,value);
    return this;
  }

  public ICounter whereNq(String column,Object value){
    selecter.whereNq(column,value);
    return this;
  }

  public ICounter whereIn(String column,Object... value){
    selecter.whereIn(column,value);
    return this;
  }

  public ICounter whereNotIn(String column,Object... value){
    selecter.whereNotIn(column,value);
    return this;
  }

  public ICounter where(String sql){
    selecter.where(sql);
    return this;
  }

  public ICounter whereSql(String sql,Object[] values){
    selecter.whereSql(sql,values);
    return this;
  }

  public void setTemplate(ITemplate template){
    super.setTemplate(template);
    selecter = new CommonSelecter();
    selecter.setTemplate(template);
  }
}
