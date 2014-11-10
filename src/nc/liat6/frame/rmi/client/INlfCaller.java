package nc.liat6.frame.rmi.client;

import java.util.Map;
import nc.liat6.frame.json.element.IJsonElement;

/**
 * 远程调用客户端接口
 *
 * @author 6tail
 *
 */
public interface INlfCaller{

  /**
   * 调用
   *
   * @param ip 服务器地址
   * @param port 端口
   * @param klass 调用类
   * @param method 调用方法
   * @param args 参数
   * @return 返回结果
   */
  IJsonElement call(String ip,int port,String klass,String method,Map<String,String> args);
}