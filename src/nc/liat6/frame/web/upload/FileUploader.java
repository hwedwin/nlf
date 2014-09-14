package nc.liat6.frame.web.upload;

import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.execute.upload.IUploader;
import nc.liat6.frame.execute.upload.UploadedFile;
import nc.liat6.frame.web.upload.bean.UploadRule;
import nc.liat6.frame.web.upload.impl.UploadParser;
import nc.liat6.frame.web.upload.impl.UploadListener;

/**
 * 文件上传器
 * 
 * @author 6tail
 * 
 */
public class FileUploader implements IUploader{

  public static final String ARG_ID = "NLF_UPLOAD_ID";
  /** 源请求 */
  private Request request;

  public FileUploader(Request request){
    this.request = request;
  }

  public UploadedFile getFile(String... allow){
    return getFile(-1,allow);
  }

  public UploadedFile getFile(int maxSize,String... allow){
    UploadRule rule = new UploadRule();
    rule.setMaxSize(maxSize);
    for(String s:allow){
      rule.addAllow(s.toLowerCase());
    }
    HttpServletRequest r = request.find("request");
    String id = request.get(ARG_ID);
    IParser parser = new UploadParser();
    parser.setProgressListener(new UploadListener(id));
    return parser.parseRequest(r,rule);
  }
}
