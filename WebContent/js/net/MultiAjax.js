I.regist('net.MultiAjax',function(W,D){
  var M = {
    GET:0,
    POST:1,
    GET_FIND:2,
    POST_FIND:3
  };
  var G = false;
  var A = [];
  var Q = {
    L:[],
    add:function(o){
      this.L.push(o);
    },
    hasNext:function(){
      return this.L.length>0?true:false;
    },
    next:function(){
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
        case M.GET:
          I.net.Ajax.get(m.url+'?'+m.args.join('&'),function(o){
            m.func(o);
            if(Q.hasNext()){
              W.setTimeout(inst,1);
            }else{
              G = false;
            }
          });
          break;
        case M.POST:
          I.net.Ajax.post(m.url,m.args.join('&'),function(o){
            m.func(o);
            if(Q.hasNext()){
              W.setTimeout(inst,1);
            }else{
              G = false;
            }
          });
          break;
        case M.GET_FIND:
            I.net.Ajax.getFind(m.url+'?'+m.args.join('&'),function(o){
              m.func(o);
              if(Q.hasNext()){
                W.setTimeout(inst,1);
              }else{
                G = false;
              }
            });
        break;
        case M.POST_FIND:
          I.net.Ajax.postFind(m.url,m.args.join('&'),function(o){
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
  var _call = function(u,f,k){
    var o = {
      url:u,
      func:f,
      args:A.slice(0),
      method:k
    };
    A.length = 0;
    Q.add(o);
    if(!G){
      ajax();
    }
  };
  return {
    set:function(k,v){set(k,v);},
    get:function(u,f){return _call(u,f,0);},
    post:function(u,f){return _call(u,f,1);},
    getFind:function(u,f){return _call(u,f,2);},
    postFind:function(u,f){return _call(u,f,3);}
  };
}+'');