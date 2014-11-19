package nc.liat6.frame.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nc.liat6.frame.Factory;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.IExecute;
import nc.liat6.frame.execute.impl.AbstractExecute;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.web.config.ClassMethod;
import nc.liat6.frame.web.config.IWebConfig;
import nc.liat6.frame.web.config.IWebManager;

/**
 * WEB应用调度器
 *
 * @author 6tail
 *
 */
public class Dispatcher implements Filter{

  /** WEB配置 */
  public static IWebConfig config;
  /** 禁止请求的路径 */
  public static Set<String> forbiddenPaths = new HashSet<String>();
  /** 允许请求的路径，比禁止请求的优先级高 */
  public static Set<String> allowPaths = new HashSet<String>();

  public void destroy(){}

  public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain) throws IOException,ServletException{
    HttpServletRequest req = (HttpServletRequest)request;
    HttpServletResponse res = (HttpServletResponse)response;
    // 设置编码
    req.setCharacterEncoding(Statics.ENCODE);
    // 设置原请求
    Context.set(WebExecute.HTTP_SERVLET_REQUEST,req);
    // 设置原响应
    Context.set(WebExecute.HTTP_SERVLET_RESPONSE,res);
    // 设置过滤链
    Context.set(WebExecute.HTTP_FILTERCHAIN,chain);
    // 设置参数
    Map<String,String> args = new HashMap<String,String>();
    Enumeration<?> en = req.getParameterNames();
    while(en.hasMoreElements()){
      String key = (String)en.nextElement();
      args.put(key,req.getParameter(key));
    }
    Context.set(AbstractExecute.EXECUTE_ARGS,args);
    // 访问地址
    String path = req.getServletPath();
    // 去除冗余/
    while(path.startsWith("//")){
      path = path.substring(1);
    }
    // WEB管理器
    IWebManager wm = config.getWebManager();
    ClassMethod cm = wm.before(path);
    // 不受管理的路径
    if(null==cm){
      chain.doFilter(request,response);
      return;
    }
    // WEB执行器
    IExecute executer = config.getExecuter();
    executer.begin();
    Object r = null;
    try{
      r = Factory.getCaller().execute(cm.getKlass(),cm.getMethod());
    }catch(Throwable e){
      r = wm.failed(e);
    }finally{
      wm.after();
    }
    Context.set(AbstractExecute.EXECUTE_RETURN,r);
    wm.filter();
    executer.end();
    Context.clear();
  }

  public void init(FilterConfig fc) throws ServletException{
    ServletContext ctx = fc.getServletContext();
    // 配置为WEB应用
    WebContext.isWebApp = true;
    WebContext.REAL_PATH = ctx.getRealPath("");
    WebContext.CONTEXT_PATH = ctx.getContextPath();
    WebContext.CLASSES_PATH = ctx.getRealPath("/WEB-INF/classes");
    WebContext.LIB_PATH = ctx.getRealPath("/WEB-INF/lib");

    // 初始化工厂
    Factory.initApp(WebContext.CLASSES_PATH,WebContext.LIB_PATH);
    // WEB配置接口初始化
    config = Factory.getCaller().newInstance(IWebConfig.class);
    // WEB配置初始化
    config.init();
    forbiddenPaths.addAll(config.getForbiddenPaths());
    allowPaths.addAll(config.getAllowPaths());
    Map<String,Object> globalVars = config.getGlobalVars();
    if(null==globalVars){
      globalVars = new HashMap<String,Object>();
    }
    globalVars.put(config.getAppRootTag(),WebContext.CONTEXT_PATH);
    for(String k:globalVars.keySet()){
      ctx.setAttribute(k,globalVars.get(k));
    }
    Logger.getLog().info(Stringer.print("??\r\n??\r\n??\r\n??\r\n??\r\n",L.get(LocaleFactory.locale,"web.app_path"),WebContext.REAL_PATH,L.get(LocaleFactory.locale,"web.app_config"),config,L.get(LocaleFactory.locale,"web.global_vars"),JSON.toJson(globalVars,false),L.get(LocaleFactory.locale,"web.forbid"),JSON.toJson(forbiddenPaths,false),L.get(LocaleFactory.locale,"web.allow"),JSON.toJson(allowPaths,false)));
    config.start();
  }
}