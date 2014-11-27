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
   * 整型转指定长度的byte数组
   * 
   * @param value 整型数字
   * @param size byte长度
   * @return byte数组
   */
  public static byte[] toByte(int value,int size){
    byte[] b = new byte[size];
    for(int i = 0;i<size;i++){
      b[i] = (byte)((value>>>(b.length-i-1)*8)&0xFF);
    }
    return b;
  }

  /**
   * byte数组转整型
   * 
   * @param b byte数组
   * @return 整型
   */
  public static int toInt(byte[] b){
    int n = 0;
    for(int i=0;i<b.length;i++){
      n += (b[i]&0xFF)<<((b.length-i-1)*8);
    }
    return n;
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