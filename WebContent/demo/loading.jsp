<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>进度条</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a id="btnA">显示红色的进度条</a>
<a id="btnB">显示默认的进度条</a>
<a id="btnC">显示深色遮罩的进度条</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{icon:'fa fa-phone',callback:function(){
    //显示红色的进度条，2秒后关闭
    var ld = I.ui.Loading.create({color:'red'});
    I.delay(2000,function(){
      ld.close();
    });
  }});

  I.ui.Button.render('btnB',{icon:'fa fa-car',callback:function(){
    //显示默认的进度条，2秒后关闭
    var ld = I.ui.Loading.create();
    I.delay(2000,function(){
      ld.close();
    });
  }});

  I.ui.Button.render('btnC',{callback:function(){
    var ld = I.ui.Loading.create({
      color:'#FFF',
      mask_color:'#000',
      mask_opacity:60,
    });
    I.delay(2000,function(){
      ld.close();
    });
  }});
});
</script>
</body>
</html>