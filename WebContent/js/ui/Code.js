/**
 * I.ui.Code
 * <i>代码着色</i>
 */
I.regist('ui.Code',function(W,D){
  var CFG = {
    skin:'Default',
    border:'1px solid #DDD'
  };
  var _TAG = {
    keyword:[
     'var','function','try','catch','throw','if','else','return','new','break','switch','case','for','while','delete','do','typeof','undefined','in','this','void','continue','true','false','instanceof','finally','with','null',
     'abstract','boolean','byte','char','class','default','double','extends','final','float','implements','import','int','interface','long','native','package','private','protected','public','short','static','super','synchronized','throws','transient','volatile'
    ]
  };
  var TAG = {
    keyword:{}
  };
  for(var i in _TAG){
    var o = _TAG[i];
    for(var j=0;j<o.length;j++){
      TAG[i][o[j]] = true;
    }
  }
  function StringReader(os){
    //模板字符串
    var r = os;
    //已读取字符数
    var readed = 0;
    //总字符数
    var total = r.length;

    var hasNext = function(){
      return readed<total;
    };

    /**
     * 读取下一个字符，回车换行符被替换为空格
     * @return 下一个字符
     */
    var next = function(){
      var s = r.substring(readed,readed+1);
      readed++;
      return s;
    };

    var testNext = function(n){
      var q = [];
      var i=0;
      while(hasNext()&&i<n){
        q.push(next());
        i++;
      }
      readed-=i;
      return q.join('');
    };

    var skip = function(n){
      for(var i=0;i<n;i++){
        if(hasNext()){
          next();
        }else{
          break;
        }
      }
    };

    var readUntil = function(ss){
      var q = [];
      while(hasNext()){
        var s = testNext(1);
        for(var i=0;i<ss.length;i++){
          if(s==ss[i]){
            return q.join('');
          }
        }
        q.push(s);
        skip(1);
      }
      return q.join('');
    };

    return {
      /**
       * 是否还有下一个字符
       * @return true/false
       */
      hasNext:function(){return hasNext();},
      /**
       * 读取下一个字符
       * @return 下一个字符
       */
      next:function(){return next();},
      /**
       * 预先检查后面的字符
       * @param n 检查几个字符
       * @return 预读出的字符串
       */
      testNext:function(n){return testNext(n);},
      /**
       * 跳过后面的字符
       * @param n 要跳过的字符数
       */
      skip:function(n){skip(n);},
      /**
       * 一直读取，直到遇到指定的字符数组中的任意字符
       * @param ss 指定的字符数组
       * @return 读取到的字符串，不包括遇到的那个符合条件的字符
       */
      readUntil:function(){return readUntil(arguments);}
    };
  }
  var _renderCode = function(obj){
    var cfg = obj.config;
    obj.dom.style.border = cfg.border;
    if(cfg.height){
      obj.dom.style.height = cfg.height+'px';
    }else{
      obj.dom.style.overflow = 'hidden';
    }
    var s = obj.proxyDom.innerText||obj.proxyDom.textContent;
    s = s.replace(/\r/g,'\n');
    s = s.replace(/\n\n/g,'\n');
    var ns = s.split('\n');
    var comment = 0;
    for(var i=0,k=ns.length;i<k;i++){
      var rs = ns[i];
      rs = rs.replace('\t','  ');
      if(i==k-1){
        if(I.trim(rs).length<1){
          break;
        }
      }
      var li = I.insert('li',obj.dom);
      var span = I.insert('span',li);
      var q = [];
      var reader = new StringReader(rs);
      while(reader.hasNext()){
        var c = reader.readUntil(' ','=','(','{',')','}',';',':');
        if(c.length>1&&c.indexOf('*/')==c.length-2){
          comment--;
          c = '<span class="comment">'+c+'</span>';
          q.push(c);
          q.push(reader.next());
        }else if(comment>0){
          c = '<span class="comment">'+c+'</span>';
          q.push(c);
          q.push(reader.next());
        }else if(c.indexOf('//')==0){
          c += reader.next();
          c += reader.readUntil('');
          c = '<span class="comment">'+c+'</span>';
          q.push(c);
          break;
        }else if(c.indexOf('/*')==0){
          comment++;
          c = '<span class="comment">'+c+'</span>';
          q.push(c);
          q.push(reader.next());
        }else{
          for(var j in TAG){
            if(TAG[j][c]){
              c = '<span class="'+j+'">'+c+'</span>';
            }
          }
          q.push(c);
          q.push(reader.next());
        }
      }
      span.innerHTML = q.join('');
    }
    obj.proxyDom.style.display = 'none';
  };
  var _render = function(dom,config){
    dom = I.$(dom);
    var ol = I.insert('ol','after',dom);
    var obj = {
      dom:ol,
      proxyDom:dom,
      className:null,
      config:null
    };
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    obj.className = 'i-ui-Code-'+cfg.skin;
    I.util.Skin.init(cfg.skin);
    I.cls(obj.dom,obj.className);
    _renderCode(obj);
    return obj;
  };
  var _autoRender = function(config){
    var doms = I.$(config?(config.dom?config.dom:D.body):D.body,'class','i-ui-Code-NeedRender');
    if(!doms){
      return;
    }
    for(var i=0;i<doms.length;i++){
      _render(doms[i],config);
    }
  };
  return {
    render:function(dom,cfg){return _render(dom,cfg);},
    autoRender:function(cfg){_autoRender(cfg);}
  };
}+'');