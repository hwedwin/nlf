package nc.liat6.frame.json.element;

import java.io.Serializable;

/**
 * JSON对象接口
 * @author 6tail
 *
 */
public interface IJsonElement extends Serializable{

	/**
	 * 类型
	 * @return 类型
	 */
	public JsonType type();

	/**
	 * 转换为{}类型
	 * @return
	 */
	public JsonMap toJsonMap();

	/**
	 * 转换为[]类型
	 * @return
	 */
	public JsonList toJsonList();
	
	/**
	 * 转换为布尔值
	 * @return
	 */
	public JsonBool toJsonBool();
	
	/**
	 * 转换为数字类型
	 * @return
	 */
	public JsonNumber toJsonNumber();
	
	/**
	 * 转换为字符串类型
	 * @return
	 */
	public JsonString toJsonString();
	
	/**
	 * 根据路径获取节点值,只有JsonMap和JsonList才实现该方法
	 * @param path 路径，如{'a':{'b':1}}，select("a.b")结果为1，如[1,2,3,4]，select("0")结果为1，如果无法找到指定节点，返回null
	 * @return 节点值
	 */
	public IJsonElement select(String path);
	
	/**
	 * 设置注释
	 * @param note 注释
	 */
	public void setNote(String note);
	
	/**
	 * 获取注释
	 * @return 注释
	 */
	public String getNote();

}
