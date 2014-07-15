/**
 * I.ui.Upload
 * <i>上传组件</i>
 */
I.regist('ui.Upload',function(W,D){
  var CFG = {
    skin:'Default',
    icon:'fa fa-upload',
    width:40,
    height:40,
    dom:D.body,
    onUpload:function(cfg){},
    onSuccess:function(result){},
    onFailed:function(result){
      I.ui.Tip.create({msg:result.msg,skin:this.skin});
    }
  };
  var ARG_ID = 'NLF_UPLOAD_ID';
  var FRAME_TAG = 'iLibUploadFrame';
  var IFRAME = null;
  var _createFrame = function(){
    var fr = I.$('name',FRAME_TAG);
    if(fr.length<1){
      var o = I.insert('div');
      o.innerHTML = '<iframe name="'+FRAME_TAG+'" style="display:none;"></iframe>';
      I.cls(o,'position:absolute;left:-1000px;top:-1000px');
      IFRAME = I.$(o,'*')[0];
    }
  };
  var _bind = function(obj,div,cfg){
    obj.layer = div;
    obj.icon = I.$(div,'tag','i')[0];
    obj.textLayer = I.$(div,'tag','b')[0];
    obj.form = I.$(div,'tag','form')[0];
    obj.input = I.$(div,'tag','input')[0];
    obj.layer.style.width = cfg.width+'px';
    obj.layer.style.height = cfg.height+'px';
    obj.icon.style.width = cfg.width+'px';
    obj.icon.style.height = cfg.height+'px';
    obj.icon.style.lineHeight = cfg.height+'px';
    obj.icon.style.fontSize = Math.ceil(cfg.height/2)+'px';
    obj.textLayer.style.width = cfg.width+'px';
    obj.textLayer.style.height = cfg.height+'px';
    obj.textLayer.style.lineHeight = cfg.height+'px';
    obj.textLayer.style.fontSize = Math.ceil(cfg.height/3)+'px';
    I.cls(obj.icon,cfg.icon);
    I.opacity(obj.form,0);
    obj.form.method = 'post';
    obj.form.target = FRAME_TAG;
    obj.form.enctype = 'multipart/form-data';
    obj.input.name = 'iLibUploadFile';
    obj.input.onfocus = function(){this.blur();};
    I.listen(obj.input,'change',function(m,e){
      obj.upload(m.value);
    });
    return obj;
  };
  var _initObj = function(obj){
    obj.check = function(uuid){
      var inst = this;
      I.net.SilentRmi.set(ARG_ID,uuid);
      I.net.SilentRmi.call('nc.liat6.frame.web.upload.UploadStatus','getStatus',function(o){
        inst.uploaded = o.uploaded;
        inst.total = o.total;
        if(1==inst.state){
          W.setTimeout(function(){
            inst.check(uuid);
          },1000);
        }
      });
    };
    obj.upload = function(v){
      if(!v){
        return;
      }
      var inst = this;
      var cfg = inst.config;
      if(inst.timer) return;
      
      var uuid = I.util.UUID.next();
      var bindArgs = [];
      if(cfg.args){
        for(var i in cfg.args){
          bindArgs.push(i+'='+I.net.Ajax.encode(cfg.args[i]));
        }
      }
      var formAction = cfg.url;
      if(!formAction){
        cfg.onFailed({success:false,data:null,msg:'缺少url'});
        return;
      }else{
        if(formAction.indexOf('?')>-1){
          formAction += '&';
        }else{
          formAction += '?';
        }
        formAction += ARG_ID+'='+uuid;
        for(var i=0;i<bindArgs.length;i++){
          formAction += '&'+bindArgs[i];
        }
      }
      
      inst.uploaded=0;
      inst.total=0;
      IFRAME.contentWindow.document.body.innerHTML = '';
      inst.state = 1;
      I.opacity(inst.icon,0);
      inst.timer = W.setInterval(function(){
        if(1!=inst.state){
          I.opacity(inst.icon,100);
          inst.textLayer.innerHTML = '';
          W.clearInterval(inst.timer);
          inst.timer = null;
          return;
        }
        if(inst.total>0){
          inst.textLayer.innerHTML = Math.floor(100*inst.uploaded/inst.total)+'%';
        }
        var bd = IFRAME.contentWindow.document.body;
        try{
          if(bd){
            var pre = I.$(bd,'*');
            if(pre&&pre.length>0){
              inst.state = 0;
              var result = eval('('+pre[0].innerHTML+')');
              if(result.success){
                cfg.onSuccess(result);
              }else{
                cfg.onFailed(result);
              }
            }
          }
        }catch(ex){
          inst.state = 0;
          cfg.onFailed({success:false,data:null,msg:ex.message});
        }
      },100);
      inst.check(uuid);
      cfg.onUpload(cfg);
      inst.form.action = formAction;
      inst.form.submit();
    };
  };
  var _prepare = function(config){
    _createFrame();
    var obj = {layer:null,textLayer:null,form:null,input:null,icon:null,timer:null,uploaded:0,total:0,state:0,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    var div = I.insert('div',cfg.dom);
    div.innerHTML = '<i></i><b></b><form><input type="file" /></form>';
    obj.layer = div;
    cfg.dom = obj.layer;
    obj.config = cfg;
    obj.className = 'i-ui-Upload-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    I.cls(obj.layer,obj.className);
    _initObj(obj,cfg);
    _bind(obj,obj.layer,cfg);
    return obj;
  };
  var _render = function(dom,config){
    _createFrame();
    dom = I.$(dom);
    var obj = {layer:dom,textLayer:null,form:null,input:null,bar:null,timer:null,uploaded:0,total:0,state:0,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    cfg.dom = obj.layer;
    obj.config = cfg;
    obj.className = 'i-ui-Upload-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    I.cls(obj.layer,obj.className);
    _initObj(obj,cfg);
    _bind(obj,obj.layer,cfg);
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);},
    render:function(dom,cfg){return _render(dom,cfg);}
  };
}+'');