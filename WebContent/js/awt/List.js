I.regist({
  'name':'awt.List',
  'need':['awt.Component','net.Rmi'],
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
      o = I.insert('input',div);
      o.readOnly = 'readonly';
      I.css(o,'margin-left:6px;width:'+w+'px;height:'+h+'px;text-indent:3px;cursor:default;outline:none');
      var ns = skin.get('awt.List.normal');
      var bs = skin.get('awt.List.bad');
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'setting':s,'data':null,'tip':null,'page':0,'maxPage':1};
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
      obj['type']='awt.List';
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
      obj['showPage'] = function(idx){
        var inst = this.instance;
        if(idx>=inst.maxPage){
          idx = inst.maxPage-1;
        }else if(idx<0){
          idx = 0;
        }
        inst.page = idx;
        var ul = I.$(inst.tip,'tag','ul')[0];
        var span = I.$(inst.tip,'tag','span')[1];
        span.innerHTML = (idx+1)+'/'+inst.maxPage;
        ul.innerHTML = '';
        I.each(inst.data,function(n,i){
          if(i<idx*15) return I.CONTINUE;
          if(i>(idx+1)*15-1) return I.BREAK;
          var li = I.insert('li',ul);
          I.css(li,'float:left;text-indent:3px;cursor:pointer;width:100px;height:24px;line-height:24px;overflow:hidden');
          li.innerHTML = n.text;
          li.setAttribute('truev',n.value);
          I.listen(li,'click',function(q,e){
            inst.layer.value = q.innerHTML;
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
        inst.maxPage = Math.ceil(inst.data.length/15);
        var l = inst.maxPage>1?146:(Math.ceil(inst.data.length/3)*24)+2;
        var wl = inst.data.length>3?302:inst.data.length*100+2;
        I.css(m,'position:absolute;left:'+r.x+'px;top:'+(r.y+r.height+2)+'px;width:'+wl+'px;height:'+l+'px;background-color:#3298FE;overflow:hidden');
        var ul = I.insert('ul',m);
        I.css(ul,'margin:0;padding:0;list-style:none;background-color:#FFF;position:absolute;left:1px;top:1px;width:'+(wl-2)+'px;height:'+(inst.maxPage>1?l-2-24:l-2)+'px;overflow:hidden');
        inst.tip = m;
        m = I.insert('div',inst.tip);
        I.css(m,'position:absolute;left:1px;top:'+(inst.maxPage>1?l-1-24:-200)+'px;width:'+(wl-2)+'px;height:24px;line-height:24px;text-align:right;background-color:#EEE');
        var lk = I.insert('span',m);
        I.css(lk,'margin-right:20px;cursor:pointer;color:#3298FE');
        lk.innerHTML = '上一页';
        I.listen(lk,'click',function(q,e){
          oinst.showPage(inst.page-1);
        });
        lk = I.insert('span',m);
        I.css(lk,'margin-right:20px');
        lk.innerHTML = '';
        lk = I.insert('span',m);
        I.css(lk,'margin-right:10px;cursor:pointer;color:#3298FE');
        lk.innerHTML = '下一页';
        I.listen(lk,'click',function(q,e){
          oinst.showPage(inst.page+1);
        });
        oinst.showPage(0);
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