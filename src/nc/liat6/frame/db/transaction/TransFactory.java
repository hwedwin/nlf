package nc.liat6.frame.db.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.frame.Factory;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.config.DbConfigFactory;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.DbSettingFactory;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * 事务工厂
 * 
 * @author 6tail
 * 
 */
public class TransFactory{

  /** 接口实现类缓存 */
  private static final Map<String,Class<?>> pool = new HashMap<String,Class<?>>();

  private TransFactory(){}

  /**
   * 获取事务，框架自动判断是否新开连接
   * 
   * @return 事务
   */
  public static ITrans getTrans(){
    return getTrans(null,false);
  }

  /**
   * 根据别名获取事务,框架自动判断是否新开连接
   * 
   * @param alias 别名
   * @return 事务
   */
  public static ITrans getTrans(String alias){
    return getTrans(alias,false);
  }

  /**
   * 获取事务
   * 
   * @param newConnection 是否强制新开连接，true强制新开,false框架自动判断
   * @return 事务
   */
  public static ITrans getTrans(boolean newConnection){
    return getTrans(null,newConnection);
  }

  /**
   * 根据别名获取事务
   * 
   * @param alias 别名
   * @param newConnection 是否强制新开连接，true强制新开,false框架自动判断
   * @return 事务
   */
  public static ITrans getTrans(String alias,boolean newConnection){
    String actAlias = alias;
    if(null==actAlias){
      if(Context.contains(Statics.DEFAULT_CONNECTION_ALIAS)){
        actAlias = Context.get(Statics.DEFAULT_CONNECTION_ALIAS);
      }else{
        actAlias = DbSettingFactory.getDefaultSetting().getAlias();
      }
    }
    ITrans t = Factory.getCaller().newInstance(ITrans.class);
    if(newConnection){
      List<ConnVar> l = Context.get(Statics.CONNECTIONS);
      Context.remove(Statics.CONNECTIONS);
      try{
        t.init(actAlias);
      }finally{
        Context.set(Statics.CONNECTIONS,l);
      }
    }else{
      t.init(actAlias);
    }
    return t;
  }

  public static Class<?> getImplClass(String dbType,Class<?> interfaceClass){
    String key = dbType+"|"+interfaceClass.getName();
    if(pool.containsKey(key)){
      return pool.get(key);
    }
    List<String> nl = new ArrayList<String>();
    List<String> l = Factory.getImpls(interfaceClass.getName());
    List<String> lo = Factory.getImpls(DbConfigFactory.getDbConfig(dbType).getSuperInterfaceName());
    for(String c:l){
      if(lo.contains(c)){
        nl.add(c);
      }
    }
    if(nl.size()<1){
      for(String c:l){
        String cn = c;
        if(c.contains(".")){
          cn = c.substring(c.lastIndexOf("."));
        }
        if(cn.startsWith(".Common")){
          nl.add(c);
        }
      }
    }
    String className = nl.get(0);
    Class<?> klass = null;
    try{
      klass = Class.forName(className);
    }catch(ClassNotFoundException e){
      throw new DaoException(className,e);
    }
    pool.put(key,klass);
    Logger.getLog().debug(Stringer.print("?[?]?[?]??",L.get(LocaleFactory.locale,"db.load"),interfaceClass.getSimpleName(),L.get(LocaleFactory.locale,"db.own"),dbType,L.get(LocaleFactory.locale,"db.impl"),klass.getName()));
    return klass;
  }
}
