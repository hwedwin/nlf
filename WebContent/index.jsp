<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>demo</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<ul id="menu" class="i-ui-Tree-Default">
  <li><b></b><i></i><a>UI组件</a>
    <ul><li><b></b><i></i><a data-url="${PATH}/test.Action/paging">自动分页示例</a></li></ul>
  </li>
  <li><b></b><i></i><a data-url="${PATH}/demo/tree.jsp">树</a></li>
</ul>
<ul id="test">
  <li><a class="i-ui-Button-Default fa fa-thumbs-up" href="javascript:void(0);">普通按钮</a></li>
  <li><a class="i-ui-Button-Blue fa fa-thumbs-up" href="javascript:void(0);">蓝色按钮</a></li>
</ul>
<script type="text/javascript">
I.want(function(){
  I.ui.Tree.render('menu',{
    onClick:function(who){
      var url = who.dom.a.getAttribute('data-url');
      if(url){
        self.location = url;
      }
    }
  });
});
var TOOLTIP = {};
I.want(function(){
  //加载默认皮肤以自动渲染页面
  I.util.Skin.init('Default');
  //加载蓝色皮肤以自动渲染页面
  I.util.Skin.init('Blue');

  //创建一个按钮
  I.ui.Button.create({label:'显示Loading...',icon:'phone',callback:function(){
    //显示加载进度条，2秒后关闭
    var ld = I.ui.Loading.create();
    I.delay(2000,function(){
      ld.close();
    });
  }});
  
  I.ui.Button.create({skin:'Blue',label:'显示Tip',icon:'phone',callback:function(){
    I.ui.Tip.create({msg:'Hello World!',skin:'Blue'});
  }});

  I.ui.Button.create({skin:'Blue',label:'Alert',icon:'thumbs-up',callback:function(){
    I.z.Alert.create({skin:'Blue',content:'Hello World!'});
  }});
  
  I.ui.Button.create({skin:'Green',label:'Alert',icon:'thumbs-up',callback:function(){
    I.z.Alert.create({skin:'Green',content:'Hello World!'});
  }});
  
  I.ui.Button.create({label:'Alert',icon:'thumbs-up',callback:function(){
    I.z.Alert.create({content:'Hello World!'});
  }});
  
  I.listen(I.$('test','tag','a'),'mouseover',function(m,e){
    var tooltip = I.ui.ToolTip.create({dom:m,content:m.innerHTML});
    m.setAttribute('data-tooltip-id',tooltip.id);
    TOOLTIP[tooltip.id] = tooltip;
  });
  I.listen(I.$('test','tag','a'),'mouseout',function(m,e){
    TOOLTIP[m.getAttribute('data-tooltip-id')].close();
  });
});
I.want(function(){
  I.ui.Button.create({label:'逗比',callback:function(){
    I.z.Confirm.create({content:'Hello World!',yes:function(){
      I.ui.Notify.create({content:'Hello World!'});
    },no:function(){
      window.alert('u choosed no');
    }});
  }});
  var ntw = I.z.SimpleWin.create({width:600,height:400,content:'loading'});
  I.net.Page.find('test.Action/paging',null,ntw.contentPanel);
});
</script>
</body>
</html>