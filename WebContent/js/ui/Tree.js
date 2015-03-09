I.regist('ui.Tree',function(W,D){
var CFG={
 skin:'Default',
 dom:D.body,
 check:false,
 icon:true,
 arrow:true,
 arrow_color:'#333',
 arrow_color_hover:'#333',
 icon_color:'#999',
 icon_color_hover:'#000',
 a_color:'#333',
 a_color_hover:'#FFF',
 a_background:'transparent',
 a_background_hover:'gray',
 folder_open_arrow:'fa fa-caret-down',
 folder_close_arrow:'fa fa-caret-right',
 file_arrow:'fa fa-caret-right not-visible',
 folder_open_icon:'fa fa-folder-open-o',
 folder_close_icon:'fa fa-folder-o',
 file_icon:'fa fa-file-o',
 onClick:function(who){},
 onCheck:function(who){}
};
var _create=function(obj,ul,cfg,data){
 var d,li,item,chd;
 for(var i=0,j=data.length;i<j;i++){
  d=data[i];
  li=I.insert('li',ul);
  li.innerHTML=[cfg.arrow?'<b></b>':'',cfg.icon?'<i></i>':'',cfg.check?'<input type="checkbox" />':'','<a>'+d.text+'</a>'].join('');
  if(d.attribute) for(var x in d.attribute) li.setAttribute(x,d.attribute[x]);
  item=_bindItem(obj,li,cfg);
  item.checked=d.checked?true:false;
  item.expand=d.expand?true:false;
  chd=d.children;
  if(chd){
   if(chd.length>0){
    item.type='folder';
    _create(obj,I.insert('ul',li),cfg,chd);
   }else item.type='file';
  }
  item.repaint();
 }
};
var _initObj=function(obj,cfg){
 obj.getSelected=function(){
  var q=[],o,ms=this.items;
  for(var i in ms){
   o=ms[i];
   if(o.checked) q.push(o);
  }
  return q;
 };
 obj.add=function(nodeHtml){
  var li=I.insert('li',obj.layer);
  li.innerHTML=nodeHtml;
  return _bindItem(obj,li,cfg);
 };
 obj.remove=function(uuid){
  var li=this.items[uuid].dom.li;
  li.parentNode.removeChild(li);
  delete this.items[uuid];
 };
 obj.getChildren=function(){
  var chd=[];
  for(var i in this.items) chd.push(this.items[i]);
  return chd;
 };
 obj.open=function(){
  var chd=this.getChildren();
  for(var i=0,j=chd.length;i<j;i++) chd[i].open();
 };
 obj.close=function(){
  var chd=this.getChildren();
  for(var i=0,j=chd.length;i<j;i++) chd[i].close();
 };
 obj.checkAll=function(b){
  var chd=this.getChildren();
  for(var i=0,j=chd.length;i<j;i++) chd[i].check(b);
 };
};
var _bindItem=function(obj,li,cfg){
 var item={uuid:null,expand:false,checked:false,type:'file',dom:{ul:null,arrow:null,icon:null,input:null,a:null,li:li}};
 item.uuid=I.util.UUID.next();
 item.dom.li.setAttribute('data-uuid',item.uuid);
 item.repaint=function(){
  var inst=this;
  if(inst.dom.input) inst.dom.input.checked=inst.checked?'checked':'';
  switch(this.type){
   case 'folder':
    if(this.expand){
     if(inst.dom.icon) I.cls(inst.dom.icon,cfg.folder_open_icon);
     if(inst.dom.arrow) I.cls(inst.dom.arrow,cfg.folder_open_arrow);
     if(inst.dom.ul) inst.dom.ul.style.display='block';
    }else{
     if(inst.dom.icon) I.cls(inst.dom.icon,cfg.folder_close_icon);
     if(inst.dom.arrow) I.cls(inst.dom.arrow,cfg.folder_close_arrow);
     if(inst.dom.ul) inst.dom.ul.style.display='none';
    }
   break;
   default:
    if(inst.dom.icon) I.cls(inst.dom.icon,cfg.file_icon);
    if(inst.dom.arrow) I.cls(inst.dom.arrow,cfg.file_arrow);
   break;
  }
 };
 item.add=function(nodeHtml){
  var inst=this;
  if(!inst.dom.ul) inst.dom.ul=I.insert('ul',inst.dom.li);
  var li=I.insert('li',inst.dom.ul);
  li.innerHTML=nodeHtml;
  inst.type='folder';
  inst.expand=true;
  inst.repaint();
  return _bindItem(obj,li,cfg);
 };
 item.getChildren=function(){
  var chd=[],inst=this,uuid,q;
  var ls=I.$(inst.dom.li,'tag','li');
  if(!ls||ls.length<1) return chd;
  for(var i=0,j=ls.length;i<j;i++){
   uuid=ls[i].getAttribute('data-uuid');
   q=obj.items[uuid];
   if(q) chd.push(q);
  }
  return chd;
 };
 item.open=function(){
  if('folder'==this.type&&!this.expand){
   this.expand=true;
   this.repaint();
  }
 };
 item.close=function(){
  if('folder'==this.type&&this.expand){
   this.expand=false;
   this.repaint();
  }
 };
 item.check=function(b){
  this.checked=b?true:false;
  this.repaint();
 };
 obj.items[item.uuid]=item;
 var tags=I.$(li,'*');
 if(!tags) return;
 var tagCount=tags.length;
 var tag,tagName;
 if(tagCount<1) return;
 for(var j=0,x=tagCount;j<x;j++){
  tag=tags[j];
  tagName=tag.tagName.toLowerCase();
  switch(tagName){
   case 'b':item.dom.arrow=tag;break;
   case 'i':item.dom.icon=tag;break;
   case 'a':item.dom.a=tag;break;
   case 'ul':item.dom.ul=tag;break;
   case 'input':item.dom.input=tag;break;
  }
 }
 if(item.dom.input) item.checked=item.dom.input.checked?true:false;
 if(item.dom.ul){
  item.type='folder';
  item.expand=true;
  item.repaint();
  _bind(obj,item.dom.ul,cfg);
 }else{
  item.type='file';
  item.expand=false;
  item.repaint();
 }
 if(item.dom.arrow){
  item.dom.arrow.style.color=cfg.arrow_color;
  item.dom.arrow.onmouseover=function(){this.style.color=cfg.arrow_color_hover;};
  item.dom.arrow.onmouseout=function(){this.style.color=cfg.arrow_color;};
  item.dom.arrow.onclick=function(){
   var q=obj.items[this.parentNode.getAttribute('data-uuid')];
   if(q&&'folder'==q.type){
    q.expand=!q.expand;
    q.repaint();
   }
  };
 }
 if(item.dom.icon){
  item.dom.icon.style.color=cfg.icon_color;
  item.dom.icon.onmouseover=function(){this.style.color=cfg.icon_color_hover;};
  item.dom.icon.onmouseout=function(){this.style.color=cfg.icon_color;};
  item.dom.icon.onclick=function(){
   var q=obj.items[this.parentNode.getAttribute('data-uuid')];
   if(q&&'folder'==q.type){
    q.expand=!q.expand;
    q.repaint();
   }
  };
 }
 if(item.dom.a){
  item.dom.a.href='javascript:void(0);';
  item.dom.a.style.color=cfg.a_color;
  item.dom.a.onmouseover=function(){
   this.style.color=cfg.a_color_hover;
   this.style.background=cfg.a_background_hover;
  };
  item.dom.a.onmouseout=function(){
   this.style.color=cfg.a_color;
   this.style.background=cfg.a_background;
  };
  item.dom.a.onclick=function(){
   var q=obj.items[this.parentNode.getAttribute('data-uuid')];
   if(q) cfg.onClick(q);
  };
 }
 if(item.dom.input){
  item.dom.input.onclick=function(){
   var q=obj.items[this.parentNode.getAttribute('data-uuid')];
   if(q){
    q.checked=this.checked?true:false;
    cfg.onCheck(q);
   }
  };
 }
 return item;
};
var _bind=function(obj,ul,cfg){
 var lis=I.$(ul,'*');
 if(!lis) return;
 var liCount=lis.length;
 for(var i=0;i<liCount;i++) _bindItem(obj,lis[i],cfg);
};
var _render=function(dom,config){
 dom=I.$(dom);
 var obj={layer:dom,className:null,config:null,items:{}};
 var cfg=I.ui.Component.initConfig(config,CFG);
 cfg.dom=obj.layer;
 obj.config=cfg;
 obj.className='i-ui-Tree-'+cfg.skin;
 _initObj(obj,cfg);
 I.util.Skin.init(cfg.skin);
 I.cls(obj.layer,obj.className);
 _bind(obj,obj.layer,cfg);
 return obj;
};
var _prepare=function(config){
 var cfg=I.ui.Component.initConfig(config,CFG);
 var ul=I.insert('ul',cfg.dom);
 var obj={layer:ul,className:null,config:null,items:{}};
 cfg.dom=obj.layer;
 obj.config=cfg;
 obj.className='i-ui-Tree-'+cfg.skin;
 _initObj(obj,cfg);
 I.util.Skin.init(cfg.skin);
 I.cls(obj.layer,obj.className);
 _create(obj,obj.layer,cfg,cfg.data);
 _bind(obj,obj.layer,cfg);
 return obj;
};
return {
 create:function(cfg){return _prepare(cfg);},
 render:function(dom,cfg){return _render(dom,cfg);}
};
}+'');