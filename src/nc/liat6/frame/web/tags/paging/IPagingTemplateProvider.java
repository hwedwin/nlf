package nc.liat6.frame.web.tags.paging;

import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.paging.PagingParam;

/**
 * 分页模板提供器
 *
 * @author 6tail
 *
 */
public interface IPagingTemplateProvider{

  /**
   * 获取普通页面的分页模板
   *
   * @param pd 分页数据
   * @param pr 分页参数
   * @param near 相邻页数
   * @return html代码
   */
  String getNormalTemplate(PageData pd,PagingParam pr,int near);

  /**
   * 获取AJAX请求页面的分页模板
   *
   * @param pd 分页数据
   * @param pr 分页参数
   * @param near 相邻页数
   * @return html代码
   */
  String getAjaxTemplate(PageData pd,PagingParam pr,int near);
}