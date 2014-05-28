I.regist({
  'name':'util.Drager',
  'need':null,
  'fn':function(W,D){
    var zIndex = 200;
    var drag = function(o){
      var C = null;
      var Q = null;
      switch(o.length){
        case 1:C=o[0];Q=o[0];break;
        case 2:C=o[0];Q=o[1];break;
        default:return;
      }
      C.style.cursor = 'move';
      Q.style.zIndex = (zIndex++)+'';
      C.ondragstart = function(){return false;};
      C.onselectstart = function(){return false;};
      I.listen(C,'mousedown',function(m,e){
        C = m;
        Q.style.zIndex = (zIndex++)+'';
        if(D.all) C.setCapture();
        var r = I.region(Q);
        Q.setAttribute('X',r.x-e.clientX);
        Q.setAttribute('Y',r.y-e.clientY);
        Q.style.position = 'absolute';
        Q.style.margin = '0';
        Q.style.left = r.x + 'px';
        Q.style.top = r.y + 'px';
        if(e.stopPropagation) e.stopPropagation();
        else e.cancelBubble = true;
        if(e.preventDefault) e.preventDefault();
        else e.returnValue = false;
        I.listen(D,'mousemove',function(m,e1){
          if(!C||!Q) return I.RETURN;
          Q.style.left = (e1.clientX + parseInt(Q.getAttribute('X'),10)) + 'px';
          Q.style.top = (e1.clientY + parseInt(Q.getAttribute('Y'),10)) + 'px';
          if(e1.stopPropagation) e1.stopPropagation();
          else e1.cancelBubble = true;
          if(e1.preventDefault) e1.preventDefault();
          else e1.returnValue = false;
        });
        I.listen(D,'mouseup',function(m,e2){
          if(!C||!Q) return I.RETURN;
          if(D.all) C.releaseCapture();
          C = null;
          if(e2.stopPropagation) e2.stopPropagation();
          else e2.cancelBubble = true;
          if(e2.preventDefault) e2.preventDefault();
          else e2.returnValue = false;
        });
      });
    };

    return {
      'version':'1.0.0',
      'drag':function(){drag(arguments);}
    };
  }
});