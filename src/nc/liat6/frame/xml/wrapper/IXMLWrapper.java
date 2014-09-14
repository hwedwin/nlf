package nc.liat6.frame.xml.wrapper;

/**
 * XML包装接口
 * 
 * @author 6tail
 * 
 */
public interface IXMLWrapper{

  /**
   * 包装
   * 
   * @param o 对象
   * @param tag 标签
   * @param level 层级
   * @return 字符串
   */
  public String wrap(Object o,String tag,int level);
}
