<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>提示信息</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<script type="text/javascript">
I.want(function(){
  //创建一个蓝色的按钮
  I.ui.Button.create({skin:'Blue',label:'显示蓝色的Tip',icon:'phone',callback:function(){
    //创建蓝色提示信息
    I.ui.Tip.create({msg:'Hello World!',skin:'Blue'});
  }});
  //创建一个默认的按钮
  I.ui.Button.create({label:'显示默认的Tip',icon:'phone',callback:function(){
    //创建默认提示信息
    I.ui.Tip.create({msg:'Hello World!'});
  }});
});
</script>
</body>
</html>