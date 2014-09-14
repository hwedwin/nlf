package nc.liat6.frame.util;

import java.io.Serializable;

/**
 * 字符串键值对
 * 
 * @author 6tail
 * 
 */
public class Pair implements Serializable{

  private static final long serialVersionUID = 3519595853819456600L;
  /** 键 */
  private String key;
  /** 值 */
  private String value;

  public Pair(){}

  public Pair(String key,String value){
    this.key = key;
    this.value = value;
  }

  /**
   * 获取键
   * 
   * @return 键
   */
  public String getKey(){
    return key;
  }

  /**
   * 设置键
   * 
   * @param key 键
   */
  public void setKey(String key){
    this.key = key;
  }

  /**
   * 获取值
   * 
   * @return 值
   */
  public String getValue(){
    return value;
  }

  /**
   * 设置值
   * 
   * @param value 值
   */
  public void setValue(String value){
    this.value = value;
  }
}
