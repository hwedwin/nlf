package nc.liat6.frame.web.config;

/**
 * WEB管理器接口
 * @author 6tail
 *
 */
public interface IWebManager{
	
	/**
	 * 执行失败时的处理
	 * @param e 异常
	 * @return 返回结果
	 */
	public Object failed(Throwable e);
	
	/**
	 * 执行前处理
	 * @param path 请求路径
	 * @return 类&方法
	 */
	public ClassMethod before(String path);
	
	/**
	 * 执行成功后处理
	 */
	public void after();
	
	/**
	 * 返回结果的过滤
	 */
	public void filter();

}
