package nc.liat6.frame.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 对象工具
 * 
 * @author 六特尔
 * 
 */
public class Objecter{

  private Objecter(){}

  /**
   * 将对象序列化并压缩编码为字符串
   * 
   * @param o 对象
   * @return 字符串
   * @throws IOException
   */
  public synchronized static String encode(Object o) throws IOException{
    ByteArrayOutputStream bObject = null;
    ByteArrayOutputStream bZip = null;
    ObjectOutputStream outObject = null;
    ZipOutputStream outZip = null;
    try{
      bObject = new ByteArrayOutputStream();
      bZip = new ByteArrayOutputStream();
      outObject = new ObjectOutputStream(bObject);
      outObject.writeObject(o);
      outObject.flush();
      outZip = new ZipOutputStream(bZip);
      outZip.putNextEntry(new ZipEntry("0"));
      outZip.write(bObject.toByteArray());
      outZip.closeEntry();
      return Base64Coder.encode(bZip.toByteArray());
    }finally{
      try{
        outZip.close();
        bZip.close();
        outObject.close();
        bObject.close();
      }catch(Exception e){}
      outZip = null;
      bZip = null;
      outObject = null;
      bObject = null;
    }
  }

  /**
   * 将字符串解压缩并解码转换为对象
   * 
   * @param s 字符串
   * @return 对象
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public synchronized static Object decode(String s) throws IOException,ClassNotFoundException{
    ByteArrayOutputStream bObject = null;
    ByteArrayInputStream bZip = null;
    ObjectInputStream inObject = null;
    ZipInputStream inZip = null;
    try{
      bObject = new ByteArrayOutputStream();
      bZip = new ByteArrayInputStream(Base64Coder.decode(s));
      inZip = new ZipInputStream(bZip);
      inZip.getNextEntry();
      byte[] buffer = new byte[1024];
      int offset = -1;
      while((offset = inZip.read(buffer))!=-1){
        bObject.write(buffer,0,offset);
      }
      inObject = new ObjectInputStream(new ByteArrayInputStream(bObject.toByteArray()));
      return inObject.readObject();
    }finally{
      try{
        inZip.close();
        bZip.close();
        inObject.close();
        bObject.close();
      }catch(Exception e){}
      inZip = null;
      bZip = null;
      inObject = null;
      bObject = null;
    }
  }
}