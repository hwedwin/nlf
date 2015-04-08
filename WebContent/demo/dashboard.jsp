<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Dashboard</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<script type="text/javascript">
I.want(function(){
  var win = I.ui.Dashboard.create({
    tool_bar_height:60
  });
  win.setMenu([
    {text:'首页',icon:'fa fa-home'},
    {text:'UI组件',icon:'fa fa-desktop',children:[
      {text:'按钮',icon:'fa fa-hand-o-up',link:'demo/button.jsp'},
      {text:'进度条',icon:'fa fa-spinner',link:'demo/loading.jsp'},
      {text:'提示信息',icon:'fa fa-comments-o',link:'demo/tip.jsp'},
      {text:'提示条',icon:'fa fa-comments-o',link:'demo/tooltip.jsp'},
      {text:'Alert',icon:'fa fa-info-circle',link:'demo/alert.jsp'},
      {text:'confirm',icon:'fa fa-question-circle',link:'demo/confirm.jsp'},
      {text:'树',icon:'fa fa-tree',link:'demo/tree.jsp'},
      {text:'上传组件',icon:'fa fa-file',link:'demo/upload.jsp'},
      {text:'通知',icon:'fa fa-bell',link:'demo/notify.jsp'},
      {text:'窗口',icon:'fa fa-desktop',children:[
        {text:'默认窗口',icon:'fa fa-desktop',link:'demo/win.jsp'},
        {text:'简单窗口',icon:'fa fa-desktop',link:'demo/simplewin.jsp'},
        {text:'无标题窗口',icon:'fa fa-desktop',link:'demo/notitlewin.jsp'}
      ]},
      {text:'表单',icon:'fa fa-check-square',link:'demo/form.jsp'},
      {text:'文本编辑器',icon:'fa fa-text-width',link:'demo/editor.jsp'},
      {text:'滑块',icon:'fa fa-sliders',link:'demo/slider.jsp'},
      {text:'日历控件',icon:'fa fa-calendar',link:'demo/calendar.jsp'},
      {text:'取色器',icon:'fa fa-tint',link:'demo/color.jsp'}
    ]},
    {text:'自动分页',icon:'fa fa-bars',children:[
      {text:'普通页面自动分页',icon:'fa fa-bars',link:'test.Action/paging',callback:function(){
        window.open('${PATH}/'+this.link);
      }},
      {text:'ajax自动分页',icon:'fa fa-bars',link:'test.Action/paging'}
    ]},
    {text:'工具',icon:'fa fa-briefcase',children:[
      {text:'js模板引擎',icon:'fa fa-terminal',link:'demo/template.jsp'},
      {text:'代码着色',icon:'fa fa-code',link:'demo/code.jsp'}
    ]}
  ]);
  win.addItem(win.toolBar,[
    {type:'head',content:'http://6tail.cn/npress/uploads/1412070377753000.jpg'},
    {type:'text',content:'六特尔'},
    {type:'text',content:'退出'}
  ],function(index){
    switch(index){
    case 0:
      I.z.Alert.create({content:'您点我头像干神马？'});
      break;
    }
  });
  
  win.addItem(win.titleBar,[
    {type:'head',content:'http://6tail.cn/npress/uploads/1412070377753000.jpg'},
    {type:'text',content:'六特尔'},
    {type:'text',content:'退出'}
  ],function(index){
    switch(index){
    case 0:
      I.z.Alert.create({content:'您点我头像干神马？'});
      break;
    }
  });
  win.addItem(win.foot,[
    {type:'head',content:'http://6tail.cn/npress/uploads/1412070377753000.jpg'},
    {type:'text',content:'六特尔'},
    {type:'text',content:'退出'}
  ],function(index){
    switch(index){
    case 0:
      I.z.Alert.create({content:'您点我头像干神马？'});
      break;
    }
  });
  win.addItem(win.footer,[
    {type:'head',content:'http://6tail.cn/npress/uploads/1412070377753000.jpg'},
    {type:'text',content:'六特尔'},
    {type:'text',content:'退出'}
  ],function(index){
    switch(index){
    case 0:
      I.z.Alert.create({content:'您点我头像干神马？'});
      break;
    }
  });
  var div = I.insert('div',win.titleBar);
  I.util.Boost.addStyle(div,'text-indent:1em;line-height:'+win.config.title_bar_height+'px;');
  div.innerHTML='顶部标题栏';
  
  //工具栏左侧添加标题
  div = I.insert('div','before',win.tools[0]);
  I.util.Boost.addStyle(div,'margin-left:20px;margin-right:20px;float:left;line-height:'+win.config.tool_bar_height+'px;color:#FFF;font-size:18px');
  div.innerHTML='NLF后台管理界面';
  
  //底部
  div = I.insert('div',win.footer);
  I.util.Boost.addStyle(div,'text-align:center;line-height:'+win.config.footer_height+'px');
  div.innerHTML='底部';
  
  //内容区的底部
  div = I.insert('div',win.foot);
  I.util.Boost.addStyle(div,'text-align:center;line-height:'+win.config.foot_height+'px');
  div.innerHTML='内容区的底部';
  
  //内容区
  I.util.Boost.addStyle(win.content,'padding:20px;');
  
  div = I.insert('div',win.content);
  I.util.Boost.addStyle(div,'line-height:3em;');
  div.innerHTML='内容区';
  
  win.addBread(win.head);
});
</script>
</body>
</html>