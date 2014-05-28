I.regist({
  'name':'z.Confirm',
  'need':['z.Win','awt.Button'],
  'fn':function(W,D){
    var create = function(st,of,cf){
      var WW = 400;
      var HH = 150;
      var w = st.width?st.width:WW;
      var h = st.height?st.height:HH;
      h = h+44;
      var nt = st;
      if(typeof st == 'string'){
        nt = {'title':'提示','width':w,'height':h,'content':'<table width="100%" height="'+(h-44)+'"><tbody><tr><td width="100" align="center"><img src="'+I.dir('z/Confirm.gif')+'" /></td><td style="text-align:left">'+st+'</td></tr></tbody></table>'};
      }
      var obj = I.z.Win.create(nt);
      var panel = obj.getContentPanel();
      var div = I.insert('div',panel);
      I.css(div,'position:absolute;left:0;top:'+(h-44)+'px;width:'+w+'px;height:43px;overflow:hidden;background-color:#F6F6F6;border-top:1px solid #DDDDDD');
      var btnCancel = I.awt.Button.create(div,{'label':'取消'});
      btnCancel.getInstance().layer.style.margin = '0';
      btnCancel.getInstance().layer.style.marginTop = '5px';
      btnCancel.getInstance().layer.style.marginRight = '5px';
      btnCancel.onclick(function(m,e){
        obj.close();
        if(cf){
          cf();
        }
      });
      var btnOK = I.awt.Button.create(div,{'label':'确定'});
      btnOK.getInstance().layer.style.marginTop = '5px';
      btnOK.getInstance().layer.style.marginRight = '15px';
      btnOK.onclick(function(m,e){
        obj.close();
        if(of){
          of();
        }
      });
      return obj;
    };
    
    return {
      'create':function(st,of,cf){return create(st,of,cf);}
    };
  }
});