package nc.liat6.frame.web.upload.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.exception.BadUploadException;
import nc.liat6.frame.execute.upload.IProgressListener;
import nc.liat6.frame.execute.upload.UploadRule;
import nc.liat6.frame.execute.upload.UploadedFile;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.ByteArray;
import nc.liat6.frame.util.Mather;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.util.UUID;
import nc.liat6.frame.web.upload.IParser;

/**
 * 通过磁盘缓存的文件上传解析器，支持多个文件的上传。
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
  /** 块分隔 */
  protected static final byte[] ENTITY_SEPARATOR = {CR,LF};
  /** boundary前缀 */
  public static final byte[] BOUNDARY_PREFIX = {CR,LF,DASH,DASH};
  public static final String BOUNDARY_TAG = "boundary=";
  public static final String FILE_TAG = "filename=\"";
  public static final String ENTITY_SEPARATOR_STR = new String(ENTITY_SEPARATOR);
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
    String ct = request.getContentType();
    if(null==ct){
      return null;
    }
    if(!ct.contains("multipart/form-data")){
      return null;
    }
    if(!ct.contains(BOUNDARY_TAG)){
      return null;
    }
    return Stringer.cut(ct,BOUNDARY_TAG);
  }

  private String getFileSuffix(String fileName){
    int idx = fileName.lastIndexOf(".");
    return idx>-1?fileName.substring(idx+1).toLowerCase():"";
  }

  private String getFileName(String s){
    String fileName = Stringer.cut(s,FILE_TAG,"\"");
    int idx = fileName.lastIndexOf("\\");
    if(idx>-1){
      fileName = fileName.substring(idx+1);
    }
    idx = fileName.lastIndexOf("/");
    if(idx>-1){
      fileName = fileName.substring(idx+1);
    }
    return fileName;
  }

  public List<UploadedFile> parseRequest(HttpServletRequest request,UploadRule rule){
    this.request = request;
    // 获取boundary
    String boundary = getBoundary();
    if(null==boundary){
      return null;
    }
    byte[] boundaryBytes = boundary.getBytes();
    //块头部分隔标识
    byte[] endHeader = Mather.merge(ENTITY_SEPARATOR,ENTITY_SEPARATOR);
    // 块结束标识
    byte[] endEntity = Mather.merge(BOUNDARY_PREFIX,boundaryBytes);
    // 总的数据大小，有时候获取不到
    int total = request.getContentLength();
    int max = rule.getMaxSize();
    if(max>-1&&total>max){
      throw new BadUploadException(L.get("upload.max_size")+(rule.getMaxSize()*100/1024/100D)+"KB");
    }
    List<String> allows = rule.getAllows();
    // 已上传字节数
    int uploaded = 0;
    listener.update(uploaded,total);
    List<UploadedFile> files = new ArrayList<UploadedFile>();
    try{
      reader = request.getInputStream();
      byte[] buffer = new byte[rule.getBufferSize()];
      byte[] temp;
      ByteArray array = new ByteArray();
      int l = 0;
      int index;
      UploadedFile uf = null;
      File tempFile = null;
      BufferedOutputStream bos = null;
      while(-1!=(l = reader.read(buffer))){
        //读数据
        uploaded += l;
        listener.update(uploaded,total);
        temp = new byte[l];
        System.arraycopy(buffer,0,temp,0,l);
        array.add(temp);
        if(null==uf){
          //块头部分隔
          index = array.indexOf(endHeader);
          if(index<0){
            continue;
          }
          // 按行分隔
          String[] heads = new String(array.sub(0,index).toArray(),Statics.ENCODE).split(ENTITY_SEPARATOR_STR);
          for(String s:heads){
            // 文件判断
            if(s.contains(FILE_TAG)){
              uf = new UploadedFile();
              uf.setName(getFileName(s));
              uf.setSuffix(getFileSuffix(uf.getName()));
              if(allows.size()>0&&!allows.contains(uf.getSuffix())){
                throw new BadUploadException(L.get("upload.formats")+Stringer.join(allows,","));
              }
            }else if(s.contains("Content-Type:")){
              uf.setContentType(Stringer.cut(s,":").trim());
            }
          }
          array = array.sub(index+endHeader.length,array.size());
          if(null!=uf){
            tempFile = new File(rule.getTempDir(),UUID.next());
            bos = new BufferedOutputStream(new FileOutputStream(tempFile));
          }
        }
        index = array.indexOf(endEntity);
        if(index<0){
          bos.write(array.sub(0,array.size()-endEntity.length).toArray());
          bos.flush();
          array = array.sub(array.size()-endEntity.length,array.size());
        }else{
          bos.write(array.sub(0,index).toArray());
          bos.flush();
          bos.close();
          uf.setSize(tempFile.length());
          uf.setInputStream(new FileInputStream(tempFile));
          files.add(uf);
          uf = null;
          array = array.sub(index,array.size());
        }
      }
      index = array.indexOf(endHeader);
      while(index>-1){
        // 按行分隔
        String[] heads = new String(array.sub(0,index).toArray(),Statics.ENCODE).split(ENTITY_SEPARATOR_STR);
        for(String s:heads){
          // 文件判断
          if(s.contains(FILE_TAG)){
            uf = new UploadedFile();
            uf.setName(getFileName(s));
            uf.setSuffix(getFileSuffix(uf.getName()));
            if(allows.size()>0&&!allows.contains(uf.getSuffix())){
              throw new BadUploadException(L.get("upload.formats")+Stringer.join(allows,","));
            }
          }else if(s.contains("Content-Type:")){
            uf.setContentType(Stringer.cut(s,":").trim());
          }
        }
        array = array.sub(index+endHeader.length,array.size());
        if(null!=uf){
          tempFile = new File(rule.getTempDir(),UUID.next());
          bos = new BufferedOutputStream(new FileOutputStream(tempFile));
          index = array.indexOf(endEntity);
          if(index<0){
            bos.write(array.sub(0,array.size()-endEntity.length).toArray());
            bos.flush();
            array = array.sub(array.size()-endEntity.length,array.size());
          }else{
            bos.write(array.sub(0,index).toArray());
            bos.flush();
            bos.close();
            uf.setSize(tempFile.length());
            uf.setInputStream(new FileInputStream(tempFile));
            files.add(uf);
            uf = null;
            array = array.sub(index,array.size());
          }
        }
        index = array.indexOf(endHeader);
      }
      // 上传完成，进度修正
      listener.update(total,total);
    }catch(Exception e){
      e.printStackTrace();
      throw new BadUploadException(e);
    }
    return files;
  }

  public IProgressListener getProgressListener(){
    return listener;
  }

  public void setProgressListener(IProgressListener listener){
    this.listener = listener;
  }
}