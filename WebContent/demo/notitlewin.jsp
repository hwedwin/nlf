<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>无标题窗</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a id="btnA">显示无标题窗</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{
    callback:function(){
      var win = I.z.NoTitleWin.create({
        content:'你没中奖...',
        width:500,
        height:300
      });
      var btn = I.ui.Button.create({
        dom:win.contentPanel,
        label:'关闭',
        callback:function(){
          win.close();
        }
      });
    }
  });
});
</script>
</body>
</html>