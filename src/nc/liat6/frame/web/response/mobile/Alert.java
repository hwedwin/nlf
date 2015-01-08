package nc.liat6.frame.web.response.mobile;

/**
 * 返回 - 移动端的弹出框
 * 
 * @author liat6
 * 
 */
public class Alert extends nc.liat6.frame.web.response.Alert{
  public Alert(){
    super();
    setXtype("mobile.Alert");
  }
  
  public Alert(String content){
    super(content);
  }
  
  public Alert(String title,String content){
    super(title,content);
  }
}