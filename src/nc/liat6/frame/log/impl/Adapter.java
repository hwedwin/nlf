package nc.liat6.frame.log.impl;

import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.ILogAdapter;

/**
 * 默认日志适配器
 * 
 * @author 6tail
 * 
 */
public class Adapter implements ILogAdapter{

  public ILog getLog(String klass){
    return new DefaultLog(klass);
  }

  public boolean isSupported(){
    return true;
  }
}
