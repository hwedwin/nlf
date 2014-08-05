/**
 * I.net.Page
 * <i>AJAX请求页面</i>
 */
I.regist('net.Page',function(W,D){
  var FIND = {
    finding:false,
    pool:[],
    load:function(u,args,tgt){
      var ntgt = tgt;
      if(!ntgt){
        try{
          ntgt = I.$(FIND_CONTAINER);
        }catch(e){
          ntgt = D.body;
        }
      }
      var instance = this;
      instance.finding = true;
      if(args){
        for(var i in args){
          I.net.Rmi.set(i,args[i]);
        }
      }
      I.net.Rmi.find(u,function(r){
        instance.finding = false;
        ntgt.innerHTML = '<b>&nbsp;</b>'+r;
        var bt = I.$(ntgt).firstChild;
        bt.parentNode.removeChild(bt);
        var scriptBegin = I.$('name','scriptBegin');
        if(scriptBegin&&scriptBegin.length>0){
          var sbs = [];
          for(var i=0;i<scriptBegin.length;i++){
            var m = scriptBegin[i];
            sbs.push(m);
            var script = I.insert('script',I.$('tag','head')[0]);
            script.text = m.value;
            script.parentNode.removeChild(script);
          }
          while(sbs.length>0){
            var sb = sbs.shift();
            sb.parentNode.removeChild(sb);
          }
        }
        instance.loadNext();
      },function(){
        instance.finding = false;
        instance.loadNext();
      });
    },
    loadNext:function(){
      if(this.pool.length>0){
        var qq = this.pool.shift();
        this.load(qq.u, qq.args, qq.tgt);
      }
    }
  };
  var _find = function(url,args,tgt){
    var uuid = I.util.UUID.next();
    if(!I['_find_mapper']){
      I['_find_mapper'] = {};
    }
    I['_find_mapper'][uuid]=tgt;
    var arg = {};
    arg['_find_uuid']=uuid;
    if(args){
      for(var i in args){
        arg[i]=args[i];
      }
    }
    FIND.pool.push({'u':url,'args':arg,'tgt':tgt});
    if(FIND.finding){
      return;
    }
    FIND.loadNext();
  };
  return {
    find:function(url,args,tgt){_find(url,args,tgt);}
  };
}+'');