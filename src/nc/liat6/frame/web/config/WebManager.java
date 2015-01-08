package nc.liat6.frame.web.config;

import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.IConnection;
import nc.liat6.frame.exception.BadException;
import nc.liat6.frame.exception.BadUploadException;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.Response;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.web.Dispatcher;
import nc.liat6.frame.web.Forbid;
import nc.liat6.frame.web.response.Json;
import nc.liat6.frame.web.response.Page;
import nc.liat6.frame.web.response.Tip;
import nc.liat6.frame.web.upload.FileUploader;

/**
 * WEB管理器默认实现
 *
 * @author 6tail
 *
 */
public class WebManager extends AbstractWebManager{
  public WebManager(IWebConfig config){
    super(config);
  }

  protected boolean contains(Set<String> l,String s){
    for(String o:l){
      if(s.startsWith(o)){
        return true;
      }
    }
    return false;
  }

  public Object failed(Throwable e){
    Throwable cause = e;
    while(null!=cause&&null!=cause.getCause()){
      cause = cause.getCause();
    }
    if(!(cause instanceof BadException)){
      Logger.getLog().error(null==e?null:e.getMessage(),e);
    }
    String r = null==cause?null:cause.getMessage();
    Request req = Context.get(Statics.REQUEST);
    HttpServletRequest oreq = req.find(Statics.FIND_REQUEST);
    Response res = Context.get(Statics.RESPONSE);
    HttpServletResponse ores = res.find(Statics.FIND_RESPONSE);
    String headAjax = oreq.getHeader("x-requested-with");
    if(null==headAjax){
      // 文件上传异常，转换为JSON返回
      if(cause instanceof BadUploadException){
        Json json = new Json(r);
        json.setMsg(r);
        json.setSuccess(false);
        return json;
      }
      // 文件上传，自动转换为JSON返回
      if(req.get(FileUploader.ARG_ID).length()>0){
        Json json = new Json(r);
        json.setMsg(r);
        json.setSuccess(false);
        return json;
      }
      //如果class或method没找到，返回404
      if(cause instanceof ClassNotFoundException || cause instanceof NoSuchMethodException){
        String errorPage = config.getErrorPage(req,404);
        if(null==errorPage){
          ores.setStatus(404);
          return null;
        }else{
          Page p = new Page(errorPage);
          p.set("e",cause);
          p.setStatus(404);
          return p;
        }
      }
      return r;
    }else{
      Tip tip = new Tip();
      tip.setMsg(r);
      if(cause instanceof BadException){
        BadException be = (BadException)cause;
        tip.setData(be.getData());
      }
      tip.setSuccess(false);
      return tip;
    }
  }

  public void after(){
    List<ConnVar> l = Context.get(Statics.CONNECTIONS);
    if(null!=l){
      for(ConnVar o:l){
        if(null==o){
          continue;
        }
        IConnection conn = o.getConnection();
        if(null==conn){
          continue;
        }
        try{
          if(conn.isClosed()){
            continue;
          }
          Logger.getLog().debug(Stringer.print("??",L.get(LocaleFactory.locale,"db.auto_rollback"),o.getAlias()));
          conn.rollback();
          conn.close();
        }catch(Exception e){}
      }
    }
  }

  public ClassMethod before(String path){
    Set<String> forbiddenPaths = Dispatcher.forbiddenPaths;
    Set<String> allowPaths = Dispatcher.allowPaths;
    // 请求过滤
    if(contains(forbiddenPaths,path)){
      if(!contains(allowPaths,path)){
        ClassMethod cm = new ClassMethod();
        cm.setKlass(Forbid.class.getName());
        cm.setMethod("access");
        return cm;
      }
    }
    // 匹配路径，pkg.Klass/method
    if(!path.matches("[/].{1,}[/]\\w{1,}")){
      return null;
    }
    String[] keys = path.split("/");
    String klass = keys[1];
    String method = keys[2];
    ClassMethod cm = new ClassMethod();
    cm.setKlass(klass);
    cm.setMethod(method);
    return cm;
  }

  public void filter(){}
}