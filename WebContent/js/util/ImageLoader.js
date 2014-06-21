/**
 * I.util.ImageLoader
 * <i>图片预加载器</i>
 */
I.regist('util.ImageLoader',function(W,D){
  var T = [];
  var CT = {};
  var thread = null;
  var clear = function(){
    T = [];
  };
  var add = function(n,u){
    T.push({name:n,state:0,dom:null,url:u});
  };
  var startLoad = function(){
    CT = {};
    for(var i=0;i<T.length;i++){
      CT[T[i].name] = T[i];
    }
    for(var i in CT){
      var o = CT[i];
      o.state = 1;
      o.dom = new Image();
      o.dom.src = o.url;
      o.dom.setAttribute('lname',o.name);
      if(o.dom.complete){
        o.state = 2;
      }else{
        o.dom.onload = function(){
          CT[this.getAttribute('lname')].state = 2;
        };
        o.dom.onerror = function(){
          CT[this.getAttribute('lname')].state = 3;
        };
      }
    }
  };
    
  var state = function(){
    var ld = 0;
    var fd = 0;
    var tl = 0;
    for(var i in CT){
      if(2==CT[i].state){
        ld ++;
      }else if(3==CT[i].state){
        fd ++;
      }
      tl++;
    }
    return {loaded:ld,failed:fd,total:tl};
  };
    
  var stop = function(){
    if(thread){
      thread.stop();
      thread = null;
    }
  };
    
  var preLoad = function(s,f,cf){
    if(thread){
      W.setTimeout(function(){
        preLoad(s,f);
      },10);
      return;
    }
    clear();
    for(var i in s){
      add(i,s[i]);
    }
    startLoad();
    thread = I.util.Thread.create('thread',function(l){
      var o = state();
      if(o.loaded>=o.total){
        var img = {};
        for(var i in CT){
          img[i] = CT[i].dom;
        }
        stop();
        f({'image':img,'size':o.total});
      }else if(o.failed>0){
        stop();
        if(cf){
          cf();
        }
      }
    },10);
    thread.start();
  };
    
  return {
    load:function(s,f,cf){preLoad(s,f,cf);}
  };
}+'');