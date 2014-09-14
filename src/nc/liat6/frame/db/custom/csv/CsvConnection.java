package nc.liat6.frame.db.custom.csv;

import java.sql.Connection;
import nc.liat6.frame.db.connection.SuperConnection;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;

/**
 * CSV数据库连接
 * 
 * @author 6tail
 * 
 */
public class CsvConnection extends SuperConnection{

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
