package nc.liat6.frame.db.sql.impl;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.entity.ResultSetIterator;
import nc.liat6.frame.db.entity.TResultSetIterator;
import nc.liat6.frame.db.entity.StatementAndResultSet;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.Stringer;

/**
 * SQL执行模板的通用实现
 * 
 * @author 6tail
 * 
 */
public class CommonTemplate extends SuperTemplate{

  private static ILog log = Logger.getLog();

  public List<Object[]> query(String sql){
    return query(sql,null);
  }

  public List<Object[]> query(String sql,Object param){
    flush();
    List<Object[]> l = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      l = new ArrayList<Object[]>();
      stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
      List<Object> pl = processParams(stmt,param);
      StringBuilder s = new StringBuilder();
      for(Object o:pl){
        s.append("\t");
        s.append(o);
        s.append("\r\n");
      }
      log.debug(Stringer.print("??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.query"),sql,L.get(LocaleFactory.locale,"sql.var"),s.toString()));
      rs = stmt.executeQuery();
      while(rs.next()){
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Object[] o = new Object[columnCount];
        for(int i = 0;i<columnCount;i++){
          o[i] = rs.getObject(i+1);
        }
        l.add(o);
      }
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
    return l;
  }

  public Object[] one(String sql){
    return one(sql,null);
  }

  public Object[] one(String sql,Object param){
    List<Object[]> l = query(sql,param);
    int n = l.size();
    if(n>1){
      throw new DaoException(L.get("sql.record_too_many"));
    }
    if(n<1){
      throw new DaoException(L.get("sql.record_not_found"));
    }
    return l.get(0);
  }

  public Bean oneEntity(String sql){
    return oneEntity(sql,null);
  }

  public Bean oneEntity(String sql,Object param){
    List<Bean> l = queryEntity(sql,param);
    int n = l.size();
    if(n>1){
      throw new DaoException(L.get("sql.record_too_many"));
    }
    if(n<1){
      throw new DaoException(L.get("sql.record_not_found"));
    }
    return l.get(0);
  }

  public List<Bean> queryEntity(String sql){
    return queryEntity(sql,null);
  }

  public List<Bean> queryEntity(String sql,Object param){
    flush();
    List<Bean> l = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      l = new ArrayList<Bean>();
      stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
      List<Object> pl = processParams(stmt,param);
      StringBuilder s = new StringBuilder();
      for(Object o:pl){
        s.append("\t");
        s.append(o);
        s.append("\r\n");
      }
      log.debug(Stringer.print("??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.query_entity"),sql,L.get(LocaleFactory.locale,"sql.var"),s.toString()));
      rs = stmt.executeQuery();
      while(rs.next()){
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Bean o = new Bean();
        for(int i = 0;i<columnCount;i++){
          o.set(rsmd.getColumnName(i+1),rs.getObject(i+1));
        }
        l.add(o);
      }
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
    return l;
  }

  public int update(String sql){
    return update(sql,null);
  }

  private int[] batchUpdate() throws SQLException{
    if(null==stackSql){
      return new int[]{};
    }
    stackSql = null;
    if(null==stackStatement){
      return new int[]{};
    }
    if(!cv.getConnection().isSupportsBatchUpdates()){
      return new int[]{};
    }
    if(!trans.isBatchEnabled()){
      return new int[]{};
    }
    try{
      return stackStatement.executeBatch();
    }finally{
      finalize(stackStatement,null);
    }
  }

  public int[] flush(){
    try{
      return batchUpdate();
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  /**
   * 更新，如果支持批处理，返回-1，如果不支持批处理，返回更新记录数
   */
  public int update(String sql,Object param){
    boolean sptBatch = cv.getConnection().isSupportsBatchUpdates()&&trans.isBatchEnabled();
    PreparedStatement stmt = null;
    try{
      // 如果支持批处理
      if(sptBatch){
        if(sql.equals(stackSql)){// 如果SQL语句一样，直接复用原来的Statement
          stmt = stackStatement;
        }else{// 如果SQL语句变了，提交上次的批处理，新建Statement
          flush();
          stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
          stackStatement = stmt;
          stackSql = sql;
        }
      }else{// 如果不支持批处理，新建Statement
        stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
        stackSql = null;
      }
      // 参数预处理
      List<Object> pl = processParams(stmt,param);
      // debug语句及参数
      StringBuilder s = new StringBuilder();
      for(Object o:pl){
        s.append("\t");
        s.append(o);
        s.append("\r\n");
      }
      log.debug(Stringer.print("??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.update"),sql,L.get(LocaleFactory.locale,"sql.var"),s.toString()));
      // 如果支持批处理，加入批处理
      if(sptBatch){
        stmt.addBatch();
        return -1;
      }
      // 如果不支持批处理，执行update
      return stmt.executeUpdate();
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      // 如果不支持批处理，直接善后
      if(!sptBatch){
        finalize(stmt,null);
      }
    }
  }

  public void call(String procName){
    flush();
    CallableStatement stmt = null;
    try{
      String sql = Stringer.print("{call ?()}",procName);
      log.debug(Stringer.print("??",L.get("sql.call_proc"),sql));
      stmt = cv.getConnection().getSqlConnection().prepareCall(sql);
      stmt.execute();
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,null);
    }
  }

  public void call(String procName,Object param){
    flush();
    CallableStatement stmt = null;
    try{
      Object[] params = null;
      if(null!=param){
        if(param instanceof Object[]){
          params = (Object[])param;
        }else{
          params = new Object[1];
          params[0] = param;
        }
      }
      StringBuilder s = new StringBuilder();
      s.append("{call ?(");
      for(int i = 0;i<params.length;i++){
        s.append("?");
        if(i<params.length-1){
          s.append(",");
        }
      }
      s.append(")}");
      String sql = Stringer.print(s.toString(),procName);
      stmt = cv.getConnection().getSqlConnection().prepareCall(sql);
      // 参数预处理
      List<Object> pl = processParams(stmt,param);
      // debug语句及参数
      StringBuilder sb = new StringBuilder();
      for(Object o:pl){
        sb.append("\t");
        sb.append(o);
        sb.append("\r\n");
      }
      log.debug(Stringer.print("??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.call_proc"),sql,L.get(LocaleFactory.locale,"sql.var"),sb.toString()));
      stmt.execute();
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,null);
    }
  }

  public int count(String sql){
    throw new DaoException(L.get("sql.count_not_support")+cv.getDbType());
  }

  public int count(String sql,Object param){
    throw new DaoException(L.get("sql.count_not_support")+cv.getDbType());
  }

  public PageData query(String sql,int pageNumber,int pageSize){
    throw new DaoException(L.get("sql.page_not_support")+cv.getDbType());
  }

  public PageData query(String sql,int pageNumber,int pageSize,Object param){
    throw new DaoException(L.get("sql.page_not_support")+cv.getDbType());
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize){
    throw new DaoException(L.get("sql.page_not_support")+cv.getDbType());
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize,Object param){
    throw new DaoException(L.get("sql.page_not_support")+cv.getDbType());
  }

  public Iterator<Bean> iterator(String sql){
    return iterator(sql,null);
  }

  public Iterator<Bean> iterator(String sql,Object param){
    flush();
    Iterator<Bean> l = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
      List<Object> pl = processParams(stmt,param);
      StringBuilder s = new StringBuilder();
      for(Object o:pl){
        s.append("\t");
        s.append(o);
        s.append("\r\n");
      }
      log.debug(Stringer.print("??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.query_entity"),sql,L.get(LocaleFactory.locale,"sql.var"),s.toString()));
      rs = stmt.executeQuery();
      l = new ResultSetIterator(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      if(null!=stmt&&null!=rs){
        sars.add(new StatementAndResultSet(stmt,rs));
      }
    }
    return l;
  }

  public <T>List<T> queryObject(String sql,Class<?> klass){
    return queryObject(sql,null,klass,null);
  }

  public <T>List<T> queryObject(String sql,Class<?> klass,IBeanRule rule){
    return queryObject(sql,null,klass,rule);
  }

  public <T>List<T> queryObject(String sql,Object param,Class<?> klass){
    return queryObject(sql,param,klass,null);
  }

  public <T>List<T> queryObject(String sql,Object param,Class<?> klass,IBeanRule rule){
    List<Bean> l = queryEntity(sql,param);
    List<T> lt = new ArrayList<T>(l.size());
    for(Bean o:l){
      T t = o.toObject(klass,rule);
      lt.add(t);
    }
    return lt;
  }

  public <T>T oneObject(String sql,Class<?> klass){
    return oneObject(sql,null,klass,null);
  }

  public <T>T oneObject(String sql,Class<?> klass,IBeanRule rule){
    return oneObject(sql,null,klass,rule);
  }

  public <T>T oneObject(String sql,Object param,Class<?> klass){
    return oneObject(sql,param,klass,null);
  }

  public <T>T oneObject(String sql,Object param,Class<?> klass,IBeanRule rule){
    return oneEntity(sql,param).toObject(klass,rule);
  }

  public PageData queryObject(String sql,int pageNumber,int pageSize,Class<?> klass){
    return queryObject(sql,pageNumber,pageSize,null,klass,null);
  }

  public PageData queryObject(String sql,int pageNumber,int pageSize,Class<?> klass,IBeanRule rule){
    return queryObject(sql,pageNumber,pageSize,null,klass,rule);
  }

  public PageData queryObject(String sql,int pageNumber,int pageSize,Object param,Class<?> klass){
    return queryObject(sql,pageNumber,pageSize,param,klass,null);
  }

  public PageData queryObject(String sql,int pageNumber,int pageSize,Object param,Class<?> klass,IBeanRule rule){
    PageData pd = queryEntity(sql,pageNumber,pageSize,param);
    int size = pd.getSize();
    List<Object> l = new ArrayList<Object>(size);
    for(int i=0,j=pd.getSize();i<j;i++){
      l.add(pd.getBean(i).toObject(klass,rule));
    }
    pd.setData(l);
    return pd;
  }

  public <T>Iterator<T> iterator(String sql,Class<?> klass){
    return iterator(sql,null,klass,null);
  }

  public <T>Iterator<T> iterator(String sql,Class<?> klass,IBeanRule rule){
    return iterator(sql,null,klass,rule);
  }

  public <T>Iterator<T> iterator(String sql,Object param,Class<?> klass){
    return iterator(sql,param,klass,null);
  }

  public <T>Iterator<T> iterator(String sql,Object param,Class<?> klass,IBeanRule rule){
    flush();
    Iterator<T> l = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(sql);
      List<Object> pl = processParams(stmt,param);
      StringBuilder s = new StringBuilder();
      for(Object o:pl){
        s.append("\t");
        s.append(o);
        s.append("\r\n");
      }
      log.debug(Stringer.print("??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.query_entity"),sql,L.get(LocaleFactory.locale,"sql.var"),s.toString()));
      rs = stmt.executeQuery();
      l = new TResultSetIterator<T>(rs,klass,rule);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      if(null!=stmt&&null!=rs){
        sars.add(new StatementAndResultSet(stmt,rs));
      }
    }
    return l;
  }
}