package nc.liat6.frame.execute.upload;

/**
 * 文件上传进度监听
 * 
 * @author 6tail
 * 
 */
public interface IProgressListener{

  /**
   * 更新上传状态
   * 
   * @param uploadedBytes 已上传字节
   * @param totalBytes 总字节
   */
  public void update(long uploadedBytes,long totalBytes);
}
