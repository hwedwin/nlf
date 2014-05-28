package nc.liat6.frame.db.entity;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Blob;
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
public class Bean implements Serializable{

	private static final long serialVersionUID = -1432802131869099829L;

	/** 键值对 */
	private Map<String,Object> values = new HashMap<String,Object>();

	/** 注释对 */
	private Map<String,String> notes = new HashMap<String,String>();

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
	 * 清空
	 * @return 自己
	 */
	public Bean clear(){
		values.clear();
		notes.clear();
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
		if(null == o){
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
		return o + "";
	}

	@SuppressWarnings("unchecked")
	public <T> T toObject(Class<?> klass){
		try{
			Object o = klass.newInstance();
			BeanInfo info = BeanPool.getBeanInfo(klass);
			PropertyDescriptor[] props = info.getPropertyDescriptors();
			for(int i = 0;i < props.length;i++){
				PropertyDescriptor desc = props[i];
				String name = desc.getName();
				Method method = desc.getWriteMethod();
				if(null != method){
					try{
						if(null != values && values.containsKey(name)){
							method.invoke(o,values.get(name));
						}else{
							try{
								Object arg = desc.getPropertyType().newInstance();
								method.invoke(o,arg);
							}catch(NlfException ex){}
						}
					}catch(Exception e){
						throw new NlfException(e);
					}
				}
			}
			return (T)o;
		}catch(Exception e){
			throw new NlfException(e);
		}
	}

}
