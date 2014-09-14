package nc.liat6.frame.db.entity;


/**
 * Bean转换规则接口
 * @author 6tail
 *
 */
public interface IBeanRule{
  
  /**
   * 获取指定属性名对应的key
   * @param property 属性名
   * @return key
   */
  public String getKey(String property);
}
