package nc.liat6.frame.web.tags.paging;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.paging.PagingParam;
import nc.liat6.frame.util.Objecter;
import nc.liat6.frame.util.Pair;
import nc.liat6.frame.web.WebContext;

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
    return near;
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

  private void doNormalStartTag(){
    Request request = Context.get(Statics.REQUEST);
    HttpServletRequest r = request.find("request");
    JspWriter out = pageContext.getOut();
    try{
      String sPageRequest = (String)r.getAttribute(Request.PAGE_PARAM_VAR);
      PagingParam pr = (PagingParam)Objecter.decode(sPageRequest);
      String pageDataVar = null==dataVar?PAGE_DATA_VAR:dataVar;
      PageData pd = (PageData)r.getAttribute(pageDataVar);
      pd.setSorts(request.getSorts());
      String fid = id==null?PAGE_FORM_VAR:id;
      String SCRIPT = "NLFPAGING_"+fid;
      String uri = pr.getUri();
      while(uri.startsWith("/")){
        uri = uri.substring(1);
      }
      uri = WebContext.CONTEXT_PATH+"/"+uri;
      StringBuilder s = new StringBuilder();
      s.append("<script type=\"text/javascript\">\r\n");
      s.append("//<![CDATA[\r\n");
      s.append("var "+SCRIPT+"=(function(W,D){");
      s.append("var submit=function(){");
      s.append("D.getElementById('"+fid+"').submit();");
      s.append("};");
      s.append("var setPage = function(o){");
      s.append("D.getElementById('"+fid+"')['"+Request.PAGE_NUMBER_VAR+"'].value=''+o;");
      s.append("};");
      s.append("var setPageSize = function(o){");
      s.append("D.getElementById('"+fid+"')['"+Request.PAGE_SIZE_VAR+"'].value=''+o;");
      s.append("};");
      s.append("var setSort = function(k,v){");
      s.append("D.getElementById('"+fid+"')['"+Request.PAGE_SORT_VAR+"'].value = k+':'+v;");
      s.append("};");
      s.append("var sorts = {};");
      for(Pair pair:pd.getSorts()){
        s.append("sorts['"+pair.getKey()+"'] = '"+pair.getValue()+"';");
      }
      s.append("var getSorts = function(){");
      s.append("return sorts;");
      s.append("};");
      s.append("return {");
      s.append("'submit':function(){submit();},");
      s.append("'setPage':function(o){setPage(o);},");
      s.append("'goPage':function(o){setPage(o);submit();},");
      s.append("'setSort':function(k,v){setSort(k,v);setPage(1);submit();},");
      s.append("'getSorts':function(){return getSorts();},");
      s.append("'setPageSize':function(o){setPageSize(o);submit();}");
      s.append("};");
      s.append("})(window,document);\r\n");
      s.append("//]]>\r\n");
      s.append("</script>");
      s.append("<form id=\""+fid+"\" class=\""+PAGE_FORM_VAR+"\" action=\""+uri+"\" target=\"_self\" method=\"post\">");
      Map<?,?> p = pr.getParams();
      for(Object ok:p.keySet()){
        String key = (String)ok;
        if(Request.PAGE_NUMBER_VAR.equals(key))
          continue;
        if(Request.PAGE_SIZE_VAR.equals(key))
          continue;
        if(Request.PAGE_SORT_VAR.equals(key))
          continue;
        s.append("<input name=\""+key+"\" type=\"hidden\" value=\""+(p.get(key)+"")+"\" />");
      }
      s.append("<input name=\""+Request.PAGE_NUMBER_VAR+"\" type=\"hidden\" value=\""+pd.getPageNumber()+"\" />");
      s.append("<input name=\""+Request.PAGE_PARAM_VAR+"\" type=\"hidden\" value=\""+sPageRequest+"\" />");
      s.append("<input name=\""+Request.PAGE_SIZE_VAR+"\" type=\"hidden\" value=\""+pd.getPageSize()+"\" />");
      s.append("<input name=\""+Request.PAGE_SORT_VAR+"\" type=\"hidden\" value=\""+pd.getSortsAsString()+"\" />");
      s.append(" <span class=\"page\">"+L.get("page.number_prefix")+"</span>");
      s.append(" <input size=\"3\" class=\"go\" title=\""+L.get("page.input_number")+"\" type=\"text\" onblur=\""+SCRIPT+".goPage(this.value);\" value=\""+pd.getPageNumber()+"\" />");
      s.append("<span class=\"page\">/"+pd.getPageCount()+L.get("page.number_suffix")+"</span>");
      s.append(" &nbsp;&nbsp;<button class=\"first\" onclick=\""+SCRIPT+".goPage('"+pd.getFirstPageNumber()+"');\"><b>"+L.get("page.first")+"</b></button>");
      s.append(" <button class=\"previous\" onclick=\""+SCRIPT+".goPage('"+pd.getPreviousPageNumber()+"');\"><b>"+L.get("page.prev")+"</b></button>");
      if(null==near||near<1){
        near = 10;
      }
      int[] pn = pd.getNearPageNumbers(near);
      if(pn.length>0){
        if(pd.getFirstPageNumber()<pn[0]){
          s.append(" <button class=\"number\" onclick=\""+SCRIPT+".goPage('"+pd.getFirstPageNumber()+"');\"><b>"+pd.getFirstPageNumber()+"</b></button>");
          if(pd.getFirstPageNumber()<pn[0]-1){
            s.append(" <span class=\"ellipsis\">...</span>");
          }
        }
      }
      for(int i = 0;i<pn.length;i++){
        s.append(" <button");
        if(pn[i]!=pd.getPageNumber()){
          s.append(" class=\"number\" onclick=\""+SCRIPT+".goPage('"+pn[i]+"');\"");
        }else{
          s.append(" class=\"current\"");
        }
        s.append("><b>"+pn[i]+"</b></button>");
      }
      if(pn.length>0){
        if(pd.getLastPageNumber()>pn[pn.length-1]){
          if(pd.getLastPageNumber()>pn[pn.length-1]+1){
            s.append(" <span class=\"ellipsis\">...</span>");
          }
          s.append(" <button class=\"number\" onclick=\""+SCRIPT+".goPage('"+pd.getLastPageNumber()+"');\"><b>"+pd.getLastPageNumber()+"</b></button>");
        }
      }
      s.append(" <button class=\"next\" onclick=\""+SCRIPT+".goPage('"+pd.getNextPageNumber()+"');\"><b>"+L.get("page.next")+"</b></button>");
      s.append(" <button class=\"last\" onclick=\""+SCRIPT+".goPage('"+pd.getLastPageNumber()+"');\"><b>"+L.get("page.last")+"</b></button>");
      s.append(" &nbsp;&nbsp;<span class=\"record\">"+L.get("page.count_prefix")+pd.getRecordCount()+L.get("page.count_suffix")+"</span>");
      s.append(" &nbsp;&nbsp;"+L.get("page.size_prefix")+"<input size=\"3\" class=\"size\" title=\""+L.get("page.input_size")+"\" type=\"text\" onblur=\""+SCRIPT+".setPageSize(this.value);\" value=\""+pd.getPageSize()+"\" />");
      s.append(" <span class=\"size\">"+L.get("page.size_suffix")+"</span>");
      s.append("</form>");
      out.write(s.toString());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  private void doAjaxStartTag(){
    Request request = Context.get(Statics.REQUEST);
    HttpServletRequest r = request.find("request");
    JspWriter out = pageContext.getOut();
    try{
      String sPageRequest = (String)r.getAttribute(Request.PAGE_PARAM_VAR);
      String uuid = request.get(FIND_UUID_VAR);
      PagingParam pr = (PagingParam)Objecter.decode(sPageRequest);
      String pageDataVar = null==dataVar?PAGE_DATA_VAR:dataVar;
      PageData pd = (PageData)r.getAttribute(pageDataVar);
      pd.setSorts(request.getSorts());
      String fid = null==id?PAGE_FORM_VAR:id;
      String SCRIPT = "NLFPAGING_"+fid;
      String uri = pr.getUri();
      while(uri.startsWith("/")){
        uri = uri.substring(1);
      }
      StringBuilder s = new StringBuilder();
      s.append("<textarea style=\"display:none\" name=\"scriptBegin\">\r\n");
      s.append("var "+SCRIPT+"=(function(W,D){");
      s.append("var submit=function(){");
      s.append("var args={};");
      Map<?,?> p = pr.getParams();
      for(Object ok:p.keySet()){
        String key = (String)ok;
        if(Request.PAGE_NUMBER_VAR.equals(key))
          continue;
        if(Request.PAGE_SIZE_VAR.equals(key))
          continue;
        if(Request.PAGE_SORT_VAR.equals(key))
          continue;
        s.append("args['"+key+"']='"+p.get(key)+"';");
      }
      s.append("args['"+Request.PAGE_NUMBER_VAR+"']=I.$('"+fid+"')['"+Request.PAGE_NUMBER_VAR+"'].value;");
      s.append("args['"+Request.PAGE_SIZE_VAR+"']=I.$('"+fid+"')['"+Request.PAGE_SIZE_VAR+"'].value;");
      s.append("args['"+Request.PAGE_SORT_VAR+"']=I.$('"+fid+"')['"+Request.PAGE_SORT_VAR+"'].value;");
      s.append("var p = null;try{p=I['"+FIND_MAPPER_VAR+"']['"+uuid+"'];}catch(e){}");
      s.append("find('"+uri+"',args,p);");
      s.append("};");
      s.append("var setPage = function(o){");
      s.append("I.$('"+fid+"')['"+Request.PAGE_NUMBER_VAR+"'].value=''+o;");
      s.append("};");
      s.append("var setPageSize = function(o){");
      s.append("I.$('"+fid+"')['"+Request.PAGE_SIZE_VAR+"'].value=''+o;");
      s.append("};");
      s.append("var setSort = function(k,v){");
      s.append("I.$('"+fid+"')['"+Request.PAGE_SORT_VAR+"'].value = k+':'+v;");
      s.append("};");
      s.append("var sorts = {};");
      for(Pair pair:pd.getSorts()){
        s.append("sorts['"+pair.getKey()+"'] = '"+pair.getValue()+"';");
      }
      s.append("var getSorts = function(){");
      s.append("return sorts;");
      s.append("};");
      s.append("return {");
      s.append("'submit':function(){submit();},");
      s.append("'setPage':function(o){setPage(o);},");
      s.append("'goPage':function(o){setPage(o);submit();},");
      s.append("'setSort':function(k,v){setSort(k,v);setPage(1);submit();},");
      s.append("'getSorts':function(){return getSorts();},");
      s.append("'setPageSize':function(o){setPageSize(o);submit();}");
      s.append("};");
      s.append("})(window,document);");
      s.append("</textarea>\r\n");
      s.append("<form id=\""+fid+"\" class=\""+PAGE_FORM_VAR+"\" onsubmit=\"return false;\">");
      s.append("<input name=\""+Request.PAGE_NUMBER_VAR+"\" type=\"hidden\" value=\""+pd.getPageNumber()+"\" />");
      s.append("<input name=\""+Request.PAGE_PARAM_VAR+"\" type=\"hidden\" value=\""+sPageRequest+"\" />");
      s.append("<input name=\""+Request.PAGE_SIZE_VAR+"\" type=\"hidden\" value=\""+pd.getPageSize()+"\" />");
      s.append("<input name=\""+Request.PAGE_SORT_VAR+"\" type=\"hidden\" value=\""+pd.getSortsAsString()+"\" />");
      s.append(" <span class=\"page\">"+L.get("page.number_prefix")+"</span>");
      s.append(" <input size=\"3\" class=\"go\" title=\""+L.get("page.input_number")+"\" type=\"text\" onblur=\""+SCRIPT+".goPage(this.value);\" value=\""+pd.getPageNumber()+"\" />");
      s.append("<span class=\"page\">/"+pd.getPageCount()+L.get("page.number_suffix")+"</span>");
      s.append(" &nbsp;&nbsp;<button class=\"first\" onclick=\""+SCRIPT+".goPage('"+pd.getFirstPageNumber()+"');\"><b>"+L.get("page.first")+"</b></button>");
      s.append(" <button class=\"previous\" onclick=\""+SCRIPT+".goPage('"+pd.getPreviousPageNumber()+"');\"><b>"+L.get("page.prev")+"</b></button>");
      if(null==near||near<1){
        near = 10;
      }
      int[] pn = pd.getNearPageNumbers(near);
      if(pn.length>0){
        if(pd.getFirstPageNumber()<pn[0]){
          s.append(" <button class=\"number\" onclick=\""+SCRIPT+".goPage('"+pd.getFirstPageNumber()+"');\"><b>"+pd.getFirstPageNumber()+"</b></button>");
          if(pd.getFirstPageNumber()<pn[0]-1){
            s.append(" <span class=\"ellipsis\">...</span>");
          }
        }
      }
      for(int i = 0;i<pn.length;i++){
        s.append(" <button");
        if(pn[i]!=pd.getPageNumber()){
          s.append(" class=\"number\" onclick=\""+SCRIPT+".goPage('"+pn[i]+"');\"");
        }else{
          s.append(" class=\"current\"");
        }
        s.append("><b>"+pn[i]+"</b></button>");
      }
      if(pn.length>0){
        if(pd.getLastPageNumber()>pn[pn.length-1]){
          if(pd.getLastPageNumber()>pn[pn.length-1]+1){
            s.append(" <span class=\"ellipsis\">...</span>");
          }
          s.append(" <button class=\"number\" onclick=\""+SCRIPT+".goPage('"+pd.getLastPageNumber()+"');\"><b>"+pd.getLastPageNumber()+"</b></button>");
        }
      }
      s.append(" <button class=\"next\" onclick=\""+SCRIPT+".goPage('"+pd.getNextPageNumber()+"');\"><b>"+L.get("page.next")+"</b></button>");
      s.append(" <button class=\"last\" onclick=\""+SCRIPT+".goPage('"+pd.getLastPageNumber()+"');\"><b>"+L.get("page.last")+"</b></button>");
      s.append(" &nbsp;&nbsp;<span class=\"record\">"+L.get("page.count_prefix")+pd.getRecordCount()+L.get("page.count_suffix")+"</span>");
      s.append(" &nbsp;&nbsp;"+L.get("page.size_prefix")+"<input size=\"3\" class=\"size\" title=\""+L.get("page.input_size")+"\" type=\"text\" onblur=\""+SCRIPT+".setPageSize(this.value);\" value=\""+pd.getPageSize()+"\" />");
      s.append(" <span class=\"size\">"+L.get("page.size_suffix")+"</span>");
      s.append("</form>");
      out.write(s.toString());
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public int doStartTag(){
    if(null!=normal&&normal){
      doNormalStartTag();
    }else{
      doAjaxStartTag();
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
