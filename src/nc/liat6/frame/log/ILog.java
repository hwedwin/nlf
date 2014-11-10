package nc.liat6.frame.log;

/**
 * 日志接口
 *
 * @author 6tail
 *
 */
public interface ILog{

  void debug(Object o);

  void debug(Object o,Throwable e);

  void error(Object o);

  void error(Object o,Throwable e);

  void fatal(Object o);

  void fatal(Object o,Throwable e);

  void info(Object o);

  void info(Object o,Throwable e);

  boolean isDebugEnabled();

  boolean isErrorEnabled();

  boolean isFatalEnabled();

  boolean isInfoEnabled();

  boolean isTraceEnabled();

  boolean isWarnEnabled();

  void trace(Object o);

  void trace(Object o,Throwable e);

  void warn(Object o);

  void warn(Object o,Throwable e);
}