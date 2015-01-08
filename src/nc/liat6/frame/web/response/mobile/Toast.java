package nc.liat6.frame.web.response.mobile;

/**
 * 返回 - Toast消息
 * 
 * @author liat6
 * 
 */
public class Toast extends nc.liat6.frame.web.response.Tip{
  
  public Toast(){
    super();
    setXtype("mobile.Toast");
  }

  public Toast(String msg){
    this();
    setMsg(msg);
  }

  public Toast(String msg,int type){
    this();
    setMsg(msg);
    setType(type);
  }

  public Toast(Object data,String msg){
    this();
    setData(data);
    setMsg(msg);
  }

  public Toast(Object data,String msg,int type){
    this();
    setData(data);
    setMsg(msg);
    setType(type);
  }
}