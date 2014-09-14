package nc.liat6.frame.web;

/**
 * WEB应用变量
 * 
 * @author 6tail
 * 
 */
public class WebContext{

  protected WebContext(){}
  /** 应用虚拟路径 */
  public static String CONTEXT_PATH = "";
  /** 应用真实路径 */
  public static String REAL_PATH = "";
  /** 是否已配置为WEB应用 */
  public static boolean isWebApp = false;
}
