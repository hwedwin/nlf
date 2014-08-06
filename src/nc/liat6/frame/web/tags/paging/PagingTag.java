package nc.liat6.frame.web.tags.paging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.paging.PagingParam;
import nc.liat6.frame.util.Objecter;
import nc.liat6.frame.web.WebContext;
import nc.liat6.frame.web.WebExecute;

/**
 * 分页标签
 * 
 * @author liat6
 * 
 */
public class PagingTag extends TagSupport{

  private static final long serialVersionUID = -5416287822609137922L;
  /** 分页数据标识 */
  public static final String PAGE_DATA_VAR = "nlfPagingData";
  /** 分页表单标识 */
  public static final String PAGE_FORM_VAR = "nlfPagingForm";
  public static final String FIND_UUID_VAR = "_find_uuid";
  public static final String FIND_MAPPER_VAR = "_find_mapper";
  /** 默认相邻页数 */
  public static int DEFAULT_NEAR = 3;
  /** 分页表单ID */
  private String id;
  /** 相邻页数 */
  private Integer near;
  /** 是否普通页面 */
  private Boolean normal;
  /** 分页数据key */
  private String dataVar;

  /**
   * 获取分页表单ID
   * 
   * @return 分页表单ID
   */
  public String getId(){
    return id;
  }

  /**
   * 设置分页表单ID
   * 
   * @param id 分页表单ID
   */
  public void setId(String id){
    this.id = id;
  }

  /**
   * 获取相邻页数
   * 
   * @return 相邻页数
   */
  public Integer getNear(){
    return (null==near||near<1)?DEFAULT_NEAR:near;
  }

  /**
   * 设置相邻页数
   * 
   * @param near 相邻页数
   */
  public void setNear(Integer near){
    this.near = near;
  }

  public Boolean getNormal(){
    return normal;
  }

  public void setNormal(Boolean normal){
    this.normal = normal;
  }

  public String getDataVar(){
    return dataVar;
  }

  public void setDataVar(String dataVar){
    this.dataVar = dataVar;
  }

  public int doStartTag(){
    Request request = Context.get(Statics.REQUEST);
    HttpServletRequest originRequest = request.find(WebExecute.TAG_REQUEST);
    JspWriter out = pageContext.getOut();
    try{
      PageData pd = (PageData)originRequest.getAttribute(null==dataVar?PAGE_DATA_VAR:dataVar);
      pd.setSorts(request.getSorts());
      String strPageRequest = originRequest.getAttribute(Request.PAGE_PARAM_VAR)+"";
      PagingParam pr = (PagingParam)Objecter.decode(strPageRequest);
      pr.setParam(Request.PAGE_NUMBER_VAR,pd.getPageNumber()+"");
      pr.setParam(Request.PAGE_PARAM_VAR,strPageRequest);
      pr.setParam(Request.PAGE_SIZE_VAR,pd.getPageSize()+"");
      pr.setParam(Request.PAGE_SORT_VAR,pd.getSortsAsString());
      String formId = id==null?PAGE_FORM_VAR:id;
      String uuid = request.get(FIND_UUID_VAR);
      String script = "NLFPAGING_"+formId;
      String html = null;
      boolean ajax = false;
      // 获取AJAX请求标识
      String headAjax = originRequest.getHeader("x-requested-with");
      if(null==headAjax){
        if("NlfHttpRequest".equals(originRequest.getHeader("nlf-requested-with"))){
          ajax = true;
        }
      }else{
        ajax = true;
      }
      if(ajax){
        html = PagingTemplateFactory.getAjaxTemplate(pd,pr,getNear());
      }else{
        html = PagingTemplateFactory.getNormalTemplate(pd,pr,getNear());
      }
      String uri = pr.getUri();
      while(uri.startsWith("/")){
        uri = uri.substring(1);
      }
      if(!ajax){
        uri = WebContext.CONTEXT_PATH+"/"+uri;
      }
      html = html.replace("${script}",script);
      html = html.replace("${formId}",formId);
      html = html.replace("${uuid}",uuid);
      html = html.replace("${mapper}",FIND_MAPPER_VAR);
      html = html.replace("${class}",PAGE_FORM_VAR);
      html = html.replace("${uri}",uri);
      out.write(html);
    }catch(Exception e){
      Logger.getLog().error(L.get("page.error"),e);
    }
    return SKIP_BODY;
  }

  public int doAfterBodyTag(){
    return SKIP_BODY;
  }

  public int doEndTag(){
    return EVAL_PAGE;
  }
}
