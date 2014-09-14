package nc.liat6.frame.db.setting.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.Factory;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.DbSettingFileFilter;
import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.IDbSettingManager;
import nc.liat6.frame.db.setting.IDbSettingProvider;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.Stringer;

/**
 * 数据库连接设置管理器
 * @author 6tail
 *
 */
public class DbSettingManager implements IDbSettingManager{
	
	/** 数据库配置文件目录 */
	public static final String DB_DIR = "db";

	@Override
	public List<IDbSetting> getDbSettings(){
		List<IDbSetting> l = new ArrayList<IDbSetting>();
		File dir = new File(Factory.APP_PATH, DB_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File[] fs = dir.listFiles(new DbSettingFileFilter());
		List<String> impls = Factory.getImpls(IDbSettingProvider.class.getName());
		outer:for (File f : fs) {
			try {
				Bean o = JSON.toBean(Stringer.readFromFile(f,"utf-8"));
				String type = o.getString("type","");
				type = type.toUpperCase();
				for(String klass:impls){
					IDbSettingProvider dsp = Factory.getCaller().newInstance(klass);
					if(dsp.support(type)){
						l.add(dsp.getDbSetting(o));
						continue outer;
					}
				}
			} catch (Exception e) {
				throw new DaoException(L.get("db.config_file_fail")+f.getName(),e);
			}
		}
		return l;
	}

}
