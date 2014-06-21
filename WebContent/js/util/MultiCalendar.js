/**
 * I.util.MultiCalendar
 * <i>双日历控件</i>
 */
I.regist('util.MultiCalendar',function(W,D){
    var mask = null,layer = null,panel = null,blockL = null,blockR = null,tbar = null,current = null;
    var skin = {
        bg:{
          tbar:'#4664EA',
          normal:'#FFFFFF',
          today:'#FFF5D1',
          weekend:'#FFFFFF',
          choosed:'#629BE0'
        },
        fg:{
          normal:'#4664EA',
          today:'#4664EA',
          weekend:'#FA824F',
          choosed:'#FFF'
        },
        border:{
          normal:'1px solid #FFF',
          today:'1px solid #FFE4A9',
          weekend:'1px solid #FFF',
          choosed:'1px solid #36557B'
        }
    };
    var HD = {
        "01-01" : "元旦节",
        "02-14" : "情人节",
        "03-08" : "妇女节",
        "03-12" : "植树节",
        "04-01" : "愚人节",
        "05-01" : "劳动节",
        "05-04" : "青年节",
        "06-01" : "儿童节",
        "08-01" : "建军节",
        "09-10" : "教师节",
        "10-01" : "国庆节",
        "12-24" : "平安夜",
        "12-25" : "圣诞节"
    };
    var input = [];
    var s2d = function(s){
      s = s.split('-');
      if(s.length!=3) return new Date();
      return new Date(s[0]+'/'+s[1]+'/'+s[2]);
    };
    var format = function(s){
      return (s<10?'0':'')+s;
    };
    var repaintL = function(a){
      try{
        var ch = s2d(current.value);
        var chy = ch.getFullYear();
        var chm = ch.getMonth()+1;
        var chd = ch.getDate();
        //var w = a.getDay();
        var y = a.getFullYear();
        var m = a.getMonth()+1;
        //var d = a.getDate();
        var b = new Date(y+'/'+m+'/1').getDay();
        var q = new Date(m>1?y:y-1,m>1?m-1:12,0).getDate();
        var c = new Date(y,m,0).getDate();
        var g = new Date();
        var tb = I.$(tbar,'tag','b');
        var bt = I.$(blockL,'tag','b');
        tb[1].innerHTML = y+'年'+m+'月';
        tb[1].setAttribute('y',y);
        tb[1].setAttribute('m',m);
        var f = 1;
        var l = 1;
        for(var i=0;i<42;i++){
          var o = bt[7+i];
          o.style.cursor = 'pointer';
          o.style.backgroundColor = skin.bg.normal;
          o.style.border = skin.border.normal;
          o.style.color = skin.fg.normal;
          o.onmouseover = null;
          o.onmouseout = null;
          o.onmousedown = function(){
            set(this.getAttribute('d'));
          };
          if(i<b){
            o.style.color = skin.bg.normal;
            o.innerHTML = q+1-b+i;
            o.setAttribute('d',(m>1?y:y-1)+'-'+format(m>1?m-1:12)+'-'+format(q+1-b+i));
          }else if(f<=c){
            o.innerHTML = f;
            if(g.getFullYear()==y && g.getMonth()+1==m && g.getDate()==f){
              o.style.backgroundColor = skin.bg.today;
              o.style.border = skin.border.today;
              o.style.color = skin.fg.today;
            }else if(chy==y&&chm==m&&chd==f){
              o.style.backgroundColor = skin.bg.choosed;
              o.style.border = skin.border.choosed;
              o.style.color = skin.fg.choosed;
            }else if(new Date(y+'/'+m+'/'+f).getDay()%6 == 0){
              o.style.color = skin.fg.weekend;
            }else{
              o.onmouseover = function(){
                this.style.backgroundColor = skin.fg.normal;
                this.style.color = skin.bg.normal;
              };
              o.onmouseout = function(){
                this.style.color = skin.fg.normal;
                this.style.backgroundColor = skin.bg.normal;
              };
            }
            o.setAttribute('d',y+'-'+format(m)+'-'+format(f++));
            try{
              var ns = o.getAttribute('d').substr(5);
              if(HD[ns]){
                o.innerHTML = HD[ns];
              }
            }catch(e){}
          }else{
            o.style.color = skin.bg.normal;
            o.innerHTML = l;
            o.setAttribute('d',(m<12?y:y+1)+'-'+format(m<12?m+1:1)+'-'+format(l++));
          }
        }
      }catch(e){}
    };
    
    var repaintR = function(a){
      try{
        var ch = s2d(current.value);
        var chy = ch.getFullYear();
        var chm = ch.getMonth()+1;
        var chd = ch.getDate();
        //var w = a.getDay();
        var y = a.getFullYear();
        var m = a.getMonth()+2;
        if(m>12){
          y++;
          m=1;
        }
        //var d = a.getDate();
        var b = new Date(y+'/'+m+'/1').getDay();
        var q = new Date(m>1?y:y-1,m>1?m-1:12,0).getDate();
        var c = new Date(y,m,0).getDate();
        var g = new Date();
        var tb = I.$(tbar,'tag','b');
        var bt = I.$(blockR,'tag','b');
        tb[2].innerHTML = y+'年'+m+'月';
        tb[2].setAttribute('y',y);
        tb[2].setAttribute('m',m);
        var f = 1;
        var l = 1;
        for(var i=0;i<42;i++){
          var o = bt[7+i];
          o.style.cursor = 'pointer';
          o.style.backgroundColor = skin.bg.normal;
          o.style.border = skin.border.normal;
          o.style.color = skin.fg.normal;
          o.onmouseover = null;
          o.onmouseout = null;
          o.onmousedown = function(){
            set(this.getAttribute('d'));
          };
          if(i<b){
            o.style.color = skin.bg.normal;
            o.innerHTML = q+1-b+i;
            o.setAttribute('d',(m>1?y:y-1)+'-'+format(m>1?m-1:12)+'-'+format(q+1-b+i));
          }else if(f<=c){
            o.innerHTML = f;
            if(g.getFullYear()==y && g.getMonth()+1==m && g.getDate()==f){
              o.style.backgroundColor = skin.bg.today;
              o.style.border = skin.border.today;
              o.style.color = skin.fg.today;
            }else if(chy==y&&chm==m&&chd==f){
              o.style.backgroundColor = skin.bg.choosed;
              o.style.border = skin.border.choosed;
              o.style.color = skin.fg.choosed;
            }else if(new Date(y+'/'+m+'/'+f).getDay()%6 == 0){
              o.style.color = skin.fg.weekend;
            }else{
              o.onmouseover = function(){
                this.style.backgroundColor = skin.fg.normal;
                this.style.color = skin.bg.normal;
              };
              o.onmouseout = function(){
                this.style.color = skin.fg.normal;
                this.style.backgroundColor = skin.bg.normal;
              };
            }
            o.setAttribute('d',y+'-'+format(m)+'-'+format(f++));
            try{
              var ns = o.getAttribute('d').substr(5);
              if(HD[ns]){
                o.innerHTML = HD[ns];
              }
            }catch(e){}
          }else{
            o.style.color = skin.bg.normal;
            o.innerHTML = l;
            o.setAttribute('d',(m<12?y:y+1)+'-'+format(m<12?m+1:1)+'-'+format(l++));
          }
        }
      }catch(e){}
    };
    
    var repaint = function(a){
      repaintL(a);
      repaintR(a);
    };
    var show = function(){
      var o = I.region(current);
      var v = I.region();
      layer.style.left = o.x+'px';
      layer.style.top = (o.y+o.height+1)+'px';
      o = I.region(layer);
      if(o.x+o.width>v.x+v.width-1) layer.style.left = (v.x+v.width-o.width-1)+'px';
      o = I.region(layer);
      mask.style.left = o.x+'px';
      mask.style.top = o.y+'px';
      mask.style.width = o.width+'px';
      mask.style.height = o.height+'px';
      mask.innerHTML = '<iframe style="margin:0;padding:0" width="100%" height="100%" border="0" frameborder="0" scrolling="no"></iframe>';
      repaint(s2d(current.value));
    };
    var set = function(s){
      current.value = s;
      hide();
    };
    var hide = function(){
      layer.style.left = '-999px';
      mask.style.left = '-999px';
    };
    var renderLayer = function(){
      var c = 'float:left;height:21px;line-height:21px;overflow:hidden;text-align:center;font-size:12px;border:0;margin:0;padding:0;font-family:微软雅黑,宋体,Simsun;';
      var o = I.insert('div');
      I.css(o,'position:absolute;left:-999px;top:-999px;width:386px;height:217px;margin:0;padding:0;border:0;background-color:#999;overflow:hidden;z-index:2147483647');
      layer = o;
      o = I.insert('div',layer);
      I.css(o,'position:absolute;left:1px;top:1px;width:384px;height:215px;margin:0;padding:0;border:0;background-color:#FFF;overflow:hidden');
      panel = o;
      o = I.insert('div',panel);
      I.css(o,'position:absolute;left:5px;top:3px;width:374px;height:23px;margin:0;padding:0;border:0;background-color:'+skin.bg.tbar+';color:#FFF;overflow:hidden');
      tbar = o;
      
      o = I.insert('b',tbar);
      I.css(o,c+'width:22px;cursor:pointer;color:#FFF;font-weight:bold');
      o.innerHTML = '&lt;';
      I.listen(o,'click',function(m,e){
          var bt = I.$(tbar,'tag','b');
          var year = parseInt(bt[1].getAttribute('y'),10);
          var month = parseInt(bt[1].getAttribute('m'),10);
          if(month==1){
              month = 11;
              year -= 1;
          }else if(month==2){
              month = 12;
              year -= 1;
          }else{
              month -= 2;
          }
          repaint(s2d(year+'-'+month+'-01'));
      });
      o = I.insert('b',tbar);
      I.css(o,c+'width:165px;color:#FFF');
      o = I.insert('b',tbar);
      I.css(o,c+'width:165px;color:#FFF');
      
      o = I.insert('b',tbar);
      I.css(o,c+'width:22px;cursor:pointer;color:#FFF;font-weight:bold');
      o.innerHTML = '&gt;';
      I.listen(o,'click',function(m,e){
          var bt = I.$(tbar,'tag','b');
          var year = parseInt(bt[1].getAttribute('y'),10);
          var month = parseInt(bt[1].getAttribute('m'),10);
          if(month==11){
              month = 1;
              year += 1;
          }else if(month==12){
              month = 2;
              year += 1;
          }else{
              month += 2;
          }
          repaint(s2d(year+'-'+month+'-01'));
      });
      
      o = I.insert('div',panel);
      I.css(o,'position:absolute;left:5px;top:29px;width:182px;height:182px;margin:0;padding:0;border:0;background-color:#4664EA;color:#FFF;overflow:hidden');
      blockL = o;
      
      for(var i=0;i<7;i++){
        o = I.insert('b',blockL);
        I.css(o,'float:left;text-align:center;display:block;width:26px;height:26px;line-height:26px;border:0;background-color:#ECECEC;color:#666666;font-size:12px;margin:0;padding:0;font-family:微软雅黑,宋体,Simsun');
        o.innerHTML = '日一二三四五六'.split('')[i];
        if(i==0||i==6){
          o.style.color = '#FA824F';
        }
      }
      for(var i=0;i<42;i++){
        o = I.insert('b',blockL);
        I.css(o,'text-align:center;font-weight:normal;float:left;width:24px;height:24px;line-height:25px;border:'+skin.border.normal+';background-color:#FFF;color:#005EAD;font-size:12px;margin:0;padding:0;font-family:微软雅黑,宋体,Simsun');
      }
      
      o = I.insert('div',panel);
      I.css(o,'position:absolute;left:191px;top:26px;width:2px;font-size:0;height:189px;margin:0;padding:0;border:0;background-color:#BBB;overflow:hidden');
      
      o = I.insert('div',panel);
      I.css(o,'position:absolute;left:197px;top:29px;width:182px;height:182px;margin:0;padding:0;border:0;background-color:#4664EA;color:#FFF;overflow:hidden');
      blockR = o;
      
      for(var i=0;i<7;i++){
        o = I.insert('b',blockR);
        I.css(o,'float:left;text-align:center;display:block;width:26px;height:26px;line-height:26px;border:0;background-color:#ECECEC;color:#666666;font-size:12px;margin:0;padding:0;font-family:微软雅黑,宋体,Simsun');
        o.innerHTML = '日一二三四五六'.split('')[i];
        if(i==0||i==6){
          o.style.color = '#FA824F';
        }
      }
      for(var i=0;i<42;i++){
        o = I.insert('b',blockR);
        I.css(o,'text-align:center;font-weight:normal;float:left;width:24px;height:24px;line-height:25px;border:'+skin.border.normal+';background-color:#FFF;color:#005EAD;font-size:12px;margin:0;padding:0;font-family:微软雅黑,宋体,Simsun');
      }
      
      I.listen(I.$(panel,'tag','b'),'focus',function(m,e){m.blur();});
    };
  
    (function(){
      var t = I.insert('div');
      I.css(t,'position:absolute;left:-999px;top:0;margin:0;padding:0;z-index:2147483647');
      mask = t;
      renderLayer();
      I.listen(I.$(),'click',function(o,e){
        try{
          o = e.srcElement || e.target;
          var v = false;
          for(var i=0;i<input.length;i++){
            if(o == input[i]){
              v = true;
              break;
            }
          }
          if(v) return;
          v = I.region();
          var x = e.clientX+v.x;
          var y = e.clientY+v.y;
          var m = I.region(layer);
          if(x < m.x || y < m.y || x > m.x+m.width || y > m.y+m.height) hide();
        }catch(ee){}
      });
    })();
  
    var bind = function(o){
      o = I.$(o);
      I.listen(o,'focus',function(m,e){
        current = m;
        show();
      });
      input.push(o);
    };
  
    return {
      bind:function(o){bind(o);}
    };
  }+'');