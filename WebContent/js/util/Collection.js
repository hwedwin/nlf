I.regist({
  'name':'util.Collection',
  'need':['util.Iterator'],
  'fn':function(W,D){
    var create = function(){
      var l = [];
      
      var add = function(o){
        l.push(o);
        return true;
      };
      
      var addAll = function(c){
        l.concat(c);
        return true;
      };
      
      var clear = function(){
        l = [];
      };
      
      var contains = function(o){
        for(var i=0;i<l.length;i++){
          if(l[i] == o){
            return true;
          }
        }
        return false;
      };
      
      var containsAll = function(o){
        for(var i=0;i<o.length;i++){
          if(!contains(o[i])) return false;
        }
        return true;
      };
      
      var equals = function(o){
         return (l.length==o.length)&&(containsAll(o));
      };
      
      var isEmpty = function(){
        return l.length<1;
      };
      
      var iterator = function(){
        var it = I.util.Iterator.create(l);
        return it;
      };
      
      var remove = function(o){
        var nl = [];
        for(var i=0;i<l.length;i++){
          if(l[i]!=o){
            nl.push(o);
          }
        }
        l = nl;
        return true;
      };
      
      var removeAll = function(o){
        for(var i=0;i<o.length;i++){
          remove(o[i]);
        }
        return true;
      };
      
      var retainAll = function(o){
        return true;
      };
      
      var size = function(){
        return l.length;
      };
      
      var toArray = function(){
        return l;
      };
      
      var obj = {
        'add':function(o){return add(o);},
        'addAll':function(o){return addAll(o);},
        'clear':function(){clear();},
        'contains':function(o){return contains(o);},
        'containsAll':function(o){return containsAll(o);},
        'equals':function(o){return equals(o);},
        'isEmpty':function(){return isEmpty();},
        'iterator':function(){return iterator();},
        'remove':function(o){return remove(o);},
        'removeAll':function(){return removeAll();},
        'retainAll':function(){return retainAll();},
        'size':function(){return size();},
        'toArray':function(){return toArray();}
      };
      return obj;
    };
    
    return {
      'create':function(){return create();}
    };
  }
});