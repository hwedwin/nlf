package test;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.setting.IDbSetting;
import nc.liat6.frame.db.setting.IDbSettingManager;
import nc.liat6.frame.db.setting.impl.JdbcSetting;

/**
 * 自定义数据库配置
 * @author 6tail
 *
 */
public class MyDbSettingManager implements IDbSettingManager{

	@Override
	public List<IDbSetting> getDbSettings(){
		List<IDbSetting> l = new ArrayList<IDbSetting>();
		JdbcSetting js = new JdbcSetting();
		js.setAlias("db0");
		js.setDbType("mysql");
		js.setUser("root");
		js.setUrl("jdbc:mysql://localhost:3906/test");
		js.setDriver("org.gjt.mm.mysql.Driver");
		l.add(js);
		return l;
	}

}
