package nc.liat6.frame.log.impl;

import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.ILogAdapter;

/**
 * Ä¬ÈÏÈÕÖ¾ÊÊÅäÆ÷
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
