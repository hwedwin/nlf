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
      I.z.Win.create({
        content:'你没中奖...',
        width:500,
        height:300
      });
    }
  });
});
</script>
</body>
</html>