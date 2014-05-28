/**
 * 缓动算法类
 * t 当前时间
 * b 初始值
 * c 变化量
 * d 持续时间
 */
I.regist({
  'name':'util.Tween',
  'need':null,
  'fn':function(W,D){
    var o = {
        'types':{}
    };
    
    /**
     * 线性运动
     */
    o.types['linear'] = function(t,b,c,d){
      return c*t/d+b;
    };
    
    o.types['quadraticIn'] = function(t,b,c,d){
      return c*(t/=d)*t+b;
    };
    
    o.types['quadraticOut'] = function(t,b,c,d){
      return -c*(t/=d)*(t-2)+b;
    };
    
    o.types['quadraticInOut'] = function(t,b,c,d){
      if ((t/=d/2)<1) return c/2*t*t+b;
      return -c/2*((--t)*(t-2)-1)+b;
    };
    
    o.types['cubicIn'] = function(t,b,c,d){
      return c*(t/=d)*t*t+b;
    };
    
    o.types['cubicOut'] = function(t,b,c,d){
      return c*((t=t/d-1)*t*t+1)+b;
    };
    
    o.types['cubicInOut'] = function(t,b,c,d){
      if ((t/=d/2)<1) return c/2*t*t*t+b;
      return c/2*((t-=2)*t*t+2)+b;
    };
    
    o.types['quarticIn'] = function(t,b,c,d){
      return c*(t/=d)*t*t*t+b;
    };
    
    o.types['quarticOut'] = function(t,b,c,d){
      return -c*((t=t/d-1)*t*t*t-1)+b;
    };
    
    o.types['quarticInOut'] = function(t,b,c,d){
      if ((t/=d/2)<1) return c/2*t*t*t*t+b;
      return -c/2*((t-=2)*t*t*t-2)+b;
    };
    
    o.types['quinticIn'] = function(t,b,c,d){
      return c*(t/=d)*t*t*t*t+b;
    };
    
    o.types['quinticOut'] = function(t,b,c,d){
      return c*((t=t/d-1)*t*t*t*t+1)+b;
    };
    
    o.types['quinticInOut'] = function(t,b,c,d){
      if ((t/=d/2)<1) return c/2*t*t*t*t*t+b;
      return c/2*((t-=2)*t*t*t*t+2)+b;
    };
    
    o.types['sinusoidalIn'] = function(t,b,c,d){
      return -c*Math.cos(t/d*(Math.PI/2))+c+b;
    };
    
    o.types['sinusoidalOut'] = function(t,b,c,d){
      return c*Math.sin(t/d*(Math.PI/2))+b;
    };
    
    o.types['sinusoidalInOut'] = function(t,b,c,d){
      return -c/2*(Math.cos(Math.PI*t/d)-1)+b;
    };
    
    o.types['exponentialIn'] = function(t,b,c,d){
      return (t==0)?b:c*Math.pow(2,10*(t/d-1))+b;
    };
    
    o.types['exponentialOut'] = function(t,b,c,d){
      return (t==d)?b+c:c*(-Math.pow(2,-10*t/d)+1)+b;
    };
    
    o.types['exponentialInOut'] = function(t,b,c,d){
      if (t==0) return b;
      if (t==d) return b+c;
      if ((t/=d/2)<1) return c/2*Math.pow(2,10*(t-1))+b;
      return c/2*(-Math.pow(2,-10*--t)+2)+b;
    };
    
    o.types['circularIn'] = function(t,b,c,d){
      return -c*(Math.sqrt(1-(t/=d)*t)-1)+b;
    };
    
    o.types['circularOut'] = function(t,b,c,d){
      return c*Math.sqrt(1-(t=t/d-1)*t)+b;
    };
    
    o.types['circularInOut'] = function(t,b,c,d){
      if ((t/=d/2)<1) return -c/2*(Math.sqrt(1-t*t)-1)+b;
      return c/2*(Math.sqrt(1-(t-=2)*t)+1)+b;
    };
    
    /**
     * 跳动，幅度由小变大
     */
    o.types['bounceIn'] = function(t,b,c,d){
      return c-o.types['bounceOut'](d-t,0,c,d)+b;
    };
    
    /**
     * 跳动，幅度由大变小
     */
    o.types['bounceOut'] = function(t,b,c,d){
      if ((t/=d)<(1/2.75)){
        return c*(7.5625*t*t) + b;
      }else if(t<(2/2.75)){
        return c*(7.5625*(t-=(1.5/2.75))*t+.75)+b;
      }else if(t<(2.5/2.75)){
        return c*(7.5625*(t-=(2.25/2.75))*t+.9375)+b;
      }else{
        return c*(7.5625*(t-=(2.625/2.75))*t+.984375)+b;
      }
    };
    
    /**
     * 跳动，幅度由小变大，再由大变小
     */
    o.types['bounceInOut'] = function(t,b,c,d){
      if (t<d/2) return o.types['bounceIn'](t*2,0,c,d)*.5+b;
      else return o.types['bounceOut'](t*2-d,0,c,d)*.5+c*.5+b;
    };
    
    /**
     * 是否支持某种算法
     * @param tp 算法名称
     */
    o['isSupport'] = function(tp){
      for(var i in this.types){
        if(i==tp){
          return true;
        }
      }
      return false;
    };
    return o;
  }
});