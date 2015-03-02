I.regist('util.Drager',function(W,D){
var C=null,Q=null;
var _drag=function(org,dst){
 C=org,Q=dst?dst:org;
 C.style.cursor='move';
 C.ondragstart=function(){return false;};
 C.onselectstart=function(){return false;};
 I.listen(C,'mousedown',function(m,e){
  C=m;
  if(D.all) C.setCapture();
  var r=I.region(Q);
  Q.setAttribute('X',r.x-e.clientX);
  Q.setAttribute('Y',r.y-e.clientY);
  I.util.Boost.addStyle(Q,'position:absolute;margin:0;left:'+r.x+'px;top:'+r.y+'px;');
 });
};
I.listen(D,'mousemove',function(m,e1){
 if(!C||!Q) return;
 I.util.Boost.addStyle(Q,'left:'+(e1.clientX+parseInt(Q.getAttribute('X'),10))+'px;top:'+(e1.clientY+parseInt(Q.getAttribute('Y'),10))+'px;');
});
I.listen(D,'mouseup',function(){
 if(!C||!Q) return;
 if(D.all) C.releaseCapture();
 C=null;
});
return {
 drag:function(org,dst){_drag(org,dst);}
};
}+'');