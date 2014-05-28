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