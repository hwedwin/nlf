package nc.liat6.frame.rmi.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Map;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.json.element.IJsonElement;
import nc.liat6.frame.util.IOHelper;
import nc.liat6.frame.util.UUID;

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

  public IJsonElement call(String ip,int port,String klass,String method,Map<String,String> args){
    Socket socket = null;
    DataInputStream in = null;
    DataOutputStream out = null;
    try{
      socket = new Socket(ip,port);
      socket.setSoTimeout(0);
      socket.setTcpNoDelay(true);
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
      Bean bean = new Bean();
      bean.set("uuid",UUID.next());
      bean.set("class",klass);
      bean.set("method",method);
      if(null==args){
        bean.set("args",new Bean());
      }else{
        bean.set("args",args);
      }
      String s = JSON.toJson(bean);
      byte[] b = s.getBytes("utf-8");
      out.writeUTF(REQ_TAG);
      out.writeInt(b.length);
      out.write(b);
      out.flush();
      String rspTag = in.readUTF();
      if(!RSP_TAG.equals(rspTag)){
        throw new RspNotMatchException(rspTag);
      }
      int l = in.readInt();
      byte[] d = new byte[l];
      in.readFully(d);
      return JSON.fromJson(new String(d,"utf-8"));
    }catch(Exception e){
      throw new NlfClientException(e);
    }finally{
      IOHelper.closeQuietly(in);
      IOHelper.closeQuietly(out);
      IOHelper.closeQuietly(socket);
    }
  }
}