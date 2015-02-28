<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>取色器</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
选择颜色：<input type="text" id="a" />
<script>
I.want(function(){
  I.ui.Color.bind('a',{
    callback:function(){
      I.$('a').value=this.color;
    },
    onPreview:function(color){
      I.$('a').value=color;
    }
  });
});
</script>
</body>
</html>