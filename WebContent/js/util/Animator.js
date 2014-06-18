/**
 * I.util.Animator
 * <i>动画制作类</i>
 * var o = I.util.Animator.create();
 * o.change('linear',func,callback,10,1,10);
 */
I.regist('util.Animator',function(W,D){
  var create = function(){
    var q = I.util.Sleep.create();
    var o = {};
      
    /**
     * 改变大小，仅数值变动
     * @param tween 方式
     * @param func 变化时执行
     * @param callback 变化完成后执行
     * @param times 花多少次完成变化
     * @param ow 原宽度
     * @param oh 原高度
     * @param tw 变化后的宽度
     * @param th 变化后的高度
     */
    o['resize'] = function(tween,func,callback,times,ow,oh,tw,th){
      if(!I.util.Tween.isSupport(tween)){
        throw 'util.Tween not support:'+tween;
      }
      q.exec(function(){
        q.lock();
        var t = 0;
        var inst = this;
        var timer = null;
        timer = W.setInterval(function(){
          var method = I.util.Tween.types[inst.tween];
          var cw = Math.floor(method(t,inst.ow,inst.tw,inst.time));
          var ch = Math.floor(method(t,inst.oh,inst.th,inst.time));
          func.call(inst,cw,ch);
          t++;
          if(t>inst.time){
            W.clearInterval(timer);
            if(inst.callback){
              inst.callback.call(inst);
            }
            q.unlock();
          }
        },12);
      },{
        'tween':tween,
        'time':times,
        'ow':ow,
        'oh':oh,
        'tw':tw-ow,
        'th':th-oh,
        'callback':callback
      });
      return this;
    };
      
    /**
     * 移动，仅数值变动
     * @param tween 方式
     * @param func 变化时执行
     * @param callback 变化完成后执行
     * @param times 花多少次完成变化
     * @param ox 原x坐标
     * @param oy 原y坐标
     * @param tx 变化后的x坐标
     * @param ty 变化后的y坐标
     */
    o['move'] = function(tween,func,callback,times,ox,oy,tx,ty){
      return this.resize(tween, func, callback, times, ox, oy, tx, ty);
    };
      
    /**
     * 改变数值
     * @param tween 方式
     * @param func 变化时执行
     * @param callback 变化完成后执行
     * @param times 花多少次完成变化
     * @param ov 原值
     * @param tv 变化后的值
     */
    o['change'] = function(tween,func,callback,times,ov,tv){
      return this.resize(tween, func, callback, times, ov, 0, tv, 0);
    };
    return o;
  };
  return {
    create:function(){return create();}
  };
}+'');