package nc.liat6.frame.json.wrapper;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.util.Iterator;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.klass.BeanPool;

/**
 * 对象包装器
 * 
 * @author 6tail
 * 
 */
public class ObjectWrapper implements IWrapper{

  private boolean tiny = JSON.TINY;
  private String quote = JSON.QUOTE_DEFAULT;
  private boolean numberQuoted = JSON.NUMBER_QUOTED;

  public ObjectWrapper(boolean tiny,String quote,boolean numberQuoted){
    this.tiny = tiny;
    this.quote = quote;
    this.numberQuoted = numberQuoted;
  }

  public String wrap(Object o,int level){
    StringBuilder s = new StringBuilder();
    s.append("{");
    if(!tiny){
      s.append("\r\n");
    }
    if(o instanceof Bean){
      Bean b = (Bean)o;
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
          if(tiny){// 精简模式不添加注释
            // s.append("/*"+note+"*/");
          }else{
            if(note.contains("\r")||note.contains("\n")){
              s.append("/*"+note+"*/");
            }else{
              s.append("//"+note);
            }
          }
          if(!tiny){
            s.append("\r\n");
          }
        }
        if(!tiny){
          for(int i = 0;i<2*(level+1);i++){
            s.append(" ");
          }
        }
        s.append(new StringWrapper(quote).wrap(k,level+1));
        s.append(":");
        s.append(new JsonWrapper(tiny,quote,numberQuoted).wrap(b.get(k),level+1));
        if(it.hasNext()){
          s.append(",");
        }
        if(!tiny){
          s.append("\r\n");
        }
      }
    }else{
      BeanInfo info = BeanPool.getBeanInfo(o.getClass());
      PropertyDescriptor[] props = info.getPropertyDescriptors();
      for(int i = 0;i<props.length;i++){
        if(!tiny){
          for(int j = 0;j<2*(level+1);j++){
            s.append(" ");
          }
        }
        PropertyDescriptor desc = props[i];
        s.append(new StringWrapper(quote).wrap(desc.getName(),level+1));
        s.append(":");
        try{
          if(null==desc.getReadMethod()){
            s.append(new JsonWrapper(tiny,quote,numberQuoted).wrap(null,level+1));
          }else{
            s.append(new JsonWrapper(tiny,quote,numberQuoted).wrap(desc.getReadMethod().invoke(o,new Object[0]),level+1));
          }
        }catch(Exception e){
          throw new NlfException(e);
        }
        if(i<props.length-1){
          s.append(",");
        }
        if(!tiny){
          s.append("\r\n");
        }
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
}
