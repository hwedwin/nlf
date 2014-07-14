/**
 * I.ui.Tree
 * <i>树</i>
 */
I.regist('ui.Tree',function(W,D){
  var CFG = {
    skin:'Default',
    dom:D.body,
    folder_open_arrow:'fa fa-caret-down',
    folder_close_arrow:'fa fa-caret-right',
    file_arrow:'fa fa-caret-right not-visible',
    folder_open_icon:'fa fa-folder-open-o',
    folder_close_icon:'fa fa-folder-o',
    file_icon:'fa fa-file-o',
    onClick:function(who){}
  };
  var _create = function(obj){
    var cfg = obj.config;
    var o = I.insert('a',cfg.dom);
    o.innerHTML = cfg.label;
    o.href = 'javascript:void(0);';
    I.listen(o,'click',function(m,e){
      cfg.callback.call(obj);
    });
    I.cls(o,obj.className);
    obj.layer = o;
    return obj;
  };
    
  var _prepare = function(config){
    var obj = {layer:null,className:null,config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-ui-Tree-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    _create(obj);
    return obj;
  };
  var _bind = function(obj,ul,cfg){
    var lis = I.$(ul,'*');
    if(!lis){
      return;
    }
    var liCount = lis.length;
    if(liCount<1){
      return;
    }
    for(var i=0;i<liCount;i++){
      var li = lis[i];
      var item = {uuid:null,expand:false,checked:false,type:'file',dom:{ul:null,arrow:null,icon:null,input:null,a:null,li:li}};
      item.uuid = I.util.UUID.next();
      item.dom.li.setAttribute('data-uuid',item.uuid);
      item.repaint = function(){
        var inst = this;
        if(inst.dom.input){
          inst.dom.input.checked = inst.checked?'checked':'';
        }
        switch(this.type){
          case 'folder':
            if(this.expand){
              if(inst.dom.icon){
                I.cls(inst.dom.icon,cfg.folder_open_icon);
              }
              if(inst.dom.arrow){
                I.cls(inst.dom.arrow,cfg.folder_open_arrow);
              }
              if(inst.dom.ul){
                inst.dom.ul.style.display = 'block';
              }
            }else{
              if(inst.dom.icon){
                I.cls(inst.dom.icon,cfg.folder_close_icon);
              }
              if(inst.dom.arrow){
                I.cls(inst.dom.arrow,cfg.folder_close_arrow);
              }
              if(inst.dom.ul){
                inst.dom.ul.style.display = 'none';
              }
            }
            break;
          default:
            if(inst.dom.icon){
              I.cls(inst.dom.icon,cfg.file_icon);
            }
            if(inst.dom.arrow){
              I.cls(inst.dom.arrow,cfg.file_arrow);
            }
            break;
        }
      };
      obj.items[item.uuid] = item;
      var tags = I.$(li,'*');
      if(!tags){
        continue;
      }
      var tagCount = tags.length;
      if(tagCount<1){
        continue;
      }
      for(var j=0;j<tagCount;j++){
        var tag = tags[j];
        var tagName = tag.tagName.toLowerCase();
        switch(tagName){
          case 'b':item.dom.arrow = tag;break;
          case 'i':item.dom.icon = tag;break;
          case 'a':item.dom.a = tag;break;
          case 'ul':item.dom.ul = tag;break;
          case 'input':item.dom.input = tag;break;
        }
      }
      if(item.dom.ul){
        item.type = 'folder';
        item.expand = true;
        item.repaint();
        if(item.dom.icon){
          item.dom.icon.onclick = function(){
            var p = this.parentNode;
            var q = obj.items[p.getAttribute('data-uuid')];
            if(!q){
              return;
            }
            if('folder'!=q.type){
              return;
            }
            q.expand = !q.expand;
            q.repaint();
          };
        }
        _bind(obj,item.dom.ul,cfg);
      }else{
        item.type = 'file';
        item.expand = false;
        item.repaint();
      }
      if(item.dom.a){
        item.dom.a.href = 'javascript:void(0);';
        item.dom.a.onclick = function(){
          var p = this.parentNode;
          var q = obj.items[p.getAttribute('data-uuid')];
          if(!q){
            return;
          }
          cfg.onClick(q);
        };
      }
      if(item.dom.input){
        item.dom.input.onclick = function(){
          var p = this.parentNode;
          var q = obj.items[p.getAttribute('data-uuid')];
          if(!q){
            return;
          }
          q.checked = this.checked?true:false;
        };
      }
    }
  };
  var _render = function(dom,config){
    dom = I.$(dom);
    var obj = {layer:dom,className:null,config:null,items:{}};
    var cfg = I.ui.Component.initConfig(config,CFG);
    cfg.dom = obj.layer;
    obj.config = cfg;
    obj.className = 'i-ui-Tree-'+cfg.skin;
    obj.getSelected = function(){
      var q = [];
      for(var i in this.items){
        var o = this.items[i];
        if(o.checked){
          q.push(o);
        }
      }
      return q;
    };
    I.util.Skin.init(cfg.skin);
    I.cls(obj.layer,obj.className);
    _bind(obj,obj.layer,cfg);
    return obj;
  };
  return {
    create:function(cfg){return _prepare(cfg);},
    render:function(dom,cfg){return _render(dom,cfg);}
  };
}+'');