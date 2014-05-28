I.regist({
  'name':'ui.TabItem',
  'need':['awt.Component'],
  'fn':function(W,D){
    var create = function(pbar,ppanel,s){
      var bar = I.insert('span',I.$(pbar));
      I.css(bar,'height:32px;display:block;font-size:12px;float:left;border:1px solid #CCC;margin-left:3px;cursor:pointer;line-height:32px;padding-left:10px;padding-right:10px;background-color:#F8F8F8;-webkit-border-radius:4px 4px 0 0;-moz-border-radius:4px 4px 0 0;border-radius:4px 4px 0 0;');
      bar.innerHTML = s.label;
      var panel = I.insert('div',I.$(ppanel));
      if(s.dom){
        var dm = I.$(s.dom);
        dm = dm.parentNode.removeChild(dm);
        panel.appendChild(dm);
      }
      var obj = I.awt.Component.create();
      var instance = {'name':s.name,'bar':bar,'panel':panel,'onShow':null};
      obj['instance'] = instance;
      obj['type']='ui.TabItem';
      obj['getName']=function(){
        return this.instance.name;
      };
      obj['getBar']=function(){
        return this.instance.bar;
      };
      obj['getPanel']=function(){
        return this.instance.panel;
      };
      obj['setPanel'] = function(m){
        var dm = I.$(m);
        this.instance.panel.appendChild(dm);
      };
      obj['show']=function(){
        var inst = this.instance;
        inst.bar.style.backgroundColor = '#FFF';
        inst.bar.style.color = '#333';
        inst.bar.style.borderBottom = '1px solid #FFF';
        inst.bar.style.fontWeight = 'bold';
        inst.panel.style.display = 'block';
        if(inst.onShow){
          inst.onShow();
        }
      };
      obj['hide']=function(){
        var inst = this.instance;
        inst.bar.style.backgroundColor = '#F8F8F8';
        inst.bar.style.color = '#888';
        inst.bar.style.border = '1px solid #CCC';
        inst.bar.style.fontWeight = 'normal';
        inst.panel.style.display = 'none';
      };
      obj['onShow']=function(f){
        this.instance.onShow = f;
      };
      obj['getInstance'] = function(){return this.instance;};
      return obj;
    };
    
    return {
      'create':function(pbar,ppanel,s){return create(pbar,ppanel,s);}
    };
  }
});