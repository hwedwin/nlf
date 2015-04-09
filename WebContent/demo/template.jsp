<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>js模板引擎</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<p>模板：(模板中内置printf函数可以输出html和js变量的混合体。)</p>
<textarea id="tmp" cols="60" rows="4">
<div>{$=data.a$}</div>
<ul>
{$
  for(var i=0;i<10;i++){
$}
  <li>{$=i$}</li>
{$
    printf('<li style="color:red">'+i+'</li>');
  }
  printf('<li style="color:red">OK</li>');
$}
</ul>
</textarea>
<p>&nbsp;</p>
<p>编译结果：</p>
<textarea id="com" cols="60" rows="4"></textarea>
<p>&nbsp;</p>
<p>渲染结果：</p>
<textarea id="res" cols="60" rows="4"></textarea>
<script type="text/javascript">
I.want(function(){
  var callback = I.util.Template.compile(I.$('tmp').value);
  I.$('com').value = callback;
  I.$('res').value = callback({'a':'hello'});
});
</script>
<p>&nbsp;</p>
<p>实际使用：</p>
<!-- 模板 -->
<nlft:tpl id="myTemplate">
<div>{$=data.a$}</div>
<ul>
{$
  for(var i=0;i<10;i++){
$}
  <li>{$=i$}</li>
{$
    printf('<li style="color:red">'+i+'</li>');
  }
  printf('<li style="color:red">OK</li>');
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