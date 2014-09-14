package nc.liat6.frame.proxy.impl;

/**
 * 代理类加载器
 * 
 * @author 6tail
 * 
 */
public class ProxyClassLoader extends ClassLoader{

  public ProxyClassLoader(ClassLoader parent){
    super(parent);
  }

  public Class<?> load(String className,byte[] b){
    return defineClass(className,b,0,b.length);
  }
}
