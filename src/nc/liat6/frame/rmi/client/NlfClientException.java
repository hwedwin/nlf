package nc.liat6.frame.rmi.client;

import nc.liat6.frame.locale.L;

/**
 * 远程调用客户端异常
 * 
 * @author 6tail
 * 
 */
public class NlfClientException extends RuntimeException{

  private static final long serialVersionUID = -8727717381933095480L;
  private static final String MESSAGE = "exception.rmi_client";

  public NlfClientException(){
    super(L.get(MESSAGE));
  }

  public NlfClientException(String message){
    super(L.get(MESSAGE)+" : "+message);
  }

  public NlfClientException(Throwable cause){
    super(L.get(MESSAGE),cause);
  }

  public NlfClientException(String message,Throwable cause){
    super(message,cause);
  }
}
