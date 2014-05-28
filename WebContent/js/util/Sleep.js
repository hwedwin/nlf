/**
 * 延时执行
 * var o = I.util.Sleep.create();
 * o.exec(func);
 * o.exec(function(){
 *  o.lock();
 *  o.unlock();
 * });
 * o.sleep(time);
 * o.exec(func);
 */
I.regist({
  'name':'util.Sleep',
  'need':null,
  'fn':function(W,D){
    var create = function(){
      var o = {
          'queue':[],
          'isRunning':false,
          'isLocked':false
      };
      var RAF = 
        W.requestAnimationFrame||
        W.webkitRequestAnimationFrame||
        W.mozRequestAnimationFrame||
        W.oRequestAnimationFrame||
        W.msRequestAnimationFrame||
        function(callback){
          W.setTimeout(callback,1);
        };
      
      /**
       * 执行调用，私有方法，不建议直接使用
       */
      o['_exec'] = function(){
        var inst = this;
        if(inst.isLocked){
          RAF(function(){
            inst._exec();
          });
          return;
        }
        if(inst.queue.length<1){
          inst.isRunning = false;
          return;
        }
        inst.isRunning = true;
        var q = inst.queue.shift();
        q._func.call(q);
      };
      
      /**
       * 添加调用到队列，私有方法，不建议直接使用
       * @param func 调用方法体
       * @param args 调用参数
       */
      o['_add'] = function(func,args){
        var inst = this;
        var q = {};
        if(args){
          for(var i in args){
            q[i] = args[i];
          }
        }
        q['_func'] = func;
        inst.queue.push(q);
        if(!inst.isRunning){
          inst._exec();
        }
      };
      
      /**
       * 锁定，处于锁定状态时，后续的调用将被挂起
       */
      o['lock'] = function(){
        this.isLocked = true;
      };
      
      /**
       * 解除锁定，解除锁定后，后续的调用将执行
       */
      o['unlock'] = function(){
        this.isLocked = false;
      };
      
      /**
       * 休息指定毫秒数之后，再执行后续调用
       * @param ms 休息毫秒数
       */
      o['sleep'] = function(ms){
        var inst = this;
        inst._add(function(){
          W.setTimeout(function(){
            inst._exec();
          },this.time);
        },{'time':ms});
      };
      
      /**
       * 如果队列为空，则立即执行，否则，等到队列中执行完毕后再执行
       * @paran func 要执行的方法体
       * @param args 要传入的参数，形如{a:1,b:'2'}
       */
      o['exec'] = function(func,args){
        var inst = this;
        var q = {};
        if(args){
          for(var i in args){
            q[i] = args[i];
          }
        }
        q['func'] = func;
        inst._add(function(){
          this.func.call(this);
          inst._exec();
        },q);
      };
      return o;
    };
    return {
      'create':function(){return create();}
    };
  }
});