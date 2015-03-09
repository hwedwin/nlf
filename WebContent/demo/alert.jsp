<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Alert</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a id="btnB">默认Alert</a>
<a id="btnA">自定义颜色Alert</a>
<a id="btnC">深色遮罩Alert</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{icon:'fa fa-thumbs-up',callback:function(){
    I.z.Alert.create({
      title_background:'#5bc0de',
      close_background:'#5bc0de',
      title_border_color:'#46b8da',
      title_color:'#FFF',
      close_color:'#FFF',
      shadow:'#5bc0de 0px 0px 8px',
      content:'Hello World!',
      button_background:'#5bc0de',
      button_border:'1px solid #46b8da',
      button_color:'#FFF',
      button_background_hover:'#31b0d5',
      button_border_hover:'1px solid #269abc',
      button_color_hover:'#FFF'
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
      content:'Hello World!'
    });
  }});
});
</script>
</body>
</html>