I.regist({
  'name':'net.Rmi',
  'need':['net.MultiAjax','ui.Tip','ui.Loading','util.Reader','util.Hide'],
  'fn':function(W,D){
    var D = I.ROOT;
    var set = function(k,v){
      I.net.MultiAjax.set(k,v);
    };
    var find = function(u,f,ef){
      var ld = I.ui.Loading.create();
      u = D+'/'+u;
      I.net.MultiAjax.pfind(u,function(mr){
        ld.close();
        if(404==mr.status){
          if(ef){
            I.ui.Tip.create({'data':null,'msg':'404:请求的资源未找到'},ef);
          }else{
            I.ui.Tip.create({'data':null,'msg':'404:请求的资源未找到','type':2});
          }
          return;
        }
        var r = mr.xhr;
        try{
          var o = r.responseText;
          o = I.util.Hide.show(s);
          if(f) f(o);
        }catch(e){
          if(ef){
            ef(e);
          }else{
            //I.ui.Tip.create({'data':null,'msg':e,'type':2});
          }
        }
      });
    };
    var call = function(c,m,f,ef){
      var ld = I.ui.Loading.create();
      var u = D+'/'+c+'/'+m;
      I.net.MultiAjax.post(u,function(mr){
        ld.close();
        if(404==mr.status){
          if(ef){
            I.ui.Tip.create({'data':null,'msg':'404:请求的资源未找到'},ef);
          }else{
            I.ui.Tip.create({'data':null,'msg':'404:请求的资源未找到','type':2});
          }
          return;
        }
        var r = mr.xhr;
        try{
          var s = r.responseText;
          s = I.util.Hide.show(s);
          var o = I.net.Ajax.json(s);
          if(o.xtype){
            if(o.success){
              I.get(o.xtype,function(oo){
                oo.create(o);
              });
              if(f){
                f(o.data);
              }
            }else{
              if(ef){
                I.ui.Tip.create(o,ef);
              }else{
                I.ui.Tip.create(o);
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
              }//else{
                //I.ui.Tip.create(o);
              //}
            }
          }
        }catch(e){
          if(ef){
            I.ui.Tip.create({'data':null,'msg':e},ef);
          }else{
            //I.ui.Tip.create({'data':null,'msg':e,'type':2});
          }
        }
      });
    };
    var from = function(a){
      var q = I.util.Reader.from(a);
      for(var i in q){
        set(i,q[i]);
      }
    };
    return {
      'version':'1.1.0',
      'set':function(k,v){set(k,v);},
      'from':function(){from(arguments);},
      'call':function(c,m,f,ef){call(c,m,f,ef);},
      'find':function(u,f,ef){find(u,f,ef);}
    };
  }
});