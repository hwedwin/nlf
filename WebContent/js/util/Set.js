I.regist({
  'name':'util.Set',
  'need':['util.Collection'],
  'fn':function(W,D){
    var create = function(){
      var cl = I.util.Collection.create();
      
      var clAdd = cl.add;
      
      var add = function(o){
        if(cl.contains(o)) return false;
        clAdd(o);
        return true;
      };
      
      var addAll = function(c){
        var b = false;
        for(var i=0;i<c.length;i++){
          if(add(c[i])){
            b = true;
          }
        }
        return b;
      };
      cl['add'] = function(o){add(o);};
      cl['addAll'] = function(o){addAll(o);};
      return cl;
    };
    
    return {
      'create':function(){return create();}
    };
  }
});