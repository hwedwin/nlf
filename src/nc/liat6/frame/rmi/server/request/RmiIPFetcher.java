package nc.liat6.frame.rmi.server.request;

import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.impl.AppExecute;
import nc.liat6.frame.execute.request.IIPFetcher;

/**
 * RMI应用IP获取器，在确实需要用到IP的时候才解析
 * 
 * @author 6tail
 * 
 */
public class RmiIPFetcher implements IIPFetcher{

  /** 当前请求 */
  private Request request;
  /** 缓存IP */
  private String ip;
  /** 当前请求是否请求过IP */
  private boolean ipFetched = false;

  public RmiIPFetcher(Request request){
    this.request = request;
  }

  public String getIP(){
    if(ipFetched){
      return ip;
    }
    RmiRequest oreq = request.find(AppExecute.TAG_REQUEST);
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
}
