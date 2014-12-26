<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mobile</title>
<meta name="viewport" content="width=device-width,user-scalable=no" />
<link type="text/css" rel="stylesheet" href="${PATH}/css/animate.css" />
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
    <i>表单</i>
  </header>
  <article>
    <label>个人资料</label>
    <ul>
      <li class="input"><i>姓名</i><input type="text" placeholder="六特尔" /></li>
      <li class="input"><i>年龄</i><input type="text" placeholder="18" /></li>
      <li class="input"><i>性别</i>
        <div>
        <div class="group">
          <a class="active">男</a>
          <a>女</a>
        </div>
        </div>
      </li>
      <li class="input"><i>简介</i><textarea rows="6"></textarea></li>
    </ul>
    <label>个人资料</label>
    <ul>
      <li>
        <input type="text" placeholder="姓名" />&nbsp;
        <input type="text" placeholder="年龄" />&nbsp;
        <select><option>男</option><option>女</option></select>
      </li>
    </ul>
    <a class="button orange fa fa-check" style="margin:1em;">确定</a>
  </article>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
  });
  </script>
</body>
</html>