<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>表单</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<div id="myForm">
  <ul>
    <li data-width="10">姓名：</li>
    <li><input id="name" type="text" /></li>
    <li data-width="10">账号：</li>
    <li><input id="account" type="text" /></li>
  </ul>
  <ul>
    <li data-width="10">姓名：</li>
    <li><input id="name" type="text" /></li>
    <li data-width="10">账号：</li>
    <li><input id="account" type="text" /></li>
    <li data-width="10">姓名：</li>
    <li><input id="name" type="text" /></li>
    <li data-width="10">账号：</li>
    <li><input id="account" type="text" /></li>
  </ul>
  <ul>
    <li data-width="10">电子邮件：</li>
    <li><input id="email" type="text" /></li>
  </ul>
  <ul>
    <li data-width="10">日期：</li>
    <li><input id="date" type="text" /></li>
  </ul>
  <ul>
    <li data-width="10">文件上传：</li>
    <li>
      <div id="myUpload">
        <i>文件上传</i>
        <b></b>
        <form>
          <input type="file" />
        </form>
      </div>
    </li>
  </ul>
  <ul>
    <li data-width="10">radio：</li>
    <li>
      <a id="radio0">是
        <input name="rd" type="radio" value="1" />
      </a>
      <a id="radio1">否
        <input name="rd" type="radio" value="0" />
      </a>
    </li>
  </ul>
  <ul>
    <li data-width="10">是否超级管理员：</li>
    <li>
      <a id="check">
        <input id="check0" type="checkbox" value="a" />
      </a>
    </li>
  </ul>
  <ul>
    <li data-width="10">星期：</li>
    <li>
      <a id="week1">一
        <input id="week_1" type="checkbox" value="1" />
      </a>
      <a id="week2">二
        <input id="week_2" type="checkbox" value="2" />
      </a>
      <a id="week3">三
        <input id="week_3" type="checkbox" value="3" />
      </a>
      <a id="week4">四
        <input id="week_4" type="checkbox" value="4" />
      </a>
    </li>
  </ul>
  <ul>
    <li data-width="10">学历：</li>
    <li>
        <select id="xueli">
          <option value="0">学前班</option>
          <option value="1">幼儿园</option>
          <option value="2">小学</option>
          <option value="3">初中</option>
          <option value="4">高中</option>
          <option value="5">本科</option>
        </select>
    </li>
  </ul>
  <ul>
    <li data-width="10">备注：</li>
    <li>
        <textarea rows="10"></textarea>
    </li>
  </ul>
  <ul>
    <li></li>
    <li data-width="10"><a id="btn">按钮</a></li>
  </ul>
</div>
<script type="text/javascript">
I.want(function(){
  I.ui.Form.render('myForm');

  I.ui.Upload.render('myUpload',{
    width:90,
    height:30,
    url:'${PATH}/test.Action/upload',//上传地址，必选
    onSuccess:function(r){//上传成功后调用
      window.alert('文件上传成功：'+r.data);
    }
  });
  I.ui.Button.render('btn',{
    callback:function(){
    }
  });
  I.ui.Radio.render('radio0');
  I.ui.Radio.render('radio1');
  I.ui.Checkbox.render('check');
  I.ui.Checkbox.render('week1');
  I.ui.Checkbox.render('week2');
  I.ui.Checkbox.render('week3');
  I.ui.Checkbox.render('week4');
  I.util.MultiCalendar.bind('date');
});
</script>
</body>
</html>