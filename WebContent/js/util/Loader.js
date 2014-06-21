/**
 * I.util.Loader
 * <i>资源加载器可加载js、css资源</i>
 * <u>I.util.Loader.load(url,callback);</u>
 */
I.regist('util.Loader',function(W,D){
  var SYN = false;
  var N = [];
  var jsLoad = function(s,f){
    var o = D.createElement('script');
    o.type = 'text/javascript';
    o.src = s;
    o.onload = function(){
      var first = false;
      try{
        this.parentNode.removeChild(this);
        first = true;
      }catch(e){}
      if(first){
        f.apply(f);
      }
    };
    o.onreadystatechange = function(){
      if(/loaded|complete/.test(this.readyState)){
        var first = false;
        try{
          this.parentNode.removeChild(this);
          first = true;
        }catch(e){}
        if(first){
          f.apply(f);
        }
      }
    };
    D.getElementsByTagName('head')[0].appendChild(o);
  };

  var cssLoad = function(s,f){
    var o = D.createElement('link');
    o.type = 'text/css';
    o.rel = 'stylesheet';
    o.href = s;
    if(o.onload){
      o.onload = function(){
        f.apply(f);
      };
    }else if(o.onreadystatechange){
      o.onreadystatechange = function(){
        if(/loaded|complete/.test(this.readyState)){
          f.apply(f);
        }
      };
    }else if(o.attachEvent){
      o.attachEvent('onload', function(){
        f.apply(f);
      });
    }else{
      var tm = null;
      tm = W.setInterval(function(){
        if(o.sheet){
          W.clearInterval(tm);
          tm = null;
          f.apply(f);
        }
      },1);
    }
    D.getElementsByTagName('head')[0].appendChild(o);
  };
    
  var nullLoad = function(s,f){
    f.apply(f);
  };
    
  var loadFs = function(f){
    SYN = false;
    f.apply(f);
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
    N.push(s);
    checkLoad(f);
  };
    
  var preLoad = function(s,f){
    if(SYN){
      W.setTimeout(function(){
        preLoad(s,f);
      },1);
    }else{
      load(s,f);
    }
  };
    
  return {
    load:function(url,callback){preLoad(url,callback);}
  };
}+'');