package nc.liat6.frame.rmi.client;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.UUID;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.json.element.IJsonElement;
import nc.liat6.frame.util.Base64Coder;
import nc.liat6.frame.util.IOHelper;

/**
 * 远程调用客户端
 * 
 * @author 6tail
 * 
 */
public class NlfClient implements INlfCaller{

  /** 请求标识 */
  static final String REQ_TAG = "nlfreq";
  /** 响应标识 */
  static final String RSP_TAG = "nlfrsp";
  /** 缓冲区大小 */
  static int BUFFER_SIZE = 20480;
  /** 连接套接字 */
  private Socket socket = null;

  /**
   * 连接
   * 
   * @throws IOException
   */
  private void connect(String ip,int port) throws IOException{
    socket = new Socket(ip,port);
    socket.setSoTimeout(0);
    socket.setTcpNoDelay(true);
  }

  /**
   * 断开
   */
  private void disconnect(){
    IOHelper.closeQuietly(socket);
  }

  public IJsonElement call(String ip,int port,String klass,String method,Map<String,String> args){
    DataInputStream in = null;
    DataOutputStream out = null;
    try{
      connect(ip,port);
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
      ByteArrayOutputStream bo = new ByteArrayOutputStream(BUFFER_SIZE);
      Bean bean = new Bean();
      Bean argb = new Bean();
      bean.set("uuid",UUID.randomUUID()+"");
      bean.set("class",klass);
      bean.set("method",method);
      if(null!=args){
        for(String key:args.keySet()){
          argb.set(key,args.get(key));
        }
      }
      bean.set("args",argb);
      String s = JSON.toJson(bean);
      byte[] reqTag = REQ_TAG.getBytes();
      byte[] b = Base64Coder.encode(s.getBytes()).getBytes();
      out.write(reqTag);
      out.write(b);
      out.write('\0');
      out.flush();
      byte[] rspTag = new byte[RSP_TAG.getBytes().length];
      int total = 0;
      int c = 0;
      while((c = in.read())!=-1){
        if('\0'==c){
          break;
        }
        if(total<rspTag.length){
          rspTag[total] = (byte)c;
          total++;
          if(total==rspTag.length){
            String tag = new String(rspTag);
            if(!RSP_TAG.equals(tag)){
              throw new RspNotMatchException(tag);
            }
          }
        }else{
          bo.write(c);
        }
      }
      // 数据内容
      byte[] d = bo.toByteArray();
      IJsonElement r = JSON.fromJson(new String(Base64Coder.decode(new String(d))));
      bo.close();
      return r;
    }catch(Exception e){
      throw new NlfClientException(e);
    }finally{
      IOHelper.closeQuietly(in);
      IOHelper.closeQuietly(out);
      disconnect();
    }
  }
}