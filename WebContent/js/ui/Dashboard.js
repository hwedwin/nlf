I.regist('ui.Dashboard',function(W,D){
var CFG={
 skin:'Dashboard',
 title_bar_height:30,
 tool_bar_height:30,
 tool_padding_h:20,
 tool_padding_v:10,
 footer_height:40,
 menu_width:200,
 head_height:40,
 foot_height:40
};
var _create=function(obj){
 var cfg=obj.config;
 var o=D.body;
 o.innerHTML=[
  '<div class="header-'+cfg.skin+'">',
   '<div class="title-bar-'+cfg.skin+'"></div>',
   '<div class="tool-bar-'+cfg.skin+'"></div>',
  '</div>',
  '<div class="body-'+cfg.skin+'">',
   '<div class="menu-bar-'+cfg.skin+'"></div>',
   '<div class="head-'+cfg.skin+'"></div>',
   '<div class="content-'+cfg.skin+'"></div>',
   '<div class="foot-'+cfg.skin+'"></div>',
  '</div>',
  '<div class="footer-'+cfg.skin+'"></div>'
 ].join('');
 var div=I.$(o,'*');
 obj.header=div[0];
 obj.body=div[1];
 obj.footer=div[2];
 div=I.$(obj.header,'*');
 obj.titleBar=div[0];
 obj.toolBar=div[1];
 div=I.$(obj.body,'*');
 obj.menu=div[0];
 obj.head=div[1];
 obj.content=div[2];
 obj.foot=div[3];
 _api(obj);
 obj.suit();
};
var _api=function(obj){
 obj.suit=function(){
  var that=this;
  var cfg=that.config;
  I.util.Boost.addStyle(that.titleBar,'height:'+cfg.title_bar_height+'px;');
  I.util.Boost.addStyle(that.toolBar,'height:'+cfg.tool_bar_height+'px;');
  I.util.Boost.addStyle(that.body,'top:'+(cfg.title_bar_height+cfg.tool_bar_height)+'px;bottom:'+cfg.footer_height+'px;');
  I.util.Boost.addStyle(that.footer,'height:'+cfg.footer_height+'px;');
  I.util.Boost.addStyle(that.menu,'width:'+cfg.menu_width+'px;');
  I.util.Boost.addStyle(that.head,'left:'+cfg.menu_width+'px;height:'+cfg.head_height+'px;');
  I.util.Boost.addStyle(that.foot,'left:'+cfg.menu_width+'px;height:'+cfg.foot_height+'px;');
  I.util.Boost.addStyle(that.content,'left:'+cfg.menu_width+'px;top:'+cfg.head_height+'px;bottom:'+cfg.foot_height+'px;_margin-left:'+cfg.menu_width+'px;');
 };
 obj.find=function(url,args){
  I.net.Page.find(url,args,this.content);
 };
 obj.addBread=function(dom){
  I.cls(I.insert('ul',dom),'bread-'+this.config.skin);
 };
 obj.updateBread=function(){
  var that=this;
  var breads=I.$('class','bread-'+that.config.skin);
  var s=[];
  if(that.breads.first) s.push('<li>'+that.breads.first.text+'</li>');
  if(that.breads.second){
   s.push('<li class="arrow">4</li>');
   s.push('<li>'+that.breads.second.text+'</li>');
  }
  if(that.breads.third){
   s.push('<li class="arrow">4</li>');
   s.push('<li>'+that.breads.third.text+'</li>');
  }
  s=s.join('');
  I.each(breads,function(q){
   q.innerHTML=s;
   var r=I.region(q.parentNode);
   var ro=I.region(q);
   I.util.Boost.addStyle(q,'margin-top:'+Math.floor((r.height-ro.height)/2)+'px;');
  });
 };
 obj.addItem=function(dom,items,callback){
  var that=this;
  var cfg=that.config;
  var r=I.region(dom);
  var o=I.insert('ul',dom);
  I.cls(o,'user-'+cfg.skin);
  var img;
  for(var i=0,j=items.length;i<j;i++){
   var s=items[i];
   var li=I.insert('li',o);
   switch(s.type){
   case 'head':
     li.innerHTML='<img src="'+s.content+'" />';
     img=I.$(li,'*')[0];
     I.util.Boost.addStyle(img,'width:'+Math.floor(r.height/2)+'px;height:'+Math.floor(r.height/2)+'px;border:1px solid #FFF;');
     I.util.Boost.round(img,Math.floor(r.height/2));
     break;
   case 'text':
     li.innerHTML=s.content;
     break;
   }
   li.setAttribute('data-index',i+'');
   if(callback){
    I.listen(li,'click',function(m){
     callback.call(m,parseInt(m.getAttribute('data-index'),10));
    });
   }
  }
  var ro=I.region(o);
  I.util.Boost.addStyle(o,'margin-top:'+Math.floor((r.height-ro.height)/2)+'px;line-height:'+ro.height+'px;');
 };
 obj.setMenu=function(data){
  var that=this,cfg=this.config;
  that.menuData=data;
  that.menuDataCache={};
  that.toolBar.innerHTML='';
  function _uuid(ds){
   if(!ds) return;
   var d;
   for(var i=0,j=ds.length;i<j;i++){
    d=ds[i];
    d.uuid=I.util.UUID.next();
    that.menuDataCache[d.uuid]=d;
    _uuid(d.children);
   }
  }
  _uuid(data);
  function _third(ul,ds){
   var d,o;
   for(var i=0,j=ds.length;i<j;i++){
    d=ds[i];
    o=I.insert('li',ul);
    o.innerHTML='<a><i class="'+d.icon+'"></i><b>'+d.text+'</b><u>4</u></a>';
    o.setAttribute('data-id',d.uuid);
    I.$(o,'*')[0].onclick=function(){
     var who=this.parentNode;
     var id=who.getAttribute('data-id');
     var cache=that.menuDataCache[id];
     var the=I.$('previous',who.parentNode);
     I.each(that.menus,function(m){
      I.cls(m,'menu-'+cfg.skin+(m==the?'-active':''));
     });
     var lis=I.$(that.menu,'tag','li');
     I.each(lis,function(m){
       I.cls(m,m==who?'active':'');
     });
     that.breads.third=cache;
     that.updateBread();
     if(cache.callback) cache.callback.call(cache);
     else if(cache.link) that.find(cache.link,null);
    };
   }
  }
  function _second(ds){
   var d,o;
   that.menu.innerHTML='';
   that.menus=[];
   for(var i=0,j=ds.length;i<j;i++){
    d=ds[i];
    o=I.insert('a',that.menu);
    o.innerHTML='<i class="'+d.icon+'"></i><b>'+d.text+'</b><u'+(d.children?' class="fa fa-caret-down"':'')+'></u>';
    I.cls(o,'menu-'+cfg.skin);
    o.setAttribute('data-id',d.uuid);
    if(d.children){
     var ul=I.insert('ul','after',o);
     I.cls(ul,'menu-item-'+cfg.skin);
     _third(ul,d.children);
    }
    o.onclick=function(){
     var who=this;
     I.each(that.menus,function(m){
      I.cls(m,'menu-'+cfg.skin+(m==who?'-active':''));
     });
     var lis=I.$(that.menu,'tag','li');
     I.each(lis,function(m){
      I.cls(m,'');
     });
     var id=this.getAttribute('data-id');
     var cache=that.menuDataCache[id];
     that.breads.second=cache;
     that.breads.third=null;
     that.updateBread();
     var cd=cache.children;
     if(cd){
      var expand=('1'==this.getAttribute('data-expand'));
      var from=expand?cd.length*40:0;
      var to=expand?0:cd.length*40;
      var icon=expand?'fa fa-caret-down':'fa fa-caret-up';
      var ul=I.$('next',who);
      I.util.Animator.create().change('linear',function(n){
       ul.style.height=n+'px';
      },function(){
       ul.style.height=to+'px';
       I.cls(I.$(who,'*')[2],icon);
      },10,from,to);
      this.setAttribute('data-expand',expand?'0':'1');
     }
     if(cache.callback) cache.callback.call(cache);
     else if(cache.link) that.find(cache.link,null);
    };
    that.menus.push(o);
   }
  };
  function _first(ds){
   var d,o;
   that.toolBar.innerHTML='';
   that.tools=[];
   that.breads={first:null,second:null,third:null};
   for(var i=0,j=ds.length;i<j;i++){
    d=ds[i];
    o=I.insert('a',that.toolBar);
    o.innerHTML='<i class="'+d.icon+'"></i><b>'+d.text+'</b>';
    I.cls(o,'tool-'+cfg.skin);
    I.util.Boost.addStyle(o,'padding:'+cfg.tool_padding_v+'px '+cfg.tool_padding_h+'px;');
    var r=I.region(o);
    if(r.height>cfg.tool_bar_height){
     var b=I.$(o,'*');
     I.util.Boost.addStyle(b[0],'display:block;float:left;width:auto;');
     I.util.Boost.addStyle(b[1],'margin-left:5px;display:block;float:left;height:1.4em;line-height:1.4em;width:auto;');
     r=I.region(o);
     I.util.Boost.addStyle(o,'margin-top:'+Math.floor(cfg.tool_bar_height-r.height)/2+'px;');
    }
    I.util.Boost.addStyle(o,'margin-top:'+Math.floor(cfg.tool_bar_height-r.height)/2+'px;');
    o.setAttribute('data-id',d.uuid);
    o.onclick=function(){
     var who=this;
     I.each(that.tools,function(m){
      I.cls(m,'tool-'+cfg.skin+(m==who?'-active':''));
     });
     var id=this.getAttribute('data-id');
     var cache=that.menuDataCache[id];
     var cd=cache.children;
     var from=cd?0:cfg.menu_width;
     var to=cd?cfg._menu_width:0;
     var all=I.$(that.menu,'*');
     I.each(all,function(q){
      q.style.visibility='hidden';
     });
     that.breads.first=cache;
     that.breads.second=null;
     that.breads.third=null;
     that.updateBread();
     I.util.Animator.create().change('linear',function(n){
       cfg.menu_width=n;
       that.suit();
      },function(){
       I.each(all,function(q){
        q.style.visibility='visible';
       });
       if(cd){
        if(id!=that.menu.getAttribute('data-id')){
         _second(cd);
        }
       }
     },10,from,to);
     if(cache.callback) cache.callback.call(cache);
     else if(cache.link) that.find(cache.link,null);
    };
    that.tools.push(o);
   }
  }
  _first(data);
 };
};
var _prepare=function(config){
 var obj={config:null,className:null,menuData:null,menuDataCache:null};
 var cfg=I.ui.Component.initConfig(config,CFG);
 var tcfg={};
 for(var i in cfg){
  tcfg['_'+i]=cfg[i];
 }
 for(var i in tcfg){
  cfg[i]=tcfg[i];
 }
 cfg.menu_width=0;
 obj.config=cfg;
 obj.className='i-ui-Bashboard-'+cfg.skin;
 I.util.Skin.init(cfg.skin);
 _create(obj);
 return obj;
};
return{
 create:function(cfg){return _prepare(cfg);}
};
}+'');