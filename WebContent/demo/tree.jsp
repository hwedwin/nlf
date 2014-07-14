<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="/nlfe" prefix="nlfe"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>树</title>
<link type="text/css" rel="stylesheet" href="${PATH}/css/font-awesome.css" />
<style type="text/css">
*{font-size:14px;}
ul.i-ui-Tree-Default{
  margin:0;
  padding:0;
  display:block;
  -webkit-margin-before:0;
  -webkit-margin-after:0;
  -webkit-margin-start:0;
  -webkit-margin-end:0;
  -webkit-padding-start:0;
}
ul.i-ui-Tree-Default i{
  font-weight:normal;
  font-style:normal;
  font-size:1em;
  margin:0;
  padding:0;
  margin-left:3px;
  cursor:default;
  color:#999;
}
ul.i-ui-Tree-Default b{
  font-weight:normal;
  font-style:normal;
  font-size:1em;
  margin:0;
  padding:0;
  cursor:default;
  color:#333;
}
ul.i-ui-Tree-Default b.not-visible{
  filter:alpha(opacity=0);
  -moz-opacity:0;
  opacity:0;
}
ul.i-ui-Tree-Default a{
  font-weight:normal;
  font-style:normal;
  margin:0;
  padding:0;
  margin-left:3px;
  text-decoration:none;
  color:#666;
}
ul.i-ui-Tree-Default a:hover{
  background-color:#EEE;
}
ul.i-ui-Tree-Default li{
  list-style:none;
  display:block;
  margin:0;
  padding:0;
  line-height:1.5em;
}
ul.i-ui-Tree-Default ul{
  margin:0;
  padding:0;
  display:block;
  margin-left:1.5em;
  -webkit-margin-before:0;
  -webkit-margin-after:0;
  -webkit-margin-start:1.5em;
  -webkit-margin-end:0;
  -webkit-padding-start:0;
}
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<ul id="tree">
  <li><b></b><i></i><input type="checkbox" /><a>一年级</a>
    <ul>
      <li><b></b><i></i><input type="checkbox" /><a>1班</a>
        <ul>
          <li><b></b><i></i><input type="checkbox" /><a>甲阿贾克斯的就啊就是看到</a></li>
          <li><b></b><i></i><input type="checkbox" /><a>乙</a></li>
          <li><b></b><i></i><input type="checkbox" /><a>丙</a></li>
        </ul>
      </li>
      <li><b></b><i></i><input type="checkbox" /><a>2班</a></li>
      <li><b></b><i></i><input type="checkbox" /><a>3班</a>
        <ul>
          <li><b></b><i></i><input type="checkbox" /><a>甲</a></li>
          <li><b></b><i></i><input type="checkbox" /><a>乙</a></li>
          <li><b></b><i></i><input type="checkbox" /><a>丙</a></li>
        </ul>
      </li>
    </ul>
  </li>
  <li><b></b><i></i><input type="checkbox" /><a>二年级</a></li>
  <li><b></b><i></i><input type="checkbox" /><a>三年级</a></li>
</ul>
<a id="btnA" class="i-ui-Button-Default">获取选中的</a>
<script type="text/javascript">
I.want(function(){
  var tree = I.ui.Tree.render('tree',{
    onClick:function(who){
      if('folder'==who.type){
        who.expand = !who.expand;
        who.repaint();
      }else{
        window.alert(who.dom.a.innerHTML);
      }
    }
  });
  I.listen('btnA','click',function(m,e){
    var text = [];
    var selected = tree.getSelected();
    for(var i=0;i<selected.length;i++){
      text.push(selected[i].dom.a.innerHTML);
    }
    window.alert(text.join(','));
  });
});
</script>
</body>
</html>