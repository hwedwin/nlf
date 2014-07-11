package nc.liat6.frame.web.response;

import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.web.tags.paging.PagingTag;

/**
 * 返回 - 分页
 * 
 * @author liat6
 * 
 */
public class Paging extends Page{

  public Paging(){
    super();
  }

  public Paging(String uri){
    super(uri);
  }

  public Paging(String uri,PageData pd){
    super(uri);
    setPageData(pd);
  }

  /**
   * 设置分页数据
   * 
   * @param pd 分页数据对象
   */
  public void setPageData(PageData pd){
    set(PagingTag.PAGE_DATA_VAR,pd);
  }
}
