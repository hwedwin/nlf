I.regist('util.MultiCalendar',function(W,D){
var inited=false,layer=null,current=null,input=[];
var WK='日一二三四五六'.split('');
var offset={x:0,y:0};
var STYLE=function(){/*
.i-calendar{position:absolute;margin:0;padding:0;left:-1000px;top:-1000px;width:424px;z-index:1000;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar b{font-weight:normal;display:block;}
.i-calendar div.container{margin:0;padding:0;background-color:#D9D9D9;width:424px;height:224px;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar div.left{float:left;width:211px;margin:0;padding:0;margin-left:1px;margin-top:1px;background-color:#FCFAFB;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar div.right{float:right;width:210px;margin:0;padding:0;margin-right:1px;margin-top:1px;background-color:#FCFAFB;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar div.left .ym{float:left;margin:0;padding:0;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar div.right .ym{float:right;margin:0;padding:0;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar div.ym{margin:0;padding:0;height:30px;line-height:30px;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar div.ym a{margin:0;padding:0;color:#999;font-size:14px;margin-left:12px;margin-right:12px;text-decoration:none;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar div.ym a:hover{text-decoration:none;}
.i-calendar div.ym i{margin:0;padding:0;font-style:normal;color:#7C7C7C;font-size:14px;margin-left:12px;margin-right:12px;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar div.panel{clear:both;margin:0;padding:0;height:192px;background-color:#FFF;border:0;border-top:1px solid #E9E9E9;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;-webkit-border-radius:0;-moz-border-radius:0;-ms-border-radius:0;-o-border-radius:0;border-radius:0;-webkit-box-shadow:none;-moz-box-shadow:none;-ms-box-shadow:none;-o-box-shadow:none;box-shadow:none;}
.i-calendar table{margin:0;padding:0;margin-left:6px;margin-top:2px;border-collapse:collapse;empty-cells:show;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar th{margin:0;padding:0;color:#AAA;font-weight:normal;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar tbody{margin:0;padding:0;border-left:1px solid #FFF;border-top:1px solid #FFF;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
.i-calendar td{margin:0;padding:0;border-right:1px solid #FFF;border-bottom:1px solid #FFF;font-size:11px;width:28px;height:26px;color:#7C7C7C;background-color:#FFF;text-align:center;line-height:26px;cursor:pointer;-webkit-box-sizing:border-box;-moz-box-sizing:border-box;-ms-box-sizing:border-box;-o-box-sizing:border-box;box-sizing:border-box;}
*/}+'';
var setOffset=function(x,y){
 offset.x=x;
 offset.y=y;
};
var genTable = function(){
 var s='<table><thead><tr>';
 for(var i=0;i<7;i++){
  s+='<th>'+WK[i]+'</th>';
 }
 s+='</tr></thead><tbody>';
 for(var i=0;i<6;i++){
  s+='<tr>';
  for(var j=0;j<7;j++){
   s+='<td></td>';
  }
  s+='</tr>';
 }
 s+='</tbody></table>';
 return s;
};
var genLR=function(lr,arrow){
 var s='<div class="'+lr+'"><div class="ym">'+('right'==lr?'<i></i>':'')+'<a href="javascript:void(0);">'+arrow+'</a>'+('left'==lr?'<i></i>':'')+'</div><div class="panel">';
 s+=genTable();
 s+='</div></div>';
 return s;
};
var genLayer=function(){
 var s='<div class="container">';
 s+=genLR('left','&lt;');
 s+=genLR('right','&gt;');
 s+='</div>';
 return s;
};
var trim=function(os){
 var s=os;
 s=s.substr('function(){/*'.length+1);
 s=s.substr(0,s.length-'*/}'.length);
 return s;
};
var s2d=function(s){
 s=s.split('-');
 if(s.length!=3) return new Date();
 return new Date(s[0]+'/'+s[1]+'/'+s[2]);
};
var format=function(s){
 return (s<10?'0':'')+s;
};
var set=function(s){
 current.value=s;
 hide();
};
var hide=function(){
 layer.style.left='-999px';
};
var repaintP=function(dom,y,m){
 var ch=s2d(current.value);
 var chy=ch.getFullYear();
 var chm=ch.getMonth()+1;
 var chd=ch.getDate();
 //var d=a.getDate();
 var b=new Date(y+'/'+m+'/1').getDay();
 var q=new Date(m>1?y:y-1,m>1?m-1:12,0).getDate();
 var c=new Date(y,m,0).getDate();
 var g=new Date();
 var tb=I.$(dom,'tag','i')[0];
 tb.innerHTML=y+'年'+m+'月';
 tb.setAttribute('y',y);
 tb.setAttribute('m',m);
 var bt=I.$(dom,'tag','td');
 var f=1;
 var l=1;
 for(var i=0;i<42;i++){
  var o=bt[i];
  o.style.backgroundColor='#FFF';
  o.style.color='#7C7C7C';
  o.setAttribute('title','');
  o.onmouseover=null;
  o.onmouseout=null;
  o.onmousedown=function(){
   set(this.getAttribute('d'));
  };
  if(i<b){
   o.style.cursor='default';
   o.innerHTML='';
   o.setAttribute('d',(m>1?y:y-1)+'-'+format(m>1?m-1:12)+'-'+format(q+1-b+i));
  }else if(f<=c){
   o.innerHTML=f;
   if(g.getFullYear()==y && g.getMonth()+1==m && g.getDate()==f){
    //today
    o.style.backgroundColor='#FA8B78';
    o.style.color='#FFF';
    o.setAttribute('title','今天');
   }else if(chy==y&&chm==m&&chd==f){
    //selected
    o.style.backgroundColor='#FFF';
    o.style.color='#C40000';
   }else if(new Date(y+'/'+m+'/'+f).getDay()%6 == 0){
    //weekend
   }else{
    //other
   }
   o.setAttribute('d',y+'-'+format(m)+'-'+format(f++));
  }else{
   o.style.cursor='default';
   o.innerHTML='';
   o.setAttribute('d',(m<12?y:y+1)+'-'+format(m<12?m+1:1)+'-'+format(l++));
  }
 }
};
var repaint=function(a){
 try{
  //var w=a.getDay();
  var y=a.getFullYear();
  var m=a.getMonth()+1;
  repaintP(I.$(layer,'class','left')[0],y,m);
  m++;
  if(m>12){
    y++;
    m=1;
  }
  repaintP(I.$(layer,'class','right')[0],y,m);
 }catch(e){}
};
var show=function(){
 var r=I.region(current);
 layer.style.left=r.x+offset.x+'px';
 layer.style.top=(r.y+r.height+offset.y)+'px';
 repaint(s2d(current.value));
};
var init=function(){
 I.style(trim(STYLE));
 var o=I.insert('div');
 I.cls(o,'i-calendar');
 o.innerHTML=genLayer();
 layer=o;
 var a=I.$(layer,'tag','a');
 var i=I.$(layer,'tag','i');
 I.listen(a[0],'click',function(m,e){
  var year=parseInt(i[0].getAttribute('y'),10);
  var month=parseInt(i[0].getAttribute('m'),10);
  if(month==1){
   month=11;
   year -= 1;
  }else if(month==2){
   month=12;
   year -= 1;
  }else{
   month -= 2;
  }
  repaint(s2d(year+'-'+month+'-01'));
 });
 I.listen(a[1],'click',function(m,e){
  var year=parseInt(i[0].getAttribute('y'),10);
  var month=parseInt(i[0].getAttribute('m'),10);
  if(month==11){
   month=1;
   year += 1;
  }else if(month==12){
   month=2;
   year += 1;
  }else{
   month += 2;
  }
  repaint(s2d(year+'-'+month+'-01'));
 });
};
I.listen(D,'click',function(o,e){
 try{
  o=e.srcElement||e.target;
  var v=false;
  for(var i=0;i<input.length;i++){
   if(o==input[i]){
    v=true;
    break;
   }
  }
  if(v) return;
  v=I.region();
  var x=e.clientX+v.x;
  var y=e.clientY+v.y;
  var m=I.region(layer);
  if(x < m.x || y < m.y || x > m.x+m.width || y > m.y+m.height) hide();
 }catch(ee){}
 return true;
});
var bind=function(o){
 if(!inited){
  init();
 }
 o=I.$(o);
 I.listen(o,'focus',function(m,e){
  current=m;
  show();
 });
 input.push(o);
};
return{
 setOffset:function(x,y){setOffset(x,y);},
 bind:function(o){bind(o);}
};
}+'');