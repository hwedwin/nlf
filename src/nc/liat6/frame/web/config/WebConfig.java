package nc.liat6.frame.web.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.frame.Factory;
import nc.liat6.frame.Version;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.IExecute;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.web.WebExecute;
import nc.liat6.frame.web.upload.UploadStatus;

/**
 * 默认WEB配置
 *
 * @author 6tail
 *
 */
public class WebConfig implements IWebConfig{

  public String getAppRootTag(){
    return Statics.DEFAULT_APP_ROOT_TAG;
  }

  public String getErrorPage(Request request,int responseStatus){
    return null;
  }

  public Map<String,Object> getGlobalVars(){
    return new HashMap<String,Object>();
  }

  public IWebManager getWebManager(){
    return new WebManager(this);
  }

  public void init(){}

  public List<String> getForbiddenPaths(){
    List<String> l = new ArrayList<String>();
    l.add("/"+Version.PACKAGE);
    for(String s:Factory.PKGS){
      l.add("/"+s);
    }
    return l;
  }

  public List<String> getAllowPaths(){
    List<String> l = new ArrayList<String>();
    l.add("/"+UploadStatus.class.getName());
    return l;
  }

  public void start(){}

  public IExecute getExecuter(){
    return new WebExecute();
  }
}
