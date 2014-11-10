package nc.liat6.frame.klass;

import java.util.Map;

public interface ICaller{

  <T>T execute(Class<?> cls,String method,Map<String,?> args);

  <T>T execute(String className,String method,Map<String,?> args);

  <T>T execute(Class<?> cls,String method);

  <T>T execute(String className,String method);

  <T>T newInstance(String className);

  <T>T newInstance(String className,Map<String,?> args);

  <T>T newInstance(Class<?> cls);

  <T>T newInstance(Class<?> cls,Map<String,?> args);
}