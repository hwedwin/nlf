package nc.liat6.frame.xml.element;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * XML Map对象类型节点
 * 
 * @author 6tail
 * 
 */
public class XmlMap extends AbstractElement{

  private static final long serialVersionUID = -6974703310055519851L;
  private Map<String,IXmlElement> o = new HashMap<String,IXmlElement>();

  /**
   * 根据名称获取子节点，如果不存在，返回null
   * 
   * @param key 子节点名称
   * @return 子节点
   */
  public IXmlElement get(String key){
    return o.get(key);
  }

  /**
   * 设置子节点，如果存在指定名称，则覆盖该子节点，否则添加子节点
   * 
   * @param key 子几点名称
   * @param value 子节点
   */
  public void set(String key,IXmlElement value){
    o.put(key,value);
  }

  /**
   * 获取子节点名称Set
   * 
   * @return 子节点名称Set
   */
  public Set<String> keySet(){
    return o.keySet();
  }

  public XmlType type(){
    return XmlType.MAP;
  }

  public XmlMap toXmlMap(){
    return this;
  }

  public String toString(){
    return o.toString();
  }

  public IXmlElement select(String path){
    if(null==path){
      return null;
    }
    path = path.trim();
    if(!path.contains(".")){
      return get(path);
    }
    String[] k = path.split("\\.");
    IXmlElement je = get(k[0]);
    if(XmlType.MAP!=je.type()){
      return null;
    }
    for(int i = 1;i<k.length;i++){
      if(XmlType.MAP!=je.type()){
        return null;
      }
      je = je.toXmlMap().get(k[i]);
    }
    return je;
  }
}
