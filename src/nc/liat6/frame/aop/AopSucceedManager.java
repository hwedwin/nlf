package nc.liat6.frame.aop;

import java.lang.reflect.Method;

/**
 * AOP方法成功后管理器
 * 
 * @author liat6
 * 
 */
public abstract class AopSucceedManager extends AopManager{

  public AopSucceedManager(String klass,String method){
    super(klass,method);
  }

  /**
   * 方法执行成功后调用
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   */
  public abstract void execute(Object o,Method m,Object[] args);

  @Override
  public void succeed(Object o,Method m,Object[] args){
    if(check(o,m))
      execute(o,m,args);
  }

  @Override
  public void before(Object o,Method m,Object[] args){}

  @Override
  public void failed(Object o,Method m,Object[] args,Throwable e){}

  @Override
  public void after(Object o,Method m,Object[] args){}
}