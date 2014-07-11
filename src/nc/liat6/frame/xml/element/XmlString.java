package nc.liat6.frame.xml.element;

import java.math.BigDecimal;

/**
 * XML字符串类型节点
 * 
 * @author 6tail
 * 
 */
public class XmlString extends AbstractElement{

  private static final long serialVersionUID = 7645328175874847771L;
  private String o;

  public XmlString(){}

  public XmlString(String o){
    this.o = o;
  }

  /**
   * 设置节点值
   * 
   * @param s 节点值
   */
  public void set(String s){
    o = s;
  }

  /**
   * 获取节点值
   * 
   * @return 节点值
   */
  public String toString(){
    return o;
  }

  public XmlType type(){
    return XmlType.STRING;
  }

  public XmlString toXmlString(){
    return this;
  }

  public XmlNumber toXmlNumber(){
    XmlNumber n = new XmlNumber(new BigDecimal(o));
    return n;
  }
}
