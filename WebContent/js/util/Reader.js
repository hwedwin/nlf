I.regist({
  'name':'util.Reader',
  'need':null,
  'fn':function(W,D){
    var from = function(a){
      if(1==a.length){
        a = a[0];
      }
      var q = {};
      I.each(a,function(m,i){
        var o = I.$(m);
        var r = '';
        if (o) {
          switch (o.tagName.toLowerCase()) {
          case 'img':
            r = o.src;
            break;
          case 'input':
            r = o.value;
            break;
          case 'select':
            r = o.value;
            break;
          case 'textarea':
            r = o.value;
            break;
          default:
            r = o.innerHTML;
            break;
          }
        }
        q[m.id?m.id:m]=r;
      });
      return q;
    };
    
    return {
      'from':function(){return from(arguments);}
    };
  }
});