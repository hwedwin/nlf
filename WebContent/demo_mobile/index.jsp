<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mobile</title>
<meta name="viewport" content="width=device-width,user-scalable=no" />
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<script src="${PATH}/js/icore.js"></script>
<style type="text/css">
body{display:none;}
</style>
</head>
<body>
  <header>
    <nav>
      <a class="fa fa-home"></a>
    </nav>
    <i>NLF MOBILE</i>
  </header>
  <article>
    <label>UI示例</label>
    <ul>
      <li class="link"><a href="mobile_List.jsp" class="fa fa-list">列表</a></li>
      <li class="link"><a href="mobile_Ball.jsp" class="fa fa-circle">mobile.Ball</a></li>
      <li><a id="win" class="fa fa-desktop">mobile.Win</a></li>
      <li><a id="alert" class="fa fa-info-circle">mobile.Alert</a></li>
      <li><a id="confirm" class="fa fa-question-circle">mobile.Confirm</a></li>
      <li><a id="toast" class="fa fa-comments-o">mobile.Toast</a></li>
      <li class="link"><a href="mobile_Group.jsp" class="fa fa-exchange">切换</a></li>
      <li class="link"><a href="mobile_Form.jsp" class="fa fa-file-o">表单</a></li>
    </ul>
    <label>标题栏</label>
    <ul>
      <li class="link"><a href="mobile_Title.jsp" class="fa fa-bars">两边都有按钮的标题栏</a></li>
      <li class="link"><a href="mobile_Title_Left.jsp" class="fa fa-bars">左边有按钮的标题栏</a></li>
      <li class="link"><a href="mobile_Title_Right.jsp" class="fa fa-bars">右边有按钮的标题栏</a></li>
      <li class="link"><a href="mobile_Title_None.jsp" class="fa fa-bars">没有按钮的标题栏</a></li>
    </ul>
    <label>底部</label>
    <ul>
      <li class="link"><a href="mobile_Bottom_Icon_V.jsp" class="fa fa-ellipsis-h">带纵向图标的底部按钮</a></li>
      <li class="link"><a href="mobile_Bottom_Icon_H.jsp" class="fa fa-ellipsis-h">带横向图标的底部按钮</a></li>
      <li class="link"><a href="mobile_Bottom_Icon_None.jsp" class="fa fa-ellipsis-h">纯文字的底部按钮</a></li>
      <li class="link"><a href="mobile_Bottom_Def.jsp" class="fa fa-ellipsis-h">自定义的底部</a></li>
      <li class="link"><a href="mobile_Bottom_Transparent.jsp" class="fa fa-ellipsis-h">半透明的浮动底部</a></li>
    </ul>
  </article>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
    I.listen('win','click',function(m,e){
      I.mobile.Win.create({title:'<i class="fa fa-info-circle">提示</i>',content:'内容部分。'});
    });
    I.listen('alert','click',function(m,e){
      I.mobile.Alert.create({content:'内容部分。'});
    });
    I.listen('confirm','click',function(m,e){
      I.mobile.Confirm.create({
        content:'您确定要修炼辟邪剑谱吗？',
        yes:function(){
          I.mobile.Alert.create({content:'您已经成功变性，但是修炼失败！'});
        },
        no:function(){
          I.mobile.Alert.create({content:'欲练此功，必先自宫哦。'});
        }
      });
    });
    I.listen('toast','click',function(m,e){
      I.mobile.Toast.create({msg:'Hello World!'});
    });
  });
  </script>
</body>
</html>