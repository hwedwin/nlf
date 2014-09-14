package nc.liat6.frame.xml.element;

import java.io.Serializable;
import java.util.Map;

/**
 * XML对象接口
 * 
 * @author 6tail
 * 
 */
public interface IXmlElement extends Serializable{

  /**
   * 类型
   * 
   * @return 类型
   */
  public XmlType type();

  /**
   * 转换为{}类型
   * 
   * @return
   */
  public XmlMap toXmlMap();

  /**
   * 转换为[]类型
   * 
   * @return
   */
  public XmlList toXmlList();

  /**
   * 转换为布尔值
   * 
   * @return
   */
  public XmlBool toXmlBool();

  /**
   * 转换为数字类型
   * 
   * @return
   */
  public XmlNumber toXmlNumber();

  /**
   * 转换为字符串类型
   * 
   * @return
   */
  public XmlString toXmlString();

  /**
   * 根据路径获取节点值,只有XmlMap和XmlList才实现该方法
   * 
   * @param path 
   *          路径，如<a><b>1</b></a>，select("a.b")结果为1，如<as><a>1</a><a>2</a></as>，select("0")结果为1，如果无法找到指定节点
   *          ，返回null
   * @return 节点值
   */
  public IXmlElement select(String path);

  /**
   * 获得节点名称
   * 
   * @return 节点名称
   */
  public String getName();

  /**
   * 设置节点名称
   * 
   * @param name 节点名称
   */
  public void setName(String name);

  /**
   * 获取节点注释
   * 
   * @return 注释
   */
  public String getNote();

  /**
   * 设置节点注释
   * 
   * @param note 注释
   */
  public void setNote(String note);

  /**
   * 获取节点属性，如果不存在属性名，返回null
   * 
   * @param attributeName 属性名
   * @return 属性值
   */
  public String getAttribute(String attributeName);

  /**
   * 设置节点属性
   * 
   * @param attributeName 属性名
   * @param value 属性值
   */
  public void setAttribute(String attributeName,String value);

  /**
   * 获取节点所有属性
   * 
   * @return 所有属性
   */
  public Map<String,String> getAttributes();

  /**
   * 覆盖节点所有属性
   * 
   * @param attributes 所有属性
   */
  public void setAttributes(Map<String,String> attributes);
}
