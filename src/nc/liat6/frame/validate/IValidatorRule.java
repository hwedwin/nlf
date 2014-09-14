package nc.liat6.frame.validate;

/**
 * 验证器规则接口
 * 
 * @author 6tail
 * 
 */
public interface IValidatorRule{

  /**
   * 获取错误信息
   * 
   * @return 错误信息
   */
  public String getErrorMessage();

  /**
   * 检查
   * 
   * @param key 要验证的值
   * @return 是否验证通过
   */
  public boolean validate(String key);
}
