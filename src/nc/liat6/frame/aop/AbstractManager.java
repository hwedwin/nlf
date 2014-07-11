package nc.liat6.frame.aop;

import java.lang.reflect.Method;
import java.util.List;
import nc.liat6.frame.util.Stringer;

/**
 * 抽象AOP管理器
 * 
 * @author liat6
 * 
 */
public abstract class AbstractManager implements IAopInterceptor{

  private static final String[] TAG_DROP = new String[]{"\r","\n"," "};
  private static final String TAG_SPLIT = ",";
  /** 适应的类列表 */
  private String klass;
  /** 适应的方法列表 */
  private String method;
  /** 需要管理的类名列表 */
  protected List<String> klasses;
  /** 需要管理的方法名列表 */
  protected List<String> methods;

  public void setMethod(String method){
    this.method = Stringer.replace(method,TAG_DROP,"");
    this.methods = Stringer.list(this.method,TAG_SPLIT);
  }

  public void setKlass(String klass){
    this.klass = Stringer.replace(klass,TAG_DROP,"");
    this.klasses = Stringer.list(this.klass,TAG_SPLIT);
  }

  public AbstractManager(String klass,String method){
    setKlass(klass);
    setMethod(method);
  }

  /**
   * 方法执行后调用
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   */
  public abstract void after(Object o,Method m,Object[] args);

  /**
   * 方法执行前调用
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   */
  public abstract void before(Object o,Method m,Object[] args);

  /**
   * 方法执行失败后调用
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   * @param e 异常
   */
  public abstract void failed(Object o,Method m,Object[] args,Throwable e);

  /**
   * 方法执行成功后调用
   * 
   * @param o 对象
   * @param m 方法
   * @param args 参数
   * @param e 异常
   */
  public abstract void succeed(Object o,Method m,Object[] args);
}
