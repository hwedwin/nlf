package nc.liat6.frame.execute.impl;

import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.rmi.server.request.RmiIPFetcher;
import nc.liat6.frame.rmi.server.request.RmiRequest;

/**
 * 应用执行实现
 * 
 * @author 6tail
 * 
 */
public class AppExecute extends AbstractExecute{

  public static final String RMI_REQUEST = "NLF_RMI_REQUEST";
  /** 用于获取原始Request的标识符 */
  public static final String TAG_REQUEST = Statics.TAG_ORG_REQUEST;
  /** 用于获取IP获取器的标识符 */
  public static final String TAG_IP_FETCHER = Statics.TAG_IP_FETCHER;

  @Override
  public void request(){
    Request req = Context.get(Statics.REQUEST);
    RmiRequest oreq = Context.get(RMI_REQUEST);
    req.bind(TAG_REQUEST,oreq);
    req.bind(TAG_IP_FETCHER,new RmiIPFetcher(req));
  }

  @Override
  public void response(){}
}
