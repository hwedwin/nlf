<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirm</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<a id="btnA">蓝色Confirm</a>
<a id="btnB">默认Confirm</a>
<a id="btnC">深色遮罩Confirm</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{icon:'fa fa-thumbs-up',callback:function(){
    I.z.Confirm.create({
      title_background:'#0074D9',
      title_border_color:'#0074D9',
      content_border_color:'#0074D9',
      content:'Hello World!',
      yes:function(){
        I.z.Alert.create({content:'u choosed yes'});
      },
      no:function(){
        I.z.Alert.create({content:'u choosed no'});
      }
    });
  }});
  I.ui.Button.render('btnB',{icon:'fa fa-thumbs-up',callback:function(){
    I.z.Confirm.create({
      content:'Hello World!',
      yes:function(){
        I.z.Alert.create({content:'u choosed yes'});
      },
      no:function(){
        I.z.Alert.create({content:'u choosed no'});
      }
    });
  }});
  I.ui.Button.render('btnC',{icon:'fa fa-thumbs-up',callback:function(){
    I.z.Confirm.create({
      mask_opacity:60,
      mask_color:'#000',
      shadow_color:'#FFF',
      shadow_opacity:50,
      title_background:'#0074D9',
      title_border_color:'#0074D9',
      content_border_color:'#0074D9',
      content:'Hello World!',
      yes:function(){
        I.z.Alert.create({content:'u choosed yes'});
      },
      no:function(){
        I.z.Alert.create({content:'u choosed no'});
      }
    });
  }});
});
</script>
</body>
</html>