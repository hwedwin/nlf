/**
 * I.net.SilentRmi
 * <i>安静的AJAX方法调用，不显示loading</i>
 */
I.regist('net.SilentRmi',function(W,D){
  var D = I.ROOT;
  var set = function(k,v){
    I.net.MultiAjax.set(k,v);
  };
  var find = function(u,f,ef){
    u = D+'/'+u;
    I.net.MultiAjax.postFind(u,function(mr){
      switch(mr.status){
        case 404:
          if(ef){
            I.ui.Tip.create({msg:'404:请求的资源未找到',callback:ef});
          }else{
            I.ui.Tip.create({msg:'404:请求的资源未找到'});
          }
          return;
        default:
          var r = mr.xhr;
          var s = r.responseText;
          s = I.util.Hide.show(s);
          if(f){
            f(s);
          }
      }
    });
  };
  var call = function(c,m,f,ef){
    var u = D+'/'+c+'/'+m;
    I.net.MultiAjax.post(u,function(mr){
      switch(mr.status){
        case 404:
          if(ef){
            I.ui.Tip.create({msg:'404:请求的资源未找到',callback:ef});
          }else{
            I.ui.Tip.create({msg:'404:请求的资源未找到'});
          }
          return;
        default:
          var r = mr.xhr;
          var s = r.responseText;
          s = I.util.Hide.show(s);
          var o = I.net.Ajax.json(s);
          if(o.xtype){
            if(o.success){
              I.get(o.xtype,function(){
                this.create(o);
              });
              if(f){
                f(o.data);
              }
            }else{
              if(ef){
                ef(o);
              }else{
                I.get(o.xtype,function(){
                  this.create(o);
                });
              }
            }
          }else{
            if(o.success){
              if(f){
                f(o.data);
              }
            }else{
              if(ef){
                ef(o);
              }
            }
          }
      }
    });
  };
  return {
    set:function(k,v){set(k,v);},
    call:function(c,m,f,ef){call(c,m,f,ef);},
    find:function(u,f,ef){find(u,f,ef);}
  };
}+'');