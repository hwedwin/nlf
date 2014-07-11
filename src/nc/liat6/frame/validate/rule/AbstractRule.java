package nc.liat6.frame.validate.rule;

import nc.liat6.frame.validate.IValidatorRule;

/**
 * 验证规则抽象
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractRule implements IValidatorRule{

  private String errorMessage;

  public String getErrorMessage(){
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage){
    this.errorMessage = errorMessage;
  }
}
