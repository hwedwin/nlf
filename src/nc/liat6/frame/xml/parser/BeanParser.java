package nc.liat6.frame.xml.parser;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.xml.element.IXmlElement;
import nc.liat6.frame.xml.element.XmlList;
import nc.liat6.frame.xml.element.XmlMap;

/**
 * 对象解析器，用于将IXmlElement转换为Bean
 * 
 * @author 6tail
 * 
 */
public class BeanParser{

  /**
   * 解析Map类型
   * 
   * @param jm
   * @return
   */
  private Bean genMap(XmlMap jm){
    Bean bean = new Bean();
    for(String key:jm.keySet()){
      IXmlElement ve = jm.get(key);
      if(null==ve){
        bean.set(key,null);
        continue;
      }
      switch(ve.type()){
        case STRING:
          bean.set(key,ve.toXmlString().toString(),ve.getNote());
          break;
        case NUMBER:
          bean.set(key,ve.toXmlNumber().value(),ve.getNote());
          break;
        case BOOL:
          bean.set(key,ve.toXmlBool().value(),ve.getNote());
          break;
        case MAP:
          bean.set(key,genMap(ve.toXmlMap()),ve.getNote());
          break;
        case LIST:
          bean.set(key,genList(ve.toXmlList()),ve.getNote());
          break;
      }
    }
    return bean;
  }

  /**
   * 解析List类型
   * 
   * @param jm
   * @return
   */
  private List<Object> genList(XmlList jm){
    List<Object> l = new ArrayList<Object>();
    for(int i = 0,n=jm.size();i<n;i++){
      IXmlElement ve = jm.get(i);
      if(null==ve){
        l.add(null);
      }
      switch(ve.type()){
        case STRING:
          l.add(ve.toXmlString().toString());
          break;
        case NUMBER:
          l.add(ve.toXmlNumber().value());
          break;
        case BOOL:
          l.add(ve.toXmlBool().value());
          break;
        case MAP:
          l.add(genMap(ve.toXmlMap()));
          break;
        case LIST:
          l.add(genList(ve.toXmlList()));
          break;
      }
    }
    return l;
  }

  @SuppressWarnings("unchecked")
  public <T>T parse(IXmlElement ve){
    if(null==ve){
      return null;
    }
    switch(ve.type()){
      case STRING:
        return (T)(ve.toXmlString().toString());
      case NUMBER:
        return (T)(ve.toXmlNumber().value());
      case BOOL:
        return (T)(new Boolean(ve.toXmlBool().value()));
      case MAP:
        return (T)(genMap(ve.toXmlMap()));
      case LIST:
        return (T)(genList(ve.toXmlList()));
    }
    return null;
  }
}