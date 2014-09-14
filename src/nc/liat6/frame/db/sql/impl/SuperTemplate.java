package nc.liat6.frame.db.sql.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.connection.ConnVar;
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

  /**
   * 参数的预处理
   * 
   * @param stmt PreparedStatement
   * @param params 参数，可以是数组，也可以是单值
   * @return 参数列表
   * @throws SQLException
   */
  protected List<Object> processParams(PreparedStatement stmt,Object params) throws SQLException{
    List<Object> pl = new ArrayList<Object>();
    if(null==params){
      return pl;
    }
    if(params instanceof Object[]){
      Object[] p = (Object[])params;
      for(int i = 0;i<p.length;i++){
        if(p[i] instanceof java.sql.Timestamp){
          stmt.setTimestamp(i+1,(java.sql.Timestamp)p[i]);
        }else if(p[i] instanceof java.sql.Date){
          stmt.setDate(i+1,(java.sql.Date)p[i]);
        }else if(p[i] instanceof java.util.Date){
          java.util.Date dd = (java.util.Date)p[i];
          stmt.setDate(i+1,new java.sql.Date(dd.getTime()));
        }else{
          stmt.setObject(i+1,p[i]);
        }
        pl.add(p[i]);
      }
    }else{
      if(params instanceof java.sql.Timestamp){
        stmt.setTimestamp(1,(java.sql.Timestamp)params);
      }else if(params instanceof java.sql.Date){
        stmt.setDate(1,(java.sql.Date)params);
      }else if(params instanceof java.util.Date){
        java.util.Date dd = (java.util.Date)params;
        stmt.setDate(1,new java.sql.Date(dd.getTime()));
      }else{
        stmt.setObject(1,params);
      }
      pl.add(params);
    }
    return pl;
  }

  /**
   * 善后处理
   * 
   * @param stmt
   * @param rs
   */
  protected void finalize(Statement stmt,ResultSet rs){
    if(null!=rs){
      try{
        rs.close();
      }catch(Exception e){
      }
    }
    if(null!=stmt){
      try{
        stmt.close();
      }catch(Exception e){
      }
    }
    rs = null;
    stmt = null;
  }

  public ConnVar getConnVar(){
    return cv;
  }
}
