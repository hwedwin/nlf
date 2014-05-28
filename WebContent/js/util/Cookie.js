I.regist({
  'name':'util.Cookie',
  'need':null,
  'fn':function(W,D){
    var set = function(o){
      switch(o.length){
        case 2:
          D.cookie = o[0]+'='+escape(o[1]);
        break;
        case 3:
          var d = new Date();
          d.setTime(d.getTime()+o[2]*24*60*60*1000);
          D.cookie = o[0]+'='+escape(o[1])+"; expires="+d.toGMTString();
        break;
      }
    };
    var get = function(o){
      switch(o.length){
        case 0: return D.cookie;
        case 1:
          try{
            var s = D.cookie.split('; ');
            for(var i=0;i<s.length;i++){
              var n = s[i].split('=');
              if(n[0] == o[0]) return unescape(n[1]);
            }
          }catch(e){}
          return null;
      }
    };
    var remove = function(o){
      switch(o.length){
        case 1: set([o,'',-1]);break;
      }
    };
    
    return {
      'set':function(){set(arguments);},
      'get':function(){return get(arguments);},
      'remove':function(){remove(arguments);}
    };
  }
});