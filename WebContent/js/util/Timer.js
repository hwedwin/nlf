I.regist({
  'name':'util.Timer',
  'need':['lang.Thread','util.UUID'],
  'fn':function(W,D){
    var create = function(v){
      var o = {
        'name':I.util.UUID.next(),
        'heep':{},
        'queue':[],
        'running':false,
        'get':function(key){
          return this.heep[key];
        },
        'set':function(key,value){
          this.heep[key] = value;
        },
        'add':function(ef,uf,sleep){
          if(this.running) return;
          this.queue.push({'ef':ef,'uf':uf,'sleep':sleep?sleep:12});
        },
        'exe':function(){
          if(this.running) return;
          this.running = true;
          var instance = this;
          var next = function(){
            if(instance.queue.length>0){
              var c = instance.queue.shift();
              I.lang.Thread.create(instance.name,function(l){
                if(c.uf(instance.heep,l)){
                  I.lang.Thread.get(instance.name).stop();
                  next();
                }else{
                  c.ef(instance.heep,l);
                }
              },c.sleep).start();
            }else{
              instance.running=false;
            }
          };
          next();
        }
      };
      if(v){
        for(var i in v){
          o.heep[i] = v[i];
        }
      }
      return o;
    };
    
    return {
      'create':function(v){return create(v);}
    };
  }
});