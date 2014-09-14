package nc.liat6.frame.xml;

import nc.liat6.frame.xml.element.IXmlElement;
import nc.liat6.frame.xml.parser.BeanParser;
import nc.liat6.frame.xml.parser.XMLParser;
import nc.liat6.frame.xml.wrapper.XMLWrapper;

/**
 * XML转换器，默认开启严格模式
 * <p>
 * 转换会遵循以下约定：
 * <ul>
 * <li>开启严格模式后，子节点为属性的，父节点设置type="bean"属性。</li>
 * <li>子节点为数组的，父节点必须设置type="list"属性。</li>
 * </ul>
 * </p>
 * 
 * @author 6tail
 * 
 */
public class XML{

  /** 默认根节点 */
  public static final String DEFAULT_ROOT_TAG = "data";
  /** 可设置的全局根节点 */
  public static String ROOT_TAG = DEFAULT_ROOT_TAG;
  /** 是否极简（不缩进不换行）的默认设置 */
  public static final boolean DEFAULT_TINY = true;
  /** 是否极简（不缩进不换行）的全局设置 */
  public static boolean TINY = DEFAULT_TINY;
  /** 默认开启严格模式 */
  public static final boolean DEFAULT_STRICT = true;
  /** 是否开启严格模式的全局设置 */
  public static boolean STRICT = DEFAULT_STRICT;

  private XML(){}

  /**
   * 采用全局根节点，将对象转换为XML字符串
   * 
   * @param o 对象
   * @return XML字符串
   */
  public static String toXML(Object o){
    return toXML(o,TINY);
  }

  /**
   * 采用全局根节点，将对象转换为XML字符串
   * 
   * @param o 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @return XML字符串
   */
  public static String toXML(Object o,boolean tiny){
    return toXML(o,tiny,ROOT_TAG);
  }

  /**
   * 将对象转换为XML字符串
   * 
   * @param o 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @param rootTag 自定义根节点
   * @return XML字符串
   */
  public static String toXML(Object o,boolean tiny,String rootTag){
    return toXML(o,tiny,rootTag,STRICT);
  }

  /**
   * 将对象转换为XML字符串
   * 
   * @param o 对象
   * @param tiny 是否是极简（不缩进不换行）
   * @param rootTag 自定义根节点
   * @param strict 是否开启严格模式，开启后非数组父节点强制添加type="bean"属性
   * @return XML字符串
   */
  public static String toXML(Object o,boolean tiny,String rootTag,boolean strict){
    StringBuilder s = new StringBuilder();
    s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    if(!tiny){
      s.append("\r\n");
    }
    s.append(new XMLWrapper(tiny,strict).wrap(o,rootTag));
    return s.toString();
  }

  /**
   * 将XML字符串转换为对象，可能会丢失节点属性和部分注释
   * 
   * @param s XML字符串
   * @return 对象
   */
  public static <T>T toBean(String s){
    return toBean(fromXML(s));
  }

  /**
   * 将XML转换为通用封装
   * 
   * @param s XML字符串
   * @return 对象
   */
  public static IXmlElement fromXML(String s){
    return new XMLParser().parse(s);
  }

  /**
   * 将XML通用封装转换为对象，可能会丢失节点属性和部分注释
   * 
   * @param xe
   * @return
   */
  public static <T>T toBean(IXmlElement xe){
    return new BeanParser().parse(xe);
  }
}
