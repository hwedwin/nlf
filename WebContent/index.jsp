<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>demo</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{margin:0;padding:0;font-size:14px;}
#a{
  margin:200px;
}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<div id="a">text</div>
<script type="text/javascript">
I.want(function(){
  var mask = I.ui.Mask.create();
  I.delay(1000,function(){
    mask.close();
    I.ui.Tip.create({msg:'Hello World!',skin:'Blue'});
    var ld = I.ui.Loading.create();
    I.delay(2000,function(){
      ld.close();
      I.ui.ToolTip.create({dom:I.$('a'),content:'Hello World!'});
      I.ui.Button.create({label:'Window',icon:'phone',callback:function(){
        I.z.Win.create({content:'Hello World!'});
      }});
      I.ui.Button.create({skin:'Blue',label:'Alert',icon:'thumbs-up',callback:function(){
        I.z.Alert.create({skin:'Blue',content:'Hello World!'});
      }});
      I.ui.Button.create({skin:'Green',label:'Alert',icon:'thumbs-up',callback:function(){
        I.z.Alert.create({skin:'Green',content:'Hello World!'});
      }});
    });
    I.z.SimpleWin.create({content:'Hello World!',width:1000});
  });
});
</script>
</body>
</html>