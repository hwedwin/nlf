<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>按钮</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<p>第1种方式：标签渲染</p>
<a id="btnA">按钮1</a>
<div id="btnB">按钮2</div>
<span id="btnC">带事件响应的按钮</span>
<p>&nbsp;</p>
<p>第2种方式：批量渲染</p>
<ul id="ul">
  <li>按钮A</li>
  <li>按钮B</li>
  <li>按钮C</li>
  <li>按钮D</li>
  <li>按钮E</li>
  <li>按钮F</li>
  <li>按钮G</li>
</ul>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render(I.$('ul','*'),{
    callback:function(){
      window.alert(this.dom.innerHTML);
    }
  });
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
      I.z.Alert.create({content:this.dom.innerHTML,mask_opacity:60,mask_color:'#000',});
    }
  });
});
</script>
<p>&nbsp;</p>
<p>第3种方式：js动态创建</p>
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