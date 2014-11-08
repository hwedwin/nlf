package nc.liat6.frame.klass;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.HashMap;
import java.util.Map;

public class BeanPool{

  /** 缓存 */
  private static Map<Class<?>,BeanInfo> pool = new HashMap<Class<?>,BeanInfo>();

  private BeanPool(){}

  public static BeanInfo getBeanInfo(Class<?> klass){
    BeanInfo info = pool.get(klass);
    if(null==info){
      try{
        info = Introspector.getBeanInfo(klass,Object.class);
      }catch(IntrospectionException e){
        throw new RuntimeException(e);
      }
      pool.put(klass,info);
    }
    return info;
  }
}