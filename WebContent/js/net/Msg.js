I.regist({
  'name':'net.Msg',
  'need':['net.Ajax','socket.JsSocket'],
  'fn':function(W,D){
    var SLEEP = 1;
    var obj2str=function(o){
      var r = [];
      if(typeof o =="string") return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";
      if(typeof o =="undefined") return "undefined";
      if(typeof o == "object"){
          if(o===null) return "null";
          else if(!o.sort){
              for(var i in o){
                  r.push(i+":"+obj2str(o[i]));
              }
              r="{"+r.join()+"}";
          }else{
              for(var i =0;i<o.length;i++){
                  r.push(obj2str(o[i]));
              }
              r="["+r.join()+"]";
          }
          return r;
      }
      return o.toString();
    };
    var create = function(server,port,args,fs){
      var instance = {'server':server,'port':port,'args':args,'argo':obj2str(args),'fs':fs};
      var socket = I.socket.JsSocket.create();
      instance['socket'] = socket;
      instance['send']=function(){
        var inst = this;
        inst.socket.connect(inst.server,inst.port);
        inst.socket.send(inst.argo);
      };
      instance['close']=function(){
        this.socket.close();
      };
      socket.onload(function(){
        instance.send();
        if(instance.fs.onLoad){
          instance.fs.onLoad();
        }
      });
      socket.onReceive=function(d){
        if(instance.fs.onReceive){
          instance.fs.onReceive(I.net.Ajax.json(d));
        }
        W.setTimeout(function(){
          instance.send();
        },Math.floor(SLEEP*1000));
      };
      socket.onError=function(text){
        if(instance.fs.onError){
          instance.fs.onError(text);
        }
      };
      socket.onConnect=function(){
        if(instance.fs.onConnect){
          instance.fs.onConnect();
        }
      };
      socket.onClose=function(){
        if(instance.fs.onClose){
          instance.fs.onClose();
        }
      };
      return instance;
    };
    
    return {
      'create':function(server,port,args,fs){return create(server,port,args,fs);}
    };
  }
});