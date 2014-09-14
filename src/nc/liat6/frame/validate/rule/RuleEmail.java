package nc.liat6.frame.validate.rule;

import nc.liat6.frame.validate.RegUtil;

/**
 * 电子邮件
 * 
 * @author 6tail
 * 
 */
public class RuleEmail extends RuleRegex{

  public RuleEmail(String item){
    super(item,RegUtil.EMAIL);
  }

  public RuleEmail(){
    this("");
  }
}
