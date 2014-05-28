I.regist({
  'name':'util.Skin',
  'need':['util.Cookie','skins.Default','skins.Wl'],
  'fn':function(W,D){
  
    /** COOKIE标识 */
    var TAG = 'i_style';
    
    /** 默认皮肤名称 */
    var DEFAULT = 'Default';
    
    /**
     * 设置皮肤
     * @param s 皮肤名称
     */
    var setSkin = function(s){
      for(var i in I.skins){
        if(i==s){
          I.util.Cookie.set(TAG,s);
          return;
        }
      }
    };
    
    /**
     * 获取当前皮肤名称
     * @return 当前皮肤名称
     */
    var getCurrent = function(){
      var s = I.util.Cookie.get(TAG);
      return s?s:DEFAULT;
    };
    
    /**
     * 获取当前皮肤
     * @return 当前皮肤
     */
    var getSkin = function(s){
      return I.skins[s?s:getCurrent()];
    };
    
    /**
     * 设置默认皮肤
     */
    var setDefault = function(){
      setSkin(DEFAULT);
    };
    
    return {
      'setSkin':function(s){setSkin(s);},
      'setDefault':function(){setDefault();},
      'getSkin':function(s){return getSkin(s);}
    };
  }
});