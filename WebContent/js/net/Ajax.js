I.regist({
  'name':'net.Ajax',
  'need':null,
  'fn':function(W,D){
  var T = ['GET','POST','HEAD'];
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
  var hand = function(t,o){
    switch(t){
      case 1:return eval('('+o+')');
    }
    return encodeURIComponent(encodeURIComponent(o));
  };
  var query = function(t,o,xWith){
    var r = _();
    r.open(T[t],o[0],t==1?(o.length>3?o[3]:true):(o.length>2?o[2]:true));
    r.onreadystatechange = function(){
      if(4 == r.readyState){
        if(200 == r.status||0==r.status){
          var rr = {'xhr':r,'status':200};
          t == 1?o[2](rr):o[1](rr);
          delete(r);
        }else if(404==r.status){
          var rr = {'xhr':r,'status':404};
          t == 1?o[2](rr):o[1](rr);
          delete(r);
        }
      }
    };
    r.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    if(xWith){
      r.setRequestHeader('x-requested-with', 'XMLHttpRequest');
    }else{
      r.setRequestHeader('nlf-requested-with', 'NlfHttpRequest');
    }
    r.send(1==t?o[1]:null);
    
    // 无聊，兼容FF
    if(0==t){
      if(navigator.userAgent.indexOf('Firefox')>-1){
        if(4 == r.readyState){
          if(200 == r.status||0==r.status){
            var rr = {'xhr':r,'status':200};
            t == 1?o[2](rr):o[1](rr);
            delete(r);
          }else if(404==r.status){
            var rr = {'xhr':r,'status':404};
            t == 1?o[2](rr):o[1](rr);
            delete(r);
          }
        }
      }
    }
  };
  return {
    'version':'3.1',
    'encode':function(o){return hand(0,o);},
    'json':function(o){return hand(1,o);},
    //url,params,f,asynch
    'post':function(){query(1,arguments,true);},
    //url,f,asynch
    'get':function(){query(0,arguments,true);},
    'pfind':function(){query(1,arguments,false);},
    'gfind':function(){query(0,arguments,false);}
  };
}});