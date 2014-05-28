I.regist({
  'name':'z.Alert',
  'need':['z.Win','awt.Button'],
  'fn':function(W,D){
    var create = function(st,cf){
      var WW = 400;
      var HH = 150;
      var w = st.width?st.width:WW;
      var h = st.height?st.height:HH;
      h = h+44;
      var nt = {};
      if(typeof st == 'string'){
        nt = {'title':'提示','width':w,'height':h,'content':'<table width="100%" height="'+(h-44)+'"><tbody><tr><td width="100" align="center"><img src="'+I.dir('z/Alert.gif')+'" /></td><td style="text-align:left">'+st+'</td></tr></tbody></table>'};
      }else{
        nt['width']=w;
        nt['height']=h;
        if(st.title){
          nt['title']=st.title;
        }else{
          nt['title']='提示';
        }
        if(0===st.content.indexOf('<')){
          nt['content']=st.content;
        }else{
          nt['content']='<table width="100%" height="'+(h-44)+'"><tbody><tr><td width="100" align="center"><img src="'+I.dir('z/Alert.gif')+'" /></td><td style="text-align:left">'+st.content+'</td></tr></tbody></table>';
        }
      }
      var obj = I.z.Win.create(nt,cf);
      var panel = obj.getContentPanel();
      var div = I.insert('div',panel);
      I.css(div,'position:absolute;left:0;top:'+(h-44)+'px;width:'+w+'px;height:43px;overflow:hidden;background-color:#F6F6F6;border-top:1px solid #DDDDDD');
      var btn = I.awt.Button.create(div,{'label':'确定'});
      btn.getInstance().layer.style.marginTop = '5px';
      btn.getInstance().layer.style.marginRight = '5px';
      btn.onclick(function(m,e){
        obj.close();
      });
      return obj;
    };
    
    return {
      'create':function(st,cf){return create(st,cf);}
    };
  }
});