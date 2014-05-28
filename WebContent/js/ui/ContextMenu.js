/**
 * 右键弹出菜单
 */
I.regist({
  'name':'ui.ContextMenu',
  'need':['awt.Component','ui.MenuItem'],
  'fn':function(W,D){
    var create = function(p,pos){
      var o = I.insert('div',p?p:D.body);
      I.css(o,'position:absolute;left:'+pos.x+'px;top:'+pos.y+'px;width:100px;border:1px solid #999999;background-color:#FFF');
      
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'items':{}};
      obj['instance'] = instance;
      obj['type']='ui.ContextMenu';
      obj['getItem']=function(name){
        return this.instance.items[name];
      };
      obj['addItem']=function(item){
        var inst = this.instance;
        var func = function(who){
          try{
            instance.layer.parentNode.removeChild(instance.layer);
          }catch(e){}
          item.func(who);
        };
        var m = I.ui.MenuItem.create(inst.layer,{'name':item.name,'label':item.label,'icon':item.icon,'func':func,'father':obj});
        inst.items[item.name]=m;
        return m;
      };
      obj['getInstance'] = function(){return this.instance;};
      I.listen(I.$(),'click',function(o,e){
        try{
          v = I.region();
          var x = e.clientX+v.x;
          var y = e.clientY+v.y;
          var m = I.region(instance.layer);
          if(x < m.x || y < m.y || x > m.x+m.width || y > m.y+m.height) {
            instance.layer.parentNode.removeChild(instance.layer);
          }
        }catch(ee){}
      });
      return obj;
    };
    
    return {
      'create':function(p,pos){return create(p,pos);}
    };
  }
});