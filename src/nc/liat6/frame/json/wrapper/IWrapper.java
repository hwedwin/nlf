package nc.liat6.frame.json.wrapper;

/**
 * 包装接口
 * @author 6tail
 *
 */
public interface IWrapper {
	
	/**
	 * 包装
	 * @param o 对象
	 * @param level 层级
	 * @return 字符串
	 */
	public String wrap(Object o,int level);

}
