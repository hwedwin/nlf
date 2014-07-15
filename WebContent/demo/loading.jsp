<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>进度条</title>
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
  //创建一个按钮
  I.ui.Button.create({label:'显示红色的进度条',icon:'phone',callback:function(){
    //显示红色的进度条，2秒后关闭
    var ld = I.ui.Loading.create({color:'red'});
    I.delay(2000,function(){
      ld.close();
    });
  }});
  
  //创建一个按钮
  I.ui.Button.create({label:'显示默认的进度条',icon:'phone',callback:function(){
    //显示红色的进度条，2秒后关闭
    var ld = I.ui.Loading.create();
    I.delay(2000,function(){
      ld.close();
    });
  }});
});
</script>
</body>
</html>