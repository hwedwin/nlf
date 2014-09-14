package nc.liat6.frame.log.impl;

import nc.liat6.frame.log.ILog;

/**
 * 日志抽象
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractLog implements ILog{

  protected String klass;

  AbstractLog(String klass){
    this.klass = klass;
  }
}
