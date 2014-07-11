package nc.liat6.frame.klass;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.util.HashMap;
import java.util.Map;
import nc.liat6.frame.exception.NlfException;

public class BeanPool{

  /** »º´æ */
  private static Map<Class<?>,Map<Class<?>,BeanInfo>> STOP_INFO_MAP = new HashMap<Class<?>,Map<Class<?>,BeanInfo>>();

  private BeanPool(){}

  public static BeanInfo getBeanInfo(Class<?> klass){
    try{
      return Introspector.getBeanInfo(klass,Object.class);
    }catch(IntrospectionException e){
      throw new NlfException(e);
    }
  }

  public static BeanInfo getBeanInfo(Class<?> klass,Class<?> stopClass){
    if(Object.class==stopClass){
      return getBeanInfo(klass);
    }
    Map<Class<?>,BeanInfo> map = STOP_INFO_MAP.get(klass);
    if(null==map){
      map = new HashMap<Class<?>,BeanInfo>();
      STOP_INFO_MAP.put(klass,map);
    }
    BeanInfo info = map.get(stopClass);
    if(null==info){
      try{
        info = Introspector.getBeanInfo(klass,stopClass);
      }catch(IntrospectionException e){
        throw new NlfException(e);
      }
      map.put(stopClass,info);
    }
    return info;
  }
}
