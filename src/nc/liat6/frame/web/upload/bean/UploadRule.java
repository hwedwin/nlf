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

  /** 允许的最大字节数，-1表式不限制 */
  private int maxSize = -1;
  /** 允许的文件类型列表，无表示全部允许 */
  private List<String> allows = new ArrayList<String>();

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
}
