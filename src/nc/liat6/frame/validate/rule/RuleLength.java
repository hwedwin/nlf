package nc.liat6.frame.validate.rule;

import nc.liat6.frame.locale.L;

/**
 * 字符数限制（默认最大字符数限制，即不能超过多少个字符）
 * 
 * @author 6tail
 * 
 */
public class RuleLength extends AbstractRule{

  private int length = -1;

  public RuleLength(int length){
    this.length = length;
    setErrorMessage(L.get("reg.length_prefix")+length+L.get("reg.length_suffix"));
  }

  public RuleLength(String item,int length){
    this.length = length;
    setErrorMessage(item+L.get("reg.length_prefix")+length+L.get("reg.length_suffix"));
  }

  public boolean validate(String key){
    if(length<0)
      return true;
    if(null==key)
      return false;
    return key.length()<=length;
  }
  /**
   * 只能等于限定字符数
   * 
   * @author 6tail
   * 
   */
  public static class LengthEquals extends AbstractRule{

    private int length = -1;

    public LengthEquals(int length){
      this.length = length;
      setErrorMessage(L.get("reg.length_equals_prefix")+length+L.get("reg.length_equals_suffix"));
    }

    public LengthEquals(String item,int length){
      this.length = length;
      setErrorMessage(item+L.get("reg.length_equals_prefix")+length+L.get("reg.length_equals_suffix"));
    }

    public boolean validate(String key){
      if(length<0)
        return true;
      if(null==key)
        return false;
      return key.length()==length;
    }
  }
  /**
   * 只能大于或等于限定字符数
   * 
   * @author 6tail
   * 
   */
  public static class LengthNotLessThan extends AbstractRule{

    private int length = -1;

    public LengthNotLessThan(int length){
      this.length = length;
      setErrorMessage(L.get("reg.length_not_less_prefix")+length+L.get("reg.length_not_less_suffix"));
    }

    public LengthNotLessThan(String item,int length){
      this.length = length;
      setErrorMessage(item+L.get("reg.length_not_less_prefix")+length+L.get("reg.length_not_less_suffix"));
    }

    public boolean validate(String key){
      if(length<0)
        return true;
      if(null==key)
        return false;
      return key.length()>=length;
    }
  }
  /**
   * 只能大于限定字符数
   * 
   * @author 6tail
   * 
   */
  public static class LengthMoreThan extends AbstractRule{

    private int length = -1;

    public LengthMoreThan(int length){
      this.length = length;
      setErrorMessage(L.get("reg.length_more_prefix")+length+L.get("reg.length_more_suffix"));
    }

    public LengthMoreThan(String item,int length){
      this.length = length;
      setErrorMessage(item+L.get("reg.length_more_prefix")+length+L.get("reg.length_more_suffix"));
    }

    public boolean validate(String key){
      if(length<0)
        return true;
      if(null==key)
        return false;
      return key.length()>length;
    }
  }
}
