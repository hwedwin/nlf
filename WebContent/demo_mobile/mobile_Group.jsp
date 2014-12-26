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
    <i>切换</i>
  </header>
  <article>
    <div style="margin:1em;">
      <div class="group">
        <a class="active">Hello</a>
        <a>World</a>
        <a>!</a>
      </div>
    </div>
    <label>个人资料</label>
    <ul>
      <li class="input"><i>性别</i>
        <div>
        <div class="group">
          <a class="active">男</a>
          <a>女</a>
        </div>
        </div>
      </li>
    </ul>
  </article>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
  });
  </script>
</body>
</html>