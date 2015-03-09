<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>提示条</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a id="btnA">显示ToolTip</a>
<script type="text/javascript">
I.want(function(){
  var tip = null;
  I.ui.Button.render('btnA');
  I.listen('btnA','mouseover',function(){
    if(tip){
      tip.close();
      tip = null;
    }
    tip = I.ui.ToolTip.create({dom:I.$('btnA'),content:'Hello World!'});
  });
  I.listen('btnA','mouseout',function(){
    if(tip){
      tip.close();
      tip = null;
    }
  });
});
</script>
</body>
</html>