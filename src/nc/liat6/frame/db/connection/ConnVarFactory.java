package nc.liat6.frame.db.connection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.frame.Factory;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.DbSettingFactory;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;

/**
 * 连接变量工厂
 * 
 * @author 6tail
 * 
 */
public class ConnVarFactory{

  /** 连接提供器缓存 */
  private static final Map<String,IConnVarProvider> pool = new HashMap<String,IConnVarProvider>();

  private ConnVarFactory(){}

  /**
   * 根据别名获取连接变量
   * 
   * @param alias 别名
   * @return 连接变量
   */
  public static ConnVar getConnVar(String alias){
    IDbSetting setting = DbSettingFactory.getSetting(alias);
    String type = setting.getType();
    //String key = type+"-"+setting.getDbName();
    //以数据库类型和数据库实例名为key是不可靠的。
    String key = alias;
    if(pool.containsKey(key)){
      return pool.get(key).getConnVar();
    }
    List<String> impls = Factory.getImpls(IConnVarProvider.class.getName());
    for(String klass:impls){
      IConnVarProvider cvp = Factory.getCaller().newInstance(klass);
      if(cvp.support(type)){
        pool.put(key,cvp);
        cvp.initSetting(setting);
        return cvp.getConnVar();
      }
    }
    throw new DaoException(L.get(LocaleFactory.locale,"db.conntype_not_support")+type);
  }

  /**
   * 获取默认连接变量
   * 
   * @return 默认连接变量
   */
  public static ConnVar getConnVar(){
    return getConnVar(DbSettingFactory.getDefaultSetting().getAlias());
  }
}