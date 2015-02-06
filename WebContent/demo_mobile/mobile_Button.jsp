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
    </nav>
    <i>按钮</i>
    <nav>
      <a class="fa fa-none"></a>
    </nav>
  </header>
  <article>
    <a class="button" style="margin:1em;">默认</a>
    <a class="button orange" style="margin:1em;">橙色</a>
    <a class="button green" style="margin:1em;">绿色</a>
    <a class="button red" style="margin:1em;">红色</a>
    <a class="button blue" style="margin:1em;">蓝色</a>
  </article>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
  });
  </script>
</body>
</html>