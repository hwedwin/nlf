package nc.liat6.frame.execute;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应
 * @author 6tail
 *
 */
public class Response {
	
	private Map<String,Object> bundle = new HashMap<String,Object>();
	
	/**
	 * 绑定变量
	 * @param key 键
	 * @param value 值
	 */
	public void bind(String key,Object value){
		bundle.put(key,value);
	}
	
	/**
	 * 获取绑定的变量值
	 * @param key 键
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public <T> T find(String key){
		return (T)bundle.get(key);
	}

}
