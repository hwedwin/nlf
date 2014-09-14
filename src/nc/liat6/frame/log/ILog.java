package nc.liat6.frame.log;

/**
 * 日志接口
 * 
 * @author 6tail
 * 
 */
public interface ILog{

  public void debug(Object o);

  public void debug(Object o,Throwable e);

  public void error(Object o);

  public void error(Object o,Throwable e);

  public void fatal(Object o);

  public void fatal(Object o,Throwable e);

  public void info(Object o);

  public void info(Object o,Throwable e);

  public boolean isDebugEnabled();

  public boolean isErrorEnabled();

  public boolean isFatalEnabled();

  public boolean isInfoEnabled();

  public boolean isTraceEnabled();

  public boolean isWarnEnabled();

  public void trace(Object o);

  public void trace(Object o,Throwable e);

  public void warn(Object o);

  public void warn(Object o,Throwable e);
}
