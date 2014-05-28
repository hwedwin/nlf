I.regist({
  'name':'ui.Loading',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(){
      var r = I.region();
      var skin = I.util.Skin.getSkin();
      var color = skin.get('ui.Loading.color');
      var obj = I.awt.Component.create();
      var instance = {'layer':null,'mask':null,timer:null,percent:0,over:false};
      var o = I.insert('div');
      I.css(o,'position:absolute;left:'+r.x+'px;top:'+r.y+'px;width:'+r.width+'px;height:'+r.height+'px;margin:0;padding:0;font-size:0;background-color:#FFF');
      o.innerHTML = '<iframe style="margin:0;padding:0" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe>';
      instance.mask = o;
      I.opacity(o,5);
      var q = I.insert('div');
      I.css(q,'position:absolute;left:'+r.x+'px;top:'+r.y+'px;0;width:0;height:1px;margin:0;padding:0;overflow:hidden;background-color:'+color+';font-size:0;border:0;filter: progid:DXImageTransform.Microsoft.Shadow(color=\''+color+'\', Direction=0, Strength=1);-moz-box-shadow:0px 1px 1px '+color+';-webkit-box-shadow:0px 1px 1px '+color+';box-shadow:0px 1px 1px '+color);
      instance.layer = q;
      var close = function(){
        instance.percent = 100;
        instance.over = true;
      };
      obj['close'] = function(){close();};
      obj['getInstance'] = function(){return instance;};
      obj['instance'] = instance;
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
      instance.timer = W.setInterval(function(){
        if(instance.percent<100){
          instance.percent+=(100-instance.percent)/60;
        }else{
          instance.percent = 100;
        }
        var tr = I.region();
        instance.layer.style.left = tr.x+'px';
        instance.layer.style.top = tr.y+'px';
        instance.layer.style.width = Math.floor(instance.percent/100*tr.width)+'px';
        if(instance.over){
          W.clearInterval(instance.timer);
          instance.timer = null;
          W.setTimeout(function(){
            instance.layer.parentNode.removeChild(instance.layer);
            instance.mask.parentNode.removeChild(instance.mask);
          },400);
        }
      },60);
      return obj;
    };
    return {
      'create':function(){return create();}
    };
  }
});