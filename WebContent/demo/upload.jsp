<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
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
<p></p>
单文件上传：
<div id="myUpload">
  <i>文件上传</i>
  <b></b>
  <form>
    <input type="file" />
  </form>
</div>
<script type="text/javascript">
//通过html渲染的上传组件
I.want(function(){
  I.ui.Upload.render('myUpload',{
    width:90,
    height:30,
    url:'${PATH}/test.Action/upload',//上传地址，必选
    onSuccess:function(r){//上传成功后调用
      window.alert('文件上传成功：'+r.data);
    }
  });
});
</script>
<p></p>
允许一次性上传多个文件：
<div id="myUploadMultiple">
  <i>文件上传</i>
  <b></b>
  <form>
    <input type="file" />
  </form>
</div>
<script type="text/javascript">
//通过html渲染的上传组件
I.want(function(){
  I.ui.Upload.render('myUploadMultiple',{
    width:90,
    height:30,
    multiple:true,
    url:'${PATH}/test.Action/upload',//上传地址，必选
    onSuccess:function(r){//上传成功后调用
      window.alert('文件上传成功：'+r.data);
    }
  });
});
</script>
<p></p>
<a id="btnA">动态创建上传组件</a>
<script type="text/javascript">
//动态创建的上传组件
I.want(function(){
  I.ui.Button.render('btnA',{
    callback:function(){
      I.ui.Upload.create({
        url:'${PATH}/test.Action/upload',//上传地址，必选
        onSuccess:function(r){//上传成功后调用
          window.alert('文件上传成功：'+r.data);
        }
      });
    }
  });
});
</script>
</body>
</html>