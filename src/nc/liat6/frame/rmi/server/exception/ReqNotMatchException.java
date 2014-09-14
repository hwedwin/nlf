package nc.liat6.frame.rmi.server.exception;

import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;

/**
 * 请求不匹配异常
 * 
 * @author 6tail
 * 
 */
public class ReqNotMatchException extends NlfException{

  private static final long serialVersionUID = 8373701140926352278L;
  private static final String MESSAGE = "exception.rmi_req_not_match";

  public ReqNotMatchException(){
    super(L.get(MESSAGE));
  }

  public ReqNotMatchException(String message){
    super(L.get(MESSAGE)+" : "+message);
  }

  public ReqNotMatchException(Throwable cause){
    super(L.get(MESSAGE),cause);
  }

  public ReqNotMatchException(String message,Throwable cause){
    super(message,cause);
  }
}
