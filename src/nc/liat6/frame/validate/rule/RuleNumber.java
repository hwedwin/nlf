package nc.liat6.frame.validate.rule;

import nc.liat6.frame.validate.RegUtil;

/**
 * 数字
 * 
 * @author 6tail
 * 
 */
public class RuleNumber extends RuleRegex{

  /**
   * 整数
   * 
   * @author 6tail
   * 
   */
  public static class Integer extends RuleRegex{

    public Integer(String item){
      super(item,RegUtil.INTEGER);
    }

    public Integer(){
      this("");
    }
  }
  /**
   * 正整数
   * 
   * @author 6tail
   * 
   */
  public static class IntegerPositive extends RuleRegex{

    public IntegerPositive(String item){
      super(item,RegUtil.INTEGER_POSITIVE);
    }

    public IntegerPositive(){
      this("");
    }
  }
  /**
   * 负整数
   * 
   * @author 6tail
   * 
   */
  public static class IntegerNegtive extends RuleRegex{

    public IntegerNegtive(String item){
      super(item,RegUtil.INTEGER_NEGTIVE);
    }

    public IntegerNegtive(){
      this("");
    }
  }
  /**
   * 非正整数
   * 
   * @author 6tail
   * 
   */
  public static class IntegerNotPositive extends RuleRegex{

    public IntegerNotPositive(String item){
      super(item,RegUtil.INTEGER_NOT_POSITIVE);
    }

    public IntegerNotPositive(){
      this("");
    }
  }
  /**
   * 非负整数
   * 
   * @author 6tail
   * 
   */
  public static class IntegerNotNegtive extends RuleRegex{

    public IntegerNotNegtive(String item){
      super(item,RegUtil.INTEGER_NOT_NEGTIVE);
    }

    public IntegerNotNegtive(){
      this("");
    }
  }
  /**
   * 浮点数
   * 
   * @author 6tail
   * 
   */
  public static class Float extends RuleRegex{

    public Float(String item){
      super(item,RegUtil.FLOAT);
    }

    public Float(){
      this("");
    }
  }
  /**
   * 正浮点数
   * 
   * @author 6tail
   * 
   */
  public static class FloatPositive extends RuleRegex{

    public FloatPositive(String item){
      super(item,RegUtil.FLOAT_POSITIVE);
    }

    public FloatPositive(){
      this("");
    }
  }
  /**
   * 负浮点数
   * 
   * @author 6tail
   * 
   */
  public static class FloatNegtive extends RuleRegex{

    public FloatNegtive(String item){
      super(item,RegUtil.FLOAT_NEGTIVE);
    }

    public FloatNegtive(){
      this("");
    }
  }
  /**
   * 非正浮点数
   * 
   * @author 6tail
   * 
   */
  public static class FloatNotPositive extends RuleRegex{

    public FloatNotPositive(String item){
      super(item,RegUtil.FLOAT_NOT_POSITIVE);
    }

    public FloatNotPositive(){
      this("");
    }
  }
  /**
   * 非负浮点数
   * 
   * @author 6tail
   * 
   */
  public static class FloatNotNegtive extends RuleRegex{

    public FloatNotNegtive(String item){
      super(item,RegUtil.FLOAT_NOT_NEGTIVE);
    }

    public FloatNotNegtive(){
      this("");
    }
  }

  public RuleNumber(String item){
    super(item,RegUtil.NUMBER);
  }

  public RuleNumber(){
    this("");
  }
}
