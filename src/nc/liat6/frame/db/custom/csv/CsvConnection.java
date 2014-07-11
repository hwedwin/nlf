package nc.liat6.frame.db.custom.csv;

import java.sql.Connection;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.IConnection;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;

/**
 * CSV数据库连接
 * 
 * @author 6tail
 * 
 */
public class CsvConnection implements IConnection{

  /** 连接变量 */
  private ConnVar connVar;

  public ConnVar getConnVar(){
    return connVar;
  }

  public void setConnVar(ConnVar connVar){
    this.connVar = connVar;
  }

  public void close(){}

  public void rollback(){}

  public Connection getSqlConnection(){
    throw new DaoException(L.get("sql.conn_not_support"));
  }

  public boolean isSupportsBatchUpdates(){
    return false;
  }

  public void commit(){}

  public boolean isClosed(){
    return false;
  }
}
