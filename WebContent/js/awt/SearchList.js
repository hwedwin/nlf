I.regist({
  'name':'awt.SearchList',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var w = skin.get('awt.SearchList.width');
      var h = skin.get('awt.SearchList.height');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      o = I.insert('input',div);
      o.readOnly = 'readonly';
      I.css(o,'margin-left:6px;width:'+w+'px;height:'+h+'px;text-indent:3px;outline:none;cursor:default');
      var ts = skin.get('awt.Text.normal');
      var ns = skin.get('awt.SearchList.normal');
      var bs = skin.get('awt.SearchList.bad');
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'setting':s,'tip':null};
      if(undefined!=s.from&&null!=s.from&&undefined!=s.value&&null!=s.value){
        if(s.from.args){
          for(var i in s.from.args){
            I.net.Rmi.set(i,s.from.args[i]);
          }
        }
        I.net.Rmi.set(s.id,s.value);
        I.net.Rmi.call(s.from.klass,s.from.method,function(r){
          if(r.length>0){
            instance.layer.value = r[0][s.k];
            instance.layer.setAttribute('truev',r[0][s.v]);
          }
        });
      }
      obj['instance'] = instance;
      obj['type']='awt.SearchList';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){var s=this.instance.layer.getAttribute('truev');return null==s?'':s;};
      obj['setValue']=function(v){
        this.instance.layer.setAttribute('truev',v);
        if(undefined!=s.from&&null!=s.from){
          if(s.from.args){
            for(var i in s.from.args){
              I.net.Rmi.set(i,s.from.args[i]);
            }
          }
          I.net.Rmi.set(s.id,v);
          I.net.Rmi.call(s.from.klass,s.from.method,function(r){
            if(r.length>0){
              instance.layer.value = r[0][s.k];
              instance.layer.setAttribute('truev',r[0][s.v]);
            }
          });
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
      obj['focus'] = function(){
        var inst = this.instance;
        if(inst.tip){
          inst.tip.parentNode.removeChild(inst.tip);
          inst.tip = null;
        }
        var m = I.insert('div');
        var r = I.region(inst.layer);
        var wl = 302;
        var hl = 202;
        I.css(m,'position:absolute;left:'+r.x+'px;top:'+(r.y+r.height+2)+'px;width:'+wl+'px;height:'+hl+'px;background-color:#3298FE;overflow:hidden;z-index:2147483647');
        var t = I.insert('div',m);
        I.css(t,'position:absolute;left:1px;top:1px;width:'+(wl-2)+'px;height:'+(hl-2)+'px;background-color:#FFF;overflow:hidden');
        var tt = I.insert('span',t);
        I.css(tt,'position:absolute;left:1px;top:6px;display:block;width:80px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
        tt.innerHTML = s.label?(s.label+skin.get('seperator')):'';
        tt = I.insert('input',t);
        I.css(tt,'position:absolute;left:87px;top:6px;width:120px;height:'+h+'px;outline:none');
        for(var i in ts){
          tt.style[i] = ts[i];
        }
        tt = I.insert('span',t);
        I.css(tt,'position:absolute;left:214px;top:6px;display:block;width:60px;height:'+h+'px;line-height:'+h+'px;text-align:center;background-color:#3298FE;color:#FFF;cursor:pointer');
        tt.innerHTML = '查询';
        I.listen(tt,'click',function(oo,ee){
          if(undefined!=s.from&&null!=s.from){
            if(s.from.args){
              for(var i in s.from.args){
                I.net.Rmi.set(i,s.from.args[i]);
              }
            }
            I.net.Rmi.set('text',oo.previousSibling.value);
            I.net.Rmi.call(s.from.klass,s.from.method,function(r){
              var ul = I.$(inst.tip,'tag','ul')[0];
              ul.innerHTML = '';
              I.each(r,function(nn,ii){
                var li = I.insert('li',ul);
                I.css(li,'margin:4px;height:24px;line-height:24px;cursor:pointer;border-bottom:1px solid #EEE');
                li.innerHTML = nn[s.k];
                li.setAttribute('truev',nn[s.v]);
                I.listen(li,'click',function(mmm,eee){
                  inst.layer.value = mmm.innerHTML;
                  inst.layer.setAttribute('truev',mmm.getAttribute('truev'));
                  if(inst.tip){
                    inst.tip.parentNode.removeChild(inst.tip);
                    inst.tip = null;
                  }
                });
                I.listen(li,'mouseover',function(mmm,eee){
                  mmm.style.backgroundColor = '#EFEFEF';
                });
                I.listen(li,'mouseout',function(mmm,eee){
                  mmm.style.backgroundColor = '#FFF';
                });
              });
            });
          }
        });
        var ul = I.insert('ul',t);
        I.css(ul,'border-top:1px solid #DDD;list-style:none;margin:0;padding:0;position:absolute;left:0;top:'+(parseInt(h,10)+12)+'px;width:'+(wl-2)+'px;height:'+(hl-parseInt(h,10)-15)+'px;overflow:auto');
        inst.tip = m;
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