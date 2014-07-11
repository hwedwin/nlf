package nc.liat6.frame.aop;

import java.lang.reflect.Method;

/**
 * AOP管理器父类
 * 
 * @author liat6
 * 
 */
public abstract class AopManager extends AbstractManager{

  public AopManager(String klass,String method){
    super(klass,method);
  }

  private boolean checkMethod(String methodName,String klassName,String method){
    String[] mn = method.split("\\*");
    switch(mn.length){
    // [*]
      case 0:
        return true;
        // [] [test*] [test]
      case 1:
        if(methodName.startsWith(mn[0]))
          return true;
        // [*Test] [test*A]
      case 2:
        if(methodName.startsWith(mn[0])&&methodName.endsWith(mn[1]))
          return true;
    }
    return false;
  }

  public boolean check(Object o,Method m){
    boolean need = false;
    String klassName = o.getClass().getName();
    String methodName = m.getName();
    // 处理代理类
    if(klassName.indexOf("$")>-1)
      klassName = klassName.substring(0,klassName.indexOf("$"));
    if("finalize".equals(methodName))
      return false;
    for(String klass:klasses){
      String[] s = klass.split("\\*");
      switch(s.length){
      // [*]
        case 0:
          for(String method:methods){
            if(checkMethod(methodName,klassName,method)){
              need = true;
              break;
            }
          }
          break;
        // [] [nc.liat6.service.*] [nc.liat6.service.TestService]
        case 1:
          if(klassName.startsWith(s[0])){
            for(String method:methods){
              if(checkMethod(methodName,klassName,method)){
                need = true;
                break;
              }
            }
          }
          break;
        // [*Service] [nc.liat6.service.*Service]
        case 2:
          if(klassName.startsWith(s[0])&&klassName.endsWith(s[1])){
            for(String method:methods){
              if(checkMethod(methodName,klassName,method)){
                need = true;
                break;
              }
            }
          }
          break;
      }
    }
    return need;
  }
}
