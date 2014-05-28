(function(W,D){
  
  W.I = {
    /** 当前版本号 */
    'version':'1.4.5',
    /** 应用根路径，auto表示自动尝试获取，如果出现问题，必须手动修改，如应用名称为test，则设置为/test */
    'ROOT':'auto',
    /** 是否开启调试模式，开启调试模式时，不本地存储js，并可使用I.lang.System.println(str);输出 */
    'debug':true
  };
  
  /** 类根目录 */
  var R = '';
  /** 文档路径是否已初始化 */
  var L = false;
  /** 文件标识 */
  var M = 'iloader.js';
  /** 命名空间 */
  var U = {};
  /** 初始化序列 */
  var Q = [];
  /** 类加载序列 */
  var N = [];
  /** 锁定标识 */
  var SYN = true;
  
  /**
   * 获取在类根目录下的资源地址，如<script src="/a/b/iloader.js"></script>，则dir([])返回/a/b/，dir(['img/a.gif'])返回/a/b/img/a.gif
   * @param u 数组，如果为空数组，则返回类根目录，如果有一个元素，则返回该文件的地址
   * @returns 表示路径的字符串
   */
  var dir = function(u){
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
      autoRoot(r);
      R = r;
      L = true;
    }else{
      r = R;
    }
    switch(u.length){
      case 1:r += u[0];break;
    }
    return r;
  };
  
  /**
   * 自动尝试初始化I.ROOT。如<script src="/a/b/iloader.js"></script>，则尝试设置I.ROOT为/a
   */
  var autoRoot = function(r){
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
  
  /**
   * 初始化包
   * @param pkg 包名
   */
  var initPackage = function(pkg){
    var p = U;
    var k = pkg.split('.');
    for(var i=0;i<k.length;i++){
      if(!p[k[i]]){
        p[k[i]]={};
      }
      p = p[k[i]];
    }
  };
  
  /**
   * 本地保存js文件内容,不能保证所有js都保存，因为I.lang.Store有可能还没有实例化
   * @param c 类名
   * @param s 类的代码，即对应js文件内容
   */
  var saveLocal = function(c,s){
    try{
      if(!I.lang.Store.get(c)){
        I.lang.Store.set(c,s);
      }
    }catch(e){}
  };
  
  /**
   * 在对应包下注册类
   * @param o 类的定义
   */
  var regist = function(o){
    var pkg = o.name.split('.');
    var name = pkg.pop();
    initPackage(pkg.join('.'));//初始化所在包
    var p = U;
    for(var i=0;i<pkg.length;i++){
      p = p[pkg[i]];
    }
    o['instance'] = null;
    p[name] = o;//将类的定义挂到对应包
    //生成类代码并保存到本地
    var s = 'I.regist({\'name\':\''+o.name+'\',\'need\':'+(o.need?'[\''+o.need.join('\',\'')+'\']':null)+',\'fn\':';
    s += o.fn;
    s += '});';
    saveLocal(o.name,s);
  };
  
  /**
   * 绑定可供实例化的类到命名空间
   * @param o 可供实例化的类，即处理好依赖后类定义中的fn
   */
  var bind = function(o){
    var p = W.I;
    var k = o.name.split('.');
    var name = k.pop();
    for(var i=0;i<k.length;i++){
      if(!p[k[i]]) p[k[i]]={};
      p = p[k[i]];
    }
    p[name] = o;
  };
  
  /**
   * 获取类定义
   * @param c 类名
   * @returns 类定义
   */
  var getKlass = function(c){
    var p = U;
    var m = c.split('.');
    for(var i=0;i<m.length;i++){
      p = p[m[i]];
    }
    return p;
  };
  
  /**
   * 判断类是否已加载
   * @param c 类名
   * @returns true/false
   */
  var isLoaded = function(c){
    var p = U;
    var m = c.split('.');
    for(var i=0;i<m.length;i++){
      if(!p[m[i]]) return false;
      p = p[m[i]];
    }
    return true;
  };
  
  /**
   * 异步加载类
   * @param c 类名
   * @param f 回调方法
   */
  var lazyLoad = function(c,f){
    var o = D.createElement('script');
    o.type = 'text/javascript';
    var src = R+c.replace(/\./g,'/')+'.js?t='+new Date().getTime();
    o.src = src;
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
  
  /**
   * 本地加载类
   * @param c 类名
   * @param f 回调方法
   */
  var localLoad = function(c,f){
    if(I.debug){
      I.lang.Store.clear();
      throw 'debug mode';
    }
    //获取本地版本号
    var v = I.lang.Store.get('iversion');
    //如果本地版本号不存在或较低，清空本地缓存，并设置为最新版本
    if(!v||I.version!=v){
      I.lang.Store.clear();
      I.lang.Store.set('iversion',I.version);
      throw 'need update';
    }
    //本地加载类定义
    var k = I.lang.Store.get(c);
    if(!k){//如果未获取到类定义，抛出异常
      throw 'need update';
    }
    //动态创建js代码
    var o = D.createElement('script');
    o.text = k;
    D.getElementsByTagName('head')[0].appendChild(o);
    o.parentNode.removeChild(o);
    //本地加载完成，回调
    f();
  };
  
  /**
   * 根据类名获取序列中的类
   * @param s 类名
   * @param p 序列
   * @returns 类，如果序列中不存在类名，返回null
   */
  var get = function(s,p){
    for(var i=0;i<p.length;i++){
      if(p[i].name == s){
        return p[i];
      }
    }
    return null;
  };
  
  /**
   * 对类的依赖权重分析
   * @param o 类的定义
   * @param p 序列
   */
  var sort = function(o,p){
    if(o.need){
      for(var i=0;i<o.need.length;i++){
        var n = get(o.need[i],p);
        n.weight = Math.max(n.weight,o.weight+1);
      }
      for(var i=0;i<o.need.length;i++){
        sort(get(o.need[i],p),p);
      }
    }
  };
  
  /**
   * 快速排序法对序列中的依赖类根据权重进行排序
   * @param p 原始序列
   * @returns 排序后的序列，权重高的在前面
   */
  var quickSort = function(p){
    if(p.length<=1) return p;
    var idx=Math.floor(p.length/2);
    var pivot=p.splice(idx,1)[0];
    var left=[];
    var right=[];
    for(var i=0;i<p.length;i++){
      if(p[i].weight>pivot.weight){
        left.push(p[i]);
      }else{
        right.push(p[i]);
      }
    }
    return quickSort(left).concat([pivot],quickSort(right));
  };
  
  /**
   * 处理所有依赖关系
   * @returns 按权重排序后的类名序列
   */
  var dependAll = function(){
    var p = [];
    for(var i=0;i<Q.length;i++){
      p.push(getKlass(Q[i]));
    }
    for(var i=0;i<p.length;i++){
      p[i]['weight'] = 0;
    }
    if(p.length>1){
      sort(p[0],p);
    }
    var np = quickSort(p);
    var s = [];
    for(var i=0;i<np.length;i++){
      delete np[i].weight;
      s.push(np[i].name);
    }
    return s;
  };
  
  /**
   * 判断序列中是否已存在指定类名
   * @param 序列
   * @param c 类名
   * @returns true/false
   */
  var exists = function(p,c){
    for(var i=0;i<p.length;i++){
      if(p[i] == c) return true;
    }
    return false;
  };
  
  /**
   * 全部加载完成调用
   * @param f 回调方法
   */
  var loadFs = function(f){
    var p = dependAll();
    Q = [];
    var ff = getKlass(p.shift());
    var o = ff.instance;
    if(!o){
      o = (ff.fn)(W,D);
      o['name'] = ff.name;
      ff.instance = o;
      bind(o);
    }
    var cp = o;
    while(p.length>0){
      var cfg = getKlass(p.shift());
      cp = cfg.instance;
      if(!cp){
        cp = (cfg.fn)(W,D);
        cp['name'] = cfg.name;
        cfg.instance = cp;
        bind(cp);
      }
    }
    SYN = false;
    f(cp);
  };
  
  /**
   * 检查依赖
   * @param c 类名
   * @param f 加载完成后调用方法
   */
  var checkDependLoad = function(c,f){
    var p = getKlass(c);//获取类定义
    if(p.need){//如果有依赖，将依赖的类加入到加载序列中
      for(var i=0;i<p.need.length;i++){
        if(!exists(N,p.need[i])){
          N.push(p.need[i]);
        }
      }
    }
    checkLoad(f);//检查类加载结果
  };
  
  /**
   * 检查类加载结果
   * @param f 加载完成后调用方法
   */
  var checkLoad = function(f){
    if(N.length<1){//如果加载序列中无类了，加载完成
      loadFs(f);
      return;
    }
    var c = N.shift();//取出加载序列中的第一个类名
    if(!exists(Q,c)){//装入初始化序列
      Q.push(c);
    }
    if(!isLoaded(c)){//如果类未加载过，则加载它，加载完成后检查依赖
      try{//尝试本地存储加载
        localLoad(c,function(){
          checkDependLoad(c,f);
        });
      }catch(e){//异步加载
        lazyLoad(c,function(){
          checkDependLoad(c,f);
        });
      }
    }else{//如果类已经加载过了，检查依赖
      checkDependLoad(c,f);
    }
  };
  
  /**
   * 正式加载
   * @param c 类名
   * @param f 加载完成后调用方法
   */
  var load = function(c,f){
    SYN = true;//锁定
    if(Object.prototype.toString.apply(c) === '[object Array]'){//将数组装入加载序列
      for(var i=0;i<c.length;i++){
        N.push(c[i]);
      }
    }else{//将单个类装入加载序列
      N.push(c);
    }
    checkLoad(f);//检查加载结果
  };
  
  /**
   * 预加载
   * @param s 类名
   * @param f 加载完成后调用方法
   */
  var preLoad = function(s,f){
    if(SYN){//如果处于锁定状态，延迟再尝试预加载
      W.setTimeout(function(){
        preLoad(s,f);
      },1);
    }else{//如果未锁定，正式加载
      load(s,f);
    }
  };
  
  /**
   * 初始化完成后执行指定方法
   * @param f 要执行的方法
   */
  var ready = function(f){
    preLoad(['lang.Core','lang.System','lang.Store'],function(o){
      f.call(f,o);
    });
  };
  
  // 初始化根目录
  dir([]);
  
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
  
  var _in = function(arr,s){
    for(var i=0;i<arr.length;i++){
      if(arr[i]==s){
        return true;
      }
    }
    return false;
  };
  
  var _run = function(func){
    var cs = [];
    var s = func+'';
    var idx = s.indexOf('I.');
    while(idx>-1){
      s = s.substr(idx+2);
      var kh = s.indexOf('(');
      var q = s.substr(0, kh);
      if(q.indexOf('.')>-1){
        var k = q.substr(0, q.lastIndexOf('.'));
        if(k.indexOf('.')>-1){
          if(!_in(cs,k)){
            cs.push(k);
          }
        }
      }
      idx = s.indexOf('I.');
    }
    if(cs.length<1){
      ready(function(){
        func.apply(func);
      });
    }else{
      ready(function(){
        if(cs.length>0){
          load(cs,func);
        }
      });
    }
  };
  
  /**
   * 注册类
   * @param o 类的定义
   * @returns I
   */
  I.regist = function(o){regist(o);return this;};
    
  /**
   * 初始化完成后执行指定方法
   * @param f 初始化完成后要调用的方法
   */
  I.ready = function(f){ready(f);};
    
  /**
   * 获取类的实例
   * @param c 类名或类名数组，可为单值，如'awt.Button'，也可为数组，如['awt.Button','awt.TextArea']
   * @param f 获取到实例后要调用的方法
   * @returns 传入的类名或类名数组
   */
  I.get = function(c,f){ready(function(){load(c,f);});return c;};
    
  /**
   * 获取资源
   * @returns
   */
  I.dir = function(){return dir(arguments);};
  
  /**
   * 不用获取类而直接使用，自动加载依赖类
   * @param func 依赖类加载完成后调用的方法
   */
  I.run = function(func){_run(func);};
})(window,document);

I.regist({
  'name':'lang.Core',
  'need':null,
  'fn':function(W,D){
    var T = ['BREAK','CONTINUE','RETURN'];
    var EACH = {'[object Array]':1,'[object NodeList]':1,'[object HTMLCollection]':1,'[object Arguments]':1};
    var C = [
      function(o,s){o.style.cssText = s;},
      function(o,s){o.setAttribute('style',s);}
    ];
    var S = [
      function(o,s){o.className = s;},
      function(o,s){o.setAttribute('class',s);}
    ];
    var STYLE = [
      function(s){
        var o = D.createStyleSheet();
        o.cssText = s;
      },
      function(s){
        var o = D.createElement("style");
        o.type = "text/css";
        o.innerHTML = s; 
        D.getElementsByTagName("head")[0].appendChild(o);
      }
    ];
    var G = {
      'ID':function(o,s){return o.getElementById(s);},
      'NAME':function(o,s){return o.getElementsByName(s);},
      'TAG':function(o,s){return o.getElementsByTagName(s);},
      'CHILD':function(o){
        var c = [];
        each(o.childNodes,function(m,i){if(1 == m.nodeType) c.push(m);});
        return c;
      },
      'CLASS':function(){
        var a = arguments;
        var c = [];
        for(var i=0;i<a[0].childNodes.length;i++){
          m = a[0].childNodes[i];
          if(m.getAttribute){
            if(a[1] == m.getAttribute('class') || a[1] == m.getAttribute('className')) c.push(m);
            each(a.callee(m,a[1]),function(o,j){
              c.push(o);
            });
          }
        }
        return c;
      }
    };
    var A = {
      'IN':function(a,b){b.appendChild(a);},
      'BEFORE':function(a,b){b.parentNode.insertBefore(a,b);},
      'AFTER':function(a,b){b.parentNode.lastChild == b?this['IN'](a,b.parentNode):this['BEFORE'](a,b.nextSibling);}
    };
  var N = function(d,o,s){
    each(d,function(f,i){
      try{
        f(o,s);
        return T[2];
      }catch(e){}
    });
  };
  var $ = function(o){
    switch(o.length){
      case 1: return typeof o[0] == 'string'?('*'==o[0]?G['CHILD'](D.body):G['ID'](D,o[0])):o[0];
      case 2: return '*'==o[1]?G['CHILD']($([o[0]])):G[o[0].toUpperCase()](D,o[1]);
      case 3: return G[o[1].toUpperCase()]($([o[0]]),o[2]);
    }
    return D;
  };
  var insert = function(o){
    if(o.length<1) return null;
    var m = typeof o[0] == 'string'?D.createElement(o[0]):o[0];
    switch(o.length){
      case 1: A['IN'](m,D.body);break;
      case 2: A['IN'](m,o[1]);break;
      case 3: A[o[1].toUpperCase()](m,o[2]);break;
    }
    return m;
  };
  function region(o){
    var x = 0,y = 0,w = 0,h = 0;
    switch(o.length){
      case 1:
        var r = region([]);
        var m = o[0];
        w = m.offsetWidth;
        h = m.offsetHeight;
        if(m.getBoundingClientRect){
          x = m.getBoundingClientRect().left + r.x - D.documentElement.clientLeft;
          y = m.getBoundingClientRect().top + r.y - D.documentElement.clientTop;
        }else for(;m;x+=m.offsetLeft,y+=m.offsetTop,m=m.offsetParent);
        break;
      default:
        x = Math.max(D.documentElement.scrollLeft,D.body.scrollLeft);
        y = Math.max(D.documentElement.scrollTop,D.body.scrollTop);
        w = D.documentElement.clientWidth;
        h = D.documentElement.clientHeight;
    }
    return {'x':x,'y':y,'width':w,'height':h};
  }
  var listen = function(o,s,f){
    o = I.$(o);
    try{o.attachEvent('on'+s,function(e){f(o,W.event || e);});}catch(e){
      try{o.addEventListener(s,function(e){f(o,W.event || e);},false);}catch(ex){}
    }
  };
  var each = function(o,f){
    var tp = Object.prototype.toString.apply(o);
    if((EACH[tp])||(o.length&&(!o.alert)&&('[object String]'!=tp))){
      a:for(var i=0;i<o.length;i++){
        switch(f(o[i],i)){
          case T[0]:break a;
          case T[1]:continue a;
          case T[2]:return;
        }
      }
    }else{
      f(o,0);
    }
  };
  var opacity = function(o,n){
    try{
      o.style.filter='alpha(opacity='+n+')';
    }catch(e){}
    try{
      o.style.opacity = n/100;
    }catch(e){}
  };
   
    I['BREAK'] = T[0];
    I['CONTINUE'] = T[1];
    I['RETURN'] = T[2];
    I['$'] = function(){return $(arguments);};
    I['region'] = function(){return region(arguments);};
    I['css'] = function(o,s){
      each(o,function(m,i){
        N(C,m,s);
      });
      return I;
    };
    I['cls'] = function(o,s){
      each(o,function(m,i){
        N(S,m,s);
      });
      return I;
    };
    I['trim'] = function(s){return s.replace(/(^\s*)|(\s*$)/g,'');};
    I['insert'] = function(){return insert(arguments);};
    I['listen'] = function(o,s,f){
      each(o,function(m,i){listen(m,s,f);});
      return f;
    };
    I['each'] = function(o,f){each(o,f);return I;};
    I['opacity'] = function(o,n){opacity(o,n);return I;};
    I['style'] = function(s){
      N(STYLE,s);
      return I;
    };
    return {
      'version':'1.0.1'
    };
  }
});

I.regist({
  'name':'lang.Store',
  'need':['lang.Core'],
  'fn':function(W,D){
    var host = location.hostname;
    var LS = {
        'loaded':false,
        'instance':null,
        'isSupport':function(){
          if(!this.loaded){
            this.instance = W.localStorage;
            this.loaded = true;
          }
          return this.instance?true:false;
        },
        'setItem':function(k,v){
          this.instance.setItem(k,v);
        },
        'getItem':function(k){
          return this.instance.getItem(k);
        },
        'clear':function(){
         this.instance.clear();
        }
    };
    var UD = {
        'loaded':false,
        'instance':null,
        'isSupport':function(){
          if(!this.loaded){
            try{
              var o = D.createElement('input');
              o.type = 'hidden';
              o.style.display = 'none';
              o.addBehavior ("#default#userData");
              D.body.appendChild(o);
              var exp = new Date();
              exp.setDate(exp.getDate()+365);
              o.expires = exp.toUTCString();
              this.instance = o;
            }catch(e){}
            this.loaded = true;
          }
          return this.instance?true:false;
        },
        'setItem':function(k,v){
          this.instance.load(host);
          this.instance.setAttribute(k,v);
          this.instance.save(host);
        },
        'getItem':function(k){
          this.instance.load(host);
          return this.instance.getAttribute(k);
        },
        'clear':function(){
          var exp = new Date();
          exp.setDate(exp.getDate()-2);
          this.instance.expires = exp.toUTCString();
          exp = new Date();
          exp.setDate(exp.getDate()+365);
          this.instance.expires = exp.toUTCString();
        }
    };
    
    var LIBS = [LS,UD];
    
    var set = function(k,v){
      I.each(LIBS,function(m,i){
        if(m.isSupport()){
          m.setItem(k,v);
          return I.BREAK;
        }
      });
    };
    var get = function(k){
      var v = null;
      I.each(LIBS,function(m,i){
        if(m.isSupport()){
          v = m.getItem(k);
          return I.BREAK;
        }
      });
      return v;
    };
    var clear = function(){
      I.each(LIBS,function(m,i){
        if(m.isSupport()){
          m.clear();
          return I.BREAK;
        }
      });
    };
    return {
      'version':'1.0.0',
      'set':function(k,v){set(k,v);},
      'get':function(k){return get(k);},
      'clear':function(){clear();}
    };
  }
});

I.regist({
  'name':'lang.System',
  'need':['lang.Core'],
  'fn':function(W,D){
    var container = null;
    var layer = null;
    var titleBar = null;
    var width = 400;
    var height = 250;
    var print = function(s,ln){
      if(!layer){
        var r = I.region();
        var o = I.insert('div');
        I.css(o,'position:absolute;left:'+(r.x+r.width-width)+'px;top:'+(r.y+r.height-height)+'px;width:'+width+'px;height:'+height+'px;overflow:hidden;background-color:#000;margin:0;padding:0');
        container = o;
        o = I.insert('div',o);
        I.css(o,'position:absolute;left:1px;top:1px;width:'+(width-2)+'px;height:24px;background-color:#EEE;color:#000;margin:0;padding:0;font-size:9pt;line-height:24px;text-indent:4px;text-align:left');
        o.innerHTML = '输出';
        titleBar = o;
        o = I.insert('div',o);
        I.css(o,'position:absolute;left:'+(width-24)+'px;width:21px;height:22px;text-align:center;line-height:22px;text-indent:0;overflow:hidden;top:1px;cursor:pointer');
        o.innerHTML = '×';
        I.listen(o,'click',function(m,e){
          layer.innerHTML = '';
          container.style.visibility = 'hidden';
        });
        o = I.insert('div',container);
        I.css(o,'position:absolute;left:3px;top:27px;width:'+(width-6)+'px;height:'+(height-30)+'px;overflow:auto;color:#FFF;margin:0;padding:0;font-size:9pt;line-height:18px;font-family:宋体,Simsun;white-space:pre;text-align:left');
        layer = o;
      }else{
        container.style.visibility = 'visible';
      }
      var sp = I.insert('span',layer);
      sp.innerHTML = s;
      sp.scrollIntoView(true);
      if(ln){
        I.insert('span',layer).innerHTML = '<br />';
      }
      try{
        I.util.Drager.drag(titleBar,container);
      }catch(e){}
    };
    return {
      'version':'1.0.0',
      'println':function(s){
        if(I.enableConsole){
          print(s,true);
        }
      },
      'print':function(s){
        if(I.enableConsole){
          print(s,false);
        }
      }
    };
  }
});