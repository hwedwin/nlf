package nc.liat6.frame.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.exception.BadException;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.Response;
import nc.liat6.frame.execute.impl.AbstractExecute;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PagingParam;
import nc.liat6.frame.util.Dater;
import nc.liat6.frame.util.Objecter;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.web.request.WebCookieFetcher;
import nc.liat6.frame.web.request.WebIPFetcher;
import nc.liat6.frame.web.request.WebLocaleFetcher;
import nc.liat6.frame.web.response.Bad;
import nc.liat6.frame.web.response.HideJson;
import nc.liat6.frame.web.response.Json;
import nc.liat6.frame.web.response.Output;
import nc.liat6.frame.web.response.Page;
import nc.liat6.frame.web.response.Tip;
import nc.liat6.frame.web.upload.FileUploader;

/**
 * WEB应用执行器
 *
 * @author 6tail
 *
 */
public class WebExecute extends AbstractExecute{

  static final String HTTP_SERVLET_REQUEST = "NLF_HTTP_SERVLET_REQUEST";
  static final String HTTP_SERVLET_RESPONSE = "NLF_HTTP_SERVLET_RESPONSE";
  static final String HTTP_FILTERCHAIN = "NLF_HTTP_FILTERCHAIN";
  static final String[] MOBILE_AGENT = {"iphone","ipad","android","phone","mobile","wap","netfront","java","opera mobi","opera mini","ucweb","windows ce","symbian","series","webos","sony","blackberry","dopod","nokia","samsung","palmsource","xda","pieplus","meizu","midp","cldc","motorola","foma","docomo","up.browser","up.link","blazer","helio","hosin","huawei","novarra","coolpad","webos","techfaith","palmsource","alcatel","amoi","ktouch","nexian","ericsson","philips","sagem","wellcom","bunjalloo","maui","smartphone","iemobile","spice","bird","zte-","longcos","pantech","gionee","portalmmm","jig browser","hiptop","benq","haier","^lct","320x320","240x320","176x220","w3c ","acs-","alav","alca","amoi","audi","avan","benq","bird","blac","blaz","brew","cell","cldc","cmd-","dang","doco","eric","hipt","inno","ipaq","java","jigs","kddi","keji","leno","lg-c","lg-d","lg-g","lge-","maui","maxo","midp","mits","mmef","mobi","mot-","moto","mwbp","nec-","newt","noki","oper","palm","pana","pant","phil","play","port","prox","qwap","sage","sams","sany","sch-","sec-","send","seri","sgh-","shar","sie-","siem","smal","smar","sony","sph-","symb","t-mo","teli","tim-","tsm-","upg1","upsi","vk-v","voda","wap-","wapa","wapi","wapp","wapr","webc","winw","winw","xda","xda-","Googlebot-Mobile"};

  protected StringBuffer logs = new StringBuffer();

  /**
   * 初始化参数
   */
  private void initParam(){
    Request req = Context.get(Statics.REQUEST);
    HttpServletRequest oreq = req.find(Statics.FIND_REQUEST);
    // 获取AJAX请求标识
    String headAjax = oreq.getHeader("x-requested-with");
    // 判断移动浏览器
    String userAgent = oreq.getHeader("User-Agent");
    if(null!=userAgent){
      for(String ma:MOBILE_AGENT){
        if(userAgent.toLowerCase().contains(ma)){
          req.setClientType(Request.CLIENT_TYPE_MOBILE);
          break;
        }
      }
    }
    // 请求方式：GET、POST等
    String reqMethod = oreq.getMethod();
    Map<String,String> args = req.getParams();
    logs.append(L.get(LocaleFactory.locale,"web.req_path")+oreq.getServletPath()+"\r\n");
    logs.append(L.get(LocaleFactory.locale,"web.req_args")+"\r\n");
    for(String key:args.keySet()){
      String value = req.get(key);
      value = null==value?"":value;
      if(null==headAjax){
        if("NlfHttpRequest".equals(oreq.getHeader("nlf-requested-with"))){
          value = Stringer.ajax(value);
        }else if("GET".equalsIgnoreCase(reqMethod)){
          value = Stringer.encode(value,"ISO-8859-1",Statics.ENCODE);
        }
      }else{
        value = Stringer.ajax(value);
      }
      req.setParam(key,value);
      logs.append("\t"+key+"="+value+"\r\n");
    }
  }

  /**
   * 初始化分页参数
   */
  private void initPagingParam(){
    Request req = Context.get(Statics.REQUEST);
    HttpSession session = req.find(Statics.FIND_SESSION);
    HttpServletRequest oreq = req.find(Statics.FIND_REQUEST);
    PagingParam pagingParam = new PagingParam();
    String s = req.get(Request.PAGE_PARAM_VAR);
    try{
      pagingParam = (PagingParam)Objecter.decode(s);
    }catch(Exception e){
      try{
        pagingParam.setParam(Request.PAGE_SIZE_VAR,session.getAttribute(Request.PAGE_SIZE_VAR)+"");
      }catch(Exception ex){}
    }
    for(String key:req.getParams().keySet()){
      if(Request.PAGE_PARAM_VAR.equals(key)){
        continue;
      }
      pagingParam.setParam(key,req.get(key));
    }
    pagingParam.setUri(oreq.getServletPath());
    req.setPagingParam(pagingParam);
    try{
      oreq.setAttribute(Request.PAGE_PARAM_VAR,Objecter.encode(pagingParam));
    }catch(IOException e){
      throw new BadException(e);
    }
    session.setAttribute(Request.PAGE_SIZE_VAR,pagingParam.getParam(Request.PAGE_SIZE_VAR));
  }

  @Override
  public void request(){
    Request req = Context.get(Statics.REQUEST);
    Response res = Context.get(Statics.RESPONSE);
    HttpServletRequest oreq = Context.get(HTTP_SERVLET_REQUEST);
    HttpServletResponse ores = Context.get(HTTP_SERVLET_RESPONSE);
    req.bind(Statics.FIND_REQUEST,oreq);
    res.bind(Statics.FIND_RESPONSE,ores);
    req.bind(Statics.FIND_SESSION,oreq.getSession());
    req.bind(Statics.FIND_UPLOADER,new FileUploader(req));
    req.bind(Statics.FIND_IP_FETCHER,new WebIPFetcher(req));
    req.bind(Statics.FIND_LOCALE_FETCHER,new WebLocaleFetcher(req));
    req.bind(Statics.FIND_COOKIE_FETCHER,new WebCookieFetcher(req));
    initParam();
    initPagingParam();
  }

  @Override
  public void response(){
    Object r = Context.get(EXECUTE_RETURN);
    if(null==r){}else if(r instanceof Number){
      responseString(r+"");
    }else if(r instanceof Boolean){
      responseString(r+"");
    }else if(r instanceof Character||r instanceof String){
      responseString(r+"");
    }else if(r instanceof Date){
      responseString(Dater.ymdhms((Date)r)+"");
    }else if(r instanceof Output){
      responseOutput((Output)r);
    }else if(r instanceof Json){
      responseJson((Json)r);
    }else if(r instanceof HideJson){
      responseHideJson((HideJson)r);
    }else if(r instanceof Tip){
      responseJson((Tip)r);
    }else if(r instanceof Page){
      responsePage((Page)r);
    }else if(r instanceof Bad){
      responseBad((Bad)r);
    }else{
      responseString(JSON.toJson(r));
    }
    Context.remove(HTTP_SERVLET_REQUEST);
    Context.remove(HTTP_SERVLET_RESPONSE);
    Context.remove(HTTP_FILTERCHAIN);
  }

  protected void responseOutput(Output p){
    logs.append(Stringer.print("??",L.get(LocaleFactory.locale,"web.res_stream"),p.getContentType()));
    Logger.getLog().debug(logs.toString());
    Response res = Context.get(Statics.RESPONSE);
    HttpServletResponse ores = res.find(Statics.FIND_RESPONSE);
    ores.setContentType(p.getContentType());
    try{
      ores.setHeader("Content-disposition","attachment;filename="+new String(p.getName().getBytes("gbk"),"ISO-8859-1"));
    }catch(UnsupportedEncodingException e){
      throw new BadException(e);
    }
    if(p.getFileSize()>-1){
      ores.setHeader("content_Length",p.getFileSize()+"");
    }
    try{
      InputStream is = p.getInputStream();
      OutputStream os = ores.getOutputStream();
      int n = 0;
      byte b[] = new byte[1024];
      while((n = p.getInputStream().read(b))!=-1){
        os.write(b,0,n);
      }
      os.close();
      is.close();
    }catch(IOException e){
      throw new BadException(e);
    }
  }

  protected void responseHideJson(HideJson p){
    String s = p+"";
    logs.append(Stringer.print("??",L.get(LocaleFactory.locale,"web.res_json"),s));
    Logger.getLog().debug(logs.toString());
    Response res = Context.get(Statics.RESPONSE);
    HttpServletResponse ores = res.find(Statics.FIND_RESPONSE);
    ores.setContentType("text/plain;charset=UTF-8");
    ores.setCharacterEncoding("UTF-8");
    try{
      ores.getWriter().write(s);
      ores.getWriter().flush();
    }catch(IOException e){
      throw new BadException(e);
    }
  }

  protected void responseString(String p){
    logs.append(Stringer.print("??",L.get(LocaleFactory.locale,"web.res_string"),p));
    Logger.getLog().debug(logs.toString());
    Response res = Context.get(Statics.RESPONSE);
    HttpServletResponse ores = res.find(Statics.FIND_RESPONSE);
    ores.setCharacterEncoding(Statics.ENCODE);
    try{
      ores.getWriter().write(p);
      ores.getWriter().flush();
    }catch(IOException e){
      throw new BadException(e);
    }
  }

  protected void responseJson(Json p){
    String s = JSON.toJson(p);
    logs.append(Stringer.print("??",L.get(LocaleFactory.locale,"web.res_json"),s));
    Logger.getLog().debug(logs.toString());
    Response res = Context.get(Statics.RESPONSE);
    HttpServletResponse ores = res.find(Statics.FIND_RESPONSE);
    ores.setContentType("text/plain;charset=UTF-8");
    ores.setCharacterEncoding("UTF-8");
    try{
      ores.getWriter().write(s);
      ores.getWriter().flush();
    }catch(IOException e){
      throw new BadException(e);
    }
  }

  protected void responsePage(Page p){
    Request req = Context.get(Statics.REQUEST);
    Response res = Context.get(Statics.RESPONSE);
    HttpServletRequest oreq = req.find(Statics.FIND_REQUEST);
    HttpServletResponse ores = res.find(Statics.FIND_RESPONSE);
    ores.setStatus(p.getStatus());
    Iterator<String> it = p.keySet().iterator();
    if(it.hasNext()){
      logs.append(L.get(LocaleFactory.locale,"web.res_page_var")+"\r\n");
    }
    while(it.hasNext()){
      String key = it.next();
      oreq.setAttribute(key,p.get(key));
      logs.append(Stringer.print("\t?=?",key,p.get(key))+"\r\n");
    }
    logs.append(Stringer.print("??\r\n",L.get(LocaleFactory.locale,"web.res_status"),p.getStatus()));
    logs.append(Stringer.print("??",L.get(LocaleFactory.locale,"web.res_page"),p.getUri()));
    Logger.getLog().debug(logs.toString());
    try{
      oreq.getRequestDispatcher(p.getUri()).forward(oreq,ores);
    }catch(Exception e){
      throw new BadException(e);
    }
  }

  protected void responseBad(Bad p){
    Request req = Context.get(Statics.REQUEST);
    Response res = Context.get(Statics.RESPONSE);
    HttpServletRequest oreq = req.find(Statics.FIND_REQUEST);
    HttpServletResponse ores = res.find(Statics.FIND_RESPONSE);
    String headAjax = oreq.getHeader("x-requested-with");
    if(null==headAjax){
      if(null!=Dispatcher.config.getErrorPage()){
        oreq.setAttribute("e",p.getThing());
        try{
          oreq.getRequestDispatcher(Dispatcher.config.getErrorPage()).forward(oreq,ores);
        }catch(Exception e){
          throw new BadException(e);
        }
      }else{
        responseString(p.getThing());
      }
    }else{
      Tip tip = new Tip(p.getData(),p.getThing());
      tip.setSuccess(false);
      responseJson(tip);
    }
  }
}