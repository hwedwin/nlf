I.regist({
  'name':'ui.Tip',
  'need':['awt.Component'],
  'fn':function(W,D){
    var TIME = {
      'T0':8000,
      'T1':3000,
      'T2':1000
    };
    var Q = {};
    var create = function(args){
      var obj = I.awt.Component.create();
      var instance = null;
      var init = function(){
        var skin = I.util.Skin.getSkin();
        var css = skin.get('ui.Tip.css');
        var sb = args[0].msg?args[0].msg:args[0];
        var t = args[0].type?args[0].type:1;
        var f = null;
        if(args.length>1){
          f = args[1];
        }
        var l = 0;
        while(Q['I'+l]){
          l++;
        }
        var tip = {'layer':null,'timer':null,'idx':l};
        I.style(css);
        var o = I.insert('div');
        I.cls(o,'i-ui-Tip');
        o.innerHTML = '<b></b><i></i><u></u><q></q><p></p><q></q><u></u><i></i><b></b>';
        I.$(o,'tag','p')[0].innerHTML = sb;
        
        var nr = I.region(o);
        var pr = I.region();
        o.style.left = Math.max(Math.floor(pr.x+pr.width/2-nr.width/2),0)+'px';
        o.style.top = Math.floor(pr.y+10)+'px';
        tip.layer = o;
        var op = 0;
        I.opacity(o,op);
        tip.timer = W.setInterval(function(){
          if(op<100){
            op+=2;
            I.opacity(o,op);
          }else{
            I.opacity(o,100);
            W.clearInterval(tip.timer);
            if(f){
              f(args[0]);
            }
            tip.timer = W.setTimeout(function(){
              tip.layer.parentNode.removeChild(tip.layer);
              delete Q['I'+tip.idx];
            },TIME['T'+t]);
          }
        },20);
        Q['I'+tip.idx] = 'true';
        instance = tip;
      };
     
      init();
      
      obj['getInstance'] = function(){return instance;};
      return obj;
    };
    
    var preCreate = function(a){
      var p = self;
      var n = null;
      while(p){
        if(p!=self){
          if(p.I){
            n = p.I;
          }else{
            break;
          }
        }
        if(p.parent == p) break;
        p = p.parent;
      }
      if(n){
        n.get('ui.Tip',function(t){
          if(a.length>1){
            t.create(a[0],a[1]);
          }else{
            t.create(a[0]);
          }
        });
      }else{
        create(a);
      }
    };
    
    return {
      'create':function(){return preCreate(arguments);}
    };
  }
});