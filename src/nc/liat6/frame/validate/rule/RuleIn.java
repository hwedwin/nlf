package nc.liat6.frame.validate.rule;

import nc.liat6.frame.locale.L;

/**
 * 枚举，不区分大小写
 * 
 * @author 6tail
 * 
 */
public class RuleIn extends AbstractRule{

  private String[] in;

  public RuleIn(String item,String[] in){
    StringBuilder s = new StringBuilder();
    for(int i = 0;i<in.length;i++){
      s.append(in[i]);
      if(i<in.length-1){
        s.append(",");
      }
    }
    setErrorMessage(item+L.get("reg.in")+s);
    this.in = in;
  }

  public boolean validate(String key){
    if(null==key)
      return false;
    for(String s:in){
      if(s.equalsIgnoreCase(key)){
        return true;
      }
    }
    return false;
  }
}
