package nc.liat6.frame.web.upload;

import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.web.response.Json;

/**
 * 文件上传状态
 * 
 * @author 6tail
 * 
 */
public final class UploadStatus{
  /**
   * 获取文件状态
   * 
   * @return
   */
  public Object getStatus(){
    Request r = Context.get(Statics.REQUEST);
    String id = r.get(FileUploader.ARG_ID);
    Bean ub = UploadPool.get(id);
    return new Json(null==ub?new Bean().set("id",id).set("uploaded",0).set("total",-1):ub);
  }
}