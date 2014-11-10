package nc.liat6.frame.db.custom.csv;

import java.util.List;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.paging.PageData;

/**
 * CSV执行模板
 *
 * @author 6tail
 *
 */
public class CsvTemplate implements ITemplate,ICsv{

  /** 当前连接变量 */
  protected ConnVar cv;
  /** 事务接口 */
  protected ITrans trans;

  public void setTrans(ITrans t){
    trans = t;
  }

  public ITrans getTrans(){
    return trans;
  }

  public Object[] one(String sql){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public Object[] one(String sql,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public List<Object[]> query(String sql){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public List<Object[]> query(String sql,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public List<Bean> queryEntity(String sql){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public List<Bean> queryEntity(String sql,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public Bean oneEntity(String sql){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public Bean oneEntity(String sql,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public int count(String sql){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public int count(String sql,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public int update(String sql){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public int update(String sql,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public PageData query(String sql,int pageNumber,int pageSize){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public PageData query(String sql,int pageNumber,int pageSize,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public PageData queryEntity(String sql,int pageNumber,int pageSize,Object param){
    throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
  }

  public ConnVar getConnVar(){
    return cv;
  }

  public int[] flush(){
    return new int[]{};
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

  public void call(String procName,Object param){
    throw new DaoException(L.get("sql.proc_not_support")+cv.getDbType());
  }

  public void call(String procName){
    throw new DaoException(L.get("sql.proc_not_support")+cv.getDbType());
  }
}
