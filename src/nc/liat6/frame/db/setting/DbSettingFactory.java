package nc.liat6.frame.db.setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.liat6.frame.Factory;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * 连接设置工厂
 * 
 * @author 6tail
 * 
 */
public class DbSettingFactory {
	
	/** 连接配置映射 */
	private static final Map<String, IDbSetting> SETTING_POOL = new HashMap<String, IDbSetting>();
	/** 连接配置列表，与映射对应 */
	private static final List<IDbSetting> SETTING_LIST = new ArrayList<IDbSetting>();

	private DbSettingFactory(){}

	static {
		init();
	}
	
	private synchronized static void init(){
		IDbSettingManager dsm = Factory.getCaller().newInstance(IDbSettingManager.class);
		List<IDbSetting> l = dsm.getDbSettings();
		StringBuilder s = new StringBuilder();
		for(IDbSetting o:l){
			s.append("\r\n\t");
			s.append(o.getAlias());
			SETTING_POOL.put(o.getAlias(), o);
			SETTING_LIST.add(o);
		}
		if(l.size()>0){
			Logger.getLog().debug(Stringer.print("??",L.get(LocaleFactory.locale,"db.load_config"),s.toString()));
		}
		//排序
		Collections.sort(SETTING_LIST,new DbSettingComparator());
	}

	/**
	 * 获取连接配置
	 * 
	 * @param alias
	 *            别名
	 * @return 连接配置
	 */
	public static IDbSetting getSetting(String alias) {
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
	public static IDbSetting getDefaultSetting() {
		if (SETTING_LIST.size() < 1) {
			throw new DaoException(L.get("db.config_not_found"));
		}
		return SETTING_LIST.get(0);
	}

}
