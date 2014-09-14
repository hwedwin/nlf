package nc.liat6.frame.xml.element;

import java.util.ArrayList;
import java.util.List;

/**
 * XML List类型节点
 * 
 * @author 6tail
 * 
 */
public class XmlList extends AbstractElement{

  private static final long serialVersionUID = -5572136068816099645L;
  private List<IXmlElement> l = new ArrayList<IXmlElement>();

  /**
   * 获取子节点数量
   * 
   * @return 子节点数量
   */
  public int size(){
    return l.size();
  }

  /**
   * 移除指定下标的节点
   * 
   * @param index 下标
   * @return 节点
   */
  public IXmlElement remove(int index){
    return l.remove(index);
  }

  /**
   * 获取指定下标的节点
   * 
   * @param index 下标
   * @return 节点
   */
  public IXmlElement get(int index){
    return l.get(index);
  }

  public XmlType type(){
    return XmlType.LIST;
  }

  /**
   * 在末尾处添加一个节点
   * 
   * @param o 节点
   */
  public void add(IXmlElement o){
    l.add(o);
  }

  public XmlList toXmlList(){
    return this;
  }

  public String toString(){
    return l.toString();
  }

  public IXmlElement select(String path){
    if(null==path){
      return null;
    }
    path = path.trim();
    return get(Integer.parseInt(path));
  }
}
