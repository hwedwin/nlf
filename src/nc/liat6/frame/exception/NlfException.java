package nc.liat6.frame.exception;

import nc.liat6.frame.locale.L;

/**
 * 框架异常
 * 
 * @author 6tail
 * 
 */
public class NlfException extends RuntimeException{

  private static final long serialVersionUID = 3199501826477240563L;
  private static final String MESSAGE = "exception.frame";

  public NlfException(){
    super(L.get(MESSAGE));
  }

  public NlfException(String message){
    super(message);
  }

  public NlfException(Throwable cause){
    this(L.get(MESSAGE),cause);
  }

  public NlfException(String message,Throwable cause){
    super(message,cause);
  }
}
