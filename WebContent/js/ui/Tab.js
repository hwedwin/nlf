I.regist({
  'name':'ui.Tab',
  'need':['awt.Component','ui.TabItem'],
  'fn':function(W,D){
    var create = function(p,s){
      var skin = I.util.Skin.getSkin(s?(s.skin?s.skin:null):null);
      var o = I.insert('div',I.$(p));
      I.css(o,'margin-top:'+skin.get('awt.margin')+'px');
      var bar = I.insert('div',o);
      I.css(bar,'margin:0;padding:0');
      var clr = I.insert('div',o);
      I.css(clr,'clear:both;margin:0;padding:0;height:0;font-size:0');
      var panel = I.insert('div',o);
      I.css(panel,'border:1px solid #CCC;margin:0;margin-top:-1px;padding:0');
      clr = I.insert('div',o);
      I.css(clr,'clear:both;margin:0;padding:0;height:0;font-size:0');
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'tabs':{},'bar':bar,'panel':panel};
      obj['instance'] = instance;
      obj['type']='ui.Tab';
      obj['get']=function(name){
        return this.instance.tabs[name];
      };
      obj['showDefault']=function(){
        var inst = this.instance;
        var l=0;
        for(var i in inst.tabs){
          if(0==l){
            inst.tabs[i].show();
          }else{
            inst.tabs[i].hide();
          }
          l++;
        }
      };
      obj['show']=function(name){
        var inst = this.instance;
        for(var i in inst.tabs){
          if(i==name){
            inst.tabs[i].show();
          }else{
            inst.tabs[i].hide();
          }
        }
      };
      obj['add']=function(name,label,dom){
        var oinst = this;
        var inst = this.instance;
        var item = I.ui.TabItem.create(inst.bar,inst.panel,{'name':name,'label':label,'dom':dom});
        inst.tabs[name]=item;
        I.listen(item.getBar(),'click',function(m,e){
          oinst.show(item.getName());
        });
        //this.showDefault();
        return item;
      };
      obj['getInstance'] = function(){return this.instance;};
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});