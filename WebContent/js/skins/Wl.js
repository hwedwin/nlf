I.regist({
  'name':'skins.Wl',
  'need':null,
  'fn':function(W,D){
  
    /** 皮肤目录 */
    var DIR = 'skins/Wl/';
    
    /**
     * 获取资源
     * @param s 资源文件名
     * @return 资源完整路径
     */
    var getRes = function(s){
      return I.dir()+DIR+s;
    };
    
    var CFG = {
      'seperator':'：',
      'awt':{
        'margin':'20',
        'Button':{
          'left':{
            'width':'6'
          },
          'center':{
            'padding':'15'
          },
          'right':{
            'width':'6'
          },
          'height':'36',
          'backgroundImage':'url(\''+getRes('awt.Button.gif')+'\')',
          'normal':{
            'left':{
              'backgroundPosition':'0 0'
            },
            'center':{
              'backgroundPosition':'0 -72px'
            },
            'right':{
              'backgroundPosition':'-6px 0'
            }
          },
          'hover':{
            'left':{
              'backgroundPosition':'0 -36px'
            },
            'center':{
              'backgroundPosition':'0 -108px'
            },
            'right':{
              'backgroundPosition':'-6px -36px'
            }
          }
        }
      }
    };
    
    var getNode = function(s){
      var c = s.split('.');
      var p = CFG;
      for(var i=0;i<c.length;i++){
        if(p[c[i]]) p = p[c[i]];
        else return I.skins['Default'].get(s);
      }
      return p;
    };
    
    return {
      'desc':'自定义皮肤',
      'get':function(s){return getNode(s);}
    };
  }
});