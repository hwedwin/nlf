package nc.liat6.frame.xml.element;

/**
 * XML布尔类型节点
 * 
 * @author 6tail
 * 
 */
public class XmlBool extends AbstractElement{

  private static final long serialVersionUID = 5735381484135872382L;
  private boolean o;

  public XmlBool(boolean o){
    set(o);
  }

  public String toString(){
    return o?"true":"false";
  }

  public XmlType type(){
    return XmlType.BOOL;
  }

  public XmlBool toXmlBool(){
    return this;
  }

  /**
   * 设置节点值
   * 
   * @param o true/false
   */
  public void set(boolean o){
    this.o = o;
  }

  /**
   * 获取节点值
   * 
   * @return true/false
   */
  public boolean value(){
    return o;
  }
}
