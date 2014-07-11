package nc.liat6.frame.util;

/**
 * 运算辅助工具
 * 
 * @author 6tail
 * 
 */
public class Mather{

  private Mather(){}

  /**
   * 整型转4位byte
   * 
   * @param value 整型数字
   * @return 4位byte
   */
  public static byte[] toByte(int value){
    byte[] b = new byte[4];
    for(int i = 0;i<4;i++){
      int offset = (b.length-1-i)*8;
      b[i] = (byte)((value>>>offset)&0xFF);
    }
    return b;
  }

  /**
   * 4位byte转整型
   * 
   * @param b 4位byte
   * @return 整型
   */
  public static int toInt(byte[] b){
    return (b[0]<<24)+((b[1]&0xFF)<<16)+((b[2]&0xFF)<<8)+(b[3]&0xFF);
  }

  /**
   * 两个byte数组对接
   * 
   * @param a 前
   * @param b 后
   * @return 对接后的数组
   */
  public static byte[] merge(byte[] a,byte[] b){
    byte[] t = new byte[a.length+b.length];
    System.arraycopy(a,0,t,0,a.length);
    System.arraycopy(b,0,t,a.length,b.length);
    return t;
  }

  /**
   * 获取一个byte数组的一部分
   * 
   * @param src 源数组
   * @param from 开始点，从0开始计算
   * @param to 结束点，从0开始计算
   * @return byte数组
   */
  public static byte[] sub(byte[] src,int from,int to){
    byte[] t = new byte[to-from+1];
    System.arraycopy(src,from,t,0,to-from+1);
    return t;
  }
}