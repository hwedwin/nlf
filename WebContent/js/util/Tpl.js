/**
 * 过时的模板引擎，功能过于简陋，不建议使用
 */
I.regist({
  'name':'util.Tpl',
  'need':['util.Render'],
  'fn':function(W,D){
    var ST = '\\$\\(';
    var ET = '\\)\\$';
    var TAG = 'TPL_';
    var loadTpl = function(n){
      var o = I.$(TAG+n);
      if(o){
        return o.value;
      }else{
        return null;
      }
    };
    
    var rend = function(data,tpl,dst,rule){
      try{
        if(!data) return;
        var t = loadTpl(tpl);
        if(!t) return;
        var s = '';
        if(Object.prototype.toString.apply(data) === '[object Array]'){
          I.each(data,function(m,i){
            var ts = t;
            if(rule){
              for(var i in m){
                m[i] = rule(i,m[i],m);
              }
            }
            for(var i in m){
              ts = ts.replace(new RegExp(ST+i+ET,'g'),null==m[i]?'':m[i]);
            }
            s += ts;
          });
        }else{
          s = t;
          if(rule){
            for(var i in data){
              data[i] = rule(i,data[i],data);
            }
          }
          for(var i in data){
            s = s.replace(new RegExp(ST+i+ET,'g'),null==data[i]?'':data[i]);
          }
        }
        I.util.Render.write(dst,s);
      }catch(e){}
      return I.$(dst);
    };
    
    return {
      'rend':function(data,tpl,dst,rule){return rend(data,tpl,dst,rule);}
    };
  }
});