package nc.liat6.frame.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.exception.NlfException;

/**
 * 字符串处理
 * 
 * @author 6tail
 * 
 */
public class Stringer{
  private Stringer(){}

  /**
   * 裁剪字符串
   * 
   * @param s 原始字符串
   * @param start 起始字符串
   * @param end 从起始字符串开始第一次遇到的结束字符串
   * @return 从起始字符串到结束字符串之间的字符串，不包括起始字符串和结束字符串
   */
  public static String cut(String s,String start,String end){
    int idx = s.indexOf(start);
    if(idx>-1){
      String k = s.substring(idx+start.length());
      k = k.substring(0,k.indexOf(end));
      return k;
    }
    return "";
  }

  /**
   * 裁剪字符串
   * 
   * @param s 原始字符串
   * @param start 起始字符串
   * @return 从起始字符串开始的字符串，不包括起始字符串
   */
  public static String cut(String s,String start){
    int idx = s.indexOf(start);
    if(idx>-1){
      String k = s.substring(idx+start.length());
      return k;
    }
    return "";
  }

  /**
   * 将字符串数组以间隔符连接为字符串
   * 
   * @param arrays 字符串数组
   * @param tag 间隔符
   * @return 连接后的字符串
   */
  public static String join(String[] arrays,String tag){
    if(null==arrays) return null;
    StringBuffer s = new StringBuffer();
    for(int i = 0;i<arrays.length;i++){
      s.append(arrays[i]);
      if(i<arrays.length-1){
        s.append(tag);
      }
    }
    return s.toString();
  }

  /**
   * 将字符串列表以间隔符连接为字符串
   * 
   * @param list 字符串列表
   * @param tag 间隔符
   * @return 连接后的字符串
   */
  public static String join(List<String> list,String tag){
    if(null==list) return null;
    StringBuffer s = new StringBuffer();
    for(int i = 0,n = list.size();i<n;i++){
      s.append(list.get(i));
      if(i<n-1){
        s.append(tag);
      }
    }
    return s.toString();
  }

  /**
   * 字符串替串
   * 
   * @param os 原字符串，如果为NULL，返回NULL
   * @param tag 需要替换的字符串列表
   * @param rs 替换为
   * @return 替换后的字符串
   */
  public static String replace(String os,String[] tag,String rs){
    if(null==os){
      return os;
    }
    String ns = os;
    for(String t:tag){
      ns = ns.replace(t,rs);
    }
    return ns;
  }

  /**
   * 字符串替换
   * 
   * @param os 原字符串，如果为NULL，返回NULL
   * @param tag 需要替换的字符串
   * @param rs 替换为
   * @return 替换后的字符串
   */
  public static String replace(String os,String tag,String rs){
    return replace(os,new String[]{tag},rs);
  }

  /**
   * 字符串按指定分隔符转换为列表
   * 
   * @param os 原字符串
   * @param spliter 分隔符
   * @param allowRepeat 是否允许重复
   * @return 转换后的列表
   */
  public static List<String> list(String os,String spliter,boolean allowRepeat){
    List<String> l = new ArrayList<String>();
    if(null==os){
      return l;
    }
    String[] cl = os.split(spliter);
    for(String s:cl){
      s = s.trim();
      if(s.length()>0){
        if(allowRepeat){
          l.add(s);
        }else{
          if(!l.contains(s)){
            l.add(s);
          }
        }
      }
    }
    return l;
  }

  /**
   * 字符串按指定分隔符转换为不重复的列表
   * 
   * @param os 原字符串
   * @param spliter 分隔符
   * @return 转换后的列表
   */
  public static List<String> list(String os,String spliter){
    return list(os,spliter,false);
  }

  /**
   * 对AJAX提交来的数据进行解码
   * 
   * @param s 原字符串
   * @return 解码后的字符串
   */
  public static String ajax(String s){
    try{
      return null==s?null:java.net.URLDecoder.decode(s,"UTF-8");
    }catch(UnsupportedEncodingException e){
      throw new NlfException(e);
    }
  }

  /**
   * 将指定字符串进行转码
   * 
   * @param s 要转码的字符串
   * @param ocharSet 原编码
   * @param ncharSet 新编码
   * @return 转码后的字符串
   */
  public static String encode(String s,String ocharSet,String ncharSet){
    try{
      return s==null?s:new String(s.getBytes(ocharSet),ncharSet);
    }catch(UnsupportedEncodingException e){
      throw new NlfException(e);
    }
  }

  /**
   * 占位输出
   * 
   * @param s
   * @param obj
   * @return
   */
  public static String print(String s,Object... obj){
    String rs = s;
    if(null==s||s.length()<1){
      rs = "";
      for(int i = 0;i<obj.length;i++){
        rs += "?";
        if(i<obj.length-1){
          rs += " ";
        }
      }
    }
    StringBuffer r = new StringBuffer();
    for(int i = 0;i<obj.length;i++){
      int idx = rs.indexOf("?");
      r.append(rs.substring(0,idx));
      r.append(obj[i]+"");
      rs = rs.substring(idx+"?".length());
    }
    r.append(rs);
    return r.toString();
  }

  /**
   * 对字符串进行MD5加密，utf-8编码
   * 
   * @param s 原文
   * @return 密文,大写形式
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   */
  public static String md5(String s){
    return md5(s,"utf-8");
  }

  /**
   * 对字符串进行MD5加密
   * 
   * @param s 原文
   * @param encode 编码
   * @return 密文,大写形式
   * @throws UnsupportedEncodingException
   * @throws NoSuchAlgorithmException
   */
  public static String md5(String s,String encode){
    try{
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(s.getBytes(encode));
      byte[] b = md.digest();
      StringBuffer sb = new StringBuffer();
      for(int i = 0;i<b.length;i++){
        String hex = Integer.toHexString(b[i]&0xFF);
        hex = (hex.length()==1?"0":"")+hex;
        sb.append(hex.toUpperCase());
      }
      return sb.toString();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }

  /**
   * 16进制字符串转字节数组
   * 
   * @param hexString 16进制字符串
   * @return 字节数组
   */
  public static byte[] hex2Bytes(String hexString){
    String s = hexString;
    if(null==s){
      return null;
    }
    if(s.length()<1){
      return new byte[0];
    }
    String chars = "0123456789ABCDEF";
    s = s.toUpperCase();
    int length = s.length()/2;
    byte[] d = new byte[length];
    for(int i = 0;i<length;i++){
      int pos = i*2;
      String h = s.substring(pos,pos+1);
      String l = s.substring(pos+1,pos+2);
      d[i] = (byte)(chars.indexOf(h)<<4|chars.indexOf(l));
    }
    return d;
  }

  /**
   * 读取文件内容到字符串
   * 
   * @param file 文件
   * @param encode 编码
   * @return 文件内容
   * @throws IOException
   */
  public static String readFromFile(File file,String encode) throws IOException{
    StringBuffer s = new StringBuffer();
    BufferedReader br = null;
    try{
      br = new BufferedReader(new InputStreamReader(new FileInputStream(file),encode));
      String line = null;
      while(null!=(line = br.readLine())){
        s.append(line+"\r\n");
      }
      return s.toString();
    }finally{
      IOHelper.closeQuietly(br);
    }
  }

  /**
   * 读取文件内容到字符串
   * 
   * @param file 文件路径
   * @param encode 编码
   * @return 文件内容
   * @throws IOException
   */
  public static String readFromFile(String file,String encode) throws IOException{
    return readFromFile(new File(file),encode);
  }

  public static String readFromFile(String file) throws IOException{
    return readFromFile(file,Statics.ENCODE);
  }

  public static String readFromFile(File file) throws IOException{
    return readFromFile(file,Statics.ENCODE);
  }

  /**
   * 将字符串写到文件
   * 
   * @param s 字符串
   * @param file 文件
   * @param encode 编码
   * @throws IOException
   */
  public static void writeToFile(String s,File file,String encode) throws IOException{
    BufferedWriter bw = null;
    try{
      bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),encode));
      bw.write(s);
      bw.flush();
    }finally{
      IOHelper.closeQuietly(bw);
    }
  }

  /**
   * 将字符串写到文件
   * 
   * @param s 字符串
   * @param file 文件路径
   * @param encode 编码
   * @throws IOException
   */
  public static void writeToFile(String s,String file,String encode) throws IOException{
    writeToFile(s,new File(file),encode);
  }

  public static void writeToFile(String s,String file) throws IOException{
    writeToFile(s,file,Statics.ENCODE);
  }

  public static void writeToFile(String s,File file) throws IOException{
    writeToFile(s,file,Statics.ENCODE);
  }
}