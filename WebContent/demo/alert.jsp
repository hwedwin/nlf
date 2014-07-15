<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Alert</title>
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
  //蓝色按钮点击后显示蓝色Alert
  I.ui.Button.create({skin:'Blue',label:'蓝色Alert',icon:'thumbs-up',callback:function(){
    I.z.Alert.create({skin:'Blue',content:'Hello World!'});
  }});
  
  //绿色按钮点击后显示绿色Alert
  I.ui.Button.create({skin:'Green',label:'绿色Alert',icon:'thumbs-up',callback:function(){
    I.z.Alert.create({skin:'Green',content:'Hello World!'});
  }});
  
  //默认按钮点击后显示默认Alert
  I.ui.Button.create({label:'默认Alert',icon:'thumbs-up',callback:function(){
    I.z.Alert.create({content:'Hello World!'});
  }});
});
</script>
</body>
</html>