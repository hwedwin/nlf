<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mobile</title>
<meta name="viewport" content="width=device-width,user-scalable=no" />
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<script src="${PATH}/js/icore.js"></script>
<style type="text/css">
body{display:none;}
</style>
</head>
<body>
  <header>
    <nav>
      <a href="index.jsp" class="fa fa-chevron-left"></a>
      <a class="fa fa-signal"></a>
    </nav>
    <i>标题栏</i>
    <nav>
      <a class="fa fa-cog"></a>
      <a class="fa fa-signal"></a>
    </nav>
  </header>
  <article>
    <ul>
      <li>看，标题栏好多按钮。</li>
    </ul>
  </article>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
  });
  </script>
</body>
</html>