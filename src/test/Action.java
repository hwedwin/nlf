package test;

import java.awt.image.BufferedImage;
import java.io.IOException;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.db.transaction.TransFactory;
import nc.liat6.frame.exception.BadUploadException;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.upload.UploadedFile;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.ID;
import nc.liat6.frame.util.ImageHelper;
import nc.liat6.frame.web.WebContext;
import nc.liat6.frame.web.response.Alert;
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
   *
   * @return
   */
  public Object paging(){
    Request r = Context.get(Statics.REQUEST);
    ITrans t = TransFactory.getTrans();
    PageData pd = t.getSelecter().table("TABLE_USER").page(r.getPageNumber(),r.getPageSize());
    t.rollback();
    t.close();
    Paging p = new Paging();
    p.setPageData(pd);
    p.setUri("/demo/paging.jsp");
    return p;
  }

  /**
   * 文件上传，仅供测试
   *
   * @return
   * @throws IOException
   */
  public Object upload(){
    Request r = Context.get(Statics.REQUEST);
    FileUploader uploader = r.find(Statics.FIND_UPLOADER);
    UploadedFile file = uploader.getFile();
    return new Json(file.getName());
  }

  /**
   * 图片上传
   *
   * @return
   * @throws IOException
   */
  public Object uploadPic(){
    Request r = Context.get(Statics.REQUEST);
    FileUploader uploader = r.find(Statics.FIND_UPLOADER);
    UploadedFile file = uploader.getFile("jpg","gif","bmp","png");
    java.io.File dir = new java.io.File(WebContext.REAL_PATH,"uploaded");
    if(!dir.exists()||!dir.isDirectory()){
      dir.mkdirs();
    }
    String fileName = ID.next()+".jpg";
    try{
      BufferedImage img = ImageHelper.image(file.getBody());
      ImageHelper.writeJPG(img,new java.io.File(dir,fileName));
    }catch(IOException e){
      throw new BadUploadException("文件上传失败",e);
    }
    return new Json(WebContext.CONTEXT_PATH+"/uploaded/"+fileName);
  }

  /**
   * 添加数据
   * @return
   */
  public Object addData(){
    ITrans t = TransFactory.getTrans();
    for(int i=0;i<20;i++){
      t.getInserter().table("TABLE_USER").set("NAME","张三"+i).set("AGE",1+(int)(Math.random()*100)).insert();
    }
    t.commit();
    t.close();
    return new Alert("数据插入成功！");
  }
}
