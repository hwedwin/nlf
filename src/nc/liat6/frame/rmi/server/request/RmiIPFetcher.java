package nc.liat6.frame.rmi.server.request;

import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.request.AbstractRequestFind;
import nc.liat6.frame.execute.request.IIPFetcher;

/**
 * RMI应用IP获取器，在确实需要用到IP的时候才解析
 *
 * @author 6tail
 *
 */
public class RmiIPFetcher extends AbstractRequestFind implements IRmiRequestFind,IIPFetcher{

  /** 缓存IP */
  private String ip;
  /** 当前请求是否请求过IP */
  private boolean ipFetched = false;

  public RmiIPFetcher(Request request){
    super(request);
  }

  public String getIP(){
    if(ipFetched){
      return ip;
    }
    RmiRequest oreq = request.find(Statics.FIND_REQUEST);
    String r = null;
    if(null!=oreq){
      r = oreq.getIp();
    }
    if("0:0:0:0:0:0:0:1".equals(r)){
      r = "127.0.0.1";
    }
    ip = r;
    ipFetched = true;
    return r;
  }

  public String getName(){
    return Statics.FIND_IP_FETCHER;
  }
}