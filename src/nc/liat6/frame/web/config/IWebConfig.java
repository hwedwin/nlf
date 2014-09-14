package nc.liat6.frame.web.config;

import java.util.List;
import java.util.Map;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.IExecute;

/**
 * WEB配置接口
 * 
 * @author 6tail
 * 
 */
public interface IWebConfig{

  /**
   * 获取应用根路径标识，如返回PATH，则jsp页面中可使用${PATH}代表应用路径
   * 
   * @return 应用根路径标识,如果返回null，则使用框架默认标识
   * @see Statics
   */
  public String getAppRootTag();

  /**
   * 获取错误页面地址
   * 
   * @return 错误页面地址，如果返回null，则使用框架默认
   */
  public String getErrorPage();

  /**
   * 获取全局变量，返回的结果将设置到application中，如设置WEB_NAME="NLF"，则jsp页面中${WEB_NAME}="NLF"
   * 
   * @return 全局变量，如果返回null，则使用框架默认
   */
  public Map<String,Object> getGlobalVars();

  /**
   * 获取禁止访问的路径列表
   * 
   * @return 禁止访问的路径列表
   */
  public List<String> getForbiddenPaths();

  /**
   * 获取允许访问的路径列表，优先级比禁止访问的高
   * 
   * @return 允许访问的路径列表
   */
  public List<String> getAllowPaths();

  /**
   * 获取WEB管理器
   * 
   * @return WEB管理器
   */
  public IWebManager getWebManager();

  /**
   * WEB应用初始化时的操作
   */
  public void init();

  /**
   * WEB应用启动后执行的操作
   */
  public void start();

  /**
   * 获取执行器
   * 
   * @return 执行器
   */
  public IExecute getExecuter();
}
