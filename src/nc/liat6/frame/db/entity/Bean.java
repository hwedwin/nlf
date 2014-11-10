package nc.liat6.frame.db.entity;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.klass.BeanPool;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.Streamer;

/**
 * 通用对象封装
 *
 * @author 6tail
 *
 */
public class Bean implements Map<String,Object>,Serializable{

  private static final long serialVersionUID = -1432802131869099829L;
  /** 键值对 */
  private Map<String,Object> values = new HashMap<String,Object>();
  /** 注释对 */
  private Map<String,String> notes = new HashMap<String,String>();

  public Bean(){}

  /**
   * 将一个对象转为Bean
   *
   * @param object 对象
   */
  public Bean(Object object){
    this(object,null);
  }

  /**
   * 将一个对象转为Bean
   *
   * @param object 对象
   * @param rule 转换规则
   */
  public Bean(Object object,IBeanRule rule){
    fromObject(object,rule);
  }

  /**
   * 是否存在指定键的对象
   *
   * @param key 键
   * @return true/false 存在/不存在
   */
  public boolean containsKey(String key){
    return values.containsKey(key);
  }

  /**
   * 获取值
   *
   * @param key 键
   * @return 值
   */
  @SuppressWarnings("unchecked")
  public <T>T get(String key){
    return (T)values.get(key);
  }

  /**
   * 获取注释
   *
   * @param key 键
   * @return 注释
   */
  public String getNote(String key){
    return notes.get(key);
  }

  /**
   * 设置值
   *
   * @param key 键
   * @param value 值
   * @return 自己
   */
  public Bean set(String key,Object value){
    values.put(key,value);
    return this;
  }

  /**
   * 设置带注释的值
   *
   * @param key 键
   * @param value 值
   * @param note 注释
   * @return 自己
   */
  public Bean set(String key,Object value,String note){
    values.put(key,value);
    notes.put(key,note);
    return this;
  }

  /**
   * 设置注释
   *
   * @param key 键
   * @param note 注释
   * @return 自己
   */
  public Bean setNote(String key,String note){
    notes.put(key,note);
    return this;
  }

  /**
   * 移除
   *
   * @param key 键
   * @return 自己
   */
  public Bean remove(String key){
    values.remove(key);
    notes.remove(key);
    return this;
  }

  /**
   * 获取键的集合
   *
   * @return 键集合
   */
  public Set<String> keySet(){
    return values.keySet();
  }

  public String toString(){
    return values.toString();
  }

  /**
   * 获取int值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public int getInt(String key,int defaultValue){
    try{
      return Integer.parseInt(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取long值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public long getLong(String key,long defaultValue){
    try{
      return Long.parseLong(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取double值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public double getDouble(String key,double defaultValue){
    try{
      return Double.parseDouble(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取float值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public float getFloat(String key,float defaultValue){
    try{
      return Float.parseFloat(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取boolean值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public boolean getBoolean(String key,boolean defaultValue){
    try{
      return Boolean.parseBoolean(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取String值，如果为null,返回null
   *
   * @param key 键
   * @return 值
   */
  public String getString(String key){
    return getString(key,null);
  }

  /**
   * 获取String值，如果为null,返回默认值
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public String getString(String key,String defaultValue){
    Object o = values.get(key);
    if(null==o){
      return defaultValue;
    }
    if(o instanceof Blob){
      Blob m = (Blob)o;
      try{
        return new String(Streamer.toByte(m.getBinaryStream()));
      }catch(Exception e){
        throw new DaoException(L.get("bean.data_convert_fail"),e);
      }
    }
    return o+"";
  }

  /**
   * 从对象读取属性到Bean，注意读取前会先清空属性
   *
   * @param object 对象
   */
  public void fromObject(Object object){
    fromObject(object,null);
  }

  /**
   * 从对象读取属性到Bean，注意读取前会先清空属性
   *
   * @param object 对象
   * @param rule 属性转换规则
   */
  public void fromObject(Object object,IBeanRule rule){
    fromObject(object,null,true);
  }

  /**
   * 从对象读取属性到Bean
   *
   * @param object 对象
   * @param rule 属性转换规则
   * @param clear 读取前是否先清空属性
   */
  public void fromObject(Object object,IBeanRule rule,boolean clear){
    if(null==object){
      return;
    }
    // 清空属性
    if(clear){
      clear();
    }
    if(object instanceof Bean){
      Bean o = (Bean)object;
      for(String p:o.keySet()){
        String key = null==rule?p:rule.getKey(p);
        set(null==key?p:key,o.get(p));
      }
    }else if(object instanceof Map){
      Map<?,?> map = (Map<?,?>)object;
      for(Object o:map.keySet()){
        String p = o.toString();
        String key = null==rule?p:rule.getKey(p);
        set(null==key?p:key,map.get(o));
      }
    }else{
      try{
        BeanInfo info = BeanPool.getBeanInfo(object.getClass());
        PropertyDescriptor[] props = info.getPropertyDescriptors();
        for(int i = 0;i<props.length;i++){
          PropertyDescriptor desc = props[i];
          // getter
          Method method = desc.getReadMethod();
          if(null==method){
            continue;
          }
          // 属性
          String p = desc.getName();
          String key = null==rule?p:rule.getKey(p);
          set(null==rule?p:key,method.invoke(object));
        }
      }catch(Exception e){
        throw new NlfException(null==e?null:e.getMessage(),e);
      }
    }
  }

  /**
   * Bean转换为Object
   *
   * @param klass 类
   * @param rule 转换规则
   * @return Object
   */
  @SuppressWarnings("unchecked")
  public <T>T toObject(Class<?> klass,IBeanRule rule){
    try{
      Object o = klass.newInstance();
      if(values.size()<1){
        return (T)o;
      }
      BeanInfo info = BeanPool.getBeanInfo(klass);
      PropertyDescriptor[] props = info.getPropertyDescriptors();
      for(int i = 0;i<props.length;i++){
        PropertyDescriptor desc = props[i];
        String property = desc.getName();
        Method method = desc.getWriteMethod();
        // property type 属性类型
        Class<?> pt = desc.getPropertyType();
        if(null==method){
          continue;
        }
        try{
          String key = property;
          if(!values.containsKey(key)){
            if(null!=rule){
              key = rule.getKey(property);
            }
          }
          if(!values.containsKey(key)){
            continue;
          }
          Object v = values.get(key);
          if(null==v){
            method.invoke(o,v);
          }else{
            // value type 值类型
            Class<?> vt = v.getClass();
            if(String.class.equals(pt)){
              method.invoke(o,v.toString());
            }else if(BigDecimal.class.equals(pt)){
              method.invoke(o,new BigDecimal(v.toString()));
            }else if(Long.class.equals(pt)||long.class.equals(pt)){
              method.invoke(o,Long.parseLong(v.toString()));
            }else if(Integer.class.equals(pt)||int.class.equals(pt)){
              method.invoke(o,Integer.parseInt(v.toString()));
            }else if(Double.class.equals(pt)||double.class.equals(pt)){
              method.invoke(o,Double.parseDouble(v.toString()));
            }else if(Float.class.equals(pt)||float.class.equals(pt)){
              method.invoke(o,Float.parseFloat(v.toString()));
            }else if(Short.class.equals(pt)||short.class.equals(pt)){
              method.invoke(o,Short.parseShort(v.toString()));
            }else if(Byte.class.equals(pt)||byte.class.equals(pt)){
              method.invoke(o,Byte.parseByte(v.toString()));
            }else if(Boolean.class.equals(pt)||boolean.class.equals(pt)){
              method.invoke(o,Boolean.parseBoolean(v.toString()));
            }else if(BigDecimal.class.equals(vt)){
              BigDecimal bd = (BigDecimal)v;
              if(Long.class.equals(pt)||long.class.equals(pt)){
                method.invoke(o,bd.longValue());
              }else if(Integer.class.equals(pt)||int.class.equals(pt)){
                method.invoke(o,bd.intValue());
              }else if(Double.class.equals(pt)||double.class.equals(pt)){
                method.invoke(o,bd.doubleValue());
              }else if(Float.class.equals(pt)||float.class.equals(pt)){
                method.invoke(o,bd.floatValue());
              }else if(Short.class.equals(pt)||short.class.equals(pt)){
                method.invoke(o,bd.shortValue());
              }else if(Byte.class.equals(pt)||byte.class.equals(pt)){
                method.invoke(o,bd.byteValue());
              }
            }else if(Boolean.class.equals(vt)){
              method.invoke(o,((Boolean)v).booleanValue());
            }else{
              method.invoke(o,v);
            }
          }
        }catch(Exception e){
          throw new NlfException(null==e?null:e.getMessage(),e);
        }
      }
      return (T)o;
    }catch(Exception e){
      throw new NlfException(null==e?null:e.getMessage(),e);
    }
  }

  /**
   * Bean转换为Object
   *
   * @param klass 类
   * @return Object
   */
  public <T>T toObject(Class<?> klass){
    return toObject(klass,null);
  }

  @Override
  @Deprecated
  public boolean containsKey(Object key){
    return containsKey(key.toString());
  }

  @Override
  public boolean containsValue(Object value){
    values.containsValue(value);
    return false;
  }

  @Override
  public Set<java.util.Map.Entry<String,Object>> entrySet(){
    return values.entrySet();
  }

  @Override
  @Deprecated
  public Object get(Object key){
    return values.get(key);
  }

  @Override
  public boolean isEmpty(){
    return values.isEmpty();
  }

  @Override
  @Deprecated
  public Object put(String key,Object value){
    return values.put(key,value);
  }

  @Override
  public void putAll(Map<? extends String,? extends Object> map){
    values.putAll(map);
  }

  @Override
  @Deprecated
  public Object remove(Object key){
    notes.remove(key);
    return values.remove(key);
  }

  @Override
  public int size(){
    return values.size();
  }

  @Override
  public Collection<Object> values(){
    return values.values();
  }

  @Override
  public void clear(){
    values.clear();
    notes.clear();
  }
}