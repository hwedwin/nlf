package nc.liat6.frame.web.response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.util.ContentTypes;

/**
 * 流式输出
 * 
 * @author 6tail
 * 
 */
public class Output{

  private InputStream inputStream;
  private int bufferSize = 20480;
  private String name;
  private String contentType = "application/octet-stream";
  private long fileSize = -1;

  public Output(File file){
    try{
      this.inputStream = new FileInputStream(file);
    }catch(FileNotFoundException e){
      throw new NlfException(e);
    }
    this.name = file.getName();
    this.fileSize = file.length();
    String f = file.getName();
    if(f.indexOf(".")>-1){
      String suffix = f.substring(f.lastIndexOf(".")+1);
      this.contentType = ContentTypes.getContenType(suffix);
    }
  }

  public Output(File file,String name){
    this(file);
    this.name = name;
  }

  public Output(InputStream inputStream,String name){
    this.inputStream = inputStream;
    this.name = name;
  }

  public InputStream getInputStream(){
    return inputStream;
  }

  public void setInputStream(InputStream inputStream){
    this.inputStream = inputStream;
  }

  public int getBufferSize(){
    return bufferSize;
  }

  public void setBufferSize(int bufferSize){
    this.bufferSize = bufferSize;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getContentType(){
    return contentType;
  }

  public void setContentType(String contentType){
    this.contentType = contentType;
  }

  public long getFileSize(){
    return fileSize;
  }

  public void setFileSize(long fileSize){
    this.fileSize = fileSize;
  }
}
