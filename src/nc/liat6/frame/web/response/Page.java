package nc.liat6.frame.web.response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;

/**
 * 返回 - 页面
 *
 * @author liat6
 *
 */
public class Page{

  /** 默认传递对象名 */
  public static final String DEFAULT_ATTRIBUTE_NAME = "data";
  /** 页面地址 */
  private String uri;
  /** 响应状态码 */
  private int status = 200;
  /** 传递参数 */
  private Map<String,Object> value = new HashMap<String,Object>();

  public Page(){}

  public Page(String uri){
    this.uri = uri;
  }

  public Page(String uri,Object o){
    this(uri);
    set(DEFAULT_ATTRIBUTE_NAME,o);
  }

  /**
   * 获取响应状态码
   *
   * @return 状态码
   */
  public int getStatus(){
    return status;
  }

  /**
   * 设置响应状态码
   *
   * @param status 状态码
   */
  public void setStatus(int status){
    this.status = status;
  }

  /**
   * 获取返回页面地址
   *
   * @return 返回页面地址
   */
  public String getUri(){
    return uri;
  }

  /**
   * 设置返回页面地址
   *
   * @param uri 返回页面地址
   */
  public void setUri(String uri){
    this.uri = uri;
  }

  /**
   * 设置传递参数
   *
   * @param key 参数名
   * @param value 参数值
   */
  public void set(String key,Object value){
    this.value.put(key,value);
  }

  /**
   * 获取传递参数的参数名集合
   *
   * @return
   */
  public Set<String> keySet(){
    return value.keySet();
  }

  /**
   * 获取参数值
   *
   * @param key 参数名
   * @return 参数值
   */
  public Object get(String key){
    return value.get(key);
  }

  /**
   * 调用后可将上一个页面传递来的参数自动传递到下一个页面
   */
  public void deliver(){
    Request req = Context.get(Statics.REQUEST);
    HttpServletRequest oreq = req.find(Statics.FIND_REQUEST);
    Map<String,String> m = req.getParams();
    for(String key:m.keySet()){
      oreq.setAttribute(key,m.get(key));
    }
  }
}