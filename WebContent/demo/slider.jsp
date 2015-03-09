<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>滑块</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<p>第1种方式：标签渲染</p>
<p>&nbsp;</p>
<p>数值：<span id="num"></span></p>
<div id="slider">
  <b></b>
  <i></i>
</div>
<script type="text/javascript">
I.want(function(){
  I.ui.Slider.render('slider',{
    callback:function(){
      I.$('num').innerHTML = this.value;
    },
    value:50
  });
});
</script>
<p>&nbsp;</p>
<p>第2种方式：js动态创建</p>
<p>&nbsp;</p>
<a id="btn">动态创建滑块</a>
<p>&nbsp;</p>
<div id="here"></div>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btn',{
    callback:function(){
      I.ui.Slider.create({
        dom:I.$('here'),
        width:400,
        height:10,
        block_width:20,
        block_height:30,
        callback:function(){
          I.$('num').innerHTML = this.value;
        },
        value:40
      });
    }
  });
});
</script>
</body>
</html>