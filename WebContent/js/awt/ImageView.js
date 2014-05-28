I.regist({
  'name':'awt.ImageView',
  'need':['awt.Component','util.ImageLoader'],
  'fn':function(W,D){
    var create = function(p,s){
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var w = skin.get('awt.ImageView.width');
      var h = skin.get('awt.ImageView.height');
      var ns = skin.get('awt.ImageView.normal');
      var bs = skin.get('awt.ImageView.bad');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      o = I.insert('div',div);
      I.css(o,'position:relative;overflow:hidden;left:6px;top:0;width:'+w+'px;height:'+h+'px');
      
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'uri':undefined != s.uri?s.uri:''};
      obj['instance'] = instance;
      obj['type']='awt.ImageView';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){
        var v=this.instance.layer.getAttribute('truev');
        return null==v?'':v;
      };
      obj['setValue']=function(v){
        var inst = this.instance;
        inst.layer.setAttribute('truev',v);
        inst.layer.innerHTML = '';
        I.util.ImageLoader.load({'img':inst.uri+v},function(r){
          var om = r.image['img'];
          var ow = om.width;
          var oh = om.height;
          var rw = w-8;
          var rh = h-8;
          var iw = rw;
          var ih = rh;
          var x = 0;
          var y = 0;

          if(ow <= rw && oh <= rh){
            x = Math.floor((rw - ow) / 2);
            y = Math.floor((rh - oh) / 2);
            iw = ow;
            ih = oh;
          }else if(ow <= rw && oh > rh){
            ih = rh;
            iw = Math.floor(ow * rh / oh);
            x = Math.floor((rw - iw) / 2);
          }else if(ow > rw && oh <= rh){
            iw = rw;
            ih = Math.floor(oh * rw / ow);
            y = Math.floor((rh - ih) / 2);
          }else{
            var r0 = ow / rw;
            var r1 = oh / rh;
            if(r0 < r1){
              ih = rh;
              iw = Math.floor(ow / r1);
              x = Math.floor((rw - iw) / 2);
            }else{
              iw = rw;
              ih = Math.floor(oh / r0);
              y = Math.floor((rh - ih) / 2);
            }
          }
          var img = I.insert('img',inst.layer);
          img.width = iw;
          img.height = ih;
          img.style.margin = '0';
          img.style.border='1px solid #FFF';
          img.style.marginLeft = (3+x) +'px';
          img.style.marginTop = (3+y) +'px';
          img.src = om.src;
        });
      };
      obj['getInstance'] = function(){return this.instance;};
      obj['normal'] = function(){
        var inst = this.instance;
        for(var i in ns){
          inst.layer.style[i] = ns[i];
        }
      };
      obj['bad'] = function(){
        var inst = this.instance;
        for(var i in bs){
          inst.layer.style[i] = bs[i];
        }
        inst.layer.scrollIntoView();
      };
      I.listen(instance.layer,'mouseover',function(m,e){
        obj.normal();
      });
      obj.normal();
      if(undefined != s.value){
        obj.setValue(s.value);
      }
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});