I.regist({
  'name':'ui.Notify',
  'need':null,
  'fn':function(W,D){
    var Notify = {
        'loaded':false,
        'instance':null,
        'isSupport':function(){
          if(!this.loaded){
            this.instance = W.Notifications;
            this.loaded = true;
          }
          if(!this.instance){
            return false;
          }
          var ps = this.instance.checkPermission();
          if(0==ps||1==ps){
            return true;
          }
          return false;
        },
        'show':function(icon,title,content){
          var inst = this.instance;
          var ps = inst.checkPermission();
          switch(ps){
          case 0:
            var m = inst.createNotification(icon,title,content);
            m.show();
            break;
          case 1:
            var div = I.insert('div');
            div.innerHTML = '通知权限设置';
            I.css(div,'position:absolute;right:0;bottom:0;width:120px;height:30px;background-color:#C40000;color:#FFF;cursor:pointer;text-align:center;line-height:30px');
            div.onclick = function(){
              this.parentNode.removeChild(this);
              inst.requestPermission(function(){
                var ns = inst.checkPermission();
                if(0==ns){
                  var n = inst.createNotification(icon,title,content);
                  n.show();
                }
              });
            };
            break;
          }
        }
    };
    var webkitNotify = {
        'loaded':false,
        'instance':null,
        'isSupport':function(){
          if(!this.loaded){
            this.instance = W.webkitNotifications;
            this.loaded = true;
          }
          if(!this.instance){
            return false;
          }
          var ps = this.instance.checkPermission();
          if(0==ps||1==ps){
            return true;
          }
          return false;
        },
        'show':function(icon,title,content){
          var inst = this.instance;
          var ps = inst.checkPermission();
          switch(ps){
          case 0:
            var m = inst.createNotification(icon,title,content);
            m.show();
            break;
          case 1:
            var div = I.insert('div');
            div.innerHTML = '通知权限设置';
            I.css(div,'position:absolute;right:0;bottom:0;width:120px;height:30px;background-color:#C40000;color:#FFF;cursor:pointer;text-align:center;line-height:30px');
            div.onclick = function(){
              this.parentNode.removeChild(this);
              inst.requestPermission(function(){
                var ns = inst.checkPermission();
                if(0==ns){
                  var n = inst.createNotification(icon,title,content);
                  n.show();
                }
              });
            };
            break;
          }
        }
    };
    
    var otherNotify = {
        'loaded':false,
        'instance':null,
        'isSupport':function(){
          return true;
        },
        'show':function(icon,title,content){
          var ot = D.title;
          var b = false;
          var timer = W.setInterval(function(){
            D.title = b?title:'有消息';
            b = !b;
          },1000);
          I.listen(D,'mousedown',function(){
            if(timer){
              W.clearInterval(timer);
              timer = null;
              D.title = ot;
            }
          });
        }
    };
    
    var LIBS = [Notify,webkitNotify,otherNotify];
    
    var show = function(icon,title,content){
      I.each(LIBS,function(m,i){
        if(m.isSupport()){
          m.show(icon,title,content);
          return I.BREAK;
        }
      });
    };
    return {
      'show':function(icon,title,content){show(icon,title,content);}
    };
  }
});