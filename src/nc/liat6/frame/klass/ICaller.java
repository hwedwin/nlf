package nc.liat6.frame.klass;


public interface ICaller{

  <T>T execute(Class<?> cls,String method);

  <T>T execute(String className,String method);

  <T>T newInstance(String className);

  <T>T newInstance(Class<?> cls);

}