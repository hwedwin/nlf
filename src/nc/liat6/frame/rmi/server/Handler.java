package nc.liat6.frame.rmi.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.frame.Factory;
import nc.liat6.frame.context.Context;
import nc.liat6.frame.execute.AppDispatcher;
import nc.liat6.frame.execute.impl.AppExecute;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.json.element.IJsonElement;
import nc.liat6.frame.json.element.JsonMap;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.rmi.server.exception.ReqNotMatchException;
import nc.liat6.frame.rmi.server.request.RmiRequest;
import nc.liat6.frame.util.IOHelper;
import nc.liat6.frame.web.response.Json;

/**
 * 单连接处理器
 *
 * @author 6tail
 *
 */
public class Handler implements Runnable{
  /** Socket */
  private Socket socket = null;

  Handler(Socket socket){
    this.socket = socket;
  }

  private boolean contains(List<String> l,String s){
    for(String o:l){
      if(s.startsWith(o)) return true;
    }
    return false;
  }

  public void run(){
    DataInputStream in = null;
    DataOutputStream out = null;
    try{
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
      String reqTag = in.readUTF();
      if(!NlfServer.REQ_TAG.equals(reqTag)){
        throw new ReqNotMatchException(reqTag);
      }
      int l = in.readInt();
      byte[] d = new byte[l];
      in.readFully(d);
      IJsonElement obj = JSON.fromJson(new String(d,"utf-8"));
      JsonMap m = obj.toJsonMap();
      StringBuilder logs = new StringBuilder();
      String uuid = m.get("uuid").toJsonString().toString();
      String klass = m.get("class").toJsonString().toString();
      String method = m.get("method").toJsonString().toString();
      logs.append("UUID："+uuid+"\r\n");
      logs.append(L.get(LocaleFactory.locale,"rmi.req_class")+klass+"\r\n");
      logs.append(L.get(LocaleFactory.locale,"rmi.req_class")+method+"\r\n");
      String km = klass+"/"+method;
      boolean valid = true;
      String s = null;
      ILog log = Logger.getLog();
      if(!Factory.contains(klass)){
        s = L.get(LocaleFactory.locale,"rmi.class_not_found")+klass;
        logs.append(s);
        valid = false;
      }
      if(valid){
        // 请求过滤
        if(contains(NlfServer.forbid,km)){
          if(!contains(NlfServer.allow,km)){
            s = L.get(LocaleFactory.locale,"request.forbid")+":"+km;
            logs.append(s);
            valid = false;
          }
        }
      }
      if(!valid){
        Json json = new Json();
        json.setSuccess(false);
        json.setMsg(s);
        s = JSON.toJson(json);
      }else{
        JsonMap args = m.get("args").toJsonMap();
        Map<String,String> map = new HashMap<String,String>();
        logs.append(L.get(LocaleFactory.locale,"rmi.req_args")+"\r\n");
        for(String key:args.keySet()){
          String value = args.get(key).toJsonString().toString();
          map.put(key,value);
          logs.append("\t"+key+"="+value+"\r\n");
        }
        if(NlfServer.enableLog){
          log.debug(logs);
        }
        logs.delete(0,logs.length());
        RmiRequest rr = new RmiRequest();
        rr.setIp(socket.getInetAddress().getHostAddress());
        rr.setPort(socket.getPort());
        // 设置原请求
        Context.set(AppExecute.RMI_REQUEST,rr);
        Object ro = new AppDispatcher().execute(klass,method,map);
        if(ro instanceof Throwable){
          Json json = new Json(ro);
          json.setSuccess(false);
          json.setMsg(((Throwable)ro).getMessage());
          s = JSON.toJson(json);
        }else{
          s = JSON.toJson(ro);
        }
      }
      logs.append("UUID："+uuid+"\r\n");
      logs.append(L.get(LocaleFactory.locale,"rmi.res_json")+s);
      if(NlfServer.enableLog){
        log.debug(logs);
      }
      byte[] r = s.getBytes("utf-8");
      out.writeUTF(NlfServer.RSP_TAG);
      out.writeInt(r.length);
      out.write(r);
      out.flush();
    }catch(ReqNotMatchException e){
      // 忽略非法请求
    }catch(Exception e){
      Logger.getLog().error(L.get(LocaleFactory.locale,"rmi.req_fail"),e);
    }finally{
      IOHelper.closeQuietly(in);
      IOHelper.closeQuietly(out);
      IOHelper.closeQuietly(socket);
    }
  }
}