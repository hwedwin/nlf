package nc.liat6.frame.execute.request;

import java.util.Locale;

/**
 * 客户端locale获取接口
 *
 * @author 6tail
 *
 */
public interface ILocaleFetcher{

  /**
   * 获取客户端locale
   *
   * @return locale
   */
  Locale getLocale();

  /**
   * 获取客户端locale字符串
   *
   * @return locale字符串，如zh-CN
   */
  String getLocaleString();

  /**
   * 设置locale
   *
   * @param locale locale
   */
  void setLocale(Locale locale);

  /**
   * 设置locale
   *
   * @param locale locale字符串，如zh-CN
   */
  void setLocale(String locale);
}