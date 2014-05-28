I.regist({
  'name':'awt.Hidden',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var o = I.insert('span',p);
      o.innerHTML = '<input type="hidden" />';
      var m = I.$(o,'tag','input')[0];
      m.value = undefined==s.value?'':s.value;
      var obj = I.awt.Component.create();
      var instance = {'layer':m};
      obj['instance'] = instance;
      obj['type']='awt.Hidden';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){return this.instance.layer.value;};
      obj['setValue']=function(v){this.instance.layer.value=v;};
      obj['getInstance'] = function(){return this.instance;};
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});