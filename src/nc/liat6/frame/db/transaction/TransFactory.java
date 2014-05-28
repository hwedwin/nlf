package nc.liat6.frame.db.transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.liat6.frame.Factory;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.DbType;
import nc.liat6.frame.db.IAccess;
import nc.liat6.frame.db.IDb2;
import nc.liat6.frame.db.IJson;
import nc.liat6.frame.db.IMongo;
import nc.liat6.frame.db.IMysql;
import nc.liat6.frame.db.IOracle;
import nc.liat6.frame.db.IPostgresql;
import nc.liat6.frame.db.ISqlserver;
import nc.liat6.frame.db.ISybase;
import nc.liat6.frame.db.IXml;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.setting.SettingFactory;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * 事务工厂
 * @author 6tail
 *
 */
public class TransFactory{
	
	private static final Map<String,Class<?>> pool = new HashMap<String,Class<?>>();
	
	private static final Map<DbType,Class<?>> map = new HashMap<DbType,Class<?>>();
	
	static{
		map.put(DbType.ACCESS,IAccess.class);
		map.put(DbType.DB2,IDb2.class);
		map.put(DbType.JSON,IJson.class);
		map.put(DbType.MYSQL,IMysql.class);
		map.put(DbType.ORACLE,IOracle.class);
		map.put(DbType.POSTGRESQL,IPostgresql.class);
		map.put(DbType.SQLSERVER,ISqlserver.class);
		map.put(DbType.SYBASE,ISybase.class);
		map.put(DbType.XML,IXml.class);
		map.put(DbType.MONGO,IMongo.class);
	}

	private TransFactory(){}
	
	/**
	 * 获取事务，框架自动判断是否新开连接
	 * @return 事务
	 */
	public static ITrans getTrans(){
		return getTrans(null,false);
	}

	/**
	 * 根据别名获取事务,框架自动判断是否新开连接
	 * @param alias 别名
	 * @return 事务
	 */
	public static ITrans getTrans(String alias){
		return getTrans(alias,false);
	}
	
	/**
	 * 获取事务
	 * @param newConnection 是否强制新开连接，true强制新开,false框架自动判断
	 * @return 事务
	 */
	public static ITrans getTrans(boolean newConnection){
		return getTrans(null,newConnection);
	}
	
	/**
	 * 根据别名获取事务
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
				actAlias = SettingFactory.getDefaultSetting().getAlias();
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
	
	public static Class<?> getImplClass(DbType dbType,Class<?> interfaceClass){
		String tag = dbType+"|"+interfaceClass.getName();
		if(pool.containsKey(tag)){
			return pool.get(tag);
		}
		List<String> nl = new ArrayList<String>();
		List<String> l = Factory.getImpls(interfaceClass.getName());
		
		List<String> lo = Factory.getImpls(map.get(dbType).getName());
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
			throw new NlfException(className,e);
		}
		
		pool.put(tag,klass);
		Logger.getLog().debug(Stringer.print("?[?]?[?]??",L.get(LocaleFactory.locale,"db.load"),interfaceClass.getSimpleName(),L.get(LocaleFactory.locale,"db.own"),dbType,L.get(LocaleFactory.locale,"db.impl"),klass.getName()));
		return klass;
	}

}
