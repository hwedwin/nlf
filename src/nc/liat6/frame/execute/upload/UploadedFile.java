package nc.liat6.frame.execute.upload;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 已上传文件封装
 * 
 * @author 6tail
 * 
 */
public class UploadedFile{

  /** 缓冲区大小 */
  public static final int BUFFER_SIZE = 20480;
  /** 文件名称,包含后缀，不包含完整路径 */
  private String name;
  /** 文件大小，单位：字节 */
  private long size;
  /** 文件类型 */
  private String contentType;
  /** 文件后缀，小写字母，不包括点号 */
  private String suffix = "";
  /** 输入流 */
  private InputStream inputStream;

  /**
   * 获取文件名称
   * 
   * @return 文件名称
   */
  public String getName(){
    return name;
  }

  /**
   * 设置文件名称
   * 
   * @param name 文件名称
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * 获取文件内容，如果文件较大，不建议调用该方法
   * 
   * @return 文件内容
   */
  public byte[] getBody() throws IOException{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    byte[] buffer = new byte[BUFFER_SIZE];
    int l = 0;
    while((l = inputStream.read(buffer))!=-1){
      baos.write(buffer,0,l);
    }
    byte[] r = baos.toByteArray();
    baos.close();
    return r;
  }

  /**
   * 写到文件
   * 
   * @param file 目标文件
   * @throws IOException
   */
  public void writeTo(File file) throws IOException{
    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
    byte[] buffer = new byte[BUFFER_SIZE];
    int l = 0;
    while((l = inputStream.read(buffer))!=-1){
      bos.write(buffer,0,l);
    }
    bos.close();
  }

  /**
   * 获取输入流
   * 
   * @return 输入流
   */
  public InputStream getInputStream(){
    return inputStream;
  }

  /**
   * 设置输入流
   * 
   * @param inputStream
   */
  public void setInputStream(InputStream inputStream){
    this.inputStream = inputStream;
  }

  /**
   * 获取文件大小
   * 
   * @return 文件大小，单位：字节
   */
  public long getSize(){
    return size;
  }

  /**
   * 设置文件大小
   * 
   * @param size 文件大小，单位：字节
   */
  public void setSize(long size){
    this.size = size;
  }

  /**
   * 获取文件类型
   * 
   * @return 文件类型
   */
  public String getContentType(){
    return contentType;
  }

  /**
   * 设置文件类型
   * 
   * @param contentType 文件类型
   */
  public void setContentType(String contentType){
    this.contentType = contentType;
  }

  /**
   * 获取文件后缀
   * 
   * @return 文件后缀：小写字母，不包括点号
   */
  public String getSuffix(){
    return suffix;
  }

  /**
   * 设置文件后缀
   * 
   * @param suffix 文件后缀：小写字母，不包括点号
   */
  public void setSuffix(String suffix){
    this.suffix = suffix;
  }
}
