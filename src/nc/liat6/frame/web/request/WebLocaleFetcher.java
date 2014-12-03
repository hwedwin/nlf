package nc.liat6.frame.web.request;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.request.AbstractRequestFind;
import nc.liat6.frame.execute.request.ILocaleFetcher;
import nc.liat6.frame.locale.LocaleFactory;

/**
 * WEB应用locale获取器
 *
 * @author 6tail
 *
 */
public class WebLocaleFetcher extends AbstractRequestFind implements IWebRequestFind,ILocaleFetcher{

  public WebLocaleFetcher(Request request){
    super(request);
  }

  public Locale getLocale(){
    HttpSession session = request.find(Statics.FIND_SESSION);
    try{
      String locale = (String)session.getAttribute("locale");
      String language = locale.substring(0,2);
      String country = locale.substring(3);
      return new Locale(language,country);
    }catch(Exception e){
      return LocaleFactory.locale;
    }
  }

  public String getLocaleString(){
    return getLocale().toString();
  }

  public void setLocale(Locale locale){
    setLocale(locale.toString());
  }

  public void setLocale(String locale){
    HttpSession session = request.find(Statics.FIND_SESSION);
    session.setAttribute("locale",locale);
  }

  public String getName(){
    return Statics.FIND_LOCALE_FETCHER;
  }
}
