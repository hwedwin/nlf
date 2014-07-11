package nc.liat6.frame.xml.element;

/**
 * XML数字类型节点
 * 
 * @author 6tail
 * 
 */
public class XmlNumber extends AbstractElement{

  private static final long serialVersionUID = -6485047817119697116L;
  private Number n;

  public XmlNumber(Number n){
    set(n);
  }

  public XmlType type(){
    return XmlType.NUMBER;
  }

  public String toString(){
    return n+"";
  }

  /**
   * 设置节点值
   * 
   * @param n 节点值
   */
  public void set(Number n){
    this.n = n;
  }

  /**
   * 获取节点值
   * 
   * @return 节点值
   */
  public Number value(){
    return n;
  }

  public XmlNumber toXmlNumber(){
    return this;
  }
}
