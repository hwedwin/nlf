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
    <i>手指滑动</i>
    <nav>
      <a class="fa fa-none"></a>
    </nav>
  </header>
  <article>
  </article>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
    I.mobile.Guesture.bind(document.body,{
      onEast:function(){
        window.alert('向右滑');
      },
      onWest:function(){
        window.alert('向左滑');
      },
      onNorth:function(){
        window.alert('向上滑');
      },
      onSouth:function(){
        window.alert('向下滑');
      }
    });
  });
  </script>
</body>
</html>