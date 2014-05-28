I.regist({
  'name':'awt.Label',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var skin = I.util.Skin.getSkin();
      var w = skin.get('awt.Text.width');
      var h = skin.get('awt.Text.height');
      if(s.width){
        w = s.width;
      }
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = s.label?(s.label+skin.get('seperator')):'';
      o = I.insert('span',div);
      o.innerHTML = s.value?s.value:'';
      I.css(o,'margin-left:6px;display:block;width:'+w+'px;height:'+h+'px;line-height:'+h+'px;text-align:left;overflow:hidden');
      var obj = I.awt.Component.create();
      var instance = {'layer':o};
      obj['instance'] = instance;
      obj['type']='awt.Label';
      obj['rule']=s.rule;
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){return this.instance.layer.innerHTML;};
      obj['setValue']=function(v){this.instance.layer.innerHTML=v;};
      obj['getInstance'] = function(){return this.instance;};
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});