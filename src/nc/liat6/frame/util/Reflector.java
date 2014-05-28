package nc.liat6.frame.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import nc.liat6.frame.exception.NlfException;

/**
 * 反射工具
 * @author 6tail
 *
 */
public class Reflector{

	private Reflector(){}
	
	/**
	 * 获取类的实例
	 * @param klass 类名
	 * @return 实例对象
	 */
	public static Object newInstance(String klass){
		try{
			Class<?> c = Class.forName(klass);
			return newInstance(c);
		}catch(ClassNotFoundException e){
			throw new NlfException(e);
		}
	}
	
	/**
	 * 获取类的实例
	 * @param o 类
	 * @return 实例对象
	 */
	public static Object newInstance(Class<?> o){
		try{
			return o.newInstance();
		}catch(InstantiationException e){
			throw new NlfException(e);
		}catch(IllegalAccessException e){
			throw new NlfException(e);
		}
	}

	/**
	 * 执行方法
	 * @param o 对象
	 * @param method 方法名
	 * @param types 参数类型
	 * @param args 参数值
	 * @return 执行结果
	 */
	public static Object execute(Object o,String method,Class<?>[] types,Object[] args){
		try{
			Class<?> c = o.getClass();
			Method m = c.getMethod(method,types);
			return m.invoke(o,args);
		}catch(SecurityException e){
			throw new NlfException(e);
		}catch(NoSuchMethodException e){
			throw new NlfException(e);
		}catch(IllegalArgumentException e){
			throw new NlfException(e);
		}catch(IllegalAccessException e){
			throw new NlfException(e);
		}catch(InvocationTargetException e){
			throw new NlfException(e);
		}

	}

	/**
	 * 执行方法
	 * @param klass 类名
	 * @param method 方法名
	 * @param types 参数类型
	 * @param args 参数值
	 * @param isStatic 是否静态调用
	 * @return 返回结果
	 */
	private static Object execute(String klass,String method,Class<?>[] types,Object[] args,boolean isStatic){
		try{
			Class<?> c = Class.forName(klass);
			return execute(c,method,types,args,isStatic);
		}catch(ClassNotFoundException e){
			throw new NlfException(e);
		}
	}

	/**
	 * 执行方法
	 * @param klass 类
	 * @param method 方法名
	 * @param types 参数类型
	 * @param args 参数值
	 * @param isStatic 是否静态调用
	 * @return 返回结果
	 */
	private static Object execute(Class<?> klass,String method,Class<?>[] types,Object[] args,boolean isStatic){
		try{
			Method m = klass.getMethod(method,types);
			return m.invoke(isStatic?klass:klass.newInstance(),args);
		}catch(SecurityException e){
			throw new NlfException(e);
		}catch(NoSuchMethodException e){
			throw new NlfException(e);
		}catch(IllegalArgumentException e){
			throw new NlfException(e);
		}catch(IllegalAccessException e){
			throw new NlfException(e);
		}catch(InvocationTargetException e){
			throw new NlfException(e);
		}catch(InstantiationException e){
			throw new NlfException(e);
		}
	}

	/**
	 * 调用非静态方法
	 * @param klass 类名
	 * @param method 方法名
	 * @param types 参数类型
	 * @param args 参数值
	 * @return 返回结果
	 */
	public static Object execute(String klass,String method,Class<?>[] types,Object[] args){
		return execute(klass,method,types,args,false);
	}

	/**
	 * 调用非静态方法
	 * @param klass 类
	 * @param method 方法名
	 * @param types 参数类型
	 * @param args 参数值
	 * @return 返回结果
	 */
	public static Object execute(Class<?> klass,String method,Class<?>[] types,Object[] args){
		return execute(klass,method,types,args,false);
	}
	
	/**
	 * 调用静态方法
	 * @param klass 类名
	 * @param method 方法名
	 * @param types 参数类型
	 * @param args 参数值
	 * @return 返回结果
	 */
	public static Object executeStatic(String klass,String method,Class<?>[] types,Object[] args){
		return execute(klass,method,types,args,true);
	}

	/**
	 * 调用静态方法
	 * @param klass 类
	 * @param method 方法名
	 * @param types 参数类型
	 * @param args 参数值
	 * @return 返回结果
	 */
	public static Object executeStatic(Class<?> klass,String method,Class<?>[] types,Object[] args){
		return execute(klass,method,types,args,true);
	}
	
	/**
	 * 调用非静态无参方法
	 * @param klass 类名
	 * @param method 方法名
	 * @return 结果
	 */
	public static Object execute(String klass,String method){
		return execute(klass,method,new Class[0],new Object[0],false);
	}
	
	/**
	 * 调用非静态无参方法
	 * @param klass 类
	 * @param method 方法名
	 * @return 结果
	 */
	public static Object execute(Class<?> klass,String method){
		return execute(klass.getName(),method);
	}
	
	/**
	 * 调用无参方法
	 * @param o 对象
	 * @param method 方法名
	 * @return 结果
	 */
	public static Object execute(Object o,String method){
		return execute(o,method,new Class[0],new Object[0]);
	}

}
