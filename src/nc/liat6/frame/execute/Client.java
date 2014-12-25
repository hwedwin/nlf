package nc.liat6.frame.execute;

import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.request.IIPFetcher;

/**
 * 客户端
 * 
 * @author 6tail
 *
 */
public class Client{
  /** 客户端类型：电脑 */
  public static final int TYPE_COMPUTER = 0;
  /** 客户端类型：移动设备 */
  public static final int TYPE_MOBILE = 1;
  /** 客户端类型：微信 */
  public static final int TYPE_WEIXIN = 2;
  /** 客户端类型 */
  private int type = TYPE_COMPUTER;
  /** userAgent */
  private String userAgent;

  /**
   * 获取userAgent
   * 
   * @return userAgent
   */
  public String getUserAgent(){
    return userAgent;
  }

  /**
   * 设置userAgent
   * 
   * @param userAgent
   */
  public void setUserAgent(String userAgent){
    this.userAgent = userAgent;
  }

  /**
   * 获取客户端类型
   * 
   * @return 客户端类型
   */
  public int getType(){
    return type;
  }

  /**
   * 设置客户端类型
   * 
   * @param type 客户端类型
   */
  public void setType(int type){
    this.type = type;
  }

  /**
   * 是否是电脑
   * 
   * @return
   */
  public boolean isComputer(){
    return TYPE_COMPUTER==type;
  }

  /**
   * 是否是移动设备
   * 
   * @return
   */
  public boolean isMobile(){
    return TYPE_MOBILE==type||TYPE_WEIXIN==type;
  }

  /**
   * 是否是微信
   * 
   * @return
   */
  public boolean isWeixin(){
    return TYPE_WEIXIN==type;
  }

  /**
   * 获取客户端IP
   * 
   * @return 客户端IP
   */
  public String getIP(){
    Request r = Context.get(Statics.REQUEST);
    IIPFetcher fetcher = r.find(Statics.FIND_IP_FETCHER);
    return fetcher.getIP();
  }
}