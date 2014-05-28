package nc.liat6.frame.klass;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import nc.liat6.frame.Factory;
import nc.liat6.frame.aop.IAopInterceptor;
import nc.liat6.frame.exception.ImplNotFoundException;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.proxy.IProxy;
import nc.liat6.frame.proxy.impl.AsmProxy;
import nc.liat6.frame.proxy.impl.ReflectProxy;

public class ACaller implements ICaller{

	@SuppressWarnings("unchecked")
	public <T>T execute(Class<?> cls,String method,Map<String,?> args){
		try{
			Object o = newInstance(cls,args);
			Method m = o.getClass().getMethod(method,new Class[0]);
			return (T)m.invoke(o,new Object[0]);
		}catch(Exception e){
			throw new NlfException(e);
		}
	}

	public <T>T execute(String className,String method,Map<String,?> args){
		try{
			return execute(Class.forName(className),method,args);
		}catch(ClassNotFoundException e){
			throw new NlfException(className,e);
		}
	}

	public <T>T execute(Class<?> cls,String method){
		return execute(cls,method,null);
	}

	public <T>T execute(String className,String method){
		return execute(className,method,null);
	}

	public <T>T newInstance(String className){
			return newInstance(className,null);
	}

	public <T>T newInstance(Class<?> cls){
		return newInstance(cls.getName());
	}

	public <T>T newInstance(String className,Map<String,?> args){
		try{
			return newInstance(Class.forName(className),args);
		}catch(ClassNotFoundException e){
			throw new NlfException(className,e);
		}
	}

	public <T>T newInstance(Class<?> cls,Map<String,?> args){
		T o = null;
		IProxy proxy = null;
		if(cls.isInterface()){
			String className = cls.getName();
			List<String> impls = Factory.getImpls(className);
			if(null == impls || impls.size() < 1){
				throw new ImplNotFoundException(className);
			}
			//如果有多个实现，则取类名最大的类作为实现类
			try{
				cls = Class.forName(impls.get(0));
			}catch(ClassNotFoundException e){
				throw new NlfException(className,e);
			}
		}
		proxy = new AsmProxy();
		o = proxy.create(cls);

		IProxy newProxy = new ReflectProxy();
		for(String c:Factory.getAops()){
			try{
				newProxy.addAopInterceptor((IAopInterceptor)Class.forName(c).newInstance());
			}catch(Exception e){
				throw new NlfException(c,e);
			}
		}
		o = newProxy.create(o.getClass());
		Class<?> c = o.getClass();
		BeanInfo info = BeanPool.getBeanInfo(c);
		PropertyDescriptor[] props = info.getPropertyDescriptors();
		for(int i = 0;i < props.length;i++){
			PropertyDescriptor desc = props[i];
			String name = desc.getName();
			Method method = desc.getWriteMethod();
			if(null != method){
				try{
					if(null != args && args.containsKey(name)){
						method.invoke(o,args.get(name));
					}else{
						try{
							Object arg = newInstance(desc.getPropertyType());
							method.invoke(o,arg);
						}catch(NlfException ex){}
					}
				}catch(Exception e){
					throw new NlfException(e);
				}
			}
		}
		return o;
	}

}
