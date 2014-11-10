package nc.liat6.frame.web.upload;

import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.execute.upload.IProgressListener;
import nc.liat6.frame.execute.upload.UploadedFile;
import nc.liat6.frame.web.upload.bean.UploadRule;

public interface IParser{

  UploadedFile parseRequest(HttpServletRequest request,UploadRule rule);

  void setProgressListener(IProgressListener progressListener);
}