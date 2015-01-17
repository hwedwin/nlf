package nc.liat6.frame.web.upload;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.execute.upload.IProgressListener;
import nc.liat6.frame.execute.upload.UploadRule;
import nc.liat6.frame.execute.upload.UploadedFile;

/**
 * 文件上传解析接口
 * 
 * @author 6tail
 *
 */
public interface IParser{
  /**
   * 解析上传文件
   * 
   * @param request
   * @param rule 上传规则
   * @return 上传文件列表
   */
  List<UploadedFile> parseRequest(HttpServletRequest request,UploadRule rule);

  /**
   * 这是进度监听
   * 
   * @param progressListener 进度监听接口
   */
  void setProgressListener(IProgressListener progressListener);
}