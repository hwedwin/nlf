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
public class JSON{

  /** 单引号模式 */
  public static final String QUOTE_SINGLE = "'";
  /** 双引号模式 */
  public static final String QUOTE_MULTIPLE = "\"";
  /** 默认引号模式 */
  public static final String QUOTE_DEFAULT = QUOTE_MULTIPLE;
  /** 数字是否使用引号的默认设置 */
  public static final boolean DEFAULT_NUMBER_QUOTED = true;
  /** 数字是否使用引号 */
  public static final boolean NUMBER_QUOTED = DEFAULT_NUMBER_QUOTED;
  /** 是否极简（不缩进不换行）的默认设置 */
  public static final boolean DEFAULT_TINY = true;
  /** 是否极简（不缩进不换行）的全局设置 */
  public static boolean TINY = DEFAULT_TINY;

  private JSON(){}

  /**
   * 将对象转换为JSON字符串，采用全局极简设置
   * 
   * @param o 对象
   * @return JSON字符串
   */
  public static String toJson(Object o){
    return toJson(o,TINY);
  }

  /**
   * 将对象转换为JSON字符串
   * 
   * @param o 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @return JSON字符串
   */
  public static String toJson(Object o,boolean tiny){
    return toJson(o,tiny,QUOTE_DEFAULT);
  }

  /**
   * 将对象转换为JSON字符串
   * 
   * @param o 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @param quote 字符串首尾引号
   * @return JSON字符串
   */
  public static String toJson(Object o,boolean tiny,String quote){
    return toJson(o,tiny,QUOTE_DEFAULT,NUMBER_QUOTED);
  }

  /**
   * 将对象转换为JSON字符串
   * 
   * @param o 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @param numberQuoted 数字类型是否使用引号
   * @return JSON字符串
   */
  public static String toJson(Object o,boolean tiny,boolean numberQuoted){
    return toJson(o,tiny,QUOTE_DEFAULT,numberQuoted);
  }

  /**
   * 将对象转换为JSON字符串
   * 
   * @param o 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @param quote 字符串首尾引号
   * @param numberQuoted 数字类型是否使用引号
   * @return JSON字符串
   */
  public static String toJson(Object o,boolean tiny,String quote,boolean numberQuoted){
    return new JsonWrapper(tiny,quote,numberQuoted).wrap(o);
  }

  /**
   * 将JSON字符串转换为通用封装
   * 
   * @param s JSON字符串
   * @return 通用封装
   */
  public static IJsonElement fromJson(String s){
    return new JsonParser().parse(s);
  }

  /**
   * 将JSON字符串转换为对象
   * 
   * @param s JSON字符串
   * @return 对象
   */
  public static <T>T toBean(String s){
    return toBean(fromJson(s));
  }

  /**
   * 将通用封装转换为对象
   * 
   * @param je 通用封装
   * @return 对象
   */
  public static <T>T toBean(IJsonElement je){
    return new BeanParser().parse(je);
  }
}
