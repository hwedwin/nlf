I.regist({
  'name':'awt.Password',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var w = skin.get('awt.Text.width');
      var h = skin.get('awt.Text.height');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      o = I.insert('span',div);
      o.innerHTML = '<input type="password" />';
      o = I.$(o,'tag','input')[0];
      o.value = s.value?s.value:'';
      I.css(o,'margin-left:6px;width:'+w+'px;height:'+h+'px;text-indent:3px;outline:none');
      var ns = skin.get('awt.Text.normal');
      var bs = skin.get('awt.Text.bad');
      var fs = skin.get('awt.Text.focus');
      var obj = I.awt.Component.create();
      var instance = {'layer':o};
      obj['instance'] = instance;
      obj['type']='awt.Password';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){return this.instance.layer.value;};
      obj['setValue']=function(v){this.instance.layer.value=v;};
      obj['getInstance'] = function(){return this.instance;};
      obj['normal'] = function(){
        var inst = this.instance;
        for(var i in ns){
          inst.layer.style[i] = ns[i];
        }
      };
      obj['focus'] = function(){
        var inst = this.instance;
        for(var i in fs){
          inst.layer.style[i] = fs[i];
        }
      };
      obj['bad'] = function(){
        var inst = this.instance;
        for(var i in bs){
          inst.layer.style[i] = bs[i];
        }
      };
      obj.normal();
      I.listen(o,'focus',function(m,e){
        obj.focus();
      });
      I.listen(o,'blur',function(m,e){
        obj.normal();
      });
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});