I.regist({
  'name':'awt.Component',
  'need':['util.Skin'],
  'fn':function(W,D){
    var create = function(){
      var obj = {};
      obj['getInstance'] = function(){return null;};
      obj['normal']=function(){};
      obj['bad']=function(){};
      obj['focus']=function(){};
      obj['type']='awt.Component';
      obj['value']=function(){return '';};
      obj['setValue']=function(v){};
      return obj;
    };
    
    return {
      'create':function(){return create();}
    };
  }
});