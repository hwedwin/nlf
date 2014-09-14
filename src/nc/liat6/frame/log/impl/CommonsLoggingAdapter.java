package nc.liat6.frame.log.impl;

import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.ILogAdapter;

/**
 * apache-commons-log适配器
 * 
 * @author 6tail
 * 
 */
public class CommonsLoggingAdapter implements ILogAdapter{

  public ILog getLog(String klass){
    return new CommonsLog(klass);
  }

  public boolean isSupported(){
    try{
      Class.forName("org.apache.commons.logging.LogFactory");
    }catch(ClassNotFoundException e){
      return false;
    }
    return true;
  }
}
