package nc.liat6.frame.web.tags.paging;

import nc.liat6.frame.Factory;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.paging.PagingParam;

/**
 * 分页模板工厂
 * 
 * @author 6tail
 * 
 */
public class PagingTemplateFactory{

  private static IPagingTemplateProvider templateProvider;
  private PagingTemplateFactory(){}
  
  static{
    templateProvider = Factory.getCaller().newInstance(IPagingTemplateProvider.class);
  }

  public static String getNormalTemplate(PageData pd,PagingParam pr,int near){
    return templateProvider.getNormalTemplate(pd,pr,near);
  }

  public static String getAjaxTemplate(PageData pd,PagingParam pr,int near){
    return templateProvider.getAjaxTemplate(pd,pr,near);
  }
}
