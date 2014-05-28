/**
 * 自适应大小的简单弹出窗口
 */
I.regist({
  'name':'z.SimpleWin',
  'need':['awt.Component','util.Animator'],
  'fn':function(W,D){
    var SPACE = 20;
    var create = function(st,cf){
      st = st?st:{};
      var obj = I.awt.Component.create();
      var instance = {'layer':null,'mask':null,'width':st.width?(st.width>0?st.width:0):0};
      var q = I.insert('div');
      I.css(q,'position:absolute;margin:0;padding:0;font-size:0;background-color:#000');
      q.innerHTML = '<iframe style="margin:0;padding:0" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe>';
      instance.mask = q;
      I.opacity(q,10);
      
      var o = I.insert('div');
      I.css(o,'position:absolute;overflow:hidden;background-image:url(\''+I.dir('z/shadow.png')+'\')');
      instance.layer = o;
      var title = I.insert('div',o);
      I.css(title,'position:absolute;left:6px;top:6px;height:30px;overflow:hidden;text-indent:8px;line-height:30px;font-weight:bold;letter-spacing:2px;background-image:url(\''+I.dir('z/win_title.gif')+'\');color:#333333;border-bottom:1px solid #CCCCCC');
      title.innerHTML = st.title?st.title:'窗口';
      
      var btnClose = I.insert('div',o);
      I.css(btnClose,'position:absolute;top:6px;width:29px;height:30px;overflow:hidden;cursor:pointer;text-align:center;line-height:30px');
      btnClose.innerHTML = '×';
      btnClose.setAttribute('title','关闭');
      I.listen(btnClose,'mouseover',function(m,e){
        m.style.color = 'red';
      });
      I.listen(btnClose,'mouseout',function(m,e){
        m.style.color = 'gray';
      });
      
      var content = I.insert('div',o);
      I.css(content,'position:absolute;left:6px;top:37px;overflow:auto;background-color:#FFFFFF');
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
      obj['suit'] = function(){
        var inst = this.instance;
        try{
          var r = I.region();
          var wd = inst.width>0?(inst.width+12):(r.width-2*SPACE);
          inst.mask.style.left = r.x+'px';
          inst.mask.style.top = r.y+'px';
          inst.mask.style.width = r.width+'px';
          inst.mask.style.height = r.height+'px';
          inst.layer.style.left = (inst.width>0?(r.x+Math.floor((r.width-wd)/2)):(r.x+SPACE))+'px';
          inst.layer.style.top = (r.y+SPACE)+'px';
          inst.layer.style.width = wd+'px';
          inst.layer.style.height = (r.height-2*SPACE)+'px';
          this.titleBar.style.width = (wd-12)+'px';
          this.closeButton.style.left = (wd-35)+'px';
          this.contentPanel.style.width = (wd-12)+'px';
          this.contentPanel.style.height = (r.height-2*SPACE-43)+'px';
        }catch(ex){
        }
      };
      var bodyr = I.region();
      var bodyw = obj.instance.width>0?(obj.instance.width+12):(bodyr.width-2*SPACE);
      obj.instance.layer.style.left = (obj.instance.width>0?(bodyr.x+Math.floor((bodyr.width-bodyw)/2)):(bodyr.x+SPACE))+'px';
      obj.instance.layer.style.top = (bodyr.y+SPACE)+'px';
      I.util.Animator.create().resize('linear',function(w,h){
        var inst = obj.instance;
        inst.layer.style.width = w+'px';
        inst.layer.style.height = h+'px';
      },function(){
        I.listen(W,'resize',function(m,e){
          obj.suit();
        });
        I.listen(W,'scroll',function(m,e){
          obj.suit();
        });
        obj.suit();
      },20,0,0,bodyw,(bodyr.height-2*SPACE));
      return obj;
    };
    
    return {
      'create':function(st,cf){return create(st,cf);}
    };
  }
});