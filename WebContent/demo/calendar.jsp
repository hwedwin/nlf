<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>日历</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<p>选择日期：<input id="day" type="text" /></p>
<script type="text/javascript">
I.want(function(){
  I.util.MultiCalendar.setConfig({
    offsetMonth:-1,
    ban:[{
     tag:'=',
     y:2015,
     m:5,
     d:4
    },{
      tag:'<',
      y:2015,
      m:5,
      d:1
     }]
  });
  I.util.MultiCalendar.bind('day');
});
</script>
</body>
</html>