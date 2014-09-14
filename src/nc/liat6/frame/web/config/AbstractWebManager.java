package nc.liat6.frame.web.config;

/**
 * WEB管理器父类
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractWebManager implements IWebManager{

  /** WEB配置 */
  protected IWebConfig config;

  protected AbstractWebManager(IWebConfig config){
    this.config = config;
  }
}
