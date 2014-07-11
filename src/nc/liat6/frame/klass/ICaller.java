package nc.liat6.frame.klass;

import java.util.Map;

public interface ICaller{

  public <T>T execute(Class<?> cls,String method,Map<String,?> args);

  public <T>T execute(String className,String method,Map<String,?> args);

  public <T>T execute(Class<?> cls,String method);

  public <T>T execute(String className,String method);

  public <T>T newInstance(String className);

  public <T>T newInstance(String className,Map<String,?> args);

  public <T>T newInstance(Class<?> cls);

  public <T>T newInstance(Class<?> cls,Map<String,?> args);
}
