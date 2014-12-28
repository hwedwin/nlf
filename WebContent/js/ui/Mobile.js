/**
 * I.ui.Mobile
 * <i>移动页面</i>
 */
I.regist('ui.Mobile',function(W,D){
  var CFG = {
    skin:'MobileDefault'
  };
  var hasTouch = 'ontouchstart' in W,
      E_START = hasTouch ? 'touchstart' : 'mousedown',
      E_MOVE = hasTouch ? 'touchmove' : 'mousemove',
      E_END = hasTouch ? 'touchend' : 'mouseup';
  var UP = 1,DOWN = 2;
  function Scroll(who){
    this.wrapper = who;
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
    this._reset = function(){
      if(that.dir==UP){
        var r = I.region(that.fl);
        var pr = I.region(that.wrapper);
        if(r.y+r.height<pr.y+pr.height){
          var h = 0;
          for(var i=0,n=that.fcs.length;i<n;i++){
            var q = that.fcs[i];
            h += I.region(q).height;
            if(i>0){
              h += parseInt(I.getStyle(q,'marginTop'),10);
            }
            h += parseInt(I.getStyle(q,'marginBottom'),10);
          }
          var y = pr.height-h;
          console.log(y);
          if(y>that.orgY){
            y = that.orgY;
          }
          var oldTop = that.top;
          that.top = y;
          that._scrollTo('quadraticIn',oldTop,that.top,4);
        }
        return;
      }
      if(that.top<=0){
        return;
      }
      var oldTop = that.top;
      that.top = that.orgY;
      that._scrollTo('quadraticIn',oldTop,that.top,4);
    };
    this._start = function(m,e){
      if(hasTouch){
        e = e.touches[0];
      }
      that.state = 1;
      that.y = e.clientY;
    };
    this._move = function(m,e){
      if(1==that.state||2==that.state){
        if(e.stopPropagation) e.stopPropagation();
        else e.cancelBubble = true;
        if(e.preventDefault) e.preventDefault();
        else e.returnValue = false;
        that.state = 2;
        if(hasTouch){
          e = e.touches[0];
        }
        that.cy = e.clientY;
        var len = that.cy-that.y;
        that.fc.style.marginTop = (that.top+len)+'px';
      }
    };
    this._end = function(m,e){
      if(2==that.state){
        if(e.stopPropagation) e.stopPropagation();
        else e.cancelBubble = true;
        if(e.preventDefault) e.preventDefault();
        else e.returnValue = false;
        var len = that.cy-that.y;
        that.dir = len>0?DOWN:UP;
        that.top += len;
        that._scrollTo('cubicOut',that.top,that.top+len,10,function(){
          that._reset();
        });
      }
      that.state = 0;
      that.y = 0;
      that.cy = 0;
    };
    I.listen(D,E_START,this._start);
    I.listen(D,E_MOVE,this._move);
    I.listen(D,E_END,this._end);
  };
  var _init = function(obj){
    var gp = I.$('class','group');
    if(gp&&gp.length>0){
      for(var i=0;i<gp.length;i++){
        var g = gp[i];
        var a = I.$(g,'*');
        I.listen(a,'click',function(m,e){
          var q = m.parentNode;
          var as = I.$(q,'*');
          for(var j=0;j<as.length;j++){
            I.cls(as[j],m==as[j]?'active':'');
          }
        });
      }
    }
    var as = I.$('class','a');
    if(as&&as.length>0){
      I.listen(as,'click',function(m,e){
        var href = m.getAttribute('data-href');
        if(href){
          self.location = href;
        }
      });
    }
    var ats = I.$('tag','article');
    if(ats&&ats.length>0){
      for(var i=0,j=ats.length;i<j;i++){
        new Scroll(ats[i]);
      }
    }
  };
  var _render = function(config){
    var obj = {config:null};
    var cfg = I.ui.Component.initConfig(config,CFG);
    obj.config = cfg;
    I.util.Skin.init(cfg.skin);
    _init(obj);
    return obj;
  };
  return {
    render:function(cfg){return _render(cfg);}
  };
}+'');