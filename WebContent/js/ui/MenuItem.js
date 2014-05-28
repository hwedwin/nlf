I.regist({
  'name':'ui.MenuItem',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(p,s){
      var o = I.insert('div',p?p:D.body);
      I.css(o,'border:1px solid #FFF;padding:4px;font-size:9pt;margin:2px;cursor:pointer;background-color:#FFF');
      o.innerHTML = s.label;
      I.listen(o,'mouseover',function(m,e){
        m.style.border = '1px solid #0A246A';
        m.style.backgroundColor = '#B6BDD2';
      });
      I.listen(o,'mouseout',function(m,e){
        m.style.border = '1px solid #FFF';
        m.style.backgroundColor = '#FFF';
      });
      var obj = I.awt.Component.create();
      var instance = {'layer':o,'name':s.name,'label':s.label,'icon':s.icon,'func':s.func,'father':s.father};
      obj['instance'] = instance;
      obj['type']='ui.MenuItem';
      obj['getName']=function(){
        return this.instance.name;
      };
      obj['getInstance'] = function(){return this.instance;};
      I.listen(instance.layer,'click',function(m,e){
        instance.func(instance);
      });
      return obj;
    };
    
    return {
      'create':function(p,s){return create(p,s);}
    };
  }
});