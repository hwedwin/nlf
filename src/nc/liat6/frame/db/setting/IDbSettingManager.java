package nc.liat6.frame.db.setting;

import java.util.List;

/**
 * 数据库连接设置管理接口
 * @author 6tail
 *
 */
public interface IDbSettingManager{
	
	public List<IDbSetting> getDbSettings();

}
