I.regist({
  'name':'lang.System',
  'need':['lang.Core'],
  'fn':function(W,D){
    var container = null;
    var layer = null;
    var titleBar = null;
    var width = 400;
    var height = 250;
    var print = function(s,ln){
      if(!layer){
        var r = I.region();
        var o = I.insert('div');
        I.css(o,'position:absolute;left:'+(r.x+r.width-width)+'px;top:'+(r.y+r.height-height)+'px;width:'+width+'px;height:'+height+'px;overflow:hidden;background-color:#000;margin:0;padding:0');
        container = o;
        o = I.insert('div',o);
        I.css(o,'position:absolute;left:1px;top:1px;width:'+(width-2)+'px;height:24px;background-color:#EEE;color:#000;margin:0;padding:0;font-size:9pt;line-height:24px;text-indent:4px;text-align:left');
        o.innerHTML = '输出';
        titleBar = o;
        o = I.insert('div',o);
        I.css(o,'position:absolute;left:'+(width-24)+'px;width:21px;height:22px;text-align:center;line-height:22px;text-indent:0;overflow:hidden;top:1px;cursor:pointer');
        o.innerHTML = '×';
        I.listen(o,'click',function(m,e){
          layer.innerHTML = '';
          container.style.visibility = 'hidden';
        });
        o = I.insert('div',container);
        I.css(o,'position:absolute;left:3px;top:27px;width:'+(width-6)+'px;height:'+(height-30)+'px;overflow:auto;color:#FFF;margin:0;padding:0;font-size:9pt;line-height:18px;font-family:宋体,Simsun;white-space:pre;text-align:left');
        layer = o;
      }else{
        container.style.visibility = 'visible';
      }
      var sp = I.insert('span',layer);
      sp.innerHTML = s;
      sp.scrollIntoView(true);
      if(ln){
        I.insert('span',layer).innerHTML = '<br />';
      }
      try{
        I.util.Drager.drag(titleBar,container);
      }catch(e){}
    };
    return {
      'version':'1.0.0',
      'println':function(s){
        if(I.enableConsole){
          print(s,true);
        }
      },
      'print':function(s){
        if(I.enableConsole){
          print(s,false);
        }
      }
    };
  }
});