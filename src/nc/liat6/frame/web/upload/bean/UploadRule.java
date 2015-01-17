package nc.liat6.frame.web.upload.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 上传规则
 * 
 * @author 6tail
 * 
 */
public class UploadRule{
  /** 缓冲区字节数 */
  private int bufferSize = 20480;
  /** 允许的最大字节数，-1表式不限制 */
  private int maxSize = -1;
  /** 上传临时目录 */
  private String tempDir = System.getProperty("java.io.tmpdir");
  /** 允许的文件类型列表，无表示全部允许 */
  private List<String> allows = new ArrayList<String>();

  public int getBufferSize(){
    return bufferSize;
  }

  public void setBufferSize(int bufferSize){
    this.bufferSize = bufferSize;
  }

  public List<String> getAllows(){
    return allows;
  }

  public void setAllows(List<String> allows){
    this.allows = allows;
  }

  public void addAllow(String allow){
    allows.add(allow);
  }

  public int getMaxSize(){
    return maxSize;
  }

  public void setMaxSize(int maxSize){
    this.maxSize = maxSize;
  }

  public String getTempDir(){
    return tempDir;
  }

  public void setTempDir(String tempDir){
    this.tempDir = tempDir;
  }
}