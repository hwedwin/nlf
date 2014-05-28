I.regist({
  'name':'util.Render',
  'need':null,
  'fn':function(W,D){
    var rendOne = function(o,s){
      switch (o.tagName.toLowerCase()) {
      case 'img':
        o.src = s;
        break;
      case 'input':
        o.value = s;
        break;
      case 'select':
        o.value = s;
        break;
      case 'textarea':
        o.value = s;
        break;
      default:
        o.innerHTML = s;
        break;
      }
    };
    var rendA = function(r) {
      for(var i in r) {
        var o = I.$(i);
        if (o) {
          rendOne(o,r[i]);
        }
      }
    };
    
    var write = function(a){
      switch(a.length){
      case 1:
        rendA(a[0]);
        break;
      case 2:
        rendOne(I.$(a[0]),a[1]);
        break;
      }
    };
    
    var clear = function(a){
      I.each(a,function(m,i){
        if(Object.prototype.toString.apply(m) === '[object Array]'){
          clear(m);
        }else{
          rendOne(I.$(a[0]),'');
        }
      });
    };
    
    return {
      'write':function(){write(arguments);},
      'clear':function(){clear(arguments);}
    };
  }
});