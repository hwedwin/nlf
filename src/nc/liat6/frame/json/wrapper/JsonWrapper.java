package nc.liat6.frame.json.wrapper;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import nc.liat6.frame.json.JSON;

/**
 * JSON包装器
 * 
 * @author 6tail
 * 
 */
public class JsonWrapper implements IWrapper{

  private boolean tiny = JSON.TINY;
  private String quote = JSON.QUOTE_DEFAULT;
  private boolean numberQuoted = JSON.NUMBER_QUOTED;

  public JsonWrapper(){}

  public JsonWrapper(boolean tiny,String quote,boolean numberQuoted){
    this.tiny = tiny;
    this.quote = quote;
    this.numberQuoted = numberQuoted;
  }

  private String wrapNumber(Object o,int level){
    StringBuilder s = new StringBuilder();
    if(numberQuoted){
      s.append(quote);
    }
    s.append(o);
    if(numberQuoted){
      s.append(quote);
    }
    return s.toString();
  }

  private String wrapBool(Object o,int level){
    StringBuilder s = new StringBuilder();
    s.append(o);
    return s.toString();
  }

  private String wrapString(Object o,int level){
    StringBuilder s = new StringBuilder();
    s.append(new StringWrapper(quote).wrap(o.toString(),level));
    return s.toString();
  }

  private String wrapDate(Object o,int level){
    StringBuilder s = new StringBuilder();
    s.append(new StringWrapper(quote).wrap(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)o),level));
    return s.toString();
  }

  private String wrapArray(Object o,int level){
    StringBuilder s = new StringBuilder();
    Object[] arr = (Object[])o;
    s.append("[");
    if(!tiny){
      s.append("\r\n");
    }
    for(int i = 0;i<arr.length;i++){
      if(!tiny){
        for(int j = 0;j<2*(level+1);j++){
          s.append(" ");
        }
      }
      s.append(wrap(arr[i],level+1));
      if(i<arr.length-1){
        s.append(",");
      }
      if(!tiny){
        s.append("\r\n");
      }
    }
    if(!tiny){
      for(int i = 0;i<2*level;i++){
        s.append(" ");
      }
    }
    s.append("]");
    return s.toString();
  }

  private String wrapCollection(Object o,int level){
    StringBuilder s = new StringBuilder();
    s.append("[");
    if(!tiny){
      s.append("\r\n");
    }
    Iterator<?> it = ((Collection<?>)o).iterator();
    while(it.hasNext()){
      if(!tiny){
        for(int j = 0;j<2*(level+1);j++){
          s.append(" ");
        }
      }
      s.append(wrap(it.next(),level+1));
      if(it.hasNext()){
        s.append(",");
      }
      if(!tiny){
        s.append("\r\n");
      }
    }
    if(!tiny){
      for(int i = 0;i<2*level;i++){
        s.append(" ");
      }
    }
    s.append("]");
    return s.toString();
  }

  private String wrapMap(Object o,int level){
    StringBuilder s = new StringBuilder();
    Map<?,?> m = (Map<?,?>)o;
    s.append("{");
    if(!tiny){
      s.append("\r\n");
    }
    Iterator<?> it = m.keySet().iterator();
    while(it.hasNext()){
      if(!tiny){
        for(int j = 0;j<2*(level+1);j++){
          s.append(" ");
        }
      }
      Object key = it.next();
      s.append(new StringWrapper().wrap(key+"",level));
      s.append(":");
      s.append(wrap(m.get(key),level+1));
      if(it.hasNext()){
        s.append(",");
      }
      if(!tiny){
        s.append("\r\n");
      }
    }
    if(!tiny){
      for(int i = 0;i<2*level;i++){
        s.append(" ");
      }
    }
    s.append("}");
    return s.toString();
  }

  public String wrapObject(Object o,int level){
    StringBuilder s = new StringBuilder();
    s.append(new ObjectWrapper(tiny,quote,numberQuoted).wrap(o,level));
    return s.toString();
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
      s.append(wrapNumber(o,level));
    }else if(o instanceof Boolean){
      s.append(wrapBool(o,level));
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
