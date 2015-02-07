<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>窗口</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<a id="btnA">显示窗口</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{
    callback:function(){
      var win = I.z.Win.create({
        content:'2秒后窗口变大',
        width:500,
        height:300
      });
      I.delay(2000,function(){
        win.resizeTo(600,400);
        win.contentPanel.innerHTML = '呵呵';
      });
    }
  });
});
</script>
</body>
</html>