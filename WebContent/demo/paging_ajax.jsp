<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ajax请求的自动分页</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<a id="btnA">基于ajax的自动分页</a>
<script type="text/javascript">
I.want(function(){
  //蓝色按钮
  I.ui.Button.render('btnA',{icon:'fa fa-thumbs-up',callback:function(){
    var ntw = I.z.SimpleWin.create({width:600,content:'loading'});
    I.net.Page.find('test.Action/paging',null,ntw.contentPanel);
  }});
});
</script>
</body>
</html>