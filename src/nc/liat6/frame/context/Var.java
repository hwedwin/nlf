package nc.liat6.frame.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 上下文变量
 * 
 * @author liat6
 * 
 */
final class Var{

  /** 值 */
  private Map<Object,Object> value = new HashMap<Object,Object>();

  /**
   * 设置
   * 
   * @param k 键
   * @param v 值
   */
  void set(Object k,Object v){
    value.put(k,v);
  }

  /**
   * 获取
   * 
   * @param k 键
   * @return 值
   */
  Object get(Object k){
    return value.get(k);
  }

  /**
   * 移除
   * 
   * @param k 键
   */
  void remove(Object k){
    value.remove(k);
  }

  /**
   * 清空
   */
  void clear(){
    value.clear();
  }

  /**
   * 是否存在
   * 
   * @param k 键
   * @return true/false 是/否
   */
  boolean contains(Object k){
    return value.containsKey(k);
  }
}
