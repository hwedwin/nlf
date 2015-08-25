package nc.liat6.frame.db.sql.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.StatementAndResultSet;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;

/**
 * SQL执行模板超类
 * 
 * @author 6tail
 * 
 */
public abstract class SuperTemplate implements ITemplate{

  /** 缓存的上一条SQL语句 */
  protected String stackSql = null;
  /** 缓存的上一个Statement */
  protected PreparedStatement stackStatement = null;
  /** 当前连接变量 */
  protected ConnVar cv;
  /** 事务接口 */
  protected ITrans trans;
  protected List<StatementAndResultSet> sars = new ArrayList<StatementAndResultSet>();

  public ITrans getTrans(){
    return trans;
  }

  public void setTrans(ITrans trans){
    this.trans = trans;
  }

  public void setAlias(String alias){
    List<ConnVar> l = Context.get(Statics.CONNECTIONS);
    for(ConnVar n:l){
      if(n.getAlias().equals(alias)){
        cv = n;
        break;
      }
    }
  }
  
  protected List<Object> processParams(Object params){
    List<Object> pl = new ArrayList<Object>();
    if(null==params){
      return pl;
    }
    if(params.getClass().isArray()){
      Object[] l = (Object[])params;
      for(Object o:l){
        pl.add(o);
      }
    }else if(params instanceof Collection){
      Collection<?> l = (Collection<?>)params;
      for(Object o:l){
        pl.add(o);
      }
    }else{
      pl.add(params);
    }
    return pl;
  }

  /**
   * 参数的预处理
   * 
   * @param stmt PreparedStatement
   * @param params 参数
   * @throws SQLException
   */
  protected void processParams(PreparedStatement stmt,List<Object> params) throws SQLException{
    if(null==params){
      return;
    }
    for(int i=0,j=params.size();i<j;i++){
      Object p = params.get(i);
      if(p instanceof java.sql.Timestamp){
        stmt.setTimestamp(i+1,(java.sql.Timestamp)p);
      }else if(p instanceof java.sql.Date){
        stmt.setDate(i+1,(java.sql.Date)p);
      }else if(p instanceof java.util.Date){
        java.util.Date dd = (java.util.Date)p;
        stmt.setDate(i+1,new java.sql.Date(dd.getTime()));
      }else{
        stmt.setObject(i+1,p);
      }
    }
  }
  
  protected List<Object[]> objs(ResultSet rs) throws SQLException{
    List<Object[]> l = new ArrayList<Object[]>();
    while(rs.next()){
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      Object[] o = new Object[columnCount];
      for(int i = 0;i<columnCount;i++){
        o[i] = rs.getObject(i+1);
      }
      l.add(o);
    }
    return l;
  }
  
  protected List<Bean> beans(ResultSet rs) throws SQLException{
    List<Bean> l = new ArrayList<Bean>();
    while(rs.next()){
      ResultSetMetaData rsmd = rs.getMetaData();
      int columnCount = rsmd.getColumnCount();
      Bean o = new Bean();
      for(int i = 0;i<columnCount;i++){
        o.set(rsmd.getColumnLabel(i+1),rs.getObject(i+1));
      }
      l.add(o);
    }
    return l;
  }

  public void finalize(Statement stmt,ResultSet rs){
    if(null!=rs){
      try{
        rs.close();
      }catch(SQLException e){
      }
    }
    if(null!=stmt){
      try{
        stmt.close();
      }catch(SQLException e){
      }
    }
    rs = null;
    stmt = null;
  }

  public ConnVar getConnVar(){
    return cv;
  }
  
  public void finalizeAll(){
    for(StatementAndResultSet o:sars){
      finalize(o.getStatement(),o.getResultSet());
    }
  }
}