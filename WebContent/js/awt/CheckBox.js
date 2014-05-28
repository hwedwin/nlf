I.regist({
  'name':'awt.CheckBox',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var skin = I.util.Skin.getSkin();
      var h = skin.get('awt.Text.height');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px;overflow:hidden');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = s.label?(s.label+skin.get('seperator')):'';
      o = I.insert('button',div);
      I.css(o,'margin-left:6px;margin-top:'+(Math.floor((h-skin.get('awt.CheckBox.height'))/2))+'px;width:'+skin.get('awt.CheckBox.width')+'px;height:'+skin.get('awt.CheckBox.height')+'px;overflow:hidden;outline:none;border:0');
      var ons = skin.get('awt.CheckBox.checkOn');
      var offs = skin.get('awt.CheckBox.checkOff');
      var obj = I.awt.Component.create();
      var instance = {'layer':o};
      obj['instance'] = instance;
      obj['type']='awt.CheckBox';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){var s=this.instance.layer.getAttribute('truev');return null==s?false:('false'==s?false:true);};
      obj['setValue']=function(v){
        if(v){
          this.checkOn();
        }else{
          this.checkOff();
        }
      };
      obj['getInstance'] = function(){return this.instance;};
      obj['checkOn'] = function(){
        var inst = this.instance;
        for(var i in ons){
          inst.layer.style[i] = ons[i];
        }
        inst.layer.setAttribute('truev',true);
      };
      obj['checkOff'] = function(){
        var inst = this.instance;
        for(var i in offs){
          inst.layer.style[i] = offs[i];
        }
        inst.layer.setAttribute('truev',false);
      };
      obj['click'] = function(){
        var v = this.value();
        if(v){
          this.checkOff();
        }else{
          this.checkOn();
        }
      };
      obj.checkOff();
      if(s.checkOn){
        obj.checkOn();
      }
      I.listen(o,'click',function(m,e){
        obj.click();
      });
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});