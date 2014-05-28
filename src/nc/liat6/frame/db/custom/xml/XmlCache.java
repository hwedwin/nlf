package nc.liat6.frame.db.custom.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nc.liat6.frame.db.custom.bean.Table;

/**
 * XML数据库缓存
 * @author 6tail
 *
 */
public class XmlCache{
	
	/** 缓存池，按连接别名.表名存储 */
	private static final ThreadLocal<Map<String,Table>> pool = new ThreadLocal<Map<String,Table>>(){
		public HashMap<String,Table> initialValue() {
			return new HashMap<String,Table>();
		}
	};
	
	private XmlCache(){}
	
	/**
	 * 更新缓存表
	 * @param alias 连接别名
	 * @param table 缓存表
	 */
	public synchronized static void update(String alias,Table table){
		String key = alias+"."+table.getTable().getName().toUpperCase(); 
		pool.get().put(key,table);
	}
	
	/**
	 * 移除缓存表
	 * @param alias 连接别名
	 * @param tableName 表名
	 */
	public synchronized static void remove(String alias,String tableName){
		pool.get().remove(alias+"."+tableName.toUpperCase());
	}
	
	/**
	 * 是否存在缓存表
	 * @param alias 连接别名
	 * @param tableName
	 * @return true/false
	 */
	public static boolean contains(String alias,String tableName){
		return pool.get().containsKey(alias+"."+tableName.toUpperCase());
	}
	
	/**
	 * 获取缓存表
	 * @param alias 连接别名
	 * @param tableName 表名
	 * @return 缓存表
	 */
	public static Table getTable(String alias,String tableName){
		return pool.get().get(alias+"."+tableName.toUpperCase());
	}
	
	
	/**
	 * 获取指定连接的所有表名
	 * @param alias 连接
	 * @return 表名
	 */
	public static List<String> getTableNames(String alias){
		List<String> l = new ArrayList<String>();
		for(String k:pool.get().keySet()){
			if(k.startsWith(alias+".")){
				l.add(pool.get().get(k).getTable().getName());
			}
		}
		return l;
	}

}
