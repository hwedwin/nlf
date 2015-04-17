package nc.liat6.frame.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import nc.liat6.frame.Factory;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;

/**
 * 记录ID生成器
 * <p>
 * 通过配置文件id.json设置prefix可支持分布式应用
 * </p>
 * 
 * @author 六特尔
 */
public class ID{
  /** 前缀 */
  public static String prefix = "";
  /** 自增序列 */
  private static int serial = 0;
  /** 当前时间 */
  private static long time = 0;
  /** 自增序列位数 */
  private static final int DIGIT = 3;
  /** 配置文件 */
  public static final String CONFIG_FILE = "id.json";

  private ID(){}

  static{
    try{
      Bean o = JSON.toBean(Stringer.readFromFile(new File(Factory.APP_PATH,CONFIG_FILE)));
      prefix = o.getString("prefix","");
    }catch(IOException e){}
  }

  /**
   * 获取一个新的不重复的ID
   * 
   * @return 长整型数字
   */
  public synchronized static BigDecimal next(){
    StringBuilder s = new StringBuilder();
    long t = System.currentTimeMillis();
    if(time!=t){
      time = t;
      serial = 0;
    }else{
      int max = (int)Math.pow(10,DIGIT);
      if(serial>=max-1){
        while(t==time){
          t = System.currentTimeMillis();
        }
        time = t;
        serial = 0;
      }else serial++;
    }
    s.append(serial);
    while(s.length()<DIGIT){
      s.insert(0,"0");
    }
    s.insert(0,time);
    s.insert(0,prefix);
    return BigDecimal.valueOf(Long.parseLong(s.toString()));
  }
}