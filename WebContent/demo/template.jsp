<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>js模板引擎</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
模板：<br />
<textarea id="tmp" cols="60" rows="10">
<div>{$=data.a$}</div>
<ul>
{$
  for(var i=0;i<10;i++){
$}
  <li>{$=i$}</li>
{$
  }
$}
</ul>
</textarea>
<br />
编译结果：<br />
<textarea id="com" cols="60" rows="10"></textarea>
<br />
渲染结果：<br />
<textarea id="res" cols="60" rows="10"></textarea>
<script type="text/javascript">
I.want(function(){
  var callback = I.util.Template.compile(I.$('tmp').value);
  I.$('com').value = callback;
  I.$('res').value = callback({'a':'hello'});
});
</script>
<p></p>
实际使用：
<!-- 模板 -->
<nlft:tpl id="myTemplate">
<div>{$=data.a$}</div>
<ul>
{$
  for(var i=0;i<10;i++){
$}
  <li>{$=i$}</li>
{$
  }
$}
</ul>
</nlft:tpl>
<!-- 要渲染到的容器 -->
<div id="myResult"></div>
<a id="btnA">点击渲染</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA');
  I.listen('btnA','click',function(m,e){
    I.$('myResult').innerHTML = I.util.Template.render({a:'hello'},I.$('TPL_myTemplate').value);
  });
});
</script>
</body>
</html>