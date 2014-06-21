/**
 * I.net.Ajax
 * <i>AJAX封装</i>
 */
I.regist('net.Ajax',function(W,D){
  var Q = [
    function(){return new XMLHttpRequest();},
    function(){return new ActiveXObject("Msxml2.XMLHTTP");},
    function(){return new ActiveXObject("Microsoft.XMLHTTP");}
  ];
  var _ = function(){
    for(var i=0;i<Q.length;i++){
      try{
        return Q[i]();
      }catch(e){}
    }
    return null;
  };
  var _post = function(find,url,param,callback){
    var r = _();
    r.open('POST',url,true);
    r.onreadystatechange = function(){
      if(4 == r.readyState){
        if(200 == r.status||0==r.status||404==r.status){
          var rr = {'xhr':r,'status':r.status};
          if(callback){
            callback.call(callback,rr);
          }
        }
      }
    };
    r.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    if(find){
      r.setRequestHeader('nlf-requested-with', 'NlfHttpRequest');
    }else{
      r.setRequestHeader('x-requested-with', 'XMLHttpRequest');
    }
    r.send(param);
  };
  var _get = function(find,url,callback){
    var r = _();
    r.open('GET',url,true);
    r.onreadystatechange = function(){
      if(4 == r.readyState){
        if(200 == r.status||0==r.status||404==r.status){
          var rr = {'xhr':r,'status':r.status};
          if(callback){
            callback.call(callback,rr);
          }
        }
      }
    };
    r.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    if(find){
      r.setRequestHeader('nlf-requested-with', 'NlfHttpRequest');
    }else{
      r.setRequestHeader('x-requested-with', 'XMLHttpRequest');
    }
    r.send();
  };
  return {
    encode:function(s){return encodeURIComponent(encodeURIComponent(s));},
    json:function(s){return eval('('+s+')');},
    post:function(url,param,callback){_post(false,url,param,callback);},
    get:function(url,callback){_get(false,url,callback);},
    postFind:function(url,param,callback){_post(true,url,param,callback);},
    getFind:function(url,callback){_get(true,url,callback);}
  };
}+'');