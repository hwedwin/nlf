package nc.liat6.frame.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 流处理
 * 
 * @author 6tail
 * 
 */
public class Streamer{

  public static final int BUFFER_SIZE = 10240;

  private Streamer(){}

  /**
   * 将输入流转换为byte数组
   * 
   * @param is 输入流
   * @return byte数组
   * @throws IOException
   */
  public static byte[] toByte(InputStream is) throws IOException{
    return toByte(is,BUFFER_SIZE);
  }

  /**
   * 将输入流转换为byte数组
   * 
   * @param is 输入流
   * @param bufferSize 缓冲区大小
   * @return byte数组
   * @throws IOException
   */
  public static byte[] toByte(InputStream is,int bufferSize) throws IOException{
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    int n = 0;
    byte b[] = new byte[bufferSize];
    while((n = is.read(b))!=-1){
      os.write(b,0,n);
    }
    os.close();
    is.close();
    return os.toByteArray();
  }
}
