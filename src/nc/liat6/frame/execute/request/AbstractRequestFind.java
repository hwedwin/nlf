package nc.liat6.frame.execute.request;

import nc.liat6.frame.execute.Request;

/**
 * 抽象Find
 * 
 * @author 6tail
 *
 */
public abstract class AbstractRequestFind{
  /** 当前请求 */
  protected Request request;

  protected AbstractRequestFind(Request request){
    this.request = request;
  }
}