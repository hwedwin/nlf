package nc.liat6.frame.web.upload.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.exception.BadUploadException;
import nc.liat6.frame.execute.upload.IProgressListener;
import nc.liat6.frame.execute.upload.UploadedFile;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.ByteArray;
import nc.liat6.frame.util.ID;
import nc.liat6.frame.util.Mather;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.web.upload.IParser;
import nc.liat6.frame.web.upload.bean.UploadRule;

/**
 * 通过磁盘缓存的文件上传解析器
 * <p>
 * 只实现了单个文件的上传，多余的文件会被忽略。
 * </p>
 * <p>
 * 由于只获取到整个请求的body总大小，所以文件的上传进度的数值并不是纯粹文件的大小。
 * </p>
 * 
 * @author 6tail
 * 
 */
public class UploadParser implements IParser{

  /** 回车 */
  public static final byte CR = 0x0D;
  /** 换行 */
  public static final byte LF = 0x0A;
  /** - */
  public static final byte DASH = 0x2D;
  /** 缓冲区大小 */
  public static final int BUFFER_SIZE = 20480;
  /** 头部分隔 */
  public static final byte[] HEADER_SEPARATOR = {CR,LF,CR,LF};
  /** 块分隔 */
  protected static final byte[] FIELD_SEPARATOR = {CR,LF};
  /** boundary前缀 */
  public static final byte[] BOUNDARY_PREFIX = {CR,LF,DASH,DASH};
  /** 临时文件目录 */
  public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
  public static final String BOUNDARY_TAG = "boundary=";
  public static final String FILE_TAG = "filename=\"";
  /** 当前字节 */
  private int c;
  /** 上传进度监听，因暂时没有好的办法获取文件大小，故不是很准确 */
  private IProgressListener listener;
  private HttpServletRequest request;
  private ServletInputStream reader;

  /**
   * 获取boundary
   * 
   * @return boundary
   */
  private String getBoundary(){
    String contentType = request.getContentType();
    if(null==contentType){
      return null;
    }
    if(!contentType.contains("multipart/form-data")){
      return null;
    }
    if(!contentType.contains(BOUNDARY_TAG)){
      return null;
    }
    return contentType.substring(contentType.indexOf(BOUNDARY_TAG)+BOUNDARY_TAG.length());
  }

  /**
   * 读取下一字节
   */
  private void next() throws IOException{
    c = reader.read();
  }

  /**
   * 读取，直到遇到结束标识的字节数组
   * 
   * @param endTags 结束标识的字节数组
   * @return 已读取的字节数组
   * @throws IOException
   */
  private byte[] readUntil(byte[] endTags) throws IOException{
    int index = 0;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    while(-1!=c){
      next();
      baos.write(c);
      if(c==endTags[index]){
        index++;
        if(index>=endTags.length){
          break;
        }
      }else{
        index = 0;
      }
    }
    byte[] r = baos.toByteArray();
    baos.close();
    return r;
  }

  /**
   * 跳过，直到遇到结束标识的字节数组
   * 
   * @param endTags 结束标识的字节数组
   * @return l 跳过的总字节数
   * @throws IOException
   */
  private int skipUntil(byte[] endTags) throws IOException{
    int l = 0;
    int index = 0;
    while(-1!=c){
      next();
      l++;
      if(c==endTags[index]){
        index++;
        if(index>=endTags.length){
          break;
        }
      }else{
        index = 0;
      }
    }
    return l;
  }

  public UploadedFile parseRequest(HttpServletRequest request,UploadRule rule){
    this.request = request;
    // 获取boundary
    String boundary = getBoundary();
    if(null==boundary){
      return null;
    }
    byte[] boundaryBytes = boundary.getBytes();
    // 总的数据body大小
    int total = request.getContentLength();
    if(rule.getMaxSize()>-1){
      if(total>rule.getMaxSize()){
        throw new BadUploadException(L.get("upload.max_size")+(rule.getMaxSize()*100/1024/100D)+"KB");
      }
    }
    // 已上传字节数
    int uploaded = 0;
    // 是否是文件块
    boolean fileField = false;
    listener.update(uploaded,total);
    UploadedFile uf = new UploadedFile();
    try{
      reader = request.getInputStream();
      while(!fileField){
        // 获取每块的头部信息
        byte[] headBytes = readUntil(HEADER_SEPARATOR);
        uploaded += headBytes.length;
        // 按行分隔
        String[] heads = new String(headBytes,Statics.ENCODE).split("\r\n");
        for(String s:heads){
          // 文件判断
          if(s.contains(FILE_TAG)){
            fileField = true;
            String fileName = Stringer.cut(s,FILE_TAG,"\"");
            int index = fileName.lastIndexOf("\\");
            if(index>-1){
              fileName = fileName.substring(index+1);
            }
            index = fileName.lastIndexOf("/");
            if(index>-1){
              fileName = fileName.substring(index+1);
            }
            uf.setName(fileName);
            index = fileName.lastIndexOf(".");
            if(index>-1){
              uf.setSuffix(fileName.substring(index+1).toLowerCase());
            }
            if(rule.getAllows().size()>0){
              if(!rule.getAllows().contains(uf.getSuffix())){
                throw new BadUploadException(L.get("upload.formats")+Stringer.join(rule.getAllows(),","));
              }
            }
          }else if(s.contains("Content-Type:")){
            uf.setContentType(Stringer.cut(s,":").trim());
          }
        }
        // 跳过非文件块，以后可修改，处理传递的参数
        if(!fileField){
          uploaded += skipUntil(FIELD_SEPARATOR);
        }
      }
      // 结束解析的标识
      byte[] endBytes = Mather.merge(BOUNDARY_PREFIX,boundaryBytes);
      ByteArray array = new ByteArray();
      short count = 0;
      byte[] buffer = new byte[BUFFER_SIZE];
      int l = 0;
      File tempFile = new File(TEMP_DIR,ID.next()+"");
      BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempFile));
      while((l = reader.read(buffer))!=-1){
        uploaded += l;
        listener.update(uploaded,total);
        ByteArray temp = new ByteArray(buffer);
        temp = temp.sub(0,l);
        array.add(temp.toArray());
        if(count>0){
          int index = array.indexOf(endBytes);
          if(index>-1){
            array = array.sub(0,index);
          }
          count = 0;
          bos.write(array.toArray());
          bos.flush();
          array.clear();
        }else{
          count++;
        }
      }
      int index = array.indexOf(endBytes);
      if(index>-1){
        array = array.sub(0,index);
      }
      bos.write(array.toArray());
      bos.flush();
      bos.close();
      uf.setSize(tempFile.length());
      // 上传完成，进度修正
      listener.update(total,total);
      uf.setInputStream(new FileInputStream(tempFile));
    }catch(IOException e){
      throw new BadUploadException(e);
    }
    return uf;
  }

  public IProgressListener getProgressListener(){
    return listener;
  }

  public void setProgressListener(IProgressListener listener){
    this.listener = listener;
  }
}
