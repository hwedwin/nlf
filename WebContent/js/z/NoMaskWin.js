I.regist({
  'name':'z.NoMaskWin',
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
      
      var o = I.insert('div');
      I.css(o,'position:absolute;left:0;top:0;width:'+w+'px;height:'+h+'px;overflow:hidden;background-color:#999999');
      instance.layer = o;
      o.innerHTML = '<iframe style="margin:0;padding:0" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe><div style="position:absolute;left:0;top:0;width:'+w+'px;height:'+h+'px;overflow:hidden;background-color:#999999"></div>';
      o.style.left = Math.floor(r.x+(r.width-w)/2)+'px';
      o.style.top = Math.floor(r.y+(r.height-h)/2)+'px';
      var title = I.insert('div',o);
      I.css(title,'position:absolute;left:6px;top:6px;width:'+tuw+'px;height:29px;overflow:hidden;background-image:url(\''+I.dir('z/win_title.gif')+'\');color:#333333;text-indent:8px;line-height:30px;font-weight:bold;letter-spacing:2px');
      title.innerHTML = st.title?st.title:'窗口';
      var btnClose = I.insert('div',o);
      I.css(btnClose,'font-size:14px;font-weight:bold;color:#333333;position:absolute;left:'+(w-29-6)+'px;top:6px;width:29px;height:29px;line-height:30px;text-align:center;cursor:pointer');
      btnClose.setAttribute('title','关闭');
      btnClose.innerHTML = '×';
      I.listen(btnClose,'mouseover',function(m,e){
        m.style.color = 'red';
      });
      I.listen(btnClose,'mouseout',function(m,e){
        m.style.color = '#333333';
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
      obj['setWidth'] = function(tw){
        var w = tw+12;
        var inst = this.instance;
        inst.layer.style.width = w+'px';
        I.$(inst.layer,'tag','div')[0].style.width = w+'px';
        this.titleBar.style.width = tw+'px';
        this.closeButton.style.left = (w-29-6)+'px';
        this.contentPanel.style.width = tw+'px';
      };
      obj['close'] = function(){
        this.instance.layer.parentNode.removeChild(this.instance.layer);
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
      return obj;
    };
    
    return {
      'create':function(st,cf){return create(st,cf);}
    };
  }
});