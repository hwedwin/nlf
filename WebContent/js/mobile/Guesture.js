I.regist('mobile.Guesture',function(W,D){
var _getDir=function(x0,y0,x1,y1){
 var dy=y0-y1;
 var dx=x0-x1;
 var result=0;
 if(Math.abs(dx)<2&&Math.abs(dy)<2){
  return result;
 }
 var angle=Math.atan2(dy,dx)*180/Math.PI;
 if(angle>=-45&&angle< 45){
  result=4;
 }else if(angle>=45&&angle<135){
  result=1;
 }else if(angle>=-135&&angle<-45){
  result=2;
 }else if((angle>=135&&angle<=180)||(angle>=-180&&angle<-135)){
  result=3;
 }
 return result;
};
var _bind=function(dom,callback){
 dom=I.$(dom);
 var x,y,nx,ny;
 var _start=function(e){
  var touch=e.targetTouches[0];
  x=touch.pageX;
  y=touch.pageY;
  nx=x;
  ny=y;
  if(callback.onStart) callback.onStart();
 };
 var _move=function(e){
  var touch=e.targetTouches[0];
  nx=touch.pageX;
  ny=touch.pageY;
  if(callback.onMove){
    callback.onMove(x,y,nx,ny);
  }
 };
 var _end=function(e){
  var r=_getDir(x,y,nx,ny);
  if(callback.onSlide){
    callback.onSlide(x,y,nx,ny);
  }
  switch(r){
   case 1:if(callback.onNorth) callback.onNorth();break;
   case 2:if(callback.onSouth) callback.onSouth();break;
   case 3:if(callback.onEast) callback.onEast();break;
   case 4:if(callback.onWest) callback.onWest();break;
  }
 };
 dom.addEventListener("touchstart",_start,false);
 dom.addEventListener("touchmove",_move,false);
 dom.addEventListener("touchend",_end,false);
 dom.addEventListener("touchcancel",_end,false);
 dom.addEventListener("touchleave",_end,false);
};
return {
 bind:function(dom,callback){return _bind(dom,callback);}
};
}+'');