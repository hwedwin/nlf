package nc.liat6.frame.web.tags.entity;

import nc.liat6.frame.db.entity.Bean;

/**
 * Bean工具
 * @author 6tail
 *
 */
public class BeanUtil{
	
	/**
	 * 获取Bean的值
	 * @param bean Bean对象
	 * @param key 键
	 * @return 值
	 */
	public static Object get(Bean bean,String key){
		if(null==bean) return null;
		Object r = bean.get(key);
		//if(r instanceof String){
			//return ((String)r).replace("<","&lt;").replace(">","&gt;");
		//}
		return r;
	}

}
