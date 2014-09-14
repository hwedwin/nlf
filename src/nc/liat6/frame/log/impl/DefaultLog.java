package nc.liat6.frame.log.impl;

import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.util.Dater;
import nc.liat6.frame.util.Stringer;

/**
 * 默认日志
 * 
 * @author 6tail
 * 
 */
public class DefaultLog extends AbstractLog{

  public static final String DEBUG = L.get(LocaleFactory.locale,"logger.debug");
  public static final String INFO = L.get(LocaleFactory.locale,"logger.info");
  public static final String ERROR = L.get(LocaleFactory.locale,"logger.error");
  public static final String WARN = L.get(LocaleFactory.locale,"logger.warn");
  public static final String FATAL = L.get(LocaleFactory.locale,"logger.fatal");

  DefaultLog(String klass){
    super(klass);
  }

  public void debug(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.out.println(Stringer.print("[?] ? ? \r\n?\r\n?",DEBUG,Dater.ymdhms(Dater.now()),klass,sts[2],o));
  }

  public void debug(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.out.println(Stringer.print("[?] ? ? \r\n?\r\n?",DEBUG,Dater.ymdhms(Dater.now()),klass,sts[2],o));
    e.printStackTrace();
  }

  public void error(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.out.println(Stringer.print("[?] ? ? \r\n?\r\n?",ERROR,Dater.ymdhms(Dater.now()),klass,sts[2],o));
  }

  public void error(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.err.println(Stringer.print("[?] ? ? \r\n?\r\n?",ERROR,Dater.ymdhms(Dater.now()),klass,sts[2],o));
    e.printStackTrace();
  }

  public void fatal(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.err.println(Stringer.print("[?] ? ? \r\n?\r\n?",FATAL,Dater.ymdhms(Dater.now()),klass,sts[2],o));
  }

  public void fatal(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.err.println(Stringer.print("[?] ? ? \r\n?\r\n?",FATAL,Dater.ymdhms(Dater.now()),klass,sts[2],o));
    e.printStackTrace();
  }

  public void info(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.out.println(Stringer.print("[?] ? ? \r\n?\r\n?",INFO,Dater.ymdhms(Dater.now()),klass,sts[2],o));
  }

  public void info(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.out.println(Stringer.print("[?] ? ? \r\n?\r\n?",INFO,Dater.ymdhms(Dater.now()),klass,sts[2],o));
    e.printStackTrace();
  }

  public boolean isDebugEnabled(){
    return true;
  }

  public boolean isErrorEnabled(){
    return true;
  }

  public boolean isFatalEnabled(){
    return true;
  }

  public boolean isInfoEnabled(){
    return true;
  }

  public boolean isTraceEnabled(){
    return true;
  }

  public boolean isWarnEnabled(){
    return true;
  }

  public void trace(Object o){
    System.out.println(o);
  }

  public void trace(Object o,Throwable e){
    e.printStackTrace();
  }

  public void warn(Object o){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.out.println(Stringer.print("[?] ? ? \r\n?\r\n?",WARN,Dater.ymdhms(Dater.now()),klass,sts[2],o));
  }

  public void warn(Object o,Throwable e){
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    System.out.println(Stringer.print("[?] ? ? \r\n?\r\n?",WARN,Dater.ymdhms(Dater.now()),klass,sts[2],o));
    e.printStackTrace();
  }
}
