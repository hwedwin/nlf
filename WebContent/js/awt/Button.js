I.regist({
  'name':'awt.Button',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var skin = I.util.Skin.getSkin(s.skin?s.skin:null);
      var lw = skin.get('awt.Button.left.width');
      var padding = skin.get('awt.Button.center.padding');
      var rw = skin.get('awt.Button.right.width');
      var h = skin.get('awt.Button.height');
      var bg = skin.get('awt.Button.backgroundImage');
      var o = I.insert('div',p);
      var sb = '';
      sb += '<table style="margin:0;padding:0" cellspacing="0" cellpadding="0" border="0"><tbody><tr>';
      sb += '<td style="font-size:0;border:0;padding:0;width:'+lw+'px;height:'+h+'px;background-repeat:no-repeat;background-image:'+bg+'"></td>';
      sb += '<td style="letter-spacing:2px;border:0;padding:0;line-height:'+h+'px;padding-left:'+padding+'px;padding-right:'+padding+'px;background-repeat:repeat-x;background-image:'+bg+'"></td>';
      sb += '<td style="font-size:0;border:0;padding:0;width:'+rw+'px;background-repeat:no-repeat;background-image:'+bg+'"></td>';
      sb += '</tr></tbody></table>';
      o.innerHTML = sb;
      var tds = I.$(I.$(o,'tag','tr')[0],'*');
      tds[1].innerHTML = s.label;
      var align = 'right';
      if(s.align&&'left'==s.align){
        align = 'left';
      }
      I.css(o,'margin:'+skin.get('awt.margin')+'px;height:'+h+'px;float:'+align+';cursor:pointer');
      var ns = skin.get('awt.Button.normal');
      var hs = skin.get('awt.Button.hover');
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'tds':tds};
      obj['instance'] = instance;
      obj['type']='awt.Button';
      obj['getInstance'] = function(){return this.instance;};
      obj['onclick'] = function(f){
        var inst = this.instance;
        I.listen(inst.layer,'click',function(m,e){
          f(inst,e);
        });
      };
      obj['normal'] = function(){
        var inst = this.instance;
        inst.tds[0].style.backgroundPosition = ns['left'].backgroundPosition;
        inst.tds[1].style.backgroundPosition = ns['center'].backgroundPosition;
        inst.tds[2].style.backgroundPosition = ns['right'].backgroundPosition;
      };
      obj['hover'] = function(){
        var inst = this.instance;
        inst.tds[0].style.backgroundPosition = hs['left'].backgroundPosition;
        inst.tds[1].style.backgroundPosition = hs['center'].backgroundPosition;
        inst.tds[2].style.backgroundPosition = hs['right'].backgroundPosition;
      };
      obj.normal();
      I.listen(o,'mouseover',function(m,e){
        obj.hover();
      });
      I.listen(o,'mouseout',function(m,e){
        obj.normal();
      });
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});