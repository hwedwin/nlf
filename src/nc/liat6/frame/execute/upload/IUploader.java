package nc.liat6.frame.execute.upload;

import java.util.List;
import nc.liat6.frame.web.upload.bean.UploadRule;

/**
 * 文件上传接口，仅支持单文件上传
 *
 * @author 6tail
 *
 */
public interface IUploader{
  /**
   * 获取上传的单个文件，如果有多个，取第一个
   *
   * @param allow 允许的格式
   * @return 上传的文件
   */
  UploadedFile getFile(String... allow);

  /**
   * 获取上传的单个文件，如果有多个，取第一个
   *
   * @param maxSize 允许的最大字节数
   * @param allow 允许的格式，文件后缀，不包括点号
   * @return 上传的文件
   */
  UploadedFile getFile(int maxSize,String... allow);

  /**
   * 获取上传的单个文件，如果有多个，取第一个
   * 
   * @param rule 上传规则
   * @return 上传的文件
   */
  UploadedFile getFile(UploadRule rule);
  
  /**
   * 获取上传的文件列表
   * @param allow 允许的格式，文件后缀，不包括点号
   * @return 上传的文件
   */
  List<UploadedFile> getFiles(String... allow);
  
  /**
   * 获取上传的文件列表
   * @param maxSize 允许的最大字节数
   * @param allow 允许的格式
   * @return 上传的文件
   */
  List<UploadedFile> getFiles(int maxSize,String... allow);
  
  /**
   * 获取上传的文件列表
   * @param rule 上传规则
   * @return 上传的文件
   */
  List<UploadedFile> getFiles(UploadRule rule);
}