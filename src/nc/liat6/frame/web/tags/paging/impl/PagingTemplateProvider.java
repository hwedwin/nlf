package nc.liat6.frame.web.tags.paging.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.paging.PagingParam;
import nc.liat6.frame.util.Pair;
import nc.liat6.frame.web.tags.paging.IPagingTemplateProvider;

/**
 * 框架的默认分页模板提供器实现
 *
 * @author 6tail
 *
 */
public class PagingTemplateProvider implements IPagingTemplateProvider{

  public static final String LINK_NONE = "javascript:void(0);";
  public static final int MAX_GET_LENGTH = 2048;

  private String getLink(PagingParam pr,int pageNumber){
    StringBuilder s = new StringBuilder();
    Map<?,?> p = pr.getParams();
    for(Object k:p.keySet()){
      String key = (String)k;
      s.append("&");
      s.append(key);
      s.append("=");
      String value = (String)p.get(key);
      if(Request.PAGE_NUMBER_VAR.equals(key)){
        value = pageNumber+"";
      }
      try{
        s.append(URLEncoder.encode(value,"UTF-8"));
      }catch(UnsupportedEncodingException e){
        return LINK_NONE;
      }
    }
    if(s.length()>0){
      s.delete(0,1);
    }
    String rs = s.insert(0,"${uri}?").toString();
    if(rs.length()>MAX_GET_LENGTH){
      return LINK_NONE;
    }
    return rs;
  }

  private String getForm(PageData pd,PagingParam pr,int near){
    StringBuffer s = new StringBuffer();
    s.append("<span class=\"page-prefix\">"+L.get("page.number_prefix")+"</span>");
    s.append("<input class=\"go\" title=\""+L.get("page.input_number")+"\" type=\"text\" onblur=\"${script}.goPage(this.value);\" value=\""+pd.getPageNumber()+"\" />");
    s.append("<span class=\"page-slash\">"+L.get("page.slash")+"</span>");
    s.append("<span class=\"page-count\">"+pd.getPageCount()+"</span>");
    s.append("<span class=\"page-suffix\">"+L.get("page.number_suffix")+"</span>");
    s.append("<a class=\"first\" href=\""+getLink(pr,pd.getFirstPageNumber())+"\" onclick=\"${script}.goPage('"+pd.getFirstPageNumber()+"');\">"+L.get("page.first")+"</a>");
    s.append("<a class=\"previous\" href=\""+getLink(pr,pd.getPreviousPageNumber())+"\" onclick=\"${script}.goPage('"+pd.getPreviousPageNumber()+"');\">"+L.get("page.prev")+"</a>");
    int[] pn = pd.getNearPageNumbers(near);
    if(pn.length>0){
      if(pd.getFirstPageNumber()<pn[0]){
        s.append("<a class=\"number\" href=\""+getLink(pr,pd.getFirstPageNumber())+"\" onclick=\"${script}.goPage('"+pd.getFirstPageNumber()+"');\">"+pd.getFirstPageNumber()+"</a>");
        if(pd.getFirstPageNumber()<pn[0]-1){
          s.append("<a class=\"ellipsis\">...</a>");
        }
      }
    }
    for(int i = 0;i<pn.length;i++){
      s.append("<a");
      if(pn[i]!=pd.getPageNumber()){
        s.append(" class=\"number\" href=\""+getLink(pr,pn[i])+"\" onclick=\"${script}.goPage('"+pn[i]+"');\"");
      }else{
        s.append(" class=\"current\"");
      }
      s.append(">"+pn[i]+"</a>");
    }
    if(pn.length>0){
      if(pd.getLastPageNumber()>pn[pn.length-1]){
        if(pd.getLastPageNumber()>pn[pn.length-1]+1){
          s.append("<a href=\"javascript:void(0);\" class=\"ellipsis\">...</a>");
        }
        s.append("<a class=\"number\" href=\""+getLink(pr,pd.getLastPageNumber())+"\" onclick=\"${script}.goPage('"+pd.getLastPageNumber()+"');\">"+pd.getLastPageNumber()+"</a>");
      }
    }
    s.append("<a class=\"next\" href=\""+getLink(pr,pd.getNextPageNumber())+"\" onclick=\"${script}.goPage('"+pd.getNextPageNumber()+"');\">"+L.get("page.next")+"</a>");
    s.append("<a class=\"last\" href=\""+getLink(pr,pd.getLastPageNumber())+"\" onclick=\"${script}.goPage('"+pd.getLastPageNumber()+"');\">"+L.get("page.last")+"</a>");
    s.append("<span class=\"record-prefix\">"+L.get("page.record_prefix")+"</span>");
    s.append("<span class=\"record-count\">"+pd.getRecordCount()+"</span>");
    s.append("<span class=\"record-suffix\">"+L.get("page.record_suffix")+"</span>");
    s.append("<span class=\"size-prefix\">"+L.get("page.size_prefix")+"</span>");
    s.append("<input class=\"size\" title=\""+L.get("page.input_size")+"\" type=\"text\" onblur=\"${script}.setPageSize(this.value);\" value=\""+pd.getPageSize()+"\" />");
    s.append("<span class=\"size-suffix\">"+L.get("page.size_suffix")+"</span>");
    return s.toString();
  }
  @Override
  public String getNormalTemplate(PageData pd,PagingParam pr,int near){
    StringBuilder s = new StringBuilder();
    s.append("<form id=\"${formId}\" class=\"${class}\" action=\"${uri}\" target=\"_self\" method=\"post\">");
    Map<?,?> p = pr.getParams();
    for(Object k:p.keySet()){
      String key = (String)k;
      s.append("<input type=\"hidden\" name=\"");
      s.append(key);
      s.append("\" value=\"");
      s.append(p.get(key));
      s.append("\" />");
    }
    s.append(getForm(pd,pr,near));
    s.append("</form>\r\n");
    s.append("<script type=\"text/javascript\">\r\n");
    s.append("var ${script}=(function(W,D){");
    s.append("var submit=function(){D.getElementById('${formId}').submit();};");
    s.append("var setPage = function(n){D.getElementById('${formId}')['"+Request.PAGE_NUMBER_VAR+"'].value=n+'';};");
    s.append("var setPageSize = function(n){D.getElementById('${formId}')['"+Request.PAGE_SIZE_VAR+"'].value=n+'';};");
    s.append("var setSort = function(k,v){D.getElementById('${formId}')['"+Request.PAGE_SORT_VAR+"'].value = k+':'+v;};");
    s.append("var sorts = {};");
    for(Pair pair:pd.getSorts()){
      s.append("sorts['");
      s.append(pair.getKey());
      s.append("'] = '");
      s.append(pair.getValue());
      s.append("';");
    }
    s.append("var getSorts = function(){return sorts;};");
    s.append("return {");
    s.append("submit:function(){submit();},");
    s.append("setPage:function(n){setPage(n);},");
    s.append("goPage:function(n){setPage(n);submit();},");
    s.append("setSort:function(k,v){setSort(k,v);setPage(1);submit();},");
    s.append("getSorts:function(){return getSorts();},");
    s.append("setPageSize:function(n){setPageSize(n);submit();}");
    s.append("};");
    s.append("})(window,document);\r\n");
    s.append("I.want(function(){var a=I.$('${formId}','tag','a');for(var i=0;i<a.length;i++){a[i].href=\'javascript:void(0);\';}});\r\n");
    s.append("</script>");
    return s.toString();
  }

  @Override
  public String getAjaxTemplate(PageData pd,PagingParam pr,int near){
    StringBuilder s = new StringBuilder();
    s.append("<form id=\"${formId}\" class=\"${class}\" onsubmit=\"return false;\">");
    s.append(getForm(pd,pr,near));
    s.append("</form>\r\n");
    s.append("<script type=\"text/javascript\">\r\n");
    s.append("var ${script}=(function(W,D){");
    s.append("var args={};");
    Map<?,?> p = pr.getParams();
    for(Object k:p.keySet()){
      String key = (String)k;
      s.append("args['"+key+"']='"+p.get(key)+"';");
    }
    s.append("var submit=function(){I.want(function(){");
    s.append("var p = null;try{p=I['${mapper}']['${uuid}'];}catch(e){}");
    s.append("I.net.Page.find('${uri}',args,p);");
    s.append("});};");
    s.append("var setPage = function(n){args['"+Request.PAGE_NUMBER_VAR+"']=n+'';};");
    s.append("var setPageSize = function(n){args['"+Request.PAGE_SIZE_VAR+"']=n+'';};");
    s.append("var setSort = function(k,v){args['"+Request.PAGE_SORT_VAR+"']=k+':'+v;};");
    s.append("var sorts = {};");
    for(Pair pair:pd.getSorts()){
      s.append("sorts['");
      s.append(pair.getKey());
      s.append("'] = '");
      s.append(pair.getValue());
      s.append("';");
    }
    s.append("var getSorts = function(){return sorts;};");
    s.append("return {");
    s.append("submit:function(){submit();},");
    s.append("setPage:function(n){setPage(n);},");
    s.append("goPage:function(n){setPage(n);submit();},");
    s.append("setSort:function(k,v){setSort(k,v);setPage(1);submit();},");
    s.append("getSorts:function(){return getSorts();},");
    s.append("setPageSize:function(n){setPageSize(n);submit();}");
    s.append("};");
    s.append("})(window,document);\r\n");
    s.append("I.want(function(){var a=I.$('${formId}','tag','a');for(var i=0;i<a.length;i++){a[i].href=\'javascript:void(0);\';}});\r\n");
    s.append("</script>");
    return s.toString();
  }
}
