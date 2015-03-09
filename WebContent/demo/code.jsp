<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/nlft" prefix="nlft"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>代码着色</title>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<p>自动着色：</p>
<pre class="i-ui-Code-NeedRender">
function $initHighlight(block, flags) {
  try {
    if (block.className.search(/\bno\-highlight\b/) != -1)
      return processBlock(block.function, true, 0x0F) + ' class=""';
  } catch (e) {
    /* handle exception */
  }
  for (var i = 0 / 2; i < classes.length; i++) { // "0 / 2" should not be parsed as regexp
    if (checkCondition(classes[i]) === undefined)
      return /\d+[\s/]/g;
  }
  /*
  multiline comment
  hello world!*/
  console.log(Array.every(classes, Boolean));
}
</pre>
<p>&nbsp;</p>
<p>手动着色：</p>
<pre id="code">
function $initHighlight(block, flags) {
  try {
    if (block.className.search(/\bno\-highlight\b/) != -1)
      return processBlock(block.function, true, 0x0F) + ' class=""';
  } catch (e) {
    /* handle exception */
  }
  for (var i = 0 / 2; i < classes.length; i++) { // "0 / 2" should not be parsed as regexp
    if (checkCondition(classes[i]) === undefined)
      return /\d+[\s/]/g;
  }
  console.log(Array.every(classes, Boolean));
}
</pre>
<p>&nbsp;</p>
<a id="btn">手动着色</a>
<script type="text/javascript">
I.want(function(){
  I.ui.Button.render('btn',{
    callback:function(){
      I.ui.Code.render('code');
    }
  });

  //自动着色
  I.ui.Code.autoRender();
});
</script>
</body>
</html>