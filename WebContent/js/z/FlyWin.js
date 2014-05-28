/**
 * 浮动层
 */
I.regist({
  'name':'z.FlyWin',
  'need':['awt.Component','util.Animator'],
  'fn':function(W,D){
    var WW = 240;
    var HH = 80;
    var create = function(p,st,cf){
      st = st?st:{};
      var tuw = st.width?st.width:WW;
      var tuh = st.height?st.height:HH;
      var w = tuw+12;
      var h = tuh+12;
      var obj = I.awt.Component.create();
      var instance = {'layer':null,'mask':null};
      var r = I.region();
      var q = I.insert('div');
      I.css(q,'position:absolute;left:'+r.x+'px;top:'+r.y+'px;width:'+r.width+'px;height:'+r.height+'px;margin:0;padding:0;font-size:0;background-color:#000');
      q.innerHTML = '<iframe style="margin:0;padding:0" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe><div style="position:absolute;left:0;top:0;width:'+r.width+'px;height:'+r.height+'px;overflow:hidden;background-color:#000000\9"></div>';
      instance.mask = q;
      I.opacity(q,10);
      
      var o = I.insert('div');
      I.css(o,'position:absolute;left:0;top:0;width:1px;height:1px;overflow:hidden;background-image:url(\''+I.dir('z/shadow.png')+'\')');
      var bg = I.insert('div',o);
      I.css(bg,'position:absolute;margin:0;padding:0;left:5px;top:5px;width:'+(w-10)+'px;height:'+(h-10)+'px;overflow:hidden;background-color:#999');
      instance.layer = o;
      var rr = I.region(p);
      o.style.left = rr.x+'px';
      o.style.top = (rr.y+rr.height)+'px';
      
      var content = I.insert('div',o);
      I.css(content,'position:absolute;left:6px;top:6px;width:'+tuw+'px;height:'+tuh+'px;overflow:auto;background-color:#FFF');
      content.innerHTML = st.content?st.content:'';
      
      var btnClose = I.insert('div',o);
      I.css(btnClose,'font-size:14px;font-weight:bold;color:gray;position:absolute;left:'+(w-29-6)+'px;top:6px;width:29px;height:30px;line-height:30px;text-align:center;cursor:pointer');
      btnClose.innerHTML = '×';
      btnClose.setAttribute('title','关闭');
      I.listen(btnClose,'mouseover',function(m,e){
        m.style.color = 'red';
      });
      I.listen(btnClose,'mouseout',function(m,e){
        m.style.color = 'gray';
      });
      
      obj['closeButton'] = btnClose;
      obj['contentPanel'] = content;
      obj['instance'] = instance;
      obj['getCloseButton'] = function(){return this.closeButton;};
      obj['getContentPanel'] = function(){return this.contentPanel;};
      obj['getInstance'] = function(){return this.instance;};
      obj['close'] = function(){
        this.instance.layer.parentNode.removeChild(this.instance.layer);
        this.instance.mask.parentNode.removeChild(this.instance.mask);
        if(this.cf){
          try{
            this.cf();
          }catch(e){}
        }
      };
      obj['cf'] = cf;
      obj['closeButton'].onclick = function(){
        obj.close();
      };
      I.listen(W,'resize',function(m,e){
        try{
          var tr = I.region();
          obj.instance.mask.style.left = tr.x+'px';
          obj.instance.mask.style.top = tr.y+'px';
          obj.instance.mask.style.width = tr.width+'px';
          obj.instance.mask.style.height = tr.height+'px';
        }catch(ex){
        }
      });
      I.listen(W,'scroll',function(m,e){
        try{
          var tr = I.region();
          obj.instance.mask.style.left = tr.x+'px';
          obj.instance.mask.style.top = tr.y+'px';
          obj.instance.mask.style.width = tr.width+'px';
          obj.instance.mask.style.height = tr.height+'px';
        }catch(ex){
        }
      });
      I.util.Animator.create().resize('linear',function(tw,th){
        var inst = obj.instance;
        inst.layer.style.width = tw+'px';
        inst.layer.style.height = th+'px';
      },function(){
      },20,0,0,w,h);
      return obj;
    };
    
    return {
      'create':function(p,st,cf){return create(p,st,cf);}
    };
  }
});