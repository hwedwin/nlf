<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>通知</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<a id="btnA">显示通知</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{
    callback:function(){
      I.ui.Notify.create({
        title:'恭喜',
        content:'你没中奖'
      });
    }
  });
});
</script>
</body>
</html>