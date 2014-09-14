package nc.liat6.frame.xml.element;

import java.util.HashMap;
import java.util.Map;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;

/**
 * 抽象XML节点
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractElement implements IXmlElement{

  private String name;
  private String note;
  private Map<String,String> attributes = new HashMap<String,String>();
  private static final long serialVersionUID = 2947126141791929934L;

  public XmlMap toXmlMap(){
    throw new NlfException(L.get("xml.no_map"));
  }

  public XmlList toXmlList(){
    throw new NlfException(L.get("xml.no_list"));
  }

  public XmlBool toXmlBool(){
    throw new NlfException(L.get("xml.no_bool"));
  }

  public XmlNumber toXmlNumber(){
    throw new NlfException(L.get("xml.no_number"));
  }

  public XmlString toXmlString(){
    throw new NlfException(L.get("xml.no_string"));
  }

  public IXmlElement select(String path){
    return null;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getNote(){
    return note;
  }

  public void setNote(String note){
    this.note = note;
  }

  public Map<String,String> getAttributes(){
    return attributes;
  }

  public void setAttributes(Map<String,String> attributes){
    this.attributes = attributes;
  }

  public String getAttribute(String attributeName){
    return attributes.get(attributeName);
  }

  public void setAttribute(String attributeName,String value){
    attributes.put(attributeName,value);
  }
}
