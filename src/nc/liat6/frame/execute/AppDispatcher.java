package nc.liat6.frame.execute;

import java.util.Map;
import nc.liat6.frame.Factory;
import nc.liat6.frame.Version;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.exception.BadException;
import nc.liat6.frame.execute.impl.AbstractExecute;
import nc.liat6.frame.execute.impl.AppExecute;
import nc.liat6.frame.log.Logger;

/**
 * 应用调度器
 * 
 * @author 6tail
 * 
 */
public class AppDispatcher{

  private IExecute execute;

  private Object error(Throwable e){
    Throwable cause = e;
    while(null!=cause&&null!=cause.getCause()){
      cause = cause.getCause();
    }
    if(!(cause instanceof BadException)){
      Logger.getLog().error(null==e?null:e.getMessage(),e);
    }
    return cause;
  }

  /**
   * 执行
   * 
   * @param klass 类名
   * @param method 方法名
   * @param args 参数
   * @return 执行结果
   */
  public Object execute(String klass,String method,Map<String,String> args){
    Context.set(AbstractExecute.EXECUTE_ARGS,args);
    if(klass.startsWith(Version.PACKAGE)){
      return null;
    }
    execute = new AppExecute();
    execute.begin();
    Object r = null;
    try{
      r = Factory.getCaller().execute(klass,method);
    }catch(Throwable e){
      r = error(e);
    }
    Context.set(AbstractExecute.EXECUTE_RETURN,r);
    execute.end();
    Context.clear();
    return r;
  }
}
