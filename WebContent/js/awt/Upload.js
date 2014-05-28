I.regist({
  'name':'awt.Upload',
  'need':['awt.Component','net.Rmi','util.UUID','ui.Tip'],
  'fn':function(W,D){
    var ARG_ID = 'NLF_UPLOAD_ID';
    var FRAME_TAG = 'iLibUploadFrame';
    var IFRAME = null;
    var createFrame = function(){
      var fr = I.$('name',FRAME_TAG);
      if(fr.length<1){
        var o = I.insert('div');
        o.innerHTML = '<iframe name="'+FRAME_TAG+'" style="display:none"></iframe>';
        I.css(o,'position:absolute;left:-1000px;top:-1000px');
        IFRAME = I.$(o,'tag','iframe')[0];
      }
    };
    var create = function(p,s){
      createFrame();
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var w = skin.get('awt.Upload.width');
      var h = skin.get('awt.Upload.height');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      o = I.insert('div',div);
      I.css(o,'position:relative;overflow:hidden;left:6px;top:0;width:'+w+'px;height:'+h+'px');
      var bar = I.insert('div',o);
      I.css(bar,'position:absolute;left:1px;top:1px;width:0;height:'+(h-2)+'px;overflow:hidden;background-image:'+skin.get('awt.Upload.barImage'));
      var showLayer = I.insert('div',o);
      I.css(showLayer,'position:absolute;overflow:hidden;left:0;top:0;width:'+w+'px;height:'+h+'px;line-height:'+h+'px;text-indent:3px;cursor:default');
      var to = I.insert('div',o);
      to.innerHTML = '<form style="margin:0;padding:0" method="post" target="'+FRAME_TAG+'" enctype="multipart/form-data"><input style="font-size:100px" type="file" name="iLibUploadFile" /></form>';
      I.css(to,'position:absolute;right:0;top:0;overflow:hidden');
      I.opacity(to,0);
      
      var ns = skin.get('awt.Upload.normal');
      var bs = skin.get('awt.Upload.bad');
      var obj = I.awt.Component.create();
      var form = I.$(o,'tag','form')[0];
      var instance = {'layer':o,'showLayer':showLayer,'form':form,'timer':null,'bar':bar,'uploaded':0,'total':0,'setting':s,'state':0};
      obj['instance'] = instance;
      obj['type']='awt.Upload';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){var s=this.instance.layer.getAttribute('truev');return null==s?'':s;};
      obj['setValue']=function(v){
        this.instance.layer.setAttribute('truev',v);
      };
      obj['getInstance'] = function(){return this.instance;};
      obj['normal'] = function(){
        var inst = this.instance;
        for(var i in ns){
          inst.layer.style[i] = ns[i];
        }
      };
      obj['bad'] = function(){
        var inst = this.instance;
        for(var i in bs){
          inst.layer.style[i] = bs[i];
        }
        inst.layer.scrollIntoView();
      };
      obj['check'] = function(uuid){
        var inst = this;
        I.net.Rmi.set(ARG_ID,uuid);
        I.net.Rmi.call('nc.liat6.frame.web.upload.UploadStatus','getStatus',function(o){
          inst.instance.uploaded=o.uploaded;
          inst.instance.total=o.total;
          if(1==inst.instance.state){
            W.setTimeout(function(){
              inst.check(uuid);
            },1000);
          }
        });
      };
      obj['upload'] = function(v){
        var oinst = this;
        var inst = this.instance;
        inst.showLayer.innerHTML = v;
        inst.uploaded=0;
        inst.total=0;
        IFRAME.contentWindow.document.body.innerHTML = '';
        inst.state = 1;
        var uuid = I.util.UUID.next();
        if(inst.timer) return;
        inst.timer = W.setInterval(function(){
          if(inst.state!=1){
            W.clearInterval(inst.timer);
            inst.timer = null;
            return;
          }
          var len = 0;
          if(inst.total>0){
            len = Math.floor(inst.uploaded/inst.total*(w-2));
          }
          if(len<w-2){
            inst.bar.style.width = len+'px';
          }else{
            inst.bar.style.width = (w-2)+'px';
          }
          var bd = IFRAME.contentWindow.document.body;
          try{
            if(bd){
              var pre = I.$(bd,'*');
              if(pre&&pre.length>0){
                inst.state = 0;
                inst.bar.style.width = (w-2)+'px';
                var result = eval('('+pre[0].innerHTML+')');
                if(result.success){
                  if(inst.setting.onSuccess){
                    inst.setting.onSuccess(result);
                  }
                }else{
                  oinst.bad();
                  I.ui.Tip.create(result);
                  if(inst.setting.onFailed){
                    inst.setting.onFailed(result);
                  }
                }
              }
            }
          }catch(ex){
            inst.state = 0;
            oinst.bad();
            //I.ui.Tip.create({'data':null,'msg':ex});
            if(inst.setting.onFailed){
              inst.setting.onFailed(null);
            }
          }
        },30);
        this.check(uuid);
        if(s.onUpload){
          s.onUpload(s);
        }
        var bindArgs = [];
        if(s.args){
          for(var i in s.args){
            bindArgs.push(i+'='+I.net.Ajax.encode(s.args[i]));
          }
        }
        var formAction = I.ROOT+'/'+s.klass+'/'+s.method+'?'+ARG_ID+'='+uuid;
        for(var i=0;i<bindArgs.length;i++){
          formAction += '&'+bindArgs[i];
        }
        inst.form.action=formAction;
        inst.form.submit();
      };
      I.listen(I.$(to,'tag','input')[0],'change',function(m,e){
        obj.upload(m.value);
      });
      I.listen(to,'mouseover',function(m,e){
        obj.normal();
      });
      obj.normal();
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});