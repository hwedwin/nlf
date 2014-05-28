I.regist({
  'name':'awt.TextRich',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var w = skin.get('awt.TextArea.width');
      if(s.width){
        w = s.width;
      }
      var h = skin.get('awt.TextArea.height');
      if(s.height){
        h = s.height;
      }
      var ns = skin.get('awt.TextArea.normal');
      var bs = skin.get('awt.TextArea.bad');
      var fs = skin.get('awt.TextArea.focus');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+skin.get('awt.Text.height')+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      o = I.insert('div',div);
      o.contentEditable = true;
      o.tabIndex = -1;
      o.innerHTML = s.value?s.value:'';
      I.css(o,'float:left;margin-left:6px;width:'+w+'px;height:'+h+'px;outline:none;resize:none');
      var line = I.insert('div',p);
      I.css(line,'font-size:0;clear:both;height:0');
      var obj = I.awt.Component.create();
      var instance = {'layer':o};
      obj['instance'] = instance;
      obj['type']='awt.TextRich';
      obj['rule']=s.rule;
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){return this.instance.layer.innerHTML;};
      obj['setValue']=function(v){this.instance.layer.innerHTML=v;};
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