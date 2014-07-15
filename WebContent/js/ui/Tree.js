/**
 * I.ui.Tree
 * <i>树</i>
 */
I.regist('ui.Tree',function(W,D){
  var CFG = {
    skin:'Default',
    dom:D.body,
    check:false,
    icon:true,
    arrow:true,
    folder_open_arrow:'fa fa-caret-down',
    folder_close_arrow:'fa fa-caret-right',
    file_arrow:'fa fa-caret-right not-visible',
    folder_open_icon:'fa fa-folder-open-o',
    folder_close_icon:'fa fa-folder-o',
    file_icon:'fa fa-file-o',
    onClick:function(who){},
    onCheck:function(who){}
  };

  var _create = function(obj,ul,cfg,data){
    for(var i=0;i<data.length;i++){
      var d = data[i];
      var li = I.insert('li',ul);
      li.innerHTML = [cfg.arrow?'<b></b>':'',cfg.icon?'<i></i>':'',cfg.check?'<input type="checkbox" />':'','<a>'+d.text+'</a>'].join('');
      if(d.attribute){
        for(var j in d.attribute){
          li.setAttribute(j,d.attribute[j]);
        }
      }
      var item = _bindItem(obj,li,cfg);
      if(d.checked){
        item.checked = d.checked?true:false;
      }
      if(d.expand){
        item.expand = d.expand?true:false;
      }
      if(d.children){
        if(d.children.length>0){
          item.type = 'folder';
          var sub = I.insert('ul',li);
          _create(obj,sub,cfg,d.children);
        }else{
          item.type = 'file';
        }
      }
      item.repaint();
    }
  };
  var _initObj = function(obj,cfg){
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
    obj.add = function(nodeHtml){
      var li = I.insert('li',obj.layer);
      li.innerHTML = nodeHtml;
      return _bindItem(obj,li,cfg);
    };
    obj.remove = function(uuid){
      var li = this.items[uuid].dom.li;
      li.parentNode.removeChild(li);
      delete this.items[uuid];
    };
    obj.getChildren = function(){
      var chd = [];
      for(var i in this.items){
        chd.push(this.items[i]);
      }
      return chd;
    };
    obj.open = function(){
      var chd = this.getChildren();
      for(var i=0;i<chd.length;i++){
        chd[i].open();
      }
    };
    obj.close = function(){
      var chd = this.getChildren();
      for(var i=0;i<chd.length;i++){
        chd[i].close();
      }
    };
  };
  var _prepare = function(config){
    var cfg = I.ui.Component.initConfig(config,CFG);
    var ul = I.insert('ul',cfg.dom);
    var obj = {layer:ul,className:null,config:null,items:{}};
    cfg.dom = obj.layer;
    obj.config = cfg;
    obj.className = 'i-ui-Tree-'+cfg.skin;
    _initObj(obj,cfg);
    I.util.Skin.init(cfg.skin);
    I.cls(obj.layer,obj.className);
    _create(obj,obj.layer,cfg,cfg.data);
    _bind(obj,obj.layer,cfg);
    return obj;
  };
  var _bindItem = function(obj,li,cfg){
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
    item.add = function(nodeHtml){
      var inst = this;
      if(!inst.dom.ul){
        var ul = I.insert('ul',inst.dom.li);
        inst.dom.ul = ul;
      }
      var li = I.insert('li',inst.dom.ul);
      li.innerHTML = nodeHtml;
      inst.type = 'folder';
      inst.expand = true;
      inst.repaint();
      return _bindItem(obj,li,cfg);
    };
    item.getChildren = function(){
      var chd = [];
      var inst = this;
      var ls = I.$(inst.dom.li,'tag','li');
      if(!ls){
        return chd;
      }
      if(ls.length<1){
        return chd;
      }
      for(var i=0;i<ls.length;i++){
        var uuid = ls[i].getAttribute('data-uuid');
        var q = obj.items[uuid];
        if(q){
          chd.push(q);
        }
      }
      return chd;
    };
    item.open = function(){
      if('folder'==this.type){
        if(!this.expand){
          this.expand = true;
          this.repaint();
        }
      }
    };
    item.close = function(){
      if('folder'==this.type){
        if(this.expand){
          this.expand = false;
          this.repaint();
        }
      }
    };
    obj.items[item.uuid] = item;
    var tags = I.$(li,'*');
    if(!tags){
      return;
    }
    var tagCount = tags.length;
    if(tagCount<1){
      return;
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
      _bind(obj,item.dom.ul,cfg);
    }else{
      item.type = 'file';
      item.expand = false;
      item.repaint();
    }
    if(item.dom.arrow){
      item.dom.arrow.onclick = function(){
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
        cfg.onCheck(q);
      };
    }
    return item;
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
      _bindItem(obj,lis[i],cfg);
    }
  };
  var _render = function(dom,config){
    dom = I.$(dom);
    var obj = {layer:dom,className:null,config:null,items:{}};
    var cfg = I.ui.Component.initConfig(config,CFG);
    cfg.dom = obj.layer;
    obj.config = cfg;
    obj.className = 'i-ui-Tree-'+cfg.skin;
    _initObj(obj,cfg);
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