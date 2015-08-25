package nc.liat6.frame.db.custom.sqlserver;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.sql.impl.CommonTemplate;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.Stringer;

/**
 * SQL执行模板的SqlServer实现
 * 
 * @author 6tail
 * 
 */
public class SqlserverTemplate extends CommonTemplate implements ISqlserver{
  private static ILog log = Logger.getLog();

  public int count(String sql,Object param){
    flush();
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String nsql = sql;
    String upsql = sql.toUpperCase();
    int index = upsql.indexOf(" ORDER BY ");
    if(index>-1){
      nsql = sql.substring(0,index);
    }
    nsql = "SELECT COUNT(*) FROM ("+nsql+") NLFTABLE_";
    List<Object> pl = processParams(param);
    log.debug(Stringer.print("??\r\n??",L.get(LocaleFactory.locale,"sql.count"),nsql,L.get(LocaleFactory.locale,"sql.var"),pl));
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      rs.next();
      return rs.getInt(1);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public int count(String sql){
    return count(sql,null);
  }

  public PageData query(String sql,int pageNumber,int pageSize){
    return query(sql,pageNumber,pageSize,null);
  }

  public PageData query(String sql,int pageNumber,int pageSize,Object param){
    flush();
    PageData d = new PageData();
    d.setRecordCount(count(sql,param));
    d.setPageSize(pageSize);
    int pageCount = d.getPageCount();
    d.setPageNumber(pageNumber>pageCount?pageCount:pageNumber);
    String nsql = null;
    String upsql = sql.toUpperCase();
    int index = upsql.indexOf("SELECT ");
    if(index>-1){
      nsql = sql.substring(index+"SELECT ".length());
    }
    nsql = "SELECT TOP "+(d.getPageNumber()*d.getPageSize())+" "+nsql;
    List<Object> pl = processParams(param);
    log.debug(Stringer.print("??\r\n??\r\n??\r\n??",L.get(LocaleFactory.locale,"sql.query_page"),nsql,L.get(LocaleFactory.locale,"sql.query_page_num"),pageNumber,L.get(LocaleFactory.locale,"sql.query_page_size"),pageSize,L.get(LocaleFactory.locale,"sql.var"),pl));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Object[]> l = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      rs.absolute((d.getPageNumber()-1)*d.getPageSize());
      l = objs(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
    d.setData(l);
    return d;
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize){
    return queryEntity(sql,pageNumber,pageSize,null);
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize,Object param){
    flush();
    PageData d = new PageData();
    d.setRecordCount(count(sql,param));
    d.setPageSize(pageSize);
    int pageCount = d.getPageCount();
    d.setPageNumber(pageNumber>pageCount?pageCount:pageNumber);
    String nsql = null;
    String upsql = sql.toUpperCase();
    int index = upsql.indexOf("SELECT ");
    if(index>-1){
      nsql = sql.substring(index+"SELECT ".length());
    }
    nsql = "SELECT TOP "+(d.getPageNumber()*d.getPageSize())+" "+nsql;
    List<Object> pl = processParams(param);
    log.debug(Stringer.print("??\r\n??",L.get(LocaleFactory.locale,"sql.query_entity"),sql,L.get(LocaleFactory.locale,"sql.var"),pl));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<Bean> l = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      rs.absolute((d.getPageNumber()-1)*d.getPageSize());
      l = beans(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
    d.setData(l);
    return d;
  }

  public List<Object[]> top(String sql,Object param,int n){
    flush();
    String nsql = null;
    String upsql = sql.toUpperCase();
    int index = upsql.indexOf("SELECT ");
    if(index>-1){
      nsql = sql.substring(index+"SELECT ".length());
    }
    nsql = "SELECT TOP "+n+" "+nsql;
    List<Object> pl = processParams(param);
    log.debug(Stringer.print("??\r\n??",L.get(LocaleFactory.locale,"sql.query_top"),nsql,L.get(LocaleFactory.locale,"sql.var"),pl));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      return objs(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public List<Bean> topEntity(String sql,Object param,int n){
    flush();
    String nsql = null;
    String upsql = sql.toUpperCase();
    int index = upsql.indexOf("SELECT ");
    if(index>-1){
      nsql = sql.substring(index+"SELECT ".length());
    }
    nsql = "SELECT TOP "+n+" "+nsql;
    List<Object> pl = processParams(param);
    log.debug(Stringer.print("??\r\n??",L.get(LocaleFactory.locale,"sql.query_top"),nsql,L.get(LocaleFactory.locale,"sql.var"),pl));
    PreparedStatement stmt = null;
    ResultSet rs = null;
    try{
      stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
      processParams(stmt,pl);
      rs = stmt.executeQuery();
      return beans(rs);
    }catch(SQLException e){
      throw new DaoException(e);
    }finally{
      finalize(stmt,rs);
    }
  }

  public <T>List<T> topObject(String sql,Object param,int n,Class<?> klass,IBeanRule rule){
    List<Bean> l = topEntity(sql,param,n);
    List<T> lt = new ArrayList<T>(l.size());
    for(Bean o:l){
      T t = o.toObject(klass,rule);
      lt.add(t);
    }
    return lt;
  }
}