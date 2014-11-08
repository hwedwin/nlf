package nc.liat6.frame.json;

import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;

/**
 * 不支持的JSON格式
 *
 * @author 6tail
 *
 */
public class JsonFormatException extends NlfException{

  /** serialVersionUID */
  private static final long serialVersionUID = 3932096485367560097L;
  /** 错误提示 */
  private static final String MSG = "json.not_support";

  /**
   * 构造默认异常
   */
  public JsonFormatException(){
    super(L.get(MSG));
  }

  /**
   * 构造异常
   *
   * @param message 异常内容
   */
  public JsonFormatException(String message){
    super(L.get(MSG)+" : "+message);
  }

  /**
   * 构造异常
   *
   * @param cause
   */
  public JsonFormatException(Throwable cause){
    super(L.get(MSG),cause);
  }

  /**
   * 构造异常
   *
   * @param message 异常内容
   * @param cause
   */
  public JsonFormatException(String message,Throwable cause){
    super(L.get(MSG)+" : "+message,cause);
  }
}