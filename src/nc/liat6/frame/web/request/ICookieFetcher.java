package nc.liat6.frame.web.request;

import javax.servlet.http.Cookie;

/**
 * Cookie获取接口
 *
 * @author 6tail
 *
 */
public interface ICookieFetcher{

  /**
   * 获取Cookie
   *
   * @param name 名称
   * @return Cookie
   */
  Cookie getCookie(String name);

  /**
   * 获取所有Cookie
   * @return Cookie数组
   */
  Cookie[] getCookies();
}