<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alert</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<a id="btnA">蓝色Alert</a>
<a id="btnB">默认Alert</a>
<a id="btnC">深色遮罩Alert</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{icon:'fa fa-thumbs-up',callback:function(){
    I.z.Alert.create({
      title_background:'#0074D9',
      title_border_color:'#0074D9',
      content_border_color:'#0074D9',
      content:'Hello World!'
    });
  }});
  I.ui.Button.render('btnB',{icon:'fa fa-thumbs-up',callback:function(){
    I.z.Alert.create({
      content:'Hello World!'
    });
  }});
  I.ui.Button.render('btnC',{icon:'fa fa-thumbs-up',callback:function(){
    I.z.Alert.create({
      mask_opacity:60,
      mask_color:'#000',
      shadow_color:'#FFF',
      shadow_opacity:50,
      title_background:'#0074D9',
      title_border_color:'#0074D9',
      content_border_color:'#0074D9',
      content:'Hello World!'
    });
  }});
});
</script>
</body>
</html>