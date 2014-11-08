package nc.liat6.frame.json.wrapper;

/**
 * JSON包装接口
 *
 * @author 6tail
 *
 */
public interface IJsonWrapper{

  /**
   * 包装
   *
   * @param obj 对象
   * @param level 层级
   * @return 字符串
   */
  String wrap(Object obj,int level);
}