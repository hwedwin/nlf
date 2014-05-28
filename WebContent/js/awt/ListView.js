I.regist({
  'name':'awt.ListView',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var w = skin.get('awt.ListView.width');
      var h = skin.get('awt.ListView.height');
      var ih = skin.get('awt.ListView.item.height');
      var ns = skin.get('awt.ListView.normal');
      var bs = skin.get('awt.ListView.bad');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      o = I.insert('div',div);
      I.css(o,'position:relative;overflow:auto;left:6px;top:0;width:'+w+'px;height:'+h+'px');
      var ul = I.insert('ul',o);
      I.css(ul,'list-style:none;margin:0;padding:0');
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'ul':ul};
      obj['instance'] = instance;
      obj['type']='awt.ListView';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){
        var inst = this.instance;
        var vs = [];
        var li = I.$(inst.ul,'tag','li');
        I.each(li,function(m,i){
          if(m&&m.getAttribute){
            var s=m.getAttribute('truev');
            if(s){
              vs.push(s);
            }
          }
        });
        return vs.join(',');
      };
      obj['setValue']=function(v){
        var oinst = this;
        I.each(v,function(m,i){
          oinst.add(m);
        });
      };
      obj['add']=function(v){
        var inst = this.instance;
        var li = I.insert('li',inst.ul);
        I.css(li,'height:'+ih+'px;line-height:'+ih+'px;text-indent:3px;cursor:default;background-color:#FFFFFF');
        var dv = I.insert('div',li);
        I.css(dv,'width:'+w+'px;height:'+ih+'px;overflow:hidden;font-size:12px;cursor:default;line-height:'+ih+'px');
        dv.innerHTML = v.text;
        dv = I.insert('div',li);
        I.css(dv,'width:20px;margin-left:'+(w-25)+'px;margin-top:-'+ih+'px;font-weight:bold;text-align:right;height:'+ih+'px;color:#C40000;cursor:pointer;overflow:hidden;font-size:12px;line-height:'+ih+'px');
        dv.innerHTML = '×';
        dv.title = '删除';
        I.listen(dv,'click',function(m,i){
          li.parentNode.removeChild(li);
        });
        li.setAttribute('truev',v.value);
        I.listen(li,'mouseover',function(m,i){
          m.style.backgroundColor = '#EFEFEF';
        });
        I.listen(li,'mouseout',function(m,i){
          m.style.backgroundColor = '#FFFFFF';
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
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});