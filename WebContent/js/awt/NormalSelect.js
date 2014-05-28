I.regist({
  'name':'awt.NormalSelect',
  'need':['awt.Component','net.Rmi'],
  'fn':function(W,D){
    var create = function(p,s){
      var skin = I.util.Skin.getSkin();
      var h = skin.get('awt.Text.height');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = s.label?(s.label+skin.get('seperator')):'';
      o = I.insert('div',div);
      o = I.insert('select',o);
      I.css(o,'margin-left:6px;outline:none');
      if(s.options){
        I.each(s.options,function(m,i){
          o.options[o.options.length] = new Option(m.text,m.value);
        });
        o.value = s.value?s.value:'';
      }else if(s.from){
        if(s.from.args){
          for(var i in s.from.args){
            I.net.Rmi.set(i,s.from.args[i]);
          }
        }
        I.net.Rmi.call(s.from.klass,s.from.method,function(r){
          I.each(r,function(m,i){
            o.options[o.options.length] = new Option(r.text,r.value);
          });
          o.value = s.value?s.value:'';
        });
      }
      var obj = I.awt.Component.create();
      var instance = {'layer':o};
      obj['instance'] = instance;
      obj['type']='awt.NormalSelect';
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