<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Confirm</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<a id="btnB">默认Confirm</a>
<a id="btnA">蓝色Confirm</a>
<a id="btnC">深色遮罩Confirm</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btnA',{icon:'fa fa-thumbs-up',callback:function(){
    var win = I.z.Confirm.create({
      title_background:'#5bc0de',
      close_background:'#5bc0de',
      title_border_color:'#46b8da',
      title_color:'#FFF',
      close_color:'#FFF',
      shadow:'#5bc0de 0px 0px 8px',
      content:'Hello World!',
      yes_button_background:'#5bc0de',
      yes_button_border:'1px solid #46b8da',
      yes_button_color:'#FFF',
      yes_button_background_hover:'#31b0d5',
      yes_button_border_hover:'1px solid #269abc',
      yes_button_color_hover:'#FFF',
      content:'Hello World!',
      yes:function(){
        I.z.Alert.create({content:'u choosed yes'});
        win.close();
      },
      no:function(){
        I.z.Alert.create({content:'u choosed no'});
      }
    });
  }});
  I.ui.Button.render('btnB',{icon:'fa fa-thumbs-up',callback:function(){
    var win = I.z.Confirm.create({
      content:'Hello World!',
      yes:function(){
        I.z.Alert.create({content:'u choosed yes'});
        win.close();
      },
      no:function(){
        I.z.Alert.create({content:'u choosed no'});
      }
    });
  }});
  I.ui.Button.render('btnC',{icon:'fa fa-thumbs-up',callback:function(){
    var win = I.z.Confirm.create({
      mask_opacity:60,
      mask_color:'#000',
      content:'Hello World!',
      yes:function(){
        I.z.Alert.create({content:'u choosed yes'});
        win.close();
      },
      no:function(){
        I.z.Alert.create({content:'u choosed no'});
      }
    });
  }});
});
</script>
</body>
</html>