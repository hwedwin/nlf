package nc.liat6.frame.web.upload;

import java.util.HashMap;
import java.util.Map;
import nc.liat6.frame.db.entity.Bean;

public class UploadPool{
  private UploadPool(){}

  /** 文件状态池 */
  private static final Map<String,Bean> pool = new HashMap<String,Bean>();

  /**
   * 添加一个上传文件
   * 
   * @param id 文件标识
   * @param ub 文件状态封装对象
   */
  public static void add(String id,Bean ub){
    pool.put(id,ub);
  }

  /**
   * 更新文件状态
   * 
   * @param id 文件标识
   * @param ub 文件状态封装对象
   */
  public static void update(String id,Bean ub){
    Bean o = pool.get(id);
    if(null==o){
      add(id,ub);
    }else{
      o.set("uploaded",ub.getLong("uploaded",0));
      o.set("total",ub.getLong("total",-1));
    }
  }

  public static Bean get(String id){
    return pool.get(id);
  }
}