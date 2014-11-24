package nc.liat6.frame.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.zip.ZipFile;

/**
 * IO处理工具
 * 
 * @author 6tail
 *
 */
public class IOHelper{

  /**
   * 关闭
   * 
   * @param closeable
   */
  public static void closeQuietly(Closeable closeable){
    if(null==closeable){
      return;
    }
    try{
      closeable.close();
    }catch(IOException e){
    }
  }
  
  public static void closeQuietly(ZipFile zip){
    if(null==zip){
      return;
    }
    try{
      zip.close();
    }catch(IOException e){
    }
  }
  
  public static void closeQuietly(Socket socket){
    if(null==socket){
      return;
    }
    try{
      socket.close();
    }catch(IOException e){
    }
  }
}
