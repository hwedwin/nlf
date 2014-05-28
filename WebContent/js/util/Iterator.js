I.regist({
  'name':'util.Iterator',
  'need':null,
  'fn':function(W,D){
    var create = function(l){
      var it = l;
      
      var hasNext = function(){
        return it.length>0;
      };
      
      var next = function(){
        return it.shift();
      };
      
      var remove = function(){
        it.shift();
      };
      
      var obj = {
        'hasNext':function(){return hasNext();},
        'next':function(){return next();},
        'remove':function(){remove();}
      };
      return obj;
    };
    
    return {
      'create':function(o){return create(o);}
    };
  }
});