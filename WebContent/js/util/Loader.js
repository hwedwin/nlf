/**
 * 资源加载器，可加载js、css资源
 * I.util.Loader.load(url,callback);
 */
I.regist({
  'name':'util.Loader',
  'need':null,
  'fn':function(W,D){
    var SYN = false;
    var N = [];
    var jsLoad = function(s,f){
      var o = D.createElement('script');
      o.type = 'text/javascript';
      o.src = s;
      if(-[1,]){
        o.onload = function(){
          f();
          try{this.parentNode.removeChild(this);}catch(e){}
        };
      }else{
        o.onreadystatechange = function(){
          if(/loaded|complete/.test(this.readyState)){
            f();
            try{this.parentNode.removeChild(this);}catch(e){}
          }
        };
      }
      D.getElementsByTagName('head')[0].appendChild(o);
    };
    
    var cssLoad = function(s,f){
      var o = D.createElement('link');
      o.type = 'text/css';
      o.rel = 'stylesheet';
      o.href = s;
      if(o.onload){
        o.onload = function(){
          f();
          //try{this.parentNode.removeChild(this);}catch(e){}
        };
      }else if(o.onreadystatechange){
        o.onreadystatechange = function(){
          if(/loaded|complete/.test(this.readyState)){
            f();
            //try{this.parentNode.removeChild(this);}catch(e){}
          }
        };
      }else if(o.attachEvent){
        o.attachEvent('onload', function(){
          f();
        });
      }else{
        var tm = null;
        tm = W.setInterval(function(){
          if(o.sheet){
            W.clearInterval(tm);
            tm = null;
            f();
          }
        },0);
      }
      D.getElementsByTagName('head')[0].appendChild(o);
    };
    
    var nullLoad = function(s,f){
      f();
    };
    
    var loadFs = function(f){
      SYN = false;
      f();
    };
    
    var lazyLoad = function(c,f){
      var s = '';
      var q = c;
      if(q.indexOf('?')>-1){
        q = q.substr(0,q.indexOf('?'));
      }
      if(q.indexOf('.')>-1){
        s = q.substr(q.lastIndexOf('.'));
      }
      switch(s){
      case '.js':jsLoad(c,f);break;
      case '.css':cssLoad(c,f);break;
      default:nullLoad(c,f);break;
      }
    };
    
    var checkLoad = function(f){
      if(N.length<1){
        loadFs(f);
        return;
      }
      var c = N.shift();
      lazyLoad(c,function(){
        checkLoad(f);
      });
    };
    
    var load = function(s,f){
      SYN = true;
      if(Object.prototype.toString.apply(s) === '[object Array]'){
        for(var i=0;i<s.length;i++){
          N.push(s[i]);
        }
      }else{
        N.push(s);
      }
      checkLoad(f);
    };
    
    var preLoad = function(s,f){
      if(SYN){
        W.setTimeout(function(){
          preLoad(s,f);
        },10);
      }else{
        load(s,f);
      }
    };
    
    return {
      'load':function(s,f){preLoad(s,f);}
    };
  }
});