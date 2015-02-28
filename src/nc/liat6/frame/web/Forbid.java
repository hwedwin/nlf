package nc.liat6.frame.web;

import javax.servlet.http.HttpServletResponse;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.exception.BadException;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.Response;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.web.response.Page;

public class Forbid{

  /**
   * 禁止访问
   *
   * @return 403
   */
  public Object access(){
    Request r = Context.get(Statics.REQUEST);
    Response res = Context.get(Statics.RESPONSE);
    HttpServletResponse ores = res.find(Statics.FIND_RESPONSE);
    String errorPage = Dispatcher.config.getErrorPage(r,403);
    if(null==errorPage){
      ores.setStatus(403);
      return null;
    }else{
      Page p = new Page(errorPage);
      p.set("e",new BadException(L.get("request.forbid")));
      p.setStatus(403);
      return p;
    }
  }
}