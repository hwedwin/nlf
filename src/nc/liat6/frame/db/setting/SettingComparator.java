package nc.liat6.frame.db.setting;

import java.util.Comparator;

/**
 * 连接配置比较器，按别名alias比较，越大的越前
 * @author 6tail
 *
 */
public class SettingComparator implements Comparator<ISetting>{

	public int compare(ISetting o1,ISetting o2){
		return o2.getAlias().compareTo(o1.getAlias());
	}

}
