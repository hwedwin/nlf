I.regist({
  'name':'util.MapEntry',
  'need':null,
  'fn':function(W,D){
    var create = function(a,b){
      var k = a;
      var v = b;
      
      var equals = function(o){
        return (k==o.getKey())&&(v==o.getValue());
      };
      
      var getKey = function(){
        return k;
      };
      
      var getValue = function(){
        return v;
      };
      
      var setValue = function(o){
        v = o;
      };
      var obj = {
        'equals':function(o){return equals(o);},
        'getKey':function(){return getKey();},
        'getValue':function(){return getValue();},
        'setValue':function(o){setValue(o);}
      };
      return obj;
    };
    
    return {
      'create':function(k,v){return create(k,v);}
    };
  }
});