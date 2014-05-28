I.regist({
  'name':'net.MultiAjax',
  'need':['net.Ajax'],
  'fn':function(W,D){
    var G = false;
    var A = [];
    var Q = {
      'L':[],
      'add':function(o){
        this.L.push(o);
      },
      'hasNext':function(){
        return this.L.length>0?true:false;
      },
      'next':function(){
        return this.L.shift();
      }
    };
    var set = function(k,v){
      A.push(k+'='+I.net.Ajax.encode(v));
    };
    var ajax = function(){
      var inst = arguments.callee;
      G = true;
      if(Q.hasNext()){
        var m = Q.next();
        m.args.push('_timestamp='+new Date().getTime()+'');
        switch(m.method){
          case 1:
            I.net.Ajax.post(m.url,m.args.join('&'),function(o){
              m.func(o);
              if(Q.hasNext()){
                W.setTimeout(inst,1);
              }else{
                G = false;
              }
            });
          break;
          case 0:
            I.net.Ajax.get(m.url+'?'+m.args.join('&'),function(o){
              m.func(o);
              if(Q.hasNext()){
                W.setTimeout(inst,1);
              }else{
                G = false;
              }
            });
          break;
          case 3:
            I.net.Ajax.pfind(m.url,m.args.join('&'),function(o){
              m.func(o);
              if(Q.hasNext()){
                W.setTimeout(inst,1);
              }else{
                G = false;
              }
            });
          break;
          case 2:
            I.net.Ajax.gfind(m.url+'?'+m.args.join('&'),function(o){
              m.func(o);
              if(Q.hasNext()){
                W.setTimeout(inst,1);
              }else{
                G = false;
              }
            });
          break;
        }
      }
    };
    var call = function(u,f,k){
      var o = {
        'url':u,
        'func':f,
        'args':A.slice(0),
        'method':k
      };
      A.length = 0;
      Q.add(o);
      if(!G){
        ajax();
      }
    };
    return {
      'version':'1.1.0',
      'set':function(k,v){set(k,v);},
      'post':function(u,f){return call(u,f,1);},
      'get':function(u,f){return call(u,f,0);},
      'pfind':function(u,f){return call(u,f,3);},
      'gfind':function(u,f){return call(u,f,2);}
    };
  }
});