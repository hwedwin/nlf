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
footer{
  border-top:1px solid #D6D6D6;
  background-color:#FFF;
}
#menu{
  background-color:#FFF;
  width:50%;
  z-index:2;
  border-left:1px solid #DDD;
  -webkit-box-shadow:-2px 0px 5px #666;
  -moz-box-shadow:-2px 0px 5px #666;
  -ms-box-shadow:-2px 0px 5px #666;
  -o-box-shadow:-2px 0px 5px #666;
  box-shadow:-2px 0px 5px #666;
}
</style>
</head>
<body>
  <header>
    <nav>
      <a class="fa fa-home" href="index.jsp"></a>
    </nav>
    <i>NLF MOBILE</i>
    <nav>
      <a class="fa fa-bars a" data-aside="menu"></a>
    </nav>
  </header>
  <article>
    <label>UI示例</label>
    <ul>
      <li class="link a" data-href="mobile_List.jsp"><i class="fa fa-list">列表</i></li>
      <li class="link a" data-href="mobile_Ball.jsp"><i class="fa fa-circle">mobile.Ball</i></li>
      <li id="win"><i class="fa fa-desktop">mobile.Win</i></li>
      <li id="alert"><i class="fa fa-info-circle">mobile.Alert</i></li>
      <li id="confirm"><i class="fa fa-question-circle">mobile.Confirm</i></li>
      <li id="toast"><i class="fa fa-comments-o">mobile.Toast</i></li>
      <li id="loading"><i class="fa fa-spinner">mobile.Loading</i></li>
      <li id="mask"><i class="fa fa-slack">mobile.Mask</i></li>
      <li class="link a" data-href="mobile_Group.jsp"><i class="fa fa-exchange">切换</i></li>
      <li class="link a" data-href="mobile_Form.jsp"><i class="fa fa-file-o">表单</i></li>
      <li class="link a" data-href="mobile_Button.jsp"><i class="fa fa-stop">按钮</i></li>
      <li class="link a" data-href="mobile_Guesture.jsp"><i class="fa fa-guesture">滑动</i></li>
    </ul>
    <label>标题栏</label>
    <ul>
      <li class="link a" data-href="mobile_Title.jsp"><i class="fa fa-bars">两边都有按钮的标题栏</i></li>
      <li class="link a" data-href="mobile_Title_Left.jsp"><i class="fa fa-bars">左边有按钮的标题栏</i></li>
      <li class="link a" data-href="mobile_Title_Right.jsp"><i class="fa fa-bars">右边有按钮的标题栏</i></li>
      <li class="link a" data-href="mobile_Title_None.jsp"><i class="fa fa-bars">没有按钮的标题栏</i></li>
    </ul>
    <label>底部</label>
    <ul>
      <li class="link a" data-href="mobile_Bottom_Icon_V.jsp"><i class="fa fa-ellipsis-h">带纵向图标的底部按钮</i></li>
      <li class="link a" data-href="mobile_Bottom_Icon_H.jsp"><i class="fa fa-ellipsis-h">带横向图标的底部按钮</i></li>
      <li class="link a" data-href="mobile_Bottom_Icon_None.jsp"><i class="fa fa-ellipsis-h">纯文字的底部按钮</i></li>
      <li class="link a" data-href="mobile_Bottom_Def.jsp"><i class="fa fa-ellipsis-h">自定义的底部</i></li>
      <li class="link a" data-href="mobile_Bottom_Transparent.jsp"><i class="fa fa-ellipsis-h">半透明的浮动底部</i></li>
    </ul>
  </article>
  <aside id="menu" data-at="right">
    <header>
    <nav>
      <a class="fa fa-bars a" data-aside="menu"></a>
    </nav>
    <i>菜单</i>
    <nav>
      <a class="fa fa-none"></a>
    </nav>
    </header>
    <article>
    <ul>
      <li>测试</li>
    </ul>
    </article>
  </aside>
  <script>
  I.want(function(){
    I.ui.Mobile.render();
    I.listen('win','click',function(m,e){
      I.mobile.Win.create({title:'<i>标题</i>',content:'内容部分。'});
    });
    I.listen('alert','click',function(m,e){
      I.mobile.Alert.create({content:'内容部分。'});
    });
    I.listen('loading','click',function(m,e){
      var loading = I.mobile.Loading.create();
      I.delay(3000,function(){
        loading.close();
      });
    });
    I.listen('mask','click',function(m,e){
      var mask = I.mobile.Mask.create();
      I.delay(3000,function(){
        mask.close();
      });
    });
    I.listen('confirm','click',function(m,e){
      I.mobile.Confirm.create({
        content:'您确定要修炼辟邪剑谱吗？',
        yes:function(){
          this.close();
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