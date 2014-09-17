/**
 * I.ui.Editor
 * <i>文本编辑器组件</i>
 */
I.regist('ui.Editor',function(W,D){
  var CFG = {
    skin:'Default',
    border:'1px solid #DDD',
    toolbar:['bold','italic','underline','strikethrough','|','superscript','subscript','|','forecolor','backcolor','|','justifyleft','justifycenter','justifyright','|','link','unlink','|','image','|','horizontal'],
    dom:D.body
  };
  
  var _try = function(obj,func){
    if(obj.doc&&obj.doc.body){
      func.call(obj);
    }else{
      W.setTimeout(function(){
        _try(obj,func);
      },16);
    }
  };
  var _renderToolbar = function(obj){
    var cfg = obj.config;
    for(var i=0;i<cfg.toolbar.length;i++){
      switch(cfg.toolbar[i]){
      case '|':
        I.insert('i',obj.toolbar);
        break;
      case 'bold':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-bold');
        I.listen(a,'click',function(m,e){
          _try(obj,function(){
            this.doc.execCommand('bold', false, null);
          });
        });
        break;
      case 'italic':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-italic');
        I.listen(a,'click',function(m,e){
          _try(obj,function(){
            this.doc.execCommand('italic', false, null);
          });
        });
        break;
      case 'underline':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-underline');
        I.listen(a,'click',function(m,e){
          _try(obj,function(){
            this.doc.execCommand('underline', false, null);
          });
        });
        break;
      case 'strikethrough':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-strikethrough');
        break;
      case 'superscript':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-superscript');
        break;
      case 'subscript':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-subscript');
        break;
      case 'forecolor':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-pencil');
        var b = I.insert('b',a);
        I.cls(b,'fa fa-sort-desc');
        break;
      case 'backcolor':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-pencil-square');
        var b = I.insert('b',a);
        I.cls(b,'fa fa-sort-desc');
        break;
      case 'justifyleft':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-align-left');
        I.listen(a,'click',function(m,e){
          _try(obj,function(){
            this.doc.execCommand('justifyleft', false, null);
          });
        });
        break;
      case 'justifycenter':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-align-center');
        I.listen(a,'click',function(m,e){
          _try(obj,function(){
            this.doc.execCommand('justifycenter', false, null);
          });
        });
        break;
      case 'justifyright':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-align-right');
        I.listen(a,'click',function(m,e){
          _try(obj,function(){
            this.doc.execCommand('justifyright', false, null);
          });
        });
        break;
      case 'link':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-link');
        I.listen(a,'click',function(m,e){
          obj.range = D.all?obj.doc.selection.createRange():obj.iframe.contentWindow.getSelection().getRangeAt(0);
          var id = I.util.UUID.next();
          var win = I.z.Win.create({
            title:'添加链接',
            width:400,
            height:180,
            content:I.util.Template.render(null,'<div id="'+id+'"><ul><li data-width="20">URL：</li><li><input id="input'+id+'" type="text" /></li></ul><ul><li data-width="20">打开窗口：</li><li><select id="select'+id+'"><option value="_blank">新窗口</option><option value="_self">本窗口</option></select></li></ul><ul><li></li><li data-width="20"><a id="btn'+id+'">确定</a></li></ul></div>')
          });
          I.ui.Form.render(id,{
            border:'0',
            border_hover:'0',
            background_hover:'#FFF'
          });
          I.ui.Button.render('btn'+id,{
            callback:function(){
              _try(obj,function(){
                this.iframe.focus();
                if(D.all){
                  this.range.execCommand('createlink', false, I.$('input'+id).value);
                  this.range.parentElement().target = I.$('select'+id).value;
                }else{
                  this.doc.execCommand("createlink", false, I.$('input'+id).value);
                  this.range.commonAncestorContainer.parentNode.target = I.$('select'+id).value;
                }
                win.close();
              });
            }
          });
        });
        break;
      case 'unlink':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-chain-broken');
        I.listen(a,'click',function(m,e){
          _try(obj,function(){
            this.doc.execCommand('unlink', false, null);
          });
        });
        break;
      case 'image':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-picture-o');
        break;
      case 'horizontal':
        var a = I.insert('a',obj.toolbar);
        I.cls(a,'fa fa-minus');
        break;
      default:
        break;
      }
    }
    var as = I.$(obj.toolbar,'tag','a');
    for(var i=0;i<as.length;i++){
      as[i].onfocus = function(){this.blur();};
      as[i].href = 'javascript:void(0);';
    }
  };
  var _renderEditor = function(obj){
    var cfg = obj.config;
    var dom = obj.proxyDom;
    dom.style.border = cfg.border;
    var toolbar = I.insert('div',dom);
    I.cls(toolbar,'editor-toolbar');
    obj.toolbar = toolbar;
    
    var body = I.insert('div',dom);
    I.cls(body,'editor-body');
    obj.body = body;
    
    var iframe = I.insert('iframe',body);
    iframe.src = 'about:blank';
    obj.iframe = iframe;
    
    var timer = null;
    timer = W.setInterval(function(){
      var doc = iframe.contentWindow.document||iframe.contentDocument;
      if(doc&&doc.body){
        W.clearInterval(timer);
        timer = null;
        doc.open();
        doc.write('<html><head><style type="text/css">*{-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;}body{margin:0;padding:5px;font-size:12px;word-wrap:break-word;}</style></head><body><div></div></body></html>');
        doc.close();
        doc.contentEditable = true;
        doc.designMode = 'on';
        obj.doc = doc;
      }else{
        doc = iframe.contentWindow.document||iframe.contentDocument;
      }
    },16);
    _renderToolbar(obj);
    obj.setContent(obj.dom.value);
  };
  var _create = function(config){
    var dom = I.insert('textarea',config.dom?config.dom:CFG.dom);
    return _render(dom,config);
  };
  var _render = function(dom,config){
    dom = I.$(dom);
    dom.style.display = 'none';
    var div = I.insert('div','after',dom);
    var obj = {
      dom:dom,
      proxyDom:div,
      iframe:null,
      doc:null,
      toolbar:null,
      body:null,
      className:null,
      config:null,
      range:null,
      getContent:function(){
        if(this.doc){
          var s = this.doc.body.innerHTML;
          this.dom.value = s;
          return s;
        }else{
          return this.dom.value;
        }
      },
      setContent:function(s){
        _try(this,function(){
          this.dom.value = s;
          this.doc.body.innerHTML = s;
        });
      }
    };
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-ui-Editor-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    I.cls(div,obj.className);
    _renderEditor(obj);
    return obj;
  };
  return {
    create:function(cfg){return _create(cfg);},
    render:function(dom,cfg){return _render(dom,cfg);}
  };
}+'');