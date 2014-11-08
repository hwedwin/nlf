package nc.liat6.frame.json;

import nc.liat6.frame.json.element.IJsonElement;
import nc.liat6.frame.json.parser.BeanParser;
import nc.liat6.frame.json.parser.JsonParser;
import nc.liat6.frame.json.wrapper.JsonWrapper;

/**
 * JSON转换器
 *
 * @author 6tail
 *
 */
public final class JSON{

  /** 单引号模式 */
  public static final String QUOTE_SINGLE = "'";
  /** 双引号模式 */
  public static final String QUOTE_MULTIPLE = "\"";
  /** 全局设置，字符串首尾的引号 */
  public static String quote = QUOTE_MULTIPLE;
  /** 全局设置，数字是否使用引号 */
  public static boolean numberQuoted = true;
  /** 全局设置，是否极简（不缩进不换行） */
  public static boolean tiny = true;

  private JSON(){}

  /**
   * 将对象转换为JSON字符串
   *
   * @param obj 对象
   * @return JSON字符串
   */
  public static String toJson(Object obj){
    return toJson(obj,tiny);
  }

  /**
   * 将对象转换为JSON字符串
   *
   * @param obj 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @return JSON字符串
   */
  public static String toJson(Object obj,boolean tiny){
    return toJson(obj,tiny,quote);
  }

  /**
   * 将对象转换为JSON字符串
   *
   * @param obj 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @param quote 字符串首尾的引号
   * @return JSON字符串
   */
  public static String toJson(Object obj,boolean tiny,String quote){
    return toJson(obj,tiny,quote,numberQuoted);
  }

  /**
   * 将对象转换为JSON字符串
   *
   * @param obj 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @param numberQuoted 数字是否使用引号
   * @return JSON字符串
   */
  public static String toJson(Object obj,boolean tiny,boolean numberQuoted){
    return toJson(obj,tiny,quote,numberQuoted);
  }

  /**
   * 将对象转换为JSON字符串
   *
   * @param obj 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @param quote 字符串首尾引号
   * @param numberQuoted 数字是否使用引号
   * @return JSON字符串
   */
  public static String toJson(Object obj,boolean tiny,String quote,boolean numberQuoted){
    return new JsonWrapper(tiny,quote,numberQuoted).wrap(obj);
  }

  /**
   * 将JSON字符串转换为JSON对象接口
   *
   * @param str JSON字符串
   * @return JSON对象接口
   */
  public static IJsonElement fromJson(String str){
    return new JsonParser().parse(str);
  }

  /**
   * 将JSON字符串转换为对象
   *
   * @param str JSON字符串
   * @return 对象
   */
  public static <T>T toBean(String str){
    return toBean(fromJson(str));
  }

  /**
   * 将JSON对象接口转换为对象
   *
   * @param je JSON对象接口
   * @return 对象
   */
  public static <T>T toBean(IJsonElement jsonElement){
    return new BeanParser().parse(jsonElement);
  }
}