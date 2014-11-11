package nc.liat6.frame.web.request;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;

public class WebCookieFetcher implements ICookieFetcher{

  /** 当前请求 */
  private Request request;

  public WebCookieFetcher(Request request){
    this.request = request;
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
}
