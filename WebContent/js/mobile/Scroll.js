/**
 * I.mobile.Scroll
 * <i>给容器增加滚动效果</i>
 */
I.regist('mobile.Scroll',function(W,D){
  var CFG = {
      skin:'MobileDefault'
  };
  var hasTouch = 'ontouchstart' in W,
      E_START = hasTouch ? 'touchstart' : 'mousedown',
      E_MOVE = hasTouch ? 'touchmove' : 'mousemove',
      E_END = hasTouch ? 'touchend' : 'mouseup';
  var UP = 1,DOWN = 2;
  function Scroll(who,cfg){
    this.wrapper = who;
    this.config = cfg;
    this.fcs = I.$(who,'*');
    if(!this.fcs||this.fcs.length<1){
      return;
    }
    who.style.overflow = 'hidden';
    who.ondragstart = function(){return false;};
    who.onselectstart = function(){return false;};
    this.fc = this.fcs[0];
    this.fl = this.fcs[this.fcs.length-1];
    this.orgY = parseInt(I.getStyle(this.fc,'marginTop'),10);
    this.top = this.orgY;
    this.y = 0;
    this.cy = 0;
    this.state = 0;
    this.dir = -1;
    var that = this;
    this.scrollBar = I.insert('div',this.wrapper);
    I.cls(this.scrollBar,'i-mobile-ScrollBar-'+cfg.skin);
    this._scrollTo = function(tween,from,to,times,callback){
      var ani = I.util.Animator.create();
      ani.change(tween,function(n){
        that.fc.style.marginTop = n+'px';
      },
      function(){
        that.fc.style.marginTop = to+'px';
        if(callback){
          callback.call(callback);
        }
      },
      times,
      from,
      to
      );
    };
    this._getScrollerHeight = function(){
      var h = 0;
      for(var i=0,n=that.fcs.length;i<n;i++){
        var q = that.fcs[i];
        h += I.region(q).height;
        if(i>0){
          h += parseInt(I.getStyle(q,'marginTop'),10);
        }
        h += parseInt(I.getStyle(q,'marginBottom'),10);
      }
      return h;
    };
    this._reset = function(){
      switch(that.dir){
        case UP://向上滑
          var r = I.region(that.fl);
          var pr = I.region(that.wrapper);
          if(r.y+r.height<pr.y+pr.height){
            var h = that._getScrollerHeight();
            var y = pr.height-h;
            if(y>that.orgY){
              y = that.orgY;
            }
            var from = that.top;
            that.top = y;
            that._scrollTo('quadraticIn',from,that.top,4,function(){
              if(that.top==that.orgY){
                that.scrollBar.style.visibility = 'hidden';
              }else{
                that.scrollBar.style.top = (pr.y+((0-that.top)/h*pr.height))+'px';
              }
            });
          }
          break;
        case DOWN://向下滑
          if(that.top>that.orgY){
            var from = that.top;
            that.top = that.orgY;
            that._scrollTo('quadraticIn',from,that.top,4,function(){
              that.scrollBar.style.visibility = 'hidden';
            });
          }
          break;
      }
    };
    this._start = function(m,e){
      if(hasTouch){
        e = e.touches[0];
      }
      that.state = 1;
      that.y = e.clientY;
      return true;
    };
    this._move = function(m,e){
      if(1==that.state||2==that.state){
        that.state = 2;
        if(hasTouch){
          e = e.touches[0];
        }
        that.cy = e.clientY;
        var len = that.cy-that.y;
        var pr = I.region(that.wrapper);
        that.fc.style.marginTop = (that.top+len)+'px';
        var h = that._getScrollerHeight();
        that.scrollBar.style.height = (pr.height*pr.height/h)+'px';
        that.scrollBar.style.top = (pr.y+((0-(that.top+len))/h*pr.height))+'px';
        that.scrollBar.style.visibility = 'visible';
        return false;
      }
    };
    this._end = function(m,e){
      var st = that.state;
      if(2==st){
        var len = that.cy-that.y;
        that.dir = len>0?DOWN:UP;
        that.top += len;
        that._scrollTo('linear',that.top,that.top+len,10,function(){
          that._reset();
        });
      }
      that.state = 0;
      that.y = 0;
      that.cy = 0;
      return 2!=st;
    };
    I.listen(D,E_START,this._start);
    I.listen(D,E_MOVE,this._move);
    I.listen(D,E_END,this._end);
  };
  var _prepare = function(wrapper,config){
    var cfg = I.ui.Component.initConfig(config,CFG);
    I.util.Skin.init(cfg.skin);
    var obj = new Scroll(wrapper,cfg);
    return obj;
  };
  return {
    create:function(wrapper,cfg){return _prepare(wrapper,cfg);}
  };
}+'');