<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>无标题窗</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p></p>
<a id="btnA">显示无标题窗</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{
    callback:function(){
      var win = I.z.NoTitleWin.create({
        mask_color:'#000',
        mask_opacity:50,
        shadow_color:'#FFF',
        shadow_opacity:50,
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