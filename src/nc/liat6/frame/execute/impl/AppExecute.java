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

  public void request(){
    Request req = Context.get(Statics.REQUEST);
    RmiRequest oreq = Context.get(RMI_REQUEST);
    req.bind(Statics.FIND_REQUEST,oreq);
    req.bind(Statics.FIND_IP_FETCHER,new RmiIPFetcher(req));
  }

  public void response(){}
}