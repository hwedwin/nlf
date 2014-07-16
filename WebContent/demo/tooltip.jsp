<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>提示条</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<a id="btnA">显示ToolTip</a>
<script type="text/javascript">
I.want(function(){
  var tip = null;
  I.ui.Button.render('btnA');
  I.listen('btnA','mouseover',function(){
    if(tip){
      tip.close();
      tip = null;
    }
    tip = I.ui.ToolTip.create({dom:'btnA',content:'Hello World!'});
  });
  I.listen('btnA','mouseout',function(){
    if(tip){
      tip.close();
      tip = null;
    }
  });
});
</script>
</body>
</html>