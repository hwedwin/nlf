package nc.liat6.frame.paging;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求封装
 * 
 * @author 六特尔
 * 
 */
public class PagingParam implements Serializable{

  private static final long serialVersionUID = -7580050759495785747L;
  /** 当前控制器地址 */
  private String uri;
  /** 页面参数对 */
  private Map<String,String> params = new HashMap<String,String>();

  /**
   * 获取当前控制器地址
   * 
   * @return 当前控制器地址
   */
  public String getUri(){
    return uri;
  }

  /**
   * 设置当前控制器地址
   * 
   * @param uri 当前控制器地址
   */
  public void setUri(String uri){
    this.uri = uri;
  }

  /**
   * 获取页面参数对
   * 
   * @return 页面参数对
   */
  public Map<String,String> getParams(){
    return params;
  }

  /**
   * 设置页面参数对
   * 
   * @param params 页面参数对
   */
  public void setParams(Map<String,String> params){
    this.params = params;
  }

  /**
   * 设置页面参数
   * 
   * @param k 键
   * @param v 值
   */
  public void setParam(String k,String v){
    params.put(k,v);
  }

  /**
   * 获取页面参数
   * 
   * @param k 键
   */
  public String getParam(String k){
    return params.get(k);
  }
}