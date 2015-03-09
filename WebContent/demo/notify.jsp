<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>通知</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a id="btnA">显示通知</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{
    callback:function(){
      I.ui.Notify.create({
        title:'恭喜',
        content:'你没中奖'
      });
    }
  });
});
</script>
</body>
</html>