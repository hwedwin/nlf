I.regist({
  'name':'z.Win',
  'need':['awt.Component','util.Drager'],
  'fn':function(W,D){
    var WW = 400;
    var HH = 250;
    var create = function(st,cf){
      var tuw = st.width?st.width:WW;
      var tuh = st.height?st.height:HH;
      var w = tuw+12;
      var h = tuh+42;
      var obj = I.awt.Component.create();
      var instance = {'layer':null,'mask':null};
      var r = I.region();
      var q = I.insert('div');
      I.css(q,'position:absolute;left:'+r.x+'px;top:'+r.y+'px;width:'+r.width+'px;height:'+r.height+'px;margin:0;padding:0;font-size:0;background-color:#000');
      q.innerHTML = '<iframe style="margin:0;padding:0" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe><div style="position:absolute;left:0;top:0;width:'+r.width+'px;height:'+r.height+'px;overflow:hidden;background-color:#000000\9"></div>';
      instance.mask = q;
      I.opacity(q,10);
      
      var o = I.insert('div');
      I.css(o,'position:absolute;left:0;top:0;width:'+w+'px;height:'+h+'px;overflow:hidden;background-image:url(\''+I.dir('z/shadow.png')+'\')');
      var bg = I.insert('div',o);
      I.css(bg,'position:absolute;margin:0;padding:0;left:5px;top:5px;width:'+(w-10)+'px;height:'+(h-10)+'px;overflow:hidden;background-color:#999');
      instance.layer = o;
      o.style.left = Math.floor(r.x+(r.width-w)/2)+'px';
      o.style.top = Math.floor(r.y+(r.height-h)/2)+'px';
      var title = I.insert('div',o);
      I.css(title,'position:absolute;left:6px;top:6px;width:'+tuw+'px;height:29px;overflow:hidden;background-image:url(\''+I.dir('z/win_title.gif')+'\');color:#333333;text-indent:8px;line-height:30px;font-weight:bold;letter-spacing:2px');
      title.innerHTML = st.title?st.title:'窗口';
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
      
      var content = I.insert('div',o);
      I.css(content,'position:absolute;left:6px;top:36px;width:'+tuw+'px;height:'+tuh+'px;overflow:auto;background-color:#FFF');
      content.innerHTML = st.content?st.content:'';
      obj['closeButton'] = btnClose;
      obj['titleBar'] = title;
      obj['contentPanel'] = content;
      obj['instance'] = instance;
      obj['getCloseButton'] = function(){return this.closeButton;};
      obj['getTitleBar'] = function(){return this.titleBar;};
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
      I.util.Drager.drag(obj.titleBar,obj.instance.layer);
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
      return obj;
    };
    
    return {
      'create':function(st,cf){return create(st,cf);}
    };
  }
});