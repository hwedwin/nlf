package nc.liat6.frame.proxy;

import nc.liat6.frame.aop.IAopInterceptor;

/**
 * 动态代理接口
 * 
 * @author 6tail
 * 
 */
public interface IProxy{

	/** AOP拦截器名称 */
	public static final String AOP_INTERCEPTOR_LIST_NAME = "$NlfAopInterceptors";

	/** 代理类后缀 */
	public static final String SUFFIX = "$NlfProxy";

	/**
	 * 生成指定类的代理对象
	 * @param superClass 父类
	 * @return 代理对象
	 */
	public <T>T create(Class<?> superClass);

	/**
	 * 获取原对象
	 * @return 原对象
	 */
	public <T>T getOBean();

	/**
	 * 添加AOP拦截器
	 * @param ai AOP拦截器
	 */
	public void addAopInterceptor(IAopInterceptor ai);

}
