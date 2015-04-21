package nc.liat6.frame.db.plugin.impl;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.util.IOHelper;

/**
 * 自动关闭连接的插入器
 * 
 * @author 6tail
 *
 */
public class AutoCloseInserter implements IInserter{
  /** 事务接口 */
  protected ITrans t = null;
  /** 内嵌的计数器 */
  protected IInserter inserter = null;

  public AutoCloseInserter(ITrans t){
    this.t = t;
    inserter = t.getInserter();
  }

  public String getSql(){
    return inserter.getSql();
  }

  public Object[] getParam(){
    return inserter.getParam();
  }

  public void setTemplate(ITemplate template){
    inserter.setTemplate(template);
  }

  public ITemplate getTemplate(){
    return inserter.getTemplate();
  }

  public IInserter table(String tableName){
    inserter.table(tableName);
    return this;
  }

  public IInserter set(String column,Object value){
    inserter.set(column,value);
    return this;
  }

  public IInserter setSql(String column,String valueSql){
    inserter.setSql(column,valueSql);
    return this;
  }

  public IInserter setSql(String column,String valueSql,Object[] values){
    inserter.setSql(column,valueSql,values);
    return this;
  }

  public int insert(){
    try{
      int n = inserter.insert();
      t.commit();
      return n;
    }finally{
      IOHelper.closeQuietly(t);
    }
  }

  public IInserter setBean(Bean bean){
    inserter.setBean(bean);
    return this;
  }
}