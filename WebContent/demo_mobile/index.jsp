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
  <section>
    <header>
      <nav><a class="fa fa-home" href="${PATH}/"></a></nav>
      NLF MOBILE
      <nav>
        <a class="fa fa-info-circle"></a>
        <a class="fa fa-bars" data-aside="rmenu"></a>
      </nav>
    </header>
    <article class="padding">
      <ul>
        <li>
          <strong>Hello World!</strong>
          <small>接啊可是看到接啊看世界大赛将贷款啊时间跨度。</small>
        </li>
        <li>Hello World!</li>
        <li>Hello World!</li>
        <li>Hello World!</li>
        <li>Hello World!</li>
        <li><a class="fa fa-home">Hello World!</a></li>
      </ul>
    </article>
    <footer>
      <nav>
        <a class="fa fa-align-right" data-aside="lmenu">左菜单</a>
        <a class="fa fa-home active"></a>
        <a class="fa fa-align-left" data-aside="rmenu">右菜单</a>
      </nav>
    </footer>
  </section>
  <aside id="lmenu">
    <header>左菜单<nav><a class="fa fa-arrow-circle-left" data-aside="lmenu"></a></nav></header>
    <article>
      <ul>
        <li><a class="fa fa-info-circle">关于我们</a></li>
        <li><a class="fa fa-info-circle">关于我们</a></li>
        <li><a class="fa fa-info-circle">关于我们</a></li>
      </ul>
    </article>
    <footer></footer>
  </aside>
  <aside id="rmenu" class="right">
    <header>右菜单<nav><a class="fa fa-arrow-circle-right" data-aside="rmenu"></a></nav></header>
    <article>
      <ul>
        <li><a class="fa fa-info-circle">关于我们</a></li>
        <li><a class="fa fa-info-circle">关于我们</a></li>
        <li><a class="fa fa-info-circle">关于我们</a></li>
      </ul>
    </article>
    <footer>
      <nav>
        <a class="fa fa-align-right" data-aside="lmenu">左菜单</a>
        <a class="fa fa-align-left active" data-aside="rmenu">右菜单</a>
      </nav>
    </footer>
  </aside>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
  });
  </script>
</body>
</html>