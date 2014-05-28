I.regist({
  'name':'z.UEditor',
  'need':['awt.Component','net.Rmi','util.Loader'],
  'fn':function(W,D){
    var NEED = [
        I.dir('keditor/kindeditor.js'),
        I.dir('keditor/lang/zh_CN.js')
    ];
    var create = function(p,s){
      var REQUIRED = '<b style="color:#C40000">*</b>';
      var skin = I.util.Skin.getSkin();
      var h = skin.get('awt.Text.height');
      var div = I.insert('div',p);
      I.css(div,'margin:'+skin.get('awt.margin')+'px');
      var o = I.insert('span',div);
      I.css(o,'float:left;display:block;width:90px;height:'+h+'px;line-height:'+h+'px;text-align:right;overflow:hidden');
      o.innerHTML = (s.required?REQUIRED+' ':'')+(s.label?(s.label+skin.get('seperator')):'');
      var dd = I.insert('div',div);
      I.css(dd,'padding-left:6px;overflow:hidden');
      var tx = I.insert('textarea',dd);
      I.css(tx,'outline:none;resize:none;width:96%;height:300px');
      if(undefined!=s.value){
        tx.value = s.value;
      }
      return preCreate(tx,s);
    };
    
    var preValue = function(o,v){
      if(o.instance.layer){
        o.instance.layer.html(v);
      }else{
        W.setTimeout(function(){
          preValue(o,v);
        },30);
      }
    };
    
    var preCreate = function(tx,s){
      var obj = I.awt.Component.create();
      var instance = {'layer':null};
      obj['instance'] = instance;
      obj['type']='z.UEditor';
      obj['render'] = s.render?s.render:s.id;
      obj['value']=function(){return this.instance.layer.html();};
      obj['setValue']=function(v){
        preValue(this,v);
      };
      obj['getInstance'] = function(){return this.instance;};
      I.util.Loader.load(NEED,function(){
        var editor = KindEditor.create(tx);
        instance.layer = editor;
      });
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});