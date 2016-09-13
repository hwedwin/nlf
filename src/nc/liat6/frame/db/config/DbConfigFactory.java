package nc.liat6.frame.db.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.frame.Factory;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;

/**
 * 数据库配置工厂
 * 
 * @author 6tail
 * 
 */
public class DbConfigFactory{

  private static final Map<String,DbConfig> pool = new HashMap<String,DbConfig>();

  private DbConfigFactory(){}
  static{
    List<String> impls = Factory.getImpls(IDbConfigProvider.class.getName());
    for(String klass:impls){
      IDbConfigProvider dcp = Factory.getCaller().newInstance(klass);
      DbConfig dc = dcp.getDbConfig();
      pool.put(dc.getDbType(),dc);
    }
  }

  public static DbConfig getDbConfig(String dbType){
    dbType = dbType.toUpperCase();
    if(!pool.containsKey(dbType)){
      throw new DaoException(L.get("db.dbtype_not_support")+dbType);
    }
    return pool.get(dbType);
  }
}
