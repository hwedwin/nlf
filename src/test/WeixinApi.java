package test;

import nlf.plugin.weixin.msg.bean.IResponseMsg;
import nlf.plugin.weixin.msg.bean.impl.TextMsg;
import nlf.plugin.weixin.msg.core.impl.DefaultMsgController;
import nlf.plugin.weixin.msg.core.impl.DefaultMsgHandler;

/**
 * 接收微信公众号服务器发来请求的控制器
 * 
 * @author 6tail
 *
 */
public class WeixinApi{
  /** 微信公众号中设置的令牌 */
  public static final String TOKEN = "";

  /**
   * 处理微信公众号服务器发来的请求
   * 
   * @return 响应消息内容
   */
  public String doRequest(){
    return new DefaultMsgController(TOKEN).handle(new DefaultMsgHandler(){
      @Override
      public IResponseMsg onText(TextMsg msg){
        TextMsg tm = new TextMsg();
        tm.setToUser(msg.getFromUser());
        tm.setFromUser(msg.getToUser());
        tm.setContent("Hello world!");
        return tm;
      }
    });
  }
}