package nc.liat6.frame.web.response;

/**
 * 返回 - JSON
 * 
 * @author liat6
 * 
 */
public class Json{

  /** 是否成功 */
  private boolean success = true;
  /** 数据对象 */
  private Object data;
  /** 消息内容 */
  private String msg;

  public Json(){}

  public Json(Object data){
    setData(data);
  }

  public Json(Object data,String msg){
    setData(data);
    setMsg(msg);
  }

  public Json(Object data,String msg,boolean success){
    setData(data);
    setMsg(msg);
    setSuccess(success);
  }

  /**
   * 设置数据对象
   * 
   * @param data 数据对象
   */
  public void setData(Object data){
    this.data = data;
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
   * 设置成功标识
   * 
   * @param success 是否成功
   */
  public void setSuccess(boolean success){
    this.success = success;
  }

  /**
   * 获取成功标识
   * 
   * @return 是否成功
   */
  public boolean isSuccess(){
    return success;
  }

  public String toString(){
    return success+":"+msg+":"+data;
  }

  /**
   * 获取消息内容
   * 
   * @return 消息内容
   */
  public String getMsg(){
    return msg;
  }

  /**
   * 设置消息内容
   * 
   * @param msg 消息内容
   */
  public void setMsg(String msg){
    this.msg = msg;
  }
}
