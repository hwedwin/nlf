package nc.liat6.frame.db.plugin.impl;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.util.IOHelper;

/**
 * 自动关闭连接的更新器
 * 
 * @author 6tail
 *
 */
public class AutoCloseUpdater implements IUpdater{
  /** 事务接口 */
  protected ITrans t = null;
  /** 内嵌的更新器 */
  protected IUpdater updater = null;

  public AutoCloseUpdater(ITrans t){
    this.t = t;
    updater = t.getUpdater();
  }

  public String getSql(){
    return updater.getSql();
  }

  public Object[] getParam(){
    return updater.getParam();
  }

  public void setTemplate(ITemplate template){
    updater.setTemplate(template);
  }

  public ITemplate getTemplate(){
    return updater.getTemplate();
  }

  public IUpdater table(String tableName){
    updater.table(tableName);
    return this;
  }

  public IUpdater where(String sql){
    updater.where(sql);
    return this;
  }

  public IUpdater whereSql(String sql,Object[] values){
    updater.whereSql(sql,values);
    return this;
  }

  public IUpdater where(String column,Object value){
    updater.where(column,value);
    return this;
  }

  public IUpdater whereLike(String column,Object value){
    updater.whereLike(column,value);
    return this;
  }

  public IUpdater whereLeftLike(String column,Object value){
    updater.whereLeftLike(column,value);
    return this;
  }

  public IUpdater whereRightLike(String column,Object value){
    updater.whereRightLike(column,value);
    return this;
  }

  public IUpdater whereNq(String column,Object value){
    updater.whereNq(column,value);
    return this;
  }

  public IUpdater whereIn(String column,Object... value){
    updater.whereIn(column,value);
    return this;
  }

  public IUpdater whereNotIn(String column,Object... value){
    updater.whereNotIn(column,value);
    return this;
  }

  public IUpdater set(String column,Object value){
    updater.set(column,value);
    return this;
  }

  public IUpdater set(String sql){
    updater.set(sql);
    return this;
  }

  public IUpdater setSql(String sql,Object[] values){
    updater.setSql(sql,values);
    return this;
  }

  public int update(){
    try{
      int n = updater.update();
      t.commit();
      return n;
    }finally{
      IOHelper.closeQuietly(t);
    }
  }

  public IUpdater setBean(Bean bean){
    updater.setBean(bean);
    return this;
  }
}