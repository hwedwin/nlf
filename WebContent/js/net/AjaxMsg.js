I.regist({
  'name':'net.AjaxMsg',
  'need':['net.Ajax'],
  'fn':function(W,D){
    var create = function(klass,method,args,fs){
      var instance = {'klass':klass,'method':method,'args':args,'fs':fs,'connected':false};
      instance['send']=function(){
        var inst = this;
        var a = [];
        for(var i in inst.args){
          a.push(i+'='+I.net.Ajax.encode(inst.args[i]));
        }
        I.net.Ajax.post(I.ROOT+'/'+inst.klass+'/'+inst.method,a.join('&'),function(mr){
          if(404==mr.status){
            if(inst.fs.onError){
              inst.fs.onError('404:请求的资源未找到');
            }
            return;
          }
          var r = mr.xhr;
          try{
            var o = I.net.Ajax.json(r.responseText);
            if(inst.fs.onReceive){
              inst.fs.onReceive(o);
            }
          }catch(e){
            if(inst.fs.onError){
              inst.fs.onError(e);
            }
            return;
          }
        });
        if(!inst.connected){
          inst.connected = true;
          if(inst.fs.onConnect){
            inst.fs.onConnect();
          }
        }
      };
      
      instance['report']=function(k,m,ags){
        var inst = this;
        var a = [];
        for(var i in ags){
          a.push(i+'='+I.net.Ajax.encode(ags[i]));
        }
        I.net.Ajax.post(I.ROOT+'/'+k+'/'+m,a.join('&'),function(mr){
          if(404==mr.status){
            if(inst.fs.onError){
              inst.fs.onError('404:请求的资源未找到');
            }
            return;
          }
          inst.send();
        });
      };
      instance.send();
      return instance;
    };
    
    return {
      'create':function(klass,method,args,fs){return create(klass,method,args,fs);}
    };
  }
});