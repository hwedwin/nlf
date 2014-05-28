I.regist({
  'name':'wl.CatView',
  'need':['awt.Component','net.Rmi','util.Timer'],
  'fn':function(W,D){
    var create = function(p,s){
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var w = 200;
      var h = 200;
      var L = 3;
      var bw = w*L+L+1;
      var bh = h+2;
      var hh = bh+24;
      var div = I.insert('div',I.$(p));
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+hh+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      o = I.insert('div',div);
      I.css(o,'position:relative;overflow:hidden;background-color:#DDD;left:6px;top:0;width:'+bw+'px;height:'+hh+'px');
      var slide = I.insert('div',o);
      I.css(slide,'position:absolute;left:0;top:0;width:'+bw+'px;height:'+bh+'px;overflow:hidden');
      var bpan = I.insert('div',o);
      I.css(bpan,'position:absolute;left:1px;top:'+(bh-1)+'px;width:'+(bw-2)+'px;height:24px;line-height:24px;text-indent:3px;overflow:hidden;background-color:#EEE');
      
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'slide':slide,'slidePos':0,'klass':s.klass,'method':s.method,'level':{},'bpan':bpan,'onSelect':null,'onCancel':null};
      
      obj['instance'] = instance;
      obj['type']='wl.CatView';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){
        var v=this.instance.layer.getAttribute('truev');
        return null==v?'':v;
      };
      obj['setValue']=function(v){
        var inst = this.instance;
        inst.layer.setAttribute('truev',v);
      };
      obj['onSelect']=function(f){
        this.instance.onSelect=f;
      };
      obj['onCancel']=function(f){
        this.instance.onCancel=f;
      };
      obj['getInstance'] = function(){return this.instance;};
      obj['repaintPath']=function(){
        var oinst = this;
        var inst = this.instance;
        var l = 0;
        for(var i in inst.level){
          if(i>l) l = i;
        }
        var ps = [];
        outer:for(var i=0;i<=l;i++){
          var m =inst.level[i];
          var lis = I.$(m,'tag','li');
          for(var j=0;j<lis.length;j++){
            if('1'==lis[j].getAttribute('selected')){
              ps.push(lis[j].innerHTML);
              continue outer;
            }
          }
        }
        inst.bpan.innerHTML = '[';
        var q = I.insert('span',inst.bpan);
        q.innerHTML='×';
        q.setAttribute('title','点击取消选择');
        I.css(q,'font-weight:bold;color:red;cursor:pointer');
        I.listen(q,'click',function(m,e){
          oinst.setValue('');
          oinst.sub(0);
          inst.bpan.innerHTML = '';
          if(inst.onCancel){
            inst.onCancel();
          }
        });
        q = I.insert('span',inst.bpan);
        q.innerHTML=']已选择：'+ps.join('&nbsp;<span style="color:#AAA">&gt;</span>&nbsp;');
      };
      obj['sub']=function(level,id){
        var oinst = this;
        var inst=this.instance;
        if(undefined != id){
          I.net.Rmi.set('id',id);
        }
        I.net.Rmi.call(inst.klass,inst.method,function(r){
          if(r.length<1) return;
          inst.slide.style.width = ((level+1)*w+level+2)+'px';
          var tm = I.util.Timer.create({'dist':level<L?'0':((L-level-1)*w-level+2)});
          tm.add(function(v,l){
            if(inst.slidePos<v.dist){
              inst.slidePos+=20;
            }else if(inst.slidePos>v.dist){
              inst.slidePos-=20;
            }
            inst.slide.style.left = inst.slidePos+'px';
          },function(v,l){
            if(Math.abs(inst.slidePos-v.dist)<40){
              inst.slidePos = v.dist;
              inst.slide.style.left = inst.slidePos+'px';
              return true;
            }
            return false;
          },30);
          tm.exe();
          var div = I.insert('div',inst.slide);
          inst.level[level]=div;
          I.css(div,'position:absolute;left:'+(level*w+level+1)+'px;top:1px;width:'+w+'px;height:'+h+'px;background-color:#FFF;overflow:auto;overflow-x:hidden');
          var ul = I.insert('ul',div);
          I.each(r,function(n,i){
            var li = I.insert('li',ul);
            I.css(li,'height:24px;line-height:24px;width:'+w+'px;overflow:hidden;text-indent:3px;cursor:default;color:#000');
            li.innerHTML = n.NAME_VC;
            li.setAttribute('title',n.NAME_VC);
            li.setAttribute('level',level);
            li.setAttribute('selected','0');
            li.setAttribute('cid',n.ID_BGT);
            li.setAttribute('code',n.SN_VC);
            I.listen(li,'mouseover',function(mm,e){
              if('1'!=mm.getAttribute('selected')){
                mm.style.backgroundColor = '#EEE';
              }
            });
            I.listen(li,'mouseout',function(mm,e){
              if('1'!=mm.getAttribute('selected')){
                mm.style.backgroundColor = '#FFF';
              }
            });
            I.listen(li,'click',function(mm,e){
              var lis = I.$(mm.parentNode,'tag','li');
              I.each(lis,function(q,j){
                if(q==mm){
                  q.setAttribute('selected','1');
                  q.style.backgroundColor = '#3298FE';
                  q.style.color = '#FFF';
                  oinst.setValue(q.getAttribute('cid'));
                }else{
                  q.setAttribute('selected','0');
                  q.style.backgroundColor = '#FFF';
                  q.style.color = '#000';
                }
              });
              for(var i in inst.level){
                if(parseInt(i,10)>level){
                  inst.level[i].parentNode.removeChild(inst.level[i]);
                  delete inst.level[i];
                }
              }
              oinst.repaintPath();
              if(inst.onSelect){
                var attr = {'cid':mm.getAttribute('cid'),'code':mm.getAttribute('code'),'name':mm.getAttribute('title')};
                inst.onSelect(attr);
              }
              var idx = parseInt(mm.getAttribute('level'),10);
              oinst.sub(idx+1,mm.getAttribute('cid'));
            });
          });
        });
      };
      obj.sub(0);
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});