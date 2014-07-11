package nc.liat6.frame.db.connection;

import java.sql.Connection;
import java.sql.SQLException;
import nc.liat6.frame.db.exception.DaoException;

public class SqlConnection implements IConnection{

  private ConnVar connVar;
  private Connection conn;

  public ConnVar getConnVar(){
    return connVar;
  }

  public void setConnVar(ConnVar connVar){
    this.connVar = connVar;
  }

  public void close(){
    try{
      conn.close();
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  public void rollback(){
    try{
      conn.rollback();
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  public void commit(){
    try{
      conn.commit();
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }

  public void setSqlConnection(Connection conn){
    this.conn = conn;
  }

  public Connection getSqlConnection(){
    return conn;
  }

  public boolean isSupportsBatchUpdates(){
    try{
      return conn.getMetaData().supportsBatchUpdates();
    }catch(SQLException e){
      return false;
    }
  }

  public boolean isClosed(){
    try{
      return conn.isClosed();
    }catch(SQLException e){
      throw new DaoException(e);
    }
  }
}
