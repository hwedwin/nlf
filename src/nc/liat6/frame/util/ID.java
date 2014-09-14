package nc.liat6.frame.util;

import java.math.BigDecimal;

/**
 * 记录ID生成器
 * <p>支持每秒生成一百万个ID，不支持分布式应用</p>
 * 
 * @author 六特尔
 */
public class ID{

  /** 自增序列 */
  private static int serial = 0;
  /** 当前时间 */
  private static long time = 0;
  /** 自增序列位数 */
  private static final int DIGIT = 3;

  private ID(){}

  /**
   * 获取一个新的不重复的ID
   * 
   * @return 长整型数字
   */
  public synchronized static BigDecimal next(){
    String s = "";
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
      }else{
        serial++;
      }
    }
    s += serial;
    while(s.length()<DIGIT){
      s = "0"+s;
    }
    return BigDecimal.valueOf(Long.parseLong(time+s));
  }
}