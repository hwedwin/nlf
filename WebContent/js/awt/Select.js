I.regist({
  'name':'awt.Select',
  'need':['awt.Component','net.Rmi'],
  'fn':function(W,D){
    var create = function(p,s){
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var w = skin.get('awt.Select.width');
      var h = skin.get('awt.Select.height');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      o = I.insert('input',div);
      o.readOnly = 'readonly';
      I.css(o,'margin-left:6px;width:'+w+'px;height:'+h+'px;text-indent:3px;cursor:default;outline:none');
      var ns = skin.get('awt.Select.normal');
      var bs = skin.get('awt.Select.bad');
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'setting':s,'data':null,'tip':null};
      if(s.options){
        instance.data = s.options;
        if(undefined!=s.value&&null!=s.value){
          for(var i=0;i<instance.data.length;i++){
            if(instance.data[i].value==s.value){
              instance.layer.value = instance.data[i].text;
              instance.layer.setAttribute('truev',instance.data[i].value);
            }
          }
        }else{
          if(instance.data.length>0){
            instance.layer.value = instance.data[0].text;
            instance.layer.setAttribute('truev',instance.data[0].value);
          }
        }
      }else if(s.from){
        if(s.from.args){
          for(var i in s.from.args){
            I.net.Rmi.set(i,s.from.args[i]);
          }
        }
        I.net.Rmi.call(s.from.klass,s.from.method,function(r){
          instance.data = r;
          if(undefined!=s.value&&null!=s.value){
            for(var i=0;i<instance.data.length;i++){
              if(instance.data[i].value==s.value){
                instance.layer.value = instance.data[i].text;
                instance.layer.setAttribute('truev',instance.data[i].value);
              }
            }
          }else{
            if(instance.data.length>0){
              instance.layer.value = instance.data[0].text;
              instance.layer.setAttribute('truev',instance.data[0].value);
            }
          }
        });
      }
      obj['instance'] = instance;
      obj['type']='awt.Select';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){var s=this.instance.layer.getAttribute('truev');return null==s?'':s;};
      obj['setValue']=function(v){
        this.instance.layer.setAttribute('truev',v);
        if(this.instance.data){
          var data = this.instance.data;
          for(var i=0;i<data.length;i++){
            if(data[i].value==v){
              this.instance.layer.value = data[i].text;
              break;
            }
          }
        }else{
          this.instance.layer.value = '加载中...';
        }
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
      };
      obj['show'] = function(idx){
        var inst = this.instance;
        var ul = I.$(inst.tip,'tag','ul')[0];
        ul.innerHTML = '';
        I.each(inst.data,function(n,i){
          var li = I.insert('li',ul);
          I.css(li,'text-indent:3px;cursor:pointer;height:24px;line-height:24px;overflow:hidden');
          li.innerHTML = n.text;
          li.setAttribute('textv',n.text);
          li.setAttribute('truev',n.value);
          I.listen(li,'click',function(q,e){
            inst.layer.value = q.getAttribute('textv');
            inst.layer.setAttribute('truev',q.getAttribute('truev'));
            if(inst.tip){
              inst.tip.parentNode.removeChild(inst.tip);
              inst.tip = null;
            }
          });
          I.listen(li,'mouseover',function(q,e){
            q.style.backgroundColor = '#EFEFEF';
          });
          I.listen(li,'mouseout',function(q,e){
            q.style.backgroundColor = '#FFF';
          });
        });
        var timer = null;
        var op = 0;
        timer = W.setInterval(function(){
          if(op+20>100){
            op=100;
            I.opacity(inst.tip,op);
            W.clearInterval(timer);
            timer = null;
          }else{
            op = op+20;
            I.opacity(inst.tip,op);
          }
        },30);
      };
      
      obj['focus'] = function(){
        var oinst = this;
        var inst = this.instance;
        if(!inst.data){return;}
        if(inst.tip){
          inst.tip.parentNode.removeChild(inst.tip);
          inst.tip = null;
        }
        var m = I.insert('div');
        var r = I.region(inst.layer);
        var wl = r.width;
        var hl = 200;
        I.css(m,'z-index:2147483647;position:absolute;left:'+r.x+'px;top:'+(r.y+r.height+2)+'px;width:'+wl+'px;height:'+hl+'px;background-color:#3298FE;overflow:hidden');
        I.opacity(m,0);
        var ul = I.insert('ul',m);
        I.css(ul,'margin:0;padding:0;list-style:none;background-color:#FFF;position:absolute;left:1px;top:1px;width:'+(wl-2)+'px;height:'+(hl-2)+'px;overflow:auto');
        inst.tip = m;
        oinst.show();
      };
      I.listen(o,'focus',function(m,e){
        obj.normal();
        obj.focus();
      });
      I.listen(I.$(),'click',function(q,e){
        try{
          q = e.srcElement || e.target;
          if(q==o) return;
          v = I.region();
          var x = e.clientX+v.x;
          var y = e.clientY+v.y;
          if(instance.tip){
            var m = I.region(instance.tip);
            if(x < m.x || y < m.y || x > m.x+m.width || y > m.y+m.height){
              instance.tip.parentNode.removeChild(instance.tip);
              instance.tip = null;
            }
          }
        }catch(ee){}
      });
      obj.normal();
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});