package nc.liat6.frame.aop;

import java.lang.reflect.Method;

/**
 * AOP方法拦截接口
 * 
 * @author liat6
 * 
 */
public interface IAopInterceptor{

  /**
   * 方法出问题后执行
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   */
  public void failed(Object o,Method m,Object[] args,Throwable e);

  /**
   * 方法执行成功后执行
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   */
  public void succeed(Object o,Method m,Object[] args);

  /**
   * 方法执行前执行
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   */
  public void before(Object o,Method m,Object[] args);

  /**
   * 方法执行后执行
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   */
  public void after(Object o,Method m,Object[] args);

  /**
   * 是否满足执行条件
   * 
   * @param o 对象
   * @param m 方法
   * @return true/false 满足/不满足
   */
  public boolean check(Object o,Method m);
}
