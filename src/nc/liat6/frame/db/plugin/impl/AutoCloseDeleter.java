package nc.liat6.frame.db.plugin.impl;

import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.util.IOHelper;

/**
 * 自动关闭连接的删除器
 * 
 * @author 6tail
 *
 */
public class AutoCloseDeleter implements IDeleter{
  /** 事务接口 */
  protected ITrans t = null;
  /** 内嵌的删除器 */
  protected IDeleter deleter = null;

  public AutoCloseDeleter(ITrans t){
    this.t = t;
    deleter = t.getDeleter();
  }

  public String getSql(){
    return deleter.getSql();
  }

  public Object[] getParam(){
    return deleter.getParam();
  }

  public void setTemplate(ITemplate template){
    deleter.setTemplate(template);
  }

  public ITemplate getTemplate(){
    return deleter.getTemplate();
  }

  public IDeleter table(String tableName){
    deleter.table(tableName);
    return this;
  }

  public IDeleter where(String sql){
    deleter.where(sql);
    return this;
  }

  public IDeleter whereSql(String sql,Object[] values){
    deleter.whereSql(sql,values);
    return this;
  }

  public IDeleter where(String column,Object value){
    deleter.where(column,value);
    return this;
  }

  public IDeleter whereIn(String column,Object... value){
    deleter.whereIn(column,value);
    return this;
  }

  public IDeleter whereNotIn(String column,Object... value){
    deleter.whereNotIn(column,value);
    return this;
  }

  public int delete(){
    try{
      int n = deleter.delete();
      t.commit();
      return n;
    }finally{
      IOHelper.closeQuietly(t);
    }
  }
}