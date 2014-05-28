var FIND = {
    'finding':false,
    'pool':[],
    'load':function(u,args,tgt,noLoad){
      var ntgt = tgt;
      if(!ntgt){
        try{
          ntgt = FIND_CONTAINER;
        }catch(e){
          ntgt = document.body;
        }
      }
      var instance = this;
      instance.finding = true;
      I.get(['net.SilentRmi','util.Render'],function(o){
        if(!noLoad){
          I.util.Render.write(ntgt,'<div style="margin:5px;text-align:center"><img src="'+I.ROOT+'/img/loading.gif" /></div>');
        }
        if(args){
          for(var i in args){
            I.net.SilentRmi.set(i,args[i]);
          }
        }
        I.net.SilentRmi.find(u,function(r){
          instance.finding = false;
          I.util.Render.write(ntgt,'<b>&nbsp;</b>'+r);
          var bt = I.$(ntgt).firstChild;
          bt.parentNode.removeChild(bt);
          var scriptBegin = I.$('name','scriptBegin');
          if(scriptBegin&&scriptBegin.length>0){
            var sbs = [];
            I.each(scriptBegin,function(m,i){
              sbs.push(m);
              try{
                var script = I.insert('script',I.$('tag','head')[0]);
                script.text = m.value;
                script.parentNode.removeChild(script);
              }catch(e){
                window.alert(e);
              }
            });
            while(sbs.length>0){
              var sb = sbs.shift();
              sb.parentNode.removeChild(sb);
            }
          }
          instance.loadNext();
        },function(r){
          instance.finding = false;
          instance.loadNext();
        });
      });
    },
    'loadNext':function(){
      if(this.pool.length>0){
        var qq = this.pool.shift();
        this.load(qq.u, qq.args, qq.tgt,qq.noLoad);
      }
    }
};
var find = function(u,args,tgt,noLoad){
  I.get('util.UUID',function(o){
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
    FIND.pool.push({'u':u,'args':arg,'tgt':tgt,'noLoad':noLoad});
    if(FIND.finding){
      return;
    }
    FIND.loadNext();
  });
};