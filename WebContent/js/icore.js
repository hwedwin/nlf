(function(W,D){
  W.I = {
    version:'2.0.0',
    ROOT:'auto',
    debug:false
  };
  
  var M = 'icore.js';
  var R = '';
  var L = false;
  var SYN = true;
  var Q = {};
  
  var _dir = function(u){
    var r = '';
    if(!L){
      var c = D.getElementsByTagName('script');
      for(var i=0;i<c.length;i++){
        var s = c[i].getAttribute('src');
        if(!s) continue;
        var j = s.indexOf(M);
        if(j>-1){
          r = s.substr(0,j);
          break;
        }
      }
      _autoRoot(r);
      R = r;
      L = true;
    }else{
      r = R;
    }
    if(u){
      r += u;
    }
    return r;
  };
  
  var _autoRoot = function(r){
    if('auto'!=I.ROOT){
      return;
    }
    var n = r.indexOf('://'); 
    if(n>0){
      r = r.substr(n+3);
    }
    n = r.indexOf('/');
    if(n<0){
      I.ROOT = '';
    }else{
      r = r.substr(n);
      n = r.indexOf('/',1);
      if(n<0){
        I.ROOT = '/';
      }else{
        r = r.substr(0,n);
        if(r.indexOf('.')<0){
          I.ROOT = r;
        }else{
          I.ROOT = '/';
        }
      }
    }
  };
  
  var initPackage = function(k){
    var p = I;
    for(var i=0;i<k.length;i++){
      if(!p[k[i]]){
        p[k[i]]={};
      }
      p = p[k[i]];
    }
    return p;
  };
  
  var initClass = function(klass,code){
    var k = klass.split('.');
    var name = k.pop();
    var p = initPackage(k);
    p[name] = eval('('+code+')')(W,D);
  };
  
  var localLoad = function(c,f){
    if(I.debug){
      I.lang.Store.clear();
      throw 'I.js debug mode';
    }
    var v = I.lang.Store.get('i_js_version');

    if(!v||I.version!=v){
      I.lang.Store.clear();
      I.lang.Store.set('i_js_version',I.version);
      throw 'I.js need update';
    }
    
    var k = I.lang.Store.get(c);
    if(!k){
      throw c+' need update';
    }
    var o = D.createElement('script');
    o.text = k;
    D.getElementsByTagName('head')[0].appendChild(o);
    o.parentNode.removeChild(o);
    f.apply(f);
  };
  
  var checkLoad = function(c,callback){
    var over = true;
    for(var i=0;i<c.length;i++){
      if(!Q[c[i]]){
        over = false;
        break;
      }
    }
    if(over){
      var mc = [];
      for(var i=0;i<c.length;i++){
        var code = Q[c[i]].c;
        var nc = _depend(code);
        for(var j=0;j<nc.length;j++){
          var o = nc[j];
          if(('|'+mc.join('|')+'|').indexOf('|'+o+'|')<0){
            mc.push(o);
          }
        }
      }
      if(mc.length<1){
        for(var i=0;i<c.length;i++){
          var s = c[i];
          if(!Q[s].l){
            initClass(s,Q[s].c);
            Q[s].l = true;
          }
        }
        SYN = false;
        callback.apply(callback);
      }else{
        load(mc,function(){
          for(var i=0;i<c.length;i++){
            var s = c[i];
            if(!Q[s].l){
              initClass(s,Q[s].c);
              Q[s].l = true;
            }
          }
          callback.apply(callback);
        });
      }
    }
  };
  
  var lazyLoad = function(c,f){
    var o = D.createElement('script');
    o.type = 'text/javascript';
    var src = R+c.replace(/\./g,'/')+'.js?t='+new Date().getTime();
    o.src = src;
    try{
      o.onload = function(){
        f.apply(f);
        try{this.parentNode.removeChild(this);}catch(e){}
      };
    }catch(ex){
      o.onreadystatechange = function(){
        if(/loaded|complete/.test(this.readyState)){
          f.apply(f);
          try{this.parentNode.removeChild(this);}catch(e){}
        }
      };
    }
    D.getElementsByTagName('head')[0].appendChild(o);
  };
  
  var load = function(c,f){    
    SYN = true;
    var skip = true;
    for(var i=0;i<c.length;i++){
      var s = c[i];
      if(Q[s]){
        continue;
      }
      skip = false;
      try{
        localLoad(s,function(){
          checkLoad(c,f);
        });
      }catch(e){
        lazyLoad(s,function(){
          checkLoad(c,f);
        });
      }
    }
    if(skip){
      checkLoad(c,f);
    }
  };
  
  var preLoad = function(c,f){
    if(SYN){
      W.setTimeout(function(){
        preLoad(c,f);
      },1);
    }else{
      load(c,f);
    }
  };
  
  _dir();
  
  var F = [
    function(f){W.attachEvent('onload',f);},
    function(f){W.addEventListener('DOMContentLoaded',f,false);}
  ];
  for(var i=0;i<F.length;i++){
    try{
      F[i](function(){
        SYN = false;
      });
      break;
    }catch(e){}
  }
    
  var _regist = function(klass,code){
    Q[klass] = {
      l:false,
      c:code
    };
    try{
      if(klass=='lang.Store'){
        initClass(klass,code);
      }else{
        I.lang.Store.set(klass,'I.regist(\''+klass+'\','+code+'+\'\');');
      }
    }catch(e){}
  };
  
  var _depend = function(code){
    var c = [];
    var s = code;
    var idx = s.indexOf('I.');
    while(idx>-1){
      s = s.substr(idx+2);
      var kh = s.indexOf('(');
      var q = s.substr(0, kh);
      if(q.indexOf('.')>-1){
        var k = q.substr(0, q.lastIndexOf('.'));
        if(k.indexOf('.')>-1){
          if(('|'+c.join('|')+'|').indexOf('|'+k+'|')<0){
            c.push(k);
          }
        }
      }
      idx = s.indexOf('I.');
    }
    return c;
  };
  
  var _run = function(callback){
    var c = _depend(callback+'');
    if(c.length<1){
      callback.apply(callback);
    }else{
      preLoad(c,callback);
    }
  };

  I.regist = function(klass,code){_regist(klass,code);return this;};
  I.dir = function(res){return _dir(res);};
  I.run = function(callback){_run(callback);};
  
  I.regist('lang.Store',function(w,d){
    var _host = location.hostname;
    var _LS = [{
      loaded:false,
      instance:null,
      support:function(){
        if(!this.loaded){
          this.instance = w.localStorage;
          this.loaded = true;
        }
        return this.instance?true:false;
      },
      setItem:function(k,v){
        this.instance.setItem(k,v);
      },
      getItem:function(k){
        return this.instance.getItem(k);
      },
      clear:function(){
       this.instance.clear();
      }
    },
    {
      loaded:false,
      instance:null,
      support:function(){
        if(!this.loaded){
          try{
            var o = d.createElement('input');
            o.type = 'hidden';
            o.style.display = 'none';
            o.addBehavior ("#default#userData");
            d.body.appendChild(o);
            var exp = new Date();
            exp.setDate(exp.getDate()+365);
            o.expires = exp.toUTCString();
            this.instance = o;
          }catch(e){}
          this.loaded = true;
        }
        return this.instance?true:false;
      },
      setItem:function(k,v){
        this.instance.load(_host);
        this.instance.setAttribute(k,v);
        this.instance.save(_host);
      },
      getItem:function(k){
        this.instance.load(_host);
        return this.instance.getAttribute(k);
      },
      clear:function(){
        var exp = new Date();
        exp.setDate(exp.getDate()-2);
        this.instance.expires = exp.toUTCString();
        exp = new Date();
        exp.setDate(exp.getDate()+365);
        this.instance.expires = exp.toUTCString();
      }
    }];
    var _set = function(k,v){
      for(var i=0;i<_LS.length;i++){
        if(_LS[i].support()){
          _LS[i].setItem(k,v);
          break;
        }
      }
    };
    var _get = function(k){
      for(var i=0;i<_LS.length;i++){
        if(_LS[i].support()){
          return _LS[i].getItem(k);
        }
      }
      return null;
    };
    var _clear = function(){
      for(var i=0;i<_LS.length;i++){
        if(_LS[i].support()){
          _LS[i].clear();
          break;
        }
      }
    };
    return {
      set:function(k,v){_set(k,v);},
      get:function(k){return _get(k);},
      clear:function(){_clear();}
    };
  }+'');
})(window,document);