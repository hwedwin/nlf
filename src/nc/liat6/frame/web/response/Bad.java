package nc.liat6.frame.web.response;

/**
 * 返回 - 错误信息
 * 
 * @author 6tail
 * 
 */
public class Bad{

  /** 消息内容 */
  private String thing;
  /** 数据对象 */
  private Object data;

  public Bad(String thing){
    setThing(thing);
  }

  public Bad(String thing,Object data){
    setThing(thing);
    setData(data);
  }

  /**
   * 设置消息内容
   * 
   * @param thing 消息内容
   */
  public void setThing(String thing){
    this.thing = thing;
  }

  /**
   * 获取消息内容
   * 
   * @return 消息内容
   */
  public String getThing(){
    return thing;
  }

  public String toString(){
    return thing;
  }

  /**
   * 获取数据对象
   * 
   * @return 数据对象
   */
  public Object getData(){
    return data;
  }

  /**
   * 设置数据对象
   * 
   * @param data 数据对象
   */
  public void setData(Object data){
    this.data = data;
  }
}
