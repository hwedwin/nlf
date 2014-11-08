package nc.liat6.frame.json.wrapper;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * JSON包装器
 *
 * @author 6tail
 *
 */
public class JsonWrapper extends AbstractJsonWrapper{

  public JsonWrapper(){
    super();
  }

  public JsonWrapper(boolean tiny,String quote,boolean numberQuoted){
    super(tiny,quote,numberQuoted);
  }

  private String wrapNumber(Object obj){
    StringBuilder s = new StringBuilder();
    if(numberQuoted){
      s.append(quote);
    }
    s.append(obj);
    if(numberQuoted){
      s.append(quote);
    }
    return s.toString();
  }

  private String wrapBool(Object obj){
    return obj.toString();
  }

  private String wrapString(Object obj,int level){
    return new StringWrapper(tiny,quote,numberQuoted).wrap(obj,level);
  }

  private String wrapDate(Object o,int level){
    return wrapString(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)o),level);
  }

  private String wrapArray(Object o,int level){
    Object[] l = (Object[])o;
    return wrapCollection(Arrays.asList(l),level);
  }

  private String wrapCollection(Object o,int level){
    IndentString s = new IndentString(tiny);
    s.add('[');
    Collection<?> l = (Collection<?>)o;
    if(l.size()>0){
      s.addLine();
      Iterator<?> it = l.iterator();
      while(it.hasNext()){
        s.addSpace(level+1);
        s.add(wrap(it.next(),level+1));
        if(it.hasNext()){
          s.add(',');
        }
        s.addLine();
      }
      s.addSpace(level);
    }
    s.add(']');
    return s.toString();
  }

  private String wrapMap(Object o,int level){
    IndentString s = new IndentString(tiny);
    Map<?,?> m = (Map<?,?>)o;
    s.add('{');
    s.addLine();
    Iterator<?> it = m.keySet().iterator();
    while(it.hasNext()){
      s.addSpace(level+1);
      Object key = it.next();
      s.add(new StringWrapper().wrap(key+"",level));
      s.add(':');
      s.add(wrap(m.get(key),level+1));
      if(it.hasNext()){
        s.add(',');
      }
      s.addLine();
    }
    s.addSpace(level);
    s.add('}');
    return s.toString();
  }

  public String wrapObject(Object o,int level){
    return new ObjectWrapper(tiny,quote,numberQuoted).wrap(o,level);
  }

  public String wrap(Object o){
    return wrap(o,0);
  }

  public String wrap(Object o,int level){
    StringBuilder s = new StringBuilder();
    if(null==o){
      return "null";
    }
    if(o instanceof Number){
      s.append(wrapNumber(o));
    }else if(o instanceof Boolean){
      s.append(wrapBool(o));
    }else if(o instanceof Character||o instanceof String){
      s.append(wrapString(o,level));
    }else if(o instanceof Date){
      s.append(wrapDate(o,level));
    }else if(o.getClass().isArray()){
      s.append(wrapArray(o,level));
    }else if(o instanceof Collection){
      s.append(wrapCollection(o,level));
    }else if(o instanceof Map){
      s.append(wrapMap(o,level));
    }else if(o instanceof Enum){
      s.append(wrapString(o,level));
    }else{
      s.append(wrapObject(o,level));
    }
    return s.toString();
  }
}