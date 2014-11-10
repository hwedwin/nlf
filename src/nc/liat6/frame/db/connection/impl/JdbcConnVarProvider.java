package nc.liat6.frame.db.connection.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.SqlConnection;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.impl.JdbcSetting;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;

/**
 * JDBC连接变量提供器
 *
 * @author 6tail
 *
 */
public class JdbcConnVarProvider extends SuperConnVarProvider{

  public ConnVar getConnVar(){
    JdbcSetting setting = (JdbcSetting)this.setting;
    ConnVar cv = new ConnVar();
    cv.setDbType(setting.getDbType());
    cv.setAlias(setting.getAlias());
    try{
      Connection conn = DriverManager.getConnection(setting.getUrl(),setting.getUser(),setting.getPassword());
      try{
        conn.setAutoCommit(false);
      }catch(SQLException e){
        Logger.getLog().error(L.get(LocaleFactory.locale,"db.commit_not_support"),e);
      }
      SqlConnection sc = new SqlConnection();
      sc.setSqlConnection(conn);
      cv.setConnection(sc);
    }catch(SQLException e){
      throw new DaoException(e);
    }
    cv.setSetting(setting);
    return cv;
  }

  public boolean support(String connType){
    return "jdbc".equalsIgnoreCase(connType);
  }
}