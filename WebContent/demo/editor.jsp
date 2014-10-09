<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>文本编辑器</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a href="${PATH}/">返回首页</a>
<p>&nbsp;</p>
<textarea id="editor">Hello World!</textarea>
<p>&nbsp;</p>
<div id="v"></div>
<a id="btn">获取内容</a>
<a id="btnB">设置内容</a>
<a id="btnC">预览</a>
<script type="text/javascript">
I.want(function(){
  var editor = I.ui.Editor.render('editor',{
    uploadUrl:'${PATH}/test.Action/uploadPic'
  });
  I.ui.Button.render('btn',{
    callback:function(){
      window.alert(editor.getContent());
    }
  });
  I.ui.Button.render('btnB',{
    callback:function(){
      editor.setContent('设置的内容哟。');
    }
  });
  I.ui.Button.render('btnC',{
    callback:function(){
      I.$('v').innerHTML = editor.getContent();
    }
  });
});
</script>
</body>
</html>