<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NLF组件示例</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<ul id="menu">
  <li><b></b><i></i><a>UI组件</a>
    <ul>
      <li><b></b><i></i><a data-url="${PATH}/demo/button.jsp">按钮</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/loading.jsp">进度条</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/tip.jsp">提示信息</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/tooltip.jsp">提示条</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/alert.jsp">Alert</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/confirm.jsp">Confirm</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/tree.jsp">树</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/upload.jsp">上传组件</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/notify.jsp">通知</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/win.jsp">窗口</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/simplewin.jsp">简单窗</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/notitlewin.jsp">无标题窗</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/form.jsp">表单</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/editor.jsp">文本编辑器</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/slider.jsp">滑块</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/calendar.jsp">日历控件</a></li>
    </ul>
  </li>
  <li><b></b><i></i><a>自动分页</a>
    <ul>
      <li><b></b><i></i><a data-url="${PATH}/test.Action/paging">普通页面的自动分页示例</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/paging_ajax.jsp">AJAX请求的自动分页示例</a></li>
    </ul>
  </li>
  <li><b></b><i></i><a>工具</a>
    <ul>
      <li><b></b><i></i><a data-url="${PATH}/demo/template.jsp">js模板引擎</a></li>
      <li><b></b><i></i><a data-url="${PATH}/demo/code.jsp">代码着色</a></li>
    </ul>
  </li>
  <li><b></b><i></i><a>手机界面</a>
    <ul>
      <li><b></b><i></i><a data-url="${PATH}/demo_mobile/index.jsp">手机DEMO</a></li>
    </ul>
  </li>
</ul>
<a id="btnA">插入点数据</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Tree.render('menu',{
    onClick:function(who){
      if('folder'==who.type){
        if(who.expand){
          who.close();
        }else{
          who.open();
        }
      }
      var url = who.dom.a.getAttribute('data-url');
      if(url){
        self.location = url;
      }
    }
  });
  I.ui.Button.render('btnA',{
    callback:function(){
      I.net.Rmi.call('test.Action','addData',function(){

      });
    }
  });
});
</script>
</body>
</html>