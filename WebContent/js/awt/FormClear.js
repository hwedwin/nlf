I.regist({
  'name':'awt.FormClear',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var o = I.insert('div',p);
      I.css(o,'margin:0;font-size:0;height:0;clear:both');
      var obj = I.awt.Component.create();
      var instance = {'layer':o};
      obj['instance'] = instance;
      obj['type']='awt.FormClear';
      obj['getInstance'] = function(){return this.instance;};
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});