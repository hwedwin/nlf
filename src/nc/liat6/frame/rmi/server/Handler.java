package nc.liat6.frame.rmi.server;

import java.io.ByteArrayOutputStream;
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
import nc.liat6.frame.util.Base64Coder;
import nc.liat6.frame.util.Mather;
import nc.liat6.frame.web.response.Json;

/**
 * 单连接处理器
 * 
 * @author 6tail
 * 
 */
public class Handler implements Runnable{

  private Socket socket = null;
  private DataInputStream in = null;

  Handler(Socket socket){
    this.socket = socket;
  }

  private boolean contains(List<String> l,String s){
    for(String o:l){
      if(s.startsWith(o))
        return true;
    }
    return false;
  }

  public void run(){
    DataOutputStream out = null;
    try{
      in = new DataInputStream(socket.getInputStream());
      ByteArrayOutputStream bo = new ByteArrayOutputStream(NlfServer.BUFFER_SIZE);
      byte[] reqTag = new byte[NlfServer.REQ_TAG.getBytes().length];
      int total = 0;
      int c = 0;
      while((c = in.read())!=-1){
        if('\0'==c){
          break;
        }
        if(total<reqTag.length){
          reqTag[total] = (byte)c;
          total++;
          if(total==reqTag.length){
            String tag = new String(reqTag);
            if(!NlfServer.REQ_TAG.equals(tag)){
              throw new ReqNotMatchException(tag);
            }
          }
        }else{
          bo.write(c);
        }
      }
      // 数据内容
      byte[] d = bo.toByteArray();
      bo.close();
      IJsonElement obj = JSON.fromJson(new String(Base64Coder.decode(new String(d))));
      JsonMap m = obj.toJsonMap();
      String uuid = m.get("uuid").toJsonString().toString();
      StringBuilder logs = new StringBuilder();
      logs.append("UUID："+uuid+"\r\n");
      String klass = m.get("class").toJsonString().toString();
      logs.append(L.get(LocaleFactory.locale,"rmi.req_class")+klass+"\r\n");
      String method = m.get("method").toJsonString().toString();
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
            s = L.get(LocaleFactory.locale,"rmi.forbid")+km;
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
      byte[] t = Mather.merge(NlfServer.RSP_TAG.getBytes(),Base64Coder.encode(s.getBytes()).getBytes());
      t = Mather.merge(t,new byte[]{'\0'});
      out = new DataOutputStream(socket.getOutputStream());
      out.write(t);
      out.flush();
    }catch(ReqNotMatchException e){
      // 忽略非法请求
    }catch(Exception e){
      Logger.getLog().error(L.get(LocaleFactory.locale,"rmi.req_fail"),e);
    }finally{
      if(null!=in){
        try{
          in.close();
        }catch(Exception e){}
        in = null;
      }
      if(null!=out){
        try{
          out.close();
        }catch(Exception e){}
        out = null;
      }
      if(null!=socket){
        try{
          socket.close();
        }catch(Exception e){}
        socket = null;
      }
    }
  }
}
