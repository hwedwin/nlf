/**
 * I.net.Page
 * <i>AJAX请求页面</i>
 */
I.regist('net.Page',function(W,D){
  var _loadJs = function(url,callback){
    var o = D.createElement('script');
    o.type = 'text/javascript';
    o.src = url;
    o.onload = function(){
      try{
        this.parentNode.removeChild(this);
      }catch(e){
        return;
      }
      callback.call(callback,url);
    };
    o.onreadystatechange = function(){
      if(/loaded|complete/.test(this.readyState)){
        try{
          this.parentNode.removeChild(this);
        }catch(e){
          return;
        }
        callback.call(callback,url);
      }
    };
    D.getElementsByTagName('head')[0].appendChild(o);
  };
  var _loadCss = function(url){
    var o = D.createElement('link');
    o.type = 'text/css';
    o.rel = 'stylesheet';
    o.href = url;
    D.getElementsByTagName('head')[0].appendChild(o);
  };
  var _createCss = function(css){
    if(I.trim(css).length<1) return;
    var o = D.createElement("div");
    o.innerHTML = '<b></b><style>'+css+'</style>';
    D.getElementsByTagName("head")[0].appendChild(o.lastChild);
  };
  var _loadJsByQueue = function(queue,callback){
    if(queue.length<1){
      callback.call(callback);
    }else{
      _loadJs(queue.shift(),function(){
        _loadJsByQueue(queue,callback);
      });
    }
  };

  var _parseStyle = function(o,html){
    var s = html;
    var r = /<style[^>]*>/ig.exec(s);
    while(null!=r){
      var tag = r[0]+'';
      s = s.substr(r.index+tag.length);
      var endIndex  = s.indexOf('</'+'style>');
      o.innerStyle.push(s.substr(0,endIndex));
      s = s.substr(endIndex+8);
      r = /<style[^>]*>/ig.exec(s);
    }
    s = html;
    r = /<link[^>]*>/ig.exec(s);
    while(null!=r){
      var tag = r[0]+'';
      if(tag.indexOf('stylesheet')>-1){
        var src = tag.match(/href=["|'][^"|']*["|']/ig);
        if(null!=src){
          src = src[0].match(/["|'][^"|']*["|']/ig)[0];
          src = src.substr(1,src.length-2);
          o.outerStyle.push(src);
        }
      }
      s = s.substr(r.index+tag.length);
      r = /<link[^>]*>/ig.exec(s);
    }
  };
  var _parseScript = function(o,html){
    var s = html;
    var r = /<script[^>]*>/ig.exec(s);
    while(null!=r){
      var tag = r[0]+'';
      s = s.substr(r.index+tag.length);
      var endIndex  = s.indexOf('</'+'script>');
      var src = tag.match(/src=["|'][^"|']*["|']/ig);
      if(null!=src){
        src = ((src[0]+'').match(/["|'][^"|']*["|']/ig)[0])+'';
        o.outerScript.push(src.substr(1,src.length-2));
      }else{
        o.innerScript.push(s.substr(0,endIndex));
      }
      s = s.substr(endIndex+9);
      r = /<script[^>]*>/ig.exec(s);
    }
  };
  var _parseHtml = function(o,html){
    var s = html;
    var r = /<script[^>]*>/ig.exec(s);
    while(null!=r){
      var tag = r[0]+'';
      var left = s.substr(0,r.index);
      o.html.push(left);

      var textareaStart = left.lastIndexOf('<textarea');
      var textareaEnd = left.lastIndexOf('</textarea>');
      if(textareaStart>textareaEnd){
        s = s.substr(r.index);
        textareaEnd = s.indexOf('</textarea>');
        o.html.push(s.substr(0,textareaEnd+11));
        s = s.substr(textareaEnd+11);
        r = /<script[^>]*>/ig.exec(s);
        continue;
      }

      s = s.substr(r.index+tag.length);
      var endIndex  = s.indexOf('</'+'script>');
      var src = tag.match(/src=["|'][^"|']*["|']/ig);
      if(null!=src){
        src = ((src[0]+'').match(/["|'][^"|']*["|']/ig)[0])+'';
        o.outerScript.push(src.substr(1,src.length-2));
      }else{
        o.innerScript.push(s.substr(0,endIndex));
      }
      s = s.substr(endIndex+9);
      r = /<script[^>]*>/ig.exec(s);
    }
    o.html.push(s);
  };
  var _parseTag = function(html,tag){
    var s = html;
    var index = s.indexOf('<'+tag+'>');
    if(index<0){
      if('body'==tag){
        return s;
      }
      return null;
    }
    s = s.substr(index+2+tag.length);
    index = s.indexOf('</'+tag+'>');
    if(index<0){
      return s;
    }
    return s.substr(0,index);
  };
  var _parseHead = function(html){
    return _parseTag(html,'head');
  };
  var _parseBody = function(html){
    return _parseTag(html,'body');
  };
  var _parse = function(html){
    var o = {
      outerStyle:[],
      innerStyle:[],
      outerScript:[],
      innerScript:[],
      html:[]
    };
    var head = _parseHead(html);
    _parseStyle(o,head);
    _parseScript(o,head);
    var body = _parseBody(html);
    _parseHtml(o,body);
    return o;
  };
  var _html = function(html,dom,callback){
    var o = _parse(html);
    for(var i=0;i<o.outerStyle.length;i++){
      _loadCss(o.outerStyle[i]);
    }
    _createCss(o.innerStyle.join(''));
    var target = dom?dom:D.body;
    target.innerHTML = '<b>&nbsp;</b>'+o.html.join('');
    target.removeChild(target.firstChild);
    _loadJsByQueue(o.outerScript,function(){
      var script = D.createElement('script');
      script.text = o.innerScript.join('');
      D.getElementsByTagName('head')[0].appendChild(script);
      script.parentNode.removeChild(script);
      callback.call(callback);
    });
  };
  var FIND = {
    finding:false,
    pool:[],
    load:function(u,args,tgt){
      var ntgt = tgt;
      if(!ntgt){
        try{
          ntgt = I.$(FIND_CONTAINER);
        }catch(e){
          ntgt = D.body;
        }
      }
      var instance = this;
      instance.finding = true;
      if(args){
        for(var i in args){
          I.net.Rmi.set(i,args[i]);
        }
      }
      I.net.Rmi.find(u,function(r){
        instance.finding = false;
        _html(r,ntgt,function(){
          instance.loadNext();
        });
      },function(){
        instance.finding = false;
        instance.loadNext();
      });
    },
    loadNext:function(){
      if(this.pool.length>0){
        var qq = this.pool.shift();
        this.load(qq.u, qq.args, qq.tgt);
      }
    }
  };
  var _find = function(url,args,tgt){
    var uuid = I.util.UUID.next();
    if(!I['_find_mapper']){
      I['_find_mapper'] = {};
    }
    I['_find_mapper'][uuid]=tgt;
    var arg = {};
    arg['_find_uuid']=uuid;
    if(args){
      for(var i in args){
        arg[i]=args[i];
      }
    }
    FIND.pool.push({'u':url,'args':arg,'tgt':tgt});
    if(FIND.finding){
      return;
    }
    FIND.loadNext();
  };
  return {
    find:function(url,args,tgt){_find(url,args,tgt);}
  };
}+'');