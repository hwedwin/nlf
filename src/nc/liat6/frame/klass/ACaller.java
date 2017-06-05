package nc.liat6.frame.klass;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.frame.Factory;
import nc.liat6.frame.aop.IAopInterceptor;
import nc.liat6.frame.bytecode.ClassBuilder;
import nc.liat6.frame.bytecode.InterfaceBuilder;
import nc.liat6.frame.bytecode.Klass;
import nc.liat6.frame.bytecode.attribute.IAttribute;
import nc.liat6.frame.bytecode.attribute.InnerClass;
import nc.liat6.frame.exception.ImplNotFoundException;
import nc.liat6.frame.proxy.IProxy;
import nc.liat6.frame.proxy.impl.ProxyClassLoader;
import nc.liat6.frame.proxy.impl.ReflectProxy;

public class ACaller implements ICaller{

  /** 代理类缓存 */
  private static final Map<String,Class<?>> CLASS_POOL = new HashMap<String,Class<?>>();

  @SuppressWarnings("unchecked")
  public <T>T execute(Class<?> cls,String method){
    try{
      Object o = newInstance(cls);
      Method m = o.getClass().getMethod(method);
      return (T)m.invoke(o);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  public <T>T execute(String className,String method){
    try{
      return execute(Class.forName(className),method);
    }catch(ClassNotFoundException e){
      throw new RuntimeException(e);
    }
  }

  public <T>T newInstance(String className){
    try{
      return newInstance(Class.forName(className));
    }catch(ClassNotFoundException e){
      throw new RuntimeException(e);
    }
  }

  public <T>T newInstance(Class<?> cls){
    T o = null;
    Class<?> oldClass = cls;
    Class<?> objClass = CLASS_POOL.get(oldClass.getName());
    if(null==objClass){
      if(oldClass.isInterface()){
        List<String> impls = Factory.getImpls(oldClass.getName());
        if(null==impls||impls.size()<1){
          throw new ImplNotFoundException(oldClass.getName());
        }
        // 如果有多个实现，则取类名最大的类作为实现类
        try{
          objClass = Class.forName(impls.get(0));
        }catch(ClassNotFoundException e){
          throw new RuntimeException(e);
        }
      }else{
        objClass = oldClass;
      }
      ClassInfo ci = Factory.getClass(objClass.getName());
      if(null==ci){
        throw new RuntimeException(new ClassNotFoundException(oldClass.getName()));
      }
      try{
        ByteCodeReader bcr = new ByteCodeReader();
        // 字节码解释器
        Klass klass = new Klass(bcr.readClass(ci));
        ClassBuilder classBuilder = new ClassBuilder(klass);
        ProxyClassLoader loader = new ProxyClassLoader(Thread.currentThread().getContextClassLoader());
        if(oldClass.isInterface()){
          classBuilder.addInterface(oldClass.getName());
        }else{
          String interfaceName = oldClass.getName()+"$NlfInterface";
          byte[] interfaceCode = new InterfaceBuilder(klass).generate(interfaceName);
          // 加载动态创建的接口
          loader.load(interfaceName,interfaceCode);
          classBuilder.addInterface(interfaceName);
          List<String> ifcs = bcr.getInterfaces(ci);
          for(String ifc:ifcs){
            classBuilder.addInterface(ifc);
          }
        }
        byte[] classCode = classBuilder.generate();
        objClass = loader.load(objClass.getName(),classCode);
        for(IAttribute attr:klass.getAttributes()){
          if("InnerClasses".equals(attr.getName())){
            List<InnerClass> ics = attr.toInnerClassAttribute().getInnerClasses();
            for(InnerClass ic:ics){
              if(!Modifier.isStatic(ic.getInnerAccess())){
                String icName = ic.getInnerClassName().replace("/",".");
                loader.load(icName,bcr.readClass(Factory.getClass(icName)));
              }
            }
          }
        }
        CLASS_POOL.put(oldClass.getName(),objClass);
        CLASS_POOL.put(objClass.getName(),objClass);
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    }
    IProxy proxy = new ReflectProxy();
    for(String aop:Factory.getAops()){
      try{
        proxy.addAopInterceptor((IAopInterceptor)Class.forName(aop).newInstance());
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    }
    o = proxy.create(objClass);
    BeanInfo info = BeanPool.getBeanInfo(objClass);
    PropertyDescriptor[] props = info.getPropertyDescriptors();
    for(int i = 0;i<props.length;i++){
      PropertyDescriptor desc = props[i];
      Method method = desc.getWriteMethod();
      if(null==method){
        continue;
      }
      Class<?> pt = desc.getPropertyType();
      try{
        // 如果是接口，自动注入实现类
        if(pt.isInterface()){
          method.invoke(proxy.getOBean(),newInstance(pt));
        }
      }catch(Exception e){
        throw new RuntimeException(e);
      }
    }
    return o;
  }
}