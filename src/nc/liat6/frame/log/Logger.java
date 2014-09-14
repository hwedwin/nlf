package nc.liat6.frame.log;

import java.util.List;
import nc.liat6.frame.Factory;

/**
 * 日志工厂
 * 
 * @author 6tail
 * 
 */
public class Logger{

  /** 适配器 */
  private static ILogAdapter adapter;
  static{
    List<String> l = Factory.getImpls(ILogAdapter.class.getName());
    for(String c:l){
      try{
        ILogAdapter a = (ILogAdapter)Class.forName(c).newInstance();
        if(a.isSupported()){
          adapter = a;
          break;
        }
      }catch(Exception e){}
    }
  }

  private Logger(){}

  /**
   * 获取日志接口
   * 
   * @return 日志接口
   */
  public static ILog getLog(){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    return adapter.getLog(sts[2].getClassName());
  }

  /**
   * 获取日志接口
   * 
   * @param klass 类名
   * @return 日志接口
   */
  public static ILog getLog(String klass){
    return adapter.getLog(klass);
  }

  /**
   * 获取日志接口
   * 
   * @param c 类
   * @return 日志接口
   */
  public static ILog getLog(Class<?> c){
    return adapter.getLog(c.getName());
  }
}
