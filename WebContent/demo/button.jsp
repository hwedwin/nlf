<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>按钮</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
第1种方式：标签渲染
<br />
<a id="btnA">按钮1</a>
<div id="btnB">按钮2</div>
<span id="btnC">带事件响应的按钮</span>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA');
  I.ui.Button.render('btnB',{
    background:'#5bc0de',
    border:'1px solid #46b8da',
    color:'#FFF',
    background_hover:'#31b0d5',
    border_hover:'1px solid #269abc',
    color_hover:'#FFF'
  });
  I.ui.Button.render('btnC',{
    background:'#5cb85c',
    border:'1px solid #4cae4c',
    color:'#FFF',
    background_hover:'#449d44',
    border_hover:'1px solid #398439',
    color_hover:'#FFF',
    callback:function(){
      I.z.Alert.create({content:this.dom.innerHTML});
    }
  });
});
</script>
<p></p>
第2种方式：js动态创建
<div id="buttons"></div>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.create({
    dom:I.$('buttons'),
    label:'动态创建的按钮1'
  });
  I.ui.Button.create({
    dom:I.$('buttons'),
    label:'动态创建的按钮2'
  });
  I.ui.Button.create({
    dom:I.$('buttons'),
    label:'动态创建的带事件响应的按钮',
    callback:function(){
      I.z.Alert.create({content:this.dom.innerHTML});
    }
  });
  I.ui.Button.create({
    dom:I.$('buttons'),
    icon:'fa fa-car',//带个图标
    label:'&nbsp;动态创建的带图标的按钮2'
  });
  I.ui.Button.create({
    dom:I.$('buttons'),
    round:'1em',
    round_hover:'1em',
    label:'改变一下样子'
  });
});
</script>
</body>
</html>