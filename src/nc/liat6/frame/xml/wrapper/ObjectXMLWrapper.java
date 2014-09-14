package nc.liat6.frame.xml.wrapper;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Iterator;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.klass.BeanPool;
import nc.liat6.frame.xml.XML;
import nc.liat6.frame.xml.element.IXmlElement;
import nc.liat6.frame.xml.element.XmlList;
import nc.liat6.frame.xml.element.XmlMap;

/**
 * 对象XML包装器
 * 
 * @author 6tail
 * 
 */
public class ObjectXMLWrapper implements IXMLWrapper{

  private boolean tiny = XML.TINY;
  private boolean strict = XML.STRICT;

  public ObjectXMLWrapper(boolean tiny,boolean strict){
    this.tiny = tiny;
    this.strict = strict;
  }

  private String wrapBean(Bean b,String tag,int level){
    StringBuilder s = new StringBuilder();
    Iterator<String> it = b.keySet().iterator();
    while(it.hasNext()){
      String k = it.next();
      String note = b.getNote(k);
      if(null!=note&&note.trim().length()>0){
        if(!tiny){
          for(int i = 0;i<2*(level+1);i++){
            s.append(" ");
          }
        }
        s.append("<!--"+note+"-->");
        if(!tiny){
          s.append("\r\n");
        }
      }
      if(!tiny){
        for(int i = 0;i<2*(level+1);i++){
          s.append(" ");
        }
      }
      s.append(new XMLWrapper(tiny,strict).wrap(b.get(k),k,level+1));
      if(!tiny){
        s.append("\r\n");
      }
    }
    return s.toString();
  }

  private String wrapXmlElement(IXmlElement el,String tag,int level){
    StringBuilder s = new StringBuilder();
    String note = el.getNote();
    if(null!=note&&note.trim().length()>0){
      if(!tiny){
        for(int i = 0;i<2*level;i++){
          s.append(" ");
        }
      }
      s.append("<!--"+note+"-->");
      if(!tiny){
        s.append("\r\n");
      }
    }
    if(!tiny){
      for(int i = 0;i<2*level;i++){
        s.append(" ");
      }
    }
    s.append("<");
    s.append(tag);
    if(strict){
      switch(el.type()){
        case MAP:
          s.append(" type=\"bean\"");
          break;
        case LIST:
          s.append(" type=\"list\"");
          break;
      }
    }
    if(el.getAttributes().size()>0){
      for(String k:el.getAttributes().keySet()){
        s.append(" ");
        s.append(k);
        s.append("=\"");
        s.append(el.getAttribute(k));
        s.append("\"");
      }
    }
    s.append(">");
    switch(el.type()){
      case BOOL:
        s.append(el.toXmlBool().value());
        s.append("</"+tag+">");
        break;
      case NUMBER:
        s.append(el.toXmlNumber().value());
        s.append("</"+tag+">");
        break;
      case STRING:
        s.append(el.toXmlString().toString());
        s.append("</"+tag+">");
        break;
      case LIST:
        if(!tiny){
          s.append("\r\n");
        }
        XmlList l = el.toXmlList();
        for(int i = 0;i<l.size();i++){
          IXmlElement e = l.get(i);
          s.append(wrapXmlElement(e,e.getName(),level+1));
          if(!tiny){
            if(i<l.size()-1){
              s.append("\r\n");
            }
          }
        }
        if(!tiny){
          s.append("\r\n");
          for(int i = 0;i<2*level;i++){
            s.append(" ");
          }
        }
        s.append("</"+tag+">");
        break;
      case MAP:
        if(!tiny){
          s.append("\r\n");
        }
        XmlMap m = el.toXmlMap();
        Iterator<String> it = m.keySet().iterator();
        while(it.hasNext()){
          String k = it.next();
          IXmlElement e = m.get(k);
          s.append(wrapXmlElement(e,e.getName(),level+1));
          if(!tiny){
            if(it.hasNext()){
              s.append("\r\n");
            }
          }
        }
        if(!tiny){
          s.append("\r\n");
          for(int i = 0;i<2*level;i++){
            s.append(" ");
          }
        }
        s.append("</"+tag+">");
        break;
    }
    return s.toString();
  }

  private String wrapObject(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    BeanInfo info = BeanPool.getBeanInfo(o.getClass());
    PropertyDescriptor[] props = info.getPropertyDescriptors();
    for(int i = 0;i<props.length;i++){
      if(!tiny){
        for(int j = 0;j<2*(level+1);j++){
          s.append(" ");
        }
      }
      PropertyDescriptor desc = props[i];
      try{
        if(null==desc.getReadMethod()){
          s.append("<");
          s.append(desc.getName());
          s.append(">");
          s.append("</");
          s.append(desc.getName());
          s.append(">");
        }else{
          s.append(new XMLWrapper(tiny,strict).wrap(desc.getReadMethod().invoke(o,new Object[0]),desc.getName(),level+1));
        }
      }catch(Exception e){
        throw new NlfException(e);
      }
      if(!tiny){
        s.append("\r\n");
      }
    }
    return s.toString();
  }

  public String wrap(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    if(o instanceof IXmlElement){
      s.append(wrapXmlElement((IXmlElement)o,tag,level));
    }else{
      s.append("<");
      s.append(tag);
      if(strict){
        s.append(" type=\"bean\"");
      }
      s.append(">");
      if(!tiny){
        s.append("\r\n");
      }
      if(o instanceof Bean){
        s.append(wrapBean((Bean)o,tag,level));
      }else{
        s.append(wrapObject(o,tag,level));
      }
      if(!tiny){
        for(int i = 0;i<2*level;i++){
          s.append(" ");
        }
      }
      s.append("</"+tag+">");
    }
    return s.toString();
  }
}
