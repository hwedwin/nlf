package nc.liat6.frame.db.plugin.impl;

import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.IOHelper;

/**
 * 自动关闭连接的查询器
 * 
 * @author 6tail
 *
 */
public class AutoCloseSelecter implements ISelecter{
  /** 事务接口 */
  protected ITrans t = null;
  /** 内嵌的查询器 */
  protected ISelecter selecter = null;

  public AutoCloseSelecter(ITrans t){
    this.t = t;
    selecter = t.getSelecter();
  }

  public String getSql(){
    return selecter.getSql();
  }

  public Object[] getParam(){
    return selecter.getParam();
  }

  public void setTemplate(ITemplate template){
    selecter.setTemplate(template);
  }

  public ITemplate getTemplate(){
    return selecter.getTemplate();
  }

  public ISelecter table(String tableName){
    selecter.table(tableName);
    return this;
  }

  public ISelecter column(String... column){
    selecter.column(column);
    return this;
  }

  public ISelecter where(String sql){
    selecter.where(sql);
    return this;
  }

  public ISelecter whereSql(String sql,Object[] values){
    selecter.whereSql(sql,values);
    return this;
  }

  public ISelecter where(String column,Object value){
    selecter.where(column,value);
    return this;
  }

  public ISelecter whereLike(String column,Object value){
    selecter.whereLike(column,value);
    return this;
  }

  public ISelecter whereLeftLike(String column,Object value){
    selecter.whereLeftLike(column,value);
    return this;
  }

  public ISelecter whereRightLike(String column,Object value){
    selecter.whereRightLike(column,value);
    return this;
  }

  public ISelecter whereNq(String column,Object value){
    selecter.whereNq(column,value);
    return this;
  }

  public ISelecter whereIn(String column,Object... value){
    selecter.whereIn(column,value);
    return this;
  }

  public ISelecter whereNotIn(String column,Object... value){
    selecter.whereNotIn(column,value);
    return this;
  }

  public ISelecter asc(String... column){
    selecter.asc(column);
    return this;
  }

  public ISelecter desc(String... column){
    selecter.desc(column);
    return this;
  }

  public List<Bean> select(){
    try{
      return selecter.select();
    }finally{
      //查询完成自动关闭
      IOHelper.closeQuietly(t);
    }
  }

  public PageData page(int pageNumber,int pageSize){
    try{
      return selecter.page(pageNumber,pageSize);
    }finally{
      //查询完成自动关闭
      IOHelper.closeQuietly(t);
    }
  }

  public Bean one(){
    try{
      return selecter.one();
    }finally{
      //查询完成自动关闭
      IOHelper.closeQuietly(t);
    }
  }

  public Iterator<Bean> iterator(){
    //注意，iterator操作不允许关闭连接
    return selecter.iterator();
  }
}