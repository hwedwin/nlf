<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>上传组件</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<br />
html渲染：
<div id="myUpload">
<i></i><b></b><form><input type="file" /></form>
</div>
<script type="text/javascript">
I.want(function(){
  I.ui.Upload.render('myUpload',{
    skin:'Blue',//皮肤，非必选
    url:'${PATH}/test.Action/upload',//上传地址，必选
    onSuccess:function(r){//上传成功后调用
      window.alert('文件上传成功：'+r.data);
    }
  });
});
</script>
<br />
<a id="btnA" class="i-ui-Button-Blue" href="javascript:void(0);">动态创建上传组件</a>
<script type="text/javascript">
I.want(function(){
  I.listen('btnA','click',function(){
    I.ui.Upload.create({
      skin:'Green',//皮肤，非必选
      url:'${PATH}/test.Action/upload',//上传地址，必选
      onSuccess:function(r){//上传成功后调用
        window.alert('文件上传成功：'+r.data);
      }
    });
  });
});
</script>
</body>
</html>