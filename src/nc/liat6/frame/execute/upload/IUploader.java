package nc.liat6.frame.execute.upload;

/**
 * 文件上传接口，仅支持单文件上传
 * 
 * @author 6tail
 * 
 */
public interface IUploader{

  /**
   * 获取已上传文件封装
   * 
   * @param allow 允许的格式
   * @return 已上传文件封装
   */
  public UploadedFile getFile(String... allow);

  /**
   * 获取已上传文件封装
   * 
   * @param maxSize 允许的最大字节数
   * @param allow 允许的格式
   * @return 已上传文件封装
   */
  public UploadedFile getFile(int maxSize,String... allow);
}
