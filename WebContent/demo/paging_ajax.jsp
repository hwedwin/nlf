<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>ajax请求的自动分页</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a id="btnA">基于ajax的自动分页</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{icon:'fa fa-thumbs-up',callback:function(){
    var ntw = I.z.SimpleWin.create({width:600,content:'loading'});
    I.net.Page.find('test.Action/paging',null,ntw.contentPanel);
  }});
});
</script>
</body>
</html>