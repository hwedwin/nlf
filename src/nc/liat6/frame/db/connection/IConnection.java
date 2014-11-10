package nc.liat6.frame.db.connection;

import java.sql.Connection;

/**
 * 定制连接
 *
 * @author 6tail
 *
 */
public interface IConnection{

  ConnVar getConnVar();

  void setConnVar(ConnVar connVar);

  void commit();

  void close();

  void rollback();

  boolean isClosed();

  boolean isSupportsBatchUpdates();

  Connection getSqlConnection();
}