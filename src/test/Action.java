package test;

import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.db.transaction.TransFactory;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.upload.UploadedFile;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.web.WebExecute;
import nc.liat6.frame.web.response.Json;
import nc.liat6.frame.web.response.Paging;
import nc.liat6.frame.web.upload.FileUploader;

/**
 * 示例
 * @author 6tail
 *
 */
public class Action{
  
  /**
   * 自动分页
   * @return
   */
  public Object paging(){
    Request r = Context.get(Statics.REQUEST);
    ITrans t = TransFactory.getTrans();
    PageData pd = t.getSelecter().table("T006_CITY").page(r.getPageNumber(),r.getPageSize());
    Paging p = new Paging();
    p.setPageData(pd);
    p.setUri("/demo/paging.jsp");
    return p;
  }
  
  /**
   * 文件上传
   * @return
   */
  public Object upload(){
    Request r = Context.get(Statics.REQUEST);
    FileUploader uploader = r.find(WebExecute.TAG_UPLOADER);
    UploadedFile file = uploader.getFile();
    return new Json(file.getName());
  }
}
