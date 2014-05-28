/**
 * 相对于初始状态进行顺时针旋转
 * I.util.Rotate.transform(o,orgX,orgY,degree)
 */
I.regist({
  'name':'util.Rotate',
  'need':null,
  'fn':function(W,D){
    var FNS = {
      'filter':function(a){
        return [
        'progid:DXImageTransform.Microsoft.Matrix(M11="',
        a[0],
        '", M12="',
        a[1],
        '", M21="',
        a[2],
        '", M22="',
        a[3],
        '", sizingMethod="auto expand")'
        ].join('');
      },
      'MozTransform':function(a){
        return 'rotate('+a[0]+'deg)';
      },
      'WebkitTransform':function(a){
        return 'rotate('+a[0]+'deg)';
      },
      'OTransform':function(a){
        return 'rotate('+a[0]+'deg)';
      },
      'transform':function(a){
        return 'rotate('+a[0]+'deg)';
      }
    };
    
    var FNSO = {
        'MozTransformOrigin':function(x,y){
            return x+'px '+y+'px';
         },
         'WebkitTransformOrigin':function(x,y){
            return x+'px '+y+'px';
         },
         'OTransformOrigin':function(x,y){
            return x+'px '+y+'px';
         },
         'transformOrigin':function(x,y){
            return x+'px '+y+'px';
         }
     };
     var matrix = function(degree){
         var deg = Math.PI*degree/180;
         var sin = Math.sin(deg);
         var cos = Math.cos(deg);
         return [
            [cos,-sin],
            [sin,cos]
         ];
     };
     var multiple=function(m1, m2) {
         var m = [];
         var set = function(x, y, v) {
             if (!m[x]) {
                 m[x] = [];
             }
             m[x][y] = v;
         };
         for (var i = 0; i < m1.length; i++) {
             for (var k = 0; k < m2[0].length; k++) {
                 var sum = 0;
                 for (var j = 0; j < m2.length; j++) {
                     sum += m1[i][j] * m2[j][k];
                 }
                 set(i, k, sum);
             }
         }
         return m;
    };
    var fixIE=function(o,r,cx,cy,m) {
      var centerX = r.width/2;
      var centerY = r.height/2;
      var originX = cx;
      var originY = cy;
      var diffX = centerX-originX;
      var diffY = centerY-originY;
      var tr = multiple(m, [
         	[diffX],
         	[diffY]
      ]);
      var ttx = tr[0][0] + originX;
      var tty = tr[1][0] + originY;
      var nr = I.region(o);
      var diff = [ttx - nr.width / 2, tty - nr.height / 2];
      o.style.position = 'absolute';
      o.style.margin = '0';
      o.style.left = (nr.x+diff[0])+'px';
      o.style.top = (nr.y+diff[1])+'px';
    };

    /**
     * 变换
     * @param o 页面元素或id
     * @param cx 参考点横坐标
     * @param cy 参考点纵坐标
     * @param degree 度，不是弧度
     */
    var transform = function(o,cx,cy,degree){
      o = I.$(o);
      var r = I.region(o);
      var args = [];
      if(D.all){
        var deg = Math.PI*degree/180;
        var sin = Math.sin(deg);
        var cos = Math.cos(deg);
        args.push(cos);
        args.push(-sin);
        args.push(sin);
        args.push(cos);
      }else{
        args.push(degree);
      }
      for(var i in FNS){
        try{
            o.style[i] = FNS[i](args);
        }catch(e){}
      }
      for(var i in FNSO){
        try{
            o.style[i] = FNSO[i](cx,cy);
        }catch(e){}
      }
      if(D.all){//IE修正
        fixIE(o,r,cx,cy,matrix(degree));
      }
    };
    
    return {
      /**
       * 旋转变换
       * @param o DOM对象
       * @param orgX 参考点X坐标，相对于DOM的left
       * @param orgY 参考点Y坐标，相对于DOM的top
       * @param degree 度数，按一周360度计算
       * @returns
       */
      'transform':function(o,orgX,orgY,degree){
        transform(o,orgX,orgY,degree);
        return this;
      }
    };
  }
});