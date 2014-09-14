package nc.liat6.frame.xml.wrapper;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import nc.liat6.frame.xml.XML;

/**
 * XML包装器
 * 
 * @author 6tail
 * 
 */
public class XMLWrapper implements IXMLWrapper{

  private boolean tiny = XML.TINY;
  private boolean strict = XML.STRICT;

  public XMLWrapper(boolean tiny,boolean strict){
    this.tiny = tiny;
    this.strict = strict;
  }

  private String wrapNumber(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    s.append(">");
    s.append(o);
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  private String wrapBool(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    s.append(">");
    s.append(o);
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  private String wrapString(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    s.append(">");
    String os = o+"";
    if(os.contains("<")||os.contains(">")){
      s.append("<![CDATA[");
      s.append(os);
      s.append("]]>");
    }else{
      s.append(os);
    }
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  private String wrapDate(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    s.append(">");
    s.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)o));
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  private String wrapArray(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    Object[] arr = (Object[])o;
    s.append("<");
    s.append(tag);
    if(strict){
      s.append(" type=\"list\"");
    }
    s.append(">");
    if(!tiny){
      s.append("\r\n");
    }
    for(int i = 0;i<arr.length;i++){
      if(!tiny){
        for(int j = 0;j<2*(level+1);j++){
          s.append(" ");
        }
      }
      s.append(wrap(arr[i],tag.endsWith("s")||tag.endsWith("S")?tag.substring(0,tag.length()-1):tag,level+1));
      if(!tiny){
        s.append("\r\n");
      }
    }
    if(!tiny){
      for(int i = 0;i<2*level;i++){
        s.append(" ");
      }
    }
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  private String wrapCollection(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    if(strict){
      s.append(" type=\"list\"");
    }
    s.append(">");
    if(!tiny){
      s.append("\r\n");
    }
    Collection<?> c = (Collection<?>)o;
    Iterator<?> it = c.iterator();
    while(it.hasNext()){
      if(!tiny){
        for(int j = 0;j<2*(level+1);j++){
          s.append(" ");
        }
      }
      s.append(wrap(it.next(),tag.endsWith("s")||tag.endsWith("S")?tag.substring(0,tag.length()-1):tag,level+1));
      if(!tiny){
        s.append("\r\n");
      }
    }
    if(!tiny){
      for(int i = 0;i<2*level;i++){
        s.append(" ");
      }
    }
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  private String wrapMap(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    if(strict){
      s.append(" type=\"bean\"");
    }
    s.append(">");
    if(!tiny){
      s.append("\r\n");
    }
    Map<?,?> m = (Map<?,?>)o;
    Iterator<?> it = m.keySet().iterator();
    while(it.hasNext()){
      if(!tiny){
        for(int j = 0;j<2*(level+1);j++){
          s.append(" ");
        }
      }
      Object key = it.next();
      s.append(wrap(m.get(key),key+"",level+1));
      if(!tiny){
        s.append("\r\n");
      }
    }
    if(!tiny){
      for(int i = 0;i<2*level;i++){
        s.append(" ");
      }
    }
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  private String wrapObject(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    s.append(new ObjectXMLWrapper(tiny,strict).wrap(o,tag,level));
    return s.toString();
  }

  public String wrap(Object o,String tag){
    return wrap(o,tag,0);
  }

  public String wrap(Object o,String tag,int level){
    StringBuilder s = new StringBuilder();
    if(null==o){
      s.append(wrapNumber("",tag,level));
    }else if(o instanceof Number){
      s.append(wrapNumber(o,tag,level));
    }else if(o instanceof Boolean){
      s.append(wrapBool(o,tag,level));
    }else if(o instanceof Character||o instanceof String){
      s.append(wrapString(o,tag,level));
    }else if(o instanceof Date){
      s.append(wrapDate(o,tag,level));
    }else if(o.getClass().isArray()){
      s.append(wrapArray(o,tag,level));
    }else if(o instanceof Collection){
      s.append(wrapCollection(o,tag,level));
    }else if(o instanceof Map){
      s.append(wrapMap(o,tag,level));
    }else if(o instanceof Enum){
      s.append(wrapString(o,tag,level));
    }else{
      s.append(wrapObject(o,tag,level));
    }
    return s.toString();
  }
}
