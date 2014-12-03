package nc.liat6.frame.web.request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.request.AbstractRequestFind;

public class WebCookieFetcher extends AbstractRequestFind implements IWebRequestFind,ICookieFetcher{

  public WebCookieFetcher(Request request){
    super(request);
  }

  public Cookie getCookie(String name){
    Cookie[] cookies = getCookies();
    for(Cookie cookie:cookies){
      if(cookie.getName().equals(name)){
        return cookie;
      }
    }
    return null;
  }

  public Cookie[] getCookies(){
    HttpServletRequest oreq = request.find(Statics.FIND_REQUEST);
    return oreq.getCookies();
  }

  public String getName(){
    return Statics.FIND_COOKIE_FETCHER;
  }
}
