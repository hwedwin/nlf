package nc.liat6.frame.db.connection;

import java.io.Closeable;
import java.sql.Connection;

/**
 * 定制连接
 *
 * @author 6tail
 *
 */
public interface IConnection extends Closeable{

  ConnVar getConnVar();

  void setConnVar(ConnVar connVar);

  void commit();

  void rollback();
  
  void close();

  boolean isClosed();

  boolean isSupportsBatchUpdates();

  Connection getSqlConnection();
}