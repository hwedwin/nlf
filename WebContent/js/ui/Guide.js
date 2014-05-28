I.regist({
  'name':'ui.Guide',
  'need':['awt.Component'],
  'fn':function(W,D){
    var Q = {};
    var create = function(tgt,html,func){
      var obj = I.awt.Component.create();
      var instance = null;
      var init = function(){
        var l = 0;
        while(Q['I'+l]){
          l++;
        }
        var tip = {'layer':null,'idx':l,'timer':null,'func':func};
        var o = I.insert('div');
        var pr = I.region(tgt);
        I.css(o,'position:absolute;left:'+pr.x+'px;top:'+pr.y+'px;margin:0;padding:0;z-index:2147483647');
        var s = '';
        s += '<div style="border:1px solid #B1B1B1;background-color:#FFF;color:#C40000"><b style="display:block;font-weight:normal;margin:10px"></b></div>';
        s += '<div style="height:10px;overflow:hidden;margin-top:-1px"><div style="font-family:SimSun;overflow:hidden;margin-top:-12px;font-size:20px;color:#B1B1B1">&#9670;</div><div style="font-family:SimSun;overflow:hidden;margin-top:-24px;font-size:20px;color:#FEFEE9">&#9670;</div></div>';
        s += '';
        o.innerHTML = s;
        var b = I.$(o,'tag','b')[0];
        b.innerHTML = html;
        tip.layer = o;
        
        var sl = I.region(tip.layer);
        tip.layer.style.left = (pr.x)+'px';
            tip.layer.style.top = (pr.y-sl.height)+'px';
            I.listen(tip.layer,'click',function(m,e){
              obj.close();
            });
            I.listen(tgt,'focus',function(m,e){
              obj.close();
            });
            I.listen(tgt,'click',function(m,e){
              obj.close();
            });
        
        Q['I'+tip.idx] = 'true';
        instance = tip;
      };
     
      init();
      obj['close'] = function(){
        var inst = this.getInstance();
        try{
          if(inst.layer){
            inst.layer.parentNode.removeChild(inst.layer);
            inst.layer = null;
          }
        }catch(e){}
        if(inst.func){
          inst.func();
        }
        delete Q['I'+inst.idx];
      };
      obj['getInstance'] = function(){return instance;};
      return obj;
    };
    
    return {
      'create':function(tgt,html,func){return create(tgt,html,func);}
    };
  }
});