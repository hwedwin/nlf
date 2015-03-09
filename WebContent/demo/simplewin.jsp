<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>简单窗</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a id="btnA">显示简单窗</a>
<a id="btnB">固定宽度的简单窗</a>
<a id="btnC">固定高度的简单窗</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{
    callback:function(){
      I.z.SimpleWin.create({
        content:'我会随着窗口大小变化而变化。'
      });
    }
  });
  I.ui.Button.render('btnB',{
    callback:function(){
      I.z.SimpleWin.create({
        width:600,
        content:'我只有600像素宽。'
      });
    }
  });
  I.ui.Button.render('btnC',{
    callback:function(){
      I.z.SimpleWin.create({
        height:300,
        content:'我只有300像素高。'
      });
    }
  });
});
</script>
</body>
</html>