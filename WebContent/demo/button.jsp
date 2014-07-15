<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>按钮</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
第1种方式：a标记渲染
<a id="btnA">按钮1</a>
<a id="btnB">按钮2</a>
<a id="btnC">带事件响应的按钮</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA');
  I.ui.Button.render('btnB',{skin:'Blue'});
  I.ui.Button.render('btnC',{
    skin:'Green',
    callback:function(){
      window.alert('点击了按钮');
    }
  });
});
</script>
<p></p>
第2种方式：js动态创建
<div id="buttons"></div>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.create({
    dom:I.$('buttons'),
    label:'动态创建的按钮1'
  });
  I.ui.Button.create({
    dom:I.$('buttons'),
    skin:'Blue',
    label:'动态创建的按钮2'
  });
  I.ui.Button.create({
    dom:I.$('buttons'),
    skin:'Green',
    label:'动态创建的带事件响应的按钮',
    callback:function(){
      window.alert('点击了按钮');
    }
  });
  I.ui.Button.create({
    dom:I.$('buttons'),
    icon:'fa fa-car',//带个图标
    label:'动态创建的带图标的按钮2'
  });
});
</script>
<p></p>
第3种方式：html加css
<a class="i-ui-Button-Default fa fa-car" href="javascript:void(0);">html+css</a>
<script type="text/javascript">
I.want(function(){
  I.util.Skin.init('Default');
});
</script>
</body>
</html>