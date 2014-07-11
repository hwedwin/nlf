package nc.liat6.frame.web.upload.bean;

/**
 * 上传文件状态封装
 * 
 * @author 6tail
 * 
 */
public class UploadBean{

  /** 文件标识 */
  private String id;
  /** 已上传字节数 */
  private long uploaded;
  /** 总字节数 */
  private long total;

  /**
   * 获取文件标识
   * 
   * @return 唯一标识
   */
  public String getId(){
    return id;
  }

  /**
   * 设置文件标识
   * 
   * @param id 唯一标识
   */
  public void setId(String id){
    this.id = id;
  }

  /**
   * 获取已上传字节数
   * 
   * @return 已上传字节数
   */
  public long getUploaded(){
    return uploaded;
  }

  /**
   * 设置已上传字节数
   * 
   * @param uploaded 已上传字节数
   */
  public void setUploaded(long uploaded){
    this.uploaded = uploaded;
  }

  /**
   * 获取总字节数
   * 
   * @return 总字节数
   */
  public long getTotal(){
    return total;
  }

  /**
   * 设置总字节数
   * 
   * @param total 总字节数
   */
  public void setTotal(long total){
    this.total = total;
  }
}
