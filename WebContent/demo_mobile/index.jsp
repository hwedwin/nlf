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
      <a class="fa fa-home"></a>
      <a class="fa fa-info-circle"></a>
    </nav>
    <i>NLF MOBILE</i>
    <nav>
      <a class="fa fa-info"></a>
      <a class="fa fa-bars"></a>
    </nav>
  </header>
  <article>
    <label>个人</label>
    <ul>
      <li>个性化设置</li>
      <li class="link"><a class="fa fa-user">账户与同步</a></li>
      <li><a class="fa fa-map-marker">位置</a></li>
      <li>安全</li>
      <li>辅助功能</li>
      <li>备份与重置</li>
    </ul>
    <label>设备</label>
    <ul>
      <li>显示、手势和按钮</li>
      <li><a class="fa fa-volume-up">声音</a></li>
      <li><a class="fa fa-phone">呼叫</a></li>
      <li>应用程序</li>
      <li>存储</li>
      <li>电源</li>
    </ul>
    <label>个人资料</label>
    <ul>
      <li class="input"><i>姓名</i><input type="text" placeholder="六特尔" /></li>
      <li class="input"><i>年龄</i><input type="text" placeholder="18" /></li>
      <li class="input"><i>性别</i><select><option>男</option><option>女</option></select></li>
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
  <footer>
    <nav>
      <a><em class="fa fa-weixin"></em><div>微信</div></a>
      <a><em class="fa fa-fax"></em>通讯录</a>
      <a><em class="fa fa-crosshairs"></em>发现</a>
      <a><em class="fa fa-user"></em><div>我</div></a>
    </nav>
  </footer>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
  });
  </script>
</body>
</html>