package nc.liat6.frame.web.upload;

import java.util.HashMap;
import java.util.Map;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.web.response.Json;
import nc.liat6.frame.web.upload.bean.UploadBean;

/**
 * 文件上传状态
 * 
 * @author 6tail
 * 
 */
public class UploadStatus{

  /** 文件状态池 */
  private static final Map<String,UploadBean> POOL = new HashMap<String,UploadBean>();

  /**
   * 添加一个上传文件
   * 
   * @param id 文件标识
   * @param ub 文件状态封装对象
   */
  public static void add(String id,UploadBean ub){
    POOL.put(id,ub);
  }

  /**
   * 更新文件状态
   * 
   * @param id 文件标识
   * @param ub 文件状态封装对象
   */
  public static void update(String id,UploadBean ub){
    UploadBean o = POOL.get(id);
    if(null==o){
      add(id,ub);
    }else{
      o.setUploaded(ub.getUploaded());
      o.setTotal(ub.getTotal());
    }
  }

  /**
   * 获取文件状态
   * 
   * @return
   */
  public Object getStatus(){
    Request r = Context.get(Statics.REQUEST);
    String id = r.get(FileUploader.ARG_ID);
    UploadBean ub = POOL.get(id);
    if(null==ub){
      return new Json(new UploadBean());
    }
    return new Json(ub);
  }
}
