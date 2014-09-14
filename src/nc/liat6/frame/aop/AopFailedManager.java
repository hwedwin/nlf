package nc.liat6.frame.aop;

import java.lang.reflect.Method;

/**
 * AOP方法失败后管理器
 * 
 * @author liat6
 * 
 */
public abstract class AopFailedManager extends AopManager{

  public AopFailedManager(String klass,String method){
    super(klass,method);
  }

  /**
   * 方法执行失败后调用
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   * @param e 异常
   */
  public abstract void execute(Object o,Method m,Object[] args,Throwable e);

  @Override
  public void failed(Object o,Method m,Object[] args,Throwable e){
    if(check(o,m))
      execute(o,m,args,e);
  }

  @Override
  public void before(Object o,Method m,Object[] args){}

  @Override
  public void succeed(Object o,Method m,Object[] args){}

  @Override
  public void after(Object o,Method m,Object[] args){}
}
