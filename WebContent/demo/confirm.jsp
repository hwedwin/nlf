<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Confirm</title>
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
  I.ui.Button.create({skin:'Blue',label:'蓝色Confirm',icon:'thumbs-up',callback:function(){
    I.z.Confirm.create({
      skin:'Blue',
      content:'Hello World!',
      yes:function(){
        window.alert('u choosed yes');
      },
      no:function(){
        window.alert('u choosed no');
      }
    });
  }});
  
  //绿色按钮点击后显示绿色Alert
  I.ui.Button.create({skin:'Green',label:'绿色Confirm',icon:'thumbs-up',callback:function(){
    I.z.Confirm.create({
      skin:'Green',
      content:'Hello World!',
      yes:function(){
        window.alert('u choosed yes');
      },
      no:function(){
        window.alert('u choosed no');
      }
    });
  }});
  
  //默认按钮点击后显示默认Alert
  I.ui.Button.create({label:'默认Confirm',icon:'thumbs-up',callback:function(){
    I.z.Confirm.create({
      content:'Hello World!',
      yes:function(){
        window.alert('u choosed yes');
      },
      no:function(){
        window.alert('u choosed no');
      }
    });
  }});
});
</script>
</body>
</html>