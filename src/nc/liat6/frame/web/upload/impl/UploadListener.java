package nc.liat6.frame.web.upload.impl;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.execute.upload.IProgressListener;
import nc.liat6.frame.web.upload.UploadPool;

/**
 * 文件上传监听
 * 
 * @author 6tail
 * 
 */
public class UploadListener implements IProgressListener{
  /** 文件标识 */
  private String id;

  public UploadListener(String id){
    this.id = id;
    UploadPool.add(id,new Bean().set("id",id).set("uploaded",0).set("total",1));
  }

  public void update(long uploaded,long total){
    UploadPool.update(id,new Bean().set("id",id).set("uploaded",uploaded).set("total",total));
  }
}