<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>提示信息</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<a id="btnA">显示蓝色的Tip</a>
<a id="btnB">显示默认的Tip</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{icon:'fa fa-phone',callback:function(){
    //创建蓝色提示信息
    I.ui.Tip.create({msg:'Hello World!',background:'#0074D9',color:'#FFF'});
  }});
  I.ui.Button.render('btnB',{icon:'fa fa-car',callback:function(){
    //创建默认提示信息
    I.ui.Tip.create({msg:'Hello World!'});
  }});
});
</script>
</body>
</html>