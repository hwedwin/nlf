package nc.liat6.frame.db.setting;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.liat6.frame.Factory;
import nc.liat6.frame.db.custom.json.JsonDriver;
import nc.liat6.frame.db.custom.json.JsonSetting;
import nc.liat6.frame.db.custom.mongo.MongoDriver;
import nc.liat6.frame.db.custom.mongo.MongoSetting;
import nc.liat6.frame.db.custom.xml.XmlDriver;
import nc.liat6.frame.db.custom.xml.XmlSetting;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.impl.JdbcSetting;
import nc.liat6.frame.db.setting.impl.ProxoolSetting;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * 连接配置工厂
 * 
 * @author 6tail
 * 
 */
public class SettingFactory {
	/** 配置文件目录 */
	public static final String DB_DIR = "db";
	/** 数据库驱动映射 */
	private static final Map<String,String> DRIVER = new HashMap<String,String>();
	/** 数据库连接地址映射 */
	private static final Map<String,String> URL = new HashMap<String,String>();
	/** 连接配置映射 */
	private static final Map<String, ISetting> SETTING_POOL = new HashMap<String, ISetting>();
	/** 连接配置列表，与映射对应 */
	private static final List<ISetting> SETTING_LIST = new ArrayList<ISetting>();

	private SettingFactory(){}

	static {
		DRIVER.put("sqlserver","com.microsoft.sqlserver.jdbc.SQLServerDriver");
		DRIVER.put("oracle","oracle.jdbc.driver.OracleDriver");
		DRIVER.put("mysql","org.gjt.mm.mysql.Driver");
		DRIVER.put("db2","com.ibm.db2.jdbc.app.DB2Driver");
		DRIVER.put("sybase","com.sybase.jdbc.SybDriver");
		DRIVER.put("postgresql","org.postgresql.Driver");
		DRIVER.put("access","sun.jdbc.odbc.JdbcOdbcDriver");
		DRIVER.put("json",JsonDriver.class.getName());
		DRIVER.put("xml",XmlDriver.class.getName());
		DRIVER.put("mongo",MongoDriver.class.getName());
		
		URL.put("sqlserver","jdbc:sqlserver://?:?;DatabaseName=?");
		URL.put("oracle","jdbc:oracle:thin:@?:?:?");
		URL.put("mysql","jdbc:mysql://?:?/?");
		URL.put("db2","jdbc:db2://?:?/?");
		URL.put("sybase","jdbc:sybase:Tds:?:?/?");
		URL.put("postgresql","jdbc:postgresql://?:?/?");
		URL.put("access","jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=???");
		URL.put("json","?");
		URL.put("xml","?");
		URL.put("mongo","?:?");
		
		init();
	}
	
	private synchronized static void init(){
		List<ISetting> l = new ArrayList<ISetting>();
		File dir = new File(Factory.APP_PATH, DB_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File[] fs = dir.listFiles(new SettingFileFilter());
		for (File f : fs) {
			try {
				ISetting cs = null;
				Bean o = JSON.toBean(Stringer.readFromFile(f));
				String type = o.getString("type");
				String alias = o.getString("alias");
				String dbtype = o.getString("dbtype");
				String user = o.getString("user");
				String password = o.getString("password");
				String server = o.getString("server");
				String port = o.getString("port");
				String dbname = o.getString("dbname");
				
				type = type.toUpperCase();
				dbtype = dbtype.toLowerCase();
				
				if(ConnType.PROXOOL.equals(type)){
					ProxoolSetting ps = new ProxoolSetting();
					ps.setAlias(alias);
					ps.setDriver(DRIVER.get(dbtype));
					ps.setPassword(password);
					ps.setUrl(Stringer.print(URL.get(dbtype),server,port,dbname));
					ps.setUser(user);
					ps.setDbType(dbtype);
					ps.setDbName(dbname);
					ps.setPrototypeCount(o.getInt("prototypeCount",-1));
					ps.setHouseKeepingSleepTime(o.getInt("houseKeepingSleepTime",-1));
					ps.setMaximumActiveTime(o.getInt("maximumActiveTime",-1));
					ps.setMaximumConnectionLifeTime(o.getInt("maximumConnectionLifeTime",-1));
					ps.setMaximumConnectionCount(o.getInt("maximumConnectionCount",-1));
					ps.setMinimumConnectionCount(o.getInt("minimumConnectionCount",-1));
					ps.setSimultaneousBuildThrottle(o.getInt("simultaneousBuildThrottle",-1));
					ps.setTestBeforeUse(o.getBoolean("testBeforeUse",true));
					ps.setTestAfterUse(o.getBoolean("testAfterUse",true));
					cs = ps;
				}else if(ConnType.JDBC.equals(type)){
					JdbcSetting js = new JdbcSetting();
					js.setAlias(alias);
					js.setDriver(DRIVER.get(dbtype));
					js.setPassword(password);
					js.setUrl(Stringer.print(URL.get(dbtype),server,port,dbname));
					js.setUser(user);
					js.setDbType(dbtype);
					js.setDbName(dbname);
					cs = js;
				}else if(ConnType.MONGO.equals(type)){
					MongoSetting ms = new MongoSetting();
					ms.setAlias(alias);
					ms.setDriver(DRIVER.get(dbtype));
					ms.setPassword(password);
					ms.setUrl(Stringer.print(URL.get(dbtype),server,port));
					ms.setUser(user);
					ms.setDbType(dbtype);
					ms.setDbName(dbname);
					cs = ms;
				}else if(ConnType.JSON.equals(type)){
					JsonSetting js = new JsonSetting();
					js.setAlias(alias);
					js.setDriver(DRIVER.get(dbtype));
					js.setPassword(password);
					String url = Stringer.print(URL.get(dbtype),dbname);
					js.setUrl(url.replace("${PATH}",Factory.APP_PATH));
					js.setUser(user);
					js.setDbType(dbtype);
					js.setDbName(js.getUrl());
					cs = js;
				}else if(ConnType.XML.equals(type)){
					XmlSetting js = new XmlSetting();
					js.setAlias(alias);
					js.setDriver(DRIVER.get(dbtype));
					js.setPassword(password);
					String url = Stringer.print(URL.get(dbtype),dbname);
					js.setUrl(url.replace("${PATH}",Factory.APP_PATH));
					js.setUser(user);
					js.setDbType(dbtype);
					js.setDbName(js.getUrl());
					cs = js;
				}
				l.add(cs);
			} catch (Exception e) {
				throw new DaoException(L.get("db.config_file_fail")+f.getName(),e);
			}
		}
		StringBuilder s = new StringBuilder();
		for(ISetting o:l){
			s.append("\r\n\t");
			s.append(o.getAlias());
			SETTING_POOL.put(o.getAlias(), o);
			SETTING_LIST.add(o);
		}
		if(l.size()>0){
			Logger.getLog().debug(Stringer.print("??",L.get(LocaleFactory.locale,"db.load_config"),s.toString()));
		}
		//排序
		Collections.sort(SETTING_LIST,new SettingComparator());
	}

	/**
	 * 获取连接配置
	 * 
	 * @param alias
	 *            别名
	 * @return 连接配置
	 */
	public static ISetting getSetting(String alias) {
		if (SETTING_POOL.containsKey(alias)) {
			return SETTING_POOL.get(alias);
		}
		throw new DaoException(L.get("db.config_not_found") + alias);
	}

	/**
	 * 总连接配置数
	 * 
	 * @return 总连接配置数
	 */
	public static int size() {
		return SETTING_POOL.size();
	}

	/**
	 * 获取默认连接配置，如果有多个连接配置文件，返回别名alias最大的配置
	 * 
	 * @return 默认连接配置，如果有多个连接配置文件，返回别名alias最大的配置
	 */
	public static ISetting getDefaultSetting() {
		if (SETTING_LIST.size() < 1) {
			throw new DaoException(L.get("db.config_not_found"));
		}
		return SETTING_LIST.get(0);
	}

}
