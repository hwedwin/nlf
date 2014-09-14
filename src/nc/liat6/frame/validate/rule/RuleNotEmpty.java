package nc.liat6.frame.validate.rule;

import nc.liat6.frame.locale.L;

/**
 * 不能为空
 * 
 * @author 6tail
 * 
 */
public class RuleNotEmpty extends AbstractRule{

  public RuleNotEmpty(String item){
    setErrorMessage(item+L.get("reg.not_empty"));
  }

  public RuleNotEmpty(){
    setErrorMessage(L.get("reg.not_empty"));
  }

  public boolean validate(String key){
    if(null==key)
      return false;
    return key.length()>0;
  }
}
