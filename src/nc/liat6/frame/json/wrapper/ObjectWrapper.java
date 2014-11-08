package nc.liat6.frame.json.wrapper;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Iterator;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.klass.BeanPool;

/**
 * 对象包装器
 *
 * @author 6tail
 *
 */
public class ObjectWrapper extends AbstractJsonWrapper{

  /**
   * 构造默认字符串包装器
   */
  public ObjectWrapper(){
    super();
  }

  /**
   * 构造字符串包装器
   *
   * @param tiny 是否极简（不缩进不换行）
   * @param quote 字符串首尾的引号
   * @param numberQuoted 数字是否使用引号
   */
  public ObjectWrapper(boolean tiny,String quote,boolean numberQuoted){
    super(tiny,quote,numberQuoted);
  }

  private String wrapBean(Object obj,int level){
    Bean bean = (Bean)obj;
    IndentString s = new IndentString(tiny);
    s.add('{');
    Iterator<String> it = bean.keySet().iterator();
    if(it.hasNext()){
      String key;
      String note;
      IJsonWrapper stringWrapper = new StringWrapper(tiny,quote,numberQuoted);
      IJsonWrapper jsonWrapper = new JsonWrapper(tiny,quote,numberQuoted);
      s.addLine();
      while(it.hasNext()){
        key = it.next();
        note = bean.getNote(key);
        if(null!=note&&note.trim().length()>0){
          s.addSpace(level+1);
          // 非极简模式才添加注释
          if(!tiny){
            if(note.contains("\r")||note.contains("\n")){
              s.add("/*");
              s.add(note);
              s.add("*/");
            }else{
              s.add("//");
              s.add(note);
            }
          }
          s.addLine();
        }
        s.addSpace(level+1);
        s.add(stringWrapper.wrap(key,level+1));
        s.add(':');
        s.add(jsonWrapper.wrap(bean.get(key),level+1));
        if(it.hasNext()){
          s.add(',');
        }
        s.addLine();
      }
      s.addSpace(level);
    }
    s.add('}');
    return s.toString();
  }

  private String wrapObject(Object obj,int level){
    IndentString s = new IndentString(tiny);
    s.add('{');
    BeanInfo info = BeanPool.getBeanInfo(obj.getClass());
    PropertyDescriptor[] props = info.getPropertyDescriptors();
    if(props.length>0){
      IJsonWrapper stringWrapper = new StringWrapper(tiny,quote,numberQuoted);
      IJsonWrapper jsonWrapper = new JsonWrapper(tiny,quote,numberQuoted);
      s.addLine();
      for(int i = 0;i<props.length;i++){
        s.addSpace(level+1);
        PropertyDescriptor desc = props[i];
        s.add(stringWrapper.wrap(desc.getName(),level+1));
        s.add(':');
        try{
          Method method = desc.getReadMethod();
          if(null==method){
            s.add(jsonWrapper.wrap(null,level+1));
          }else{
            s.add(jsonWrapper.wrap(method.invoke(obj,new Object[0]),level+1));
          }
        }catch(Exception e){
          throw new NlfException(e);
        }
        if(i<props.length-1){
          s.add(',');
        }
        s.addLine();
      }
      s.addSpace(level);
    }
    s.add('}');
    return s.toString();
  }

  public String wrap(Object obj,int level){
    String s;
    if(obj instanceof Bean){
      s = wrapBean(obj,level);
    }else{
      s = wrapObject(obj,level);
    }
    return s;
  }
}