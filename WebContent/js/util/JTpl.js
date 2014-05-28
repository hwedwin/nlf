/**
 * 模板引擎
 */
I.regist({
  'name':'util.JTpl',
  'need':['util.Render','util.Reader'],
  'fn':function(W,D){
    //配置
    var CFG = {
      'prefix':['{','$'],
      'suffix':['$','}'],
      'eq':'=',
      'tag':'TPL_'
    };
    //缓存
    var POOL = {};
    //字符读取器
    function StringReader(os){
      //模板字符串
      var r = os;
      //已读取字符数
      var readed = 0;
      //总字符数
      var total = r.length;
      /**
       * 是否还有下一个字符
       * @return true/false
       */
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
        switch(s){
        case '\r':
        case '\n':s = ' ';break;
        }
        return s;
      };
      /**
       * 预先检查后面的字符
       * @param n 检查几个字符
       * @return 预读出的字符串
       */
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
      /**
       * 跳过后面的字符
       * @param n 要跳过的字符数
       */
      var skip = function(n){
        for(var i=0;i<n;i++){
          if(hasNext()){
            next();
          }else{
            break;
          }
        }
      };
      /**
       * 一直读取，直到遇到指定的字符数组中的任意字符
       * @param ss 指定的字符数组
       * @return 读取到的字符串，不包括遇到的那个符合条件的字符
       */
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
        'hasNext':function(){return hasNext();},
        'next':function(){return next();},
        'testNext':function(n){return testNext(n);},
        'skip':function(n){skip(n);},
        'readUntil':function(){return readUntil(arguments);}
      };
    }
    /**
     * 模板编译
     * @param os 模板字符串
     */
    var compile = function(os){
      var prefix = CFG.prefix.join('');
      var suffix = CFG.suffix.join('');
      //先从缓存里找找看
      if(undefined != POOL[os]){
        return POOL[os];
      }
      //初始化字符读取器
      var r = new StringReader(os);
      // 编译段缓存
      var q = [];
      /**
       * 将缓存段整合为结果函数
       * @return Function 函数
       */
      var getQ = function(){
        q.unshift('var _s = [];');
        q.push('return _s.join(\'\');');
        return new Function('data',q.join(''));
      };
      /**
       * 普通html代码压入缓存
       * @param s 普通html代码
       */
      var addQ = function(s){
        s = s.replace(/\'/g,'\\\'');
        q.push('_s.push(\''+s+'\');');
      };
      /**
       * JavaScript代码压入缓存
       * @param s js代码
       */
      var addJs = function(s){
        q.push(s);
      };
      /**
       * js变量压入缓存
       * @param v js变量
       */
      var addVar = function(v){
        q.push('_s.push('+v+');');
      };
      /**
       * 逐字符循环编译
       */
      var loop = function(){
        //读取普通html代码段
        var p = r.readUntil(CFG.prefix[0]);
        //普通html代码压入缓存
        addQ(p);
        if(!r.hasNext()){
          return;
        }
        //如果是js代码段的开头
        if(prefix==r.testNext(2)){
          //跳过起始字符
          r.skip(2);
          //是否是变量
          var isV = false;
          //如果是变量
          if(CFG.eq==r.testNext(1)){
            isV = true;
            //跳过赋值符号
            r.skip(1);
          }
          //js代码段缓存
          var tp=[];
          //循环检查是否到了结束符
          while(suffix!=r.testNext(2)&&r.hasNext()){
            p = r.readUntil(CFG.suffix[0]);
            //把前面读取的部分压入缓存
            tp.push(p);
            //如果是结束符，退出循环
            if(suffix==r.testNext(2)){
              r.skip(2);
              break;
            }else{//如果不是结束符，把这个字符压入缓存，继续
              tp.push(r.next());
            }
          }
          if(isV){
            addVar(tp.join(''));
          }else{
            addJs(tp.join(''));
          }
          //继续遍历
          if(r.hasNext()){
            loop();
          }
        }else{//如果不是js代码段
          //按普通html代码压入缓存
          addQ(r.next());
          //继续遍历
          loop();
        }
      };
      //遍历
      loop();
      //缓存结果
      POOL[os] = getQ();
      return POOL[os];
    };
    /**
     * 模板转换
     * @param data JSON格式数据对象
     * @param tpl 模板字符串
     * @return 转换后的字符串
     */
    var cast = function(data,tpl){
      var func = compile(tpl);
      return func.call(func,data);
    };
    
    /**
     * 模板渲染
     * @param data JSON格式数据对象
     * @param tplName 模板名称
     * @param tgt 渲染到的目标DOM或其ID
     */
    var rend = function(data,tplName,tgt){
      var id = CFG.tag+tplName;
      var tpl = I.util.Reader.from(id)[id];
      var rs = cast(data,tpl);
      I.util.Render.write(tgt,rs);
    };
    
    return {
      'compile':function(tplStr){return compile(tplStr);},
      'cast':function(data,tplStr){return cast(data,tplStr);},
      'rend':function(data,tplName,tgt){return rend(data,tplName,tgt);}
    };
  }
});