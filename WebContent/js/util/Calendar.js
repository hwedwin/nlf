I.regist({
  'name':'util.Calendar',
  'need':null,
  'fn':function(W,D){
    var SIGN = 'i.calendar';
    var mask = null,layer = null,block = null,ypanel = null,mpanel = null,current = null;
    var input = [];
    var s2d = function(s){
      s = s.split('-');
      if(s.length!=3) return new Date();
      return new Date(s[0]+'/'+s[1]+'/'+s[2]);
    };
    var format = function(s){
      return (s<10?'0':'')+s;
    };
    var repaint = function(a){
      try{
        //var w = a.getDay();
        var y = a.getFullYear();
        var m = a.getMonth()+1;
        //var d = a.getDate();
        var b = new Date(y+'/'+m+'/1').getDay();
        var q = new Date(m>1?y:y-1,m>1?m-1:12,0).getDate();
        var c = new Date(y,m,0).getDate();
        var g = new Date();
        var bt = I.$(block,'tag','b');
        bt[1].innerHTML = y;
        bt[3].innerHTML = format(m);
        var f = 1;
        var l = 1;
        for(var i=0;i<42;i++){
          var o = bt[12+i];
          o.style.cursor = 'pointer';
          o.style.backgroundColor = '#FFF';
          o.style.color = '#BBB';
          o.onmouseover = null;
          o.onmouseout = null;
          o.onmousedown = function(){
            set(this.getAttribute('d'));
          };
          if(i<b){
            o.innerHTML = q+1-b+i;
            o.setAttribute('d',(m>1?y:y-1)+'-'+format(m>1?m-1:12)+'-'+format(q+1-b+i));
            o.onmouseover = function(){
              this.style.backgroundColor = '#BBB';
              this.style.color = '#FFF';
            };
            o.onmouseout = function(){
              this.style.color = '#BBB';
              this.style.backgroundColor = '#FFF';
            };
          }else if(f<=c){
            o.style.color = '#0597DB';
            if(new Date(y+'/'+m+'/'+f).getDay()%6 == 0) o.style.color = 'red';
            o.innerHTML = f;
            o.setAttribute('d',y+'-'+format(m)+'-'+format(f++));
            if(g.getFullYear()==y && g.getMonth()+1==m && g.getDate()==f-1){
              o.style.backgroundColor = '#0597DB';
              o.style.color = '#FFF';
              o.onmouseover = function(){
              };
              o.onmouseout = function(){
              };
              continue;
            }
            o.onmouseover = function(){
              this.style.backgroundColor = (this.style.color=='red')?'red':'#0597DB';
              this.style.color = '#FFF';
            };
            o.onmouseout = function(){
              this.style.color = (this.style.backgroundColor=='red')?'red':'#0597DB';
              this.style.backgroundColor = '#FFF';
            };
          }else{
            o.innerHTML = l;
            o.setAttribute('d',(m<12?y:y+1)+'-'+format(m<12?m+1:1)+'-'+format(l++));
            o.onmouseover = function(){
              this.style.backgroundColor = '#BBB';
              this.style.color = '#FFF';
            };
            o.onmouseout = function(){
              this.style.color = '#BBB';
              this.style.backgroundColor = '#FFF';
            };
          }
        }
      }catch(e){}
      ypanel.style.left = '-999px';
      mpanel.style.left = '-999px';
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
      var c = 'float:left;height:21px;line-height:21px;overflow:hidden;text-align:center;font-size:9pt;border:0;margin:0;padding:0;background-color:#FFF;font-family:微软雅黑,宋体,Simsun;';
      var o = I.insert('div');
      I.css(o,'position:absolute;left:-999px;top:-999px;width:191px;height:194px;margin:0;padding:0;border:0;background-color:#079DDD;overflow:hidden;z-index:2147483647');
      layer = o;
      o = I.insert('div',layer);
      I.css(o,'position:absolute;left:1px;top:1px;width:189px;height:192px;margin:0;padding:0;border:0;background-color:#FFF;overflow:hidden');
      block = o;
      o = I.insert('b',block);
      I.css(o,c+'width:45px;cursor:pointer;color:#40ADE0;font-weight:bold');
      o.innerHTML = '&lt;';
      o.setAttribute('title','上一月');
      I.listen(o,'click',function(m,e){
          var bt = I.$(block,'tag','b');
          var year = parseInt(bt[1].innerHTML,10);
          var month = parseInt(bt[3].innerHTML,10);
          if(month<2){
              month = 12;
              year -= 1;
          }else{
              month -= 1;
          }
          repaint(s2d(year+'-'+month+'-01'));
      });
      o = I.insert('b',block);
      I.css(o,c+'width:49px;cursor:pointer;color:#40ADE0;font-weight:bold');
      o.title = '选择年份';
      I.listen(o,'click',function(m,e){
        var d = I.$(ypanel,'tag','div');
        var y = parseInt(I.$(block,'tag','b')[1].innerHTML);
        I.each(d,function(m,i){
          if(i==0) return I.CONTINUE;
          if(i>10) return I.BREAK;
          if(i<6) m.innerHTML = y-6+i;
          else m.innerHTML = y+i-6;
        });
        ypanel.style.left = '0';
      });
      o = I.insert('b',block);
      I.css(o,c+'width:12px;color:#40ADE0;font-weight:bold');
      o.innerHTML = '-';
      o = I.insert('b',block);
      I.css(o,c+'width:38px;cursor:pointer;color:#40ADE0;font-weight:bold');
      o.title = '选择月份';
      I.listen(o,'click',function(m,e){
        mpanel.style.left = '0';
      });
      o = I.insert('b',block);
      I.css(o,c+'width:45px;cursor:pointer;color:#40ADE0;font-weight:bold');
      o.innerHTML = '&gt;';
      o.setAttribute('title','下一月');
      I.listen(o,'click',function(m,e){
          var bt = I.$(block,'tag','b');
          var year = parseInt(bt[1].innerHTML,10);
          var month = parseInt(bt[3].innerHTML,10);
          if(month>11){
              month = 1;
              year += 1;
          }else{
              month += 1;
          }
          repaint(s2d(year+'-'+month+'-01'));
      });
      for(var i=0;i<7;i++){
        o = I.insert('b',block);
        I.css(o,'float:left;text-align:center;display:block;width:27px;height:21px;line-height:21px;border:0;background-color:#40ADE0;color:#FFF;font-size:9pt;margin:0;padding:0;font-family:微软雅黑,宋体,Simsun');
        o.innerHTML = '日一二三四五六'.split('')[i];
      }
      for(var i=0;i<42;i++){
        o = I.insert('b',block);
        I.css(o,'text-align:center;font-weight:normal;float:left;width:27px;height:21px;line-height:21px;border:0;background-color:#FFF;color:#0597DB;font-size:9pt;margin:0;padding:0;font-family:微软雅黑,宋体,Simsun');
      }
      o = I.insert('div',block);
      I.css(o,'clear:both;display:block;position:absolute;left:69px;top:169px;width:50px;height:18px;text-align:center;line-height:18px;overflow:hidden;color:#999;background-color:#EFEFEF;cursor:pointer;letter-spacing:2px;border:1px solid #BBB;font-family:微软雅黑,宋体,Simsun');
      o.innerHTML = '清空';
      I.listen(o,'click',function(m,e){
        set('');
      });
      
      o = I.insert('div',block);
      I.css(o,'clear:both;display:block;position:absolute;left:-999px;top:0;width:189px;height:168px;overflow:hidden;background-color:#FFF');
      mpanel = o;
      o = I.insert('div',mpanel);
      I.css(o,'position:absolute;left:0;top:0;width:189px;height:20px;line-height:20px;color:#FFF;background-color:#40ADE0;font-size:9pt;text-indent:2px');
      o.innerHTML = '请选择月份';
      for(var i=0;i<12;i++){
        o = I.insert('div',mpanel);
        I.css(o,'position:absolute;cursor:pointer;left:'+(10+60*Math.floor(i%3))+'px;top:'+(28+Math.floor(i/3)*32)+'px;width:50px;height:28px;line-height:28px;color:#40ADE0;background-color:#D0E5FF;font-size:16px;font-weight:bold;text-align:center;font-family:微软雅黑,宋体,Simsun');
        o.innerHTML = (i+1)+'';
        I.listen(o,'mouseover',function(m,e){
          m.style.color = '#FFF';
          m.style.backgroundColor = '#FF6600';
        });
        I.listen(o,'mouseout',function(m,e){
          m.style.color = '#40ADE0';
          m.style.backgroundColor = '#D0E5FF';
        });
        I.listen(o,'click',function(m,e){
          var bt = I.$(block,'tag','b');
          repaint(s2d(bt[1].innerHTML+'-'+m.innerHTML+'-01'));
        });
      }
      o = I.insert('div',mpanel);
      I.css(o,'position:absolute;left:166px;top:0;width:20px;height:20px;line-height:20px;color:#FFF;background-color:#40ADE0;font-size:9pt;text-align:center;cursor:pointer;font-weight:bold');
      o.innerHTML = '×';
      o.title = '返回';
      I.listen(o,'click',function(m,e){
        mpanel.style.left = '-999px';
      });
      o = I.insert('div',block);
      I.css(o,'clear:both;display:block;position:absolute;left:-999px;top:0;width:189px;height:168px;overflow:hidden;background-color:#FFF');
      ypanel = o;
      o = I.insert('div',ypanel);
      I.css(o,'position:absolute;left:0;top:0;width:189px;height:20px;line-height:20px;color:#FFF;background-color:#40ADE0;font-size:9pt;text-indent:2px');
      o.innerHTML = '请选择年份';
      for(var i=0;i<10;i++){
        o = I.insert('div',ypanel);
        I.css(o,'position:absolute;cursor:pointer;left:'+(10+90*Math.floor(i/5))+'px;top:'+(28+Math.floor(i%5)*22)+'px;width:80px;height:20px;line-height:20px;color:#40ADE0;background-color:#D0E5FF;font-size:16px;font-weight:bold;text-align:center;font-family:微软雅黑,宋体,Simsun');
        I.listen(o,'mouseover',function(m,e){
          m.style.color = '#FFF';
          m.style.backgroundColor = '#FF6600';
        });
        I.listen(o,'mouseout',function(m,e){
          m.style.color = '#40ADE0';
          m.style.backgroundColor = '#D0E5FF';
        });
        I.listen(o,'click',function(m,e){
          var bt = I.$(block,'tag','b');
          repaint(s2d(m.innerHTML+'-'+bt[3].innerHTML+'-01'));
        });
      }
      o = I.insert('div',ypanel);
      I.css(o,'position:absolute;left:3px;top:143px;width:90px;height:20px;line-height:20px;color:#FFF;background-color:#40ADE0;font-size:9pt;text-align:center;cursor:pointer');
      o.innerHTML = '&lt;&lt;&nbsp;前十年';
      I.listen(o,'click',function(m,e){
        var d = I.$(ypanel,'tag','div');
        var y = parseInt(d[1].innerHTML,10);
        I.each(d,function(m,i){
          if(i==0) return I.CONTINUE;
          if(i>10) return I.BREAK;
          m.innerHTML = y-11+i;
        });
      });
      o = I.insert('div',ypanel);
      I.css(o,'position:absolute;left:96px;top:143px;width:90px;height:20px;line-height:20px;color:#FFF;background-color:#40ADE0;font-size:9pt;text-align:center;cursor:pointer');
      o.innerHTML = '后十年&nbsp;&gt;&gt;';
      I.listen(o,'click',function(m,e){
        var d = I.$(ypanel,'tag','div');
        var y = parseInt(d[10].innerHTML,10);
        I.each(d,function(m,i){
          if(i==0) return I.CONTINUE;
          if(i>10) return I.BREAK;
          m.innerHTML = y+i;
        });
      });
      o = I.insert('div',ypanel);
      I.css(o,'position:absolute;left:166px;top:0;width:20px;height:20px;line-height:20px;color:#FFF;background-color:#40ADE0;font-size:9pt;text-align:center;cursor:pointer;font-weight:bold');
      o.innerHTML = '×';
      o.title = '返回';
      I.listen(o,'click',function(m,e){
        ypanel.style.left = '-999px';
      });
      I.listen(I.$(block,'tag','b'),'focus',function(m,e){m.blur();});
    };
    var detect = function(){
      I.each(I.$('tag','input'),function(o,i){
        if('text' == o.type && SIGN == o.getAttribute('title')){
          I.listen(o,'focus',function(m,e){
            if(!m.getAttribute('noicalender')){
              //m.style.backgroundImage = '';
              current = m;
              show();
            }
          });
          o.setAttribute('title','');
          o.readOnly = 'readOnly';
          /*
          if(''==o.value){
              o.style.backgroundImage = 'url(\''+I.dir()+'calendar.gif\')';
              o.style.backgroundRepeat = 'no-repeat';
              o.style.backgroundPosition = '1px 1px';
          }*/
          input.push(o);
        }
      });
    };
  
  (function(){
    var t = I.insert('div');
    I.css(t,'position:absolute;left:-999px;top:0;margin:0;padding:0;z-index:2147483647');
    mask = t;
    renderLayer();
    detect();
    I.listen(I.$(),'click',function(o,e){
      try{
        o = e.srcElement || e.target;
        var v = false;
        I.each(input,function(t,i){
          if(o == t){
            v = true;
            return I.BREAK;
          }
        });
        if(v) return;
        v = I.region();
        var x = e.clientX+v.x;
        var y = e.clientY+v.y;
        var m = I.region(layer);
        if(x < m.x || y < m.y || x > m.x+m.width || y > m.y+m.height) hide();
      }catch(ee){}
    });
  })();
  
  return {
    'version':'1.0.0',
    'fresh':function(){detect();}
  };
}});