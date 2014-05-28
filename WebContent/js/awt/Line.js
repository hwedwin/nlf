I.regist({
  'name':'awt.Line',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var skin = I.util.Skin.getSkin(s.skin?s.skin:null);
      var o = I.insert('div',p);
      I.css(o,'margin:'+skin.get('awt.margin')+'px;font-size:0;height:1px;background-color:#EEE');
      var obj = I.awt.Component.create();
      var instance = {'layer':o};
      obj['instance'] = instance;
      obj['type']='awt.Line';
      obj['getInstance'] = function(){return this.instance;};
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});