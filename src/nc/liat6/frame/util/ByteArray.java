package nc.liat6.frame.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字节数组
 * 
 * @author 6tail
 * 
 */
public class ByteArray{

  /** 字节缓存 */
  private List<Byte> l = new ArrayList<Byte>();

  public ByteArray(){}

  public ByteArray(byte[] d){
    add(d);
  }

  /**
   * 清空
   */
  public void clear(){
    l.clear();
  }

  /**
   * 设置
   * 
   * @param index 下标
   * @param b 字节
   */
  public void set(int index,byte b){
    l.set(index,b);
  }

  /**
   * 总大小
   * 
   * @return 总大小
   */
  public int size(){
    return l.size();
  }

  /**
   * 获取指定下标的字节
   * 
   * @param index 下标
   * @return 字节
   */
  public byte get(int index){
    return l.get(index);
  }

  public byte tail(){
    return l.get(l.size()-1);
  }

  /**
   * 截取
   * @param fromIndex 
   * @param toIndex
   * @return
   */
  public ByteArray sub(int fromIndex,int toIndex){
    ByteArray ba = new ByteArray();
    List<Byte> sl = l.subList(fromIndex,toIndex);
    for(int i = 0;i<sl.size();i++){
      ba.add(sl.get(i).byteValue());
    }
    return ba;
  }

  /**
   * 在末尾添加字节
   * 
   * @param b 字节
   */
  public void add(byte b){
    l.add(b);
  }

  /**
   * 在末尾添加字节数组
   * 
   * @param data 字节数组
   */
  public void add(byte[] data){
    for(byte b:data){
      add(b);
    }
  }

  /**
   * 转换为数组
   * 
   * @return 字节数组
   */
  public byte[] toArray(){
    byte[] b = new byte[l.size()];
    for(int i = 0;i<l.size();i++){
      b[i] = l.get(i).byteValue();
    }
    return b;
  }

  /**
   * byte[]出现的下标，如果不存在，返回-1
   * @param bytes byte[]
   * @return
   */
  public int indexOf(byte[] bytes){
    int m = bytes.length;
    int n = l.size();
    int index = 0;
    for(int i = 0;i<n;i++){
      byte b = l.get(i);
      if(b!=bytes[index]){
        index = 0;
      }
      if(b==bytes[index]){
        index++;
        if(index>=m){
          return i-index+1;
        }
      }
    }
    return -1;
  }

  public String toString(){
    StringBuilder s = new StringBuilder();
    for(int i = 0;i<l.size();i++){
      s.append(l.get(i));
      if(i<l.size()-1){
        s.append(",");
      }
    }
    return s.toString();
  }

  public boolean equals(ByteArray ba){
    if(null==ba){
      return false;
    }
    int len = size();
    if(len!=ba.size()){
      return false;
    }
    for(int i = 0;i<len;i++){
      if(get(i)!=ba.get(i)){
        return false;
      }
    }
    return true;
  }
}