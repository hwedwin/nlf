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
</style>
<script type="text/javascript" src="${PATH}/js/icore.js"></script>
</head>
<body>
<!-- 通过html渲染tree -->
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
<a id="btnB" class="i-ui-Button-Default">添加节点</a>
<a id="btnC" class="i-ui-Button-Default">通过json创建一颗新的树</a>
<script type="text/javascript">
I.want(function(){
  //渲染tree
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
  //按钮事件
  I.listen('btnA','click',function(m,e){
    var text = [];
    var selected = tree.getSelected();
    for(var i=0;i<selected.length;i++){
      text.push(selected[i].dom.a.innerHTML);
    }
    window.alert(text.join(','));
  });
  
  //按钮事件
  I.listen('btnB','click',function(m,e){
    var selected = tree.getSelected();
    if(selected.length<1){
      tree.add('<b></b><i></i><input type="checkbox" /><a>新添加的节点</a>');
    }else{
      for(var i=0;i<selected.length;i++){
        selected[i].add('<b></b><i></i><input type="checkbox" /><a>新添加的节点</a>');
      }
    }
  });
  
  //按钮事件，通过json渲染tree
  I.listen('btnC','click',function(m,e){
    var d = [
      {text:'爷爷',checked:true,expand:false,children:[
        {text:'父亲',children:[
          {text:'我',attribute:{
            'data-id':'6',
            gender:'男'
          }}
        ],attribute:{
          'data-id':'5',
          gender:'男'
        }}
      ],attribute:{
        'data-id':'1',
        gender:'男'
      }},
      {text:'奶奶',attribute:{
        'data-id':'2',
        gender:'女'
      }},
      {text:'外公',attribute:{
        'data-id':'3',
        gender:'男'
      }},
      {text:'外婆',attribute:{
        'data-id':'4',
        gender:'女'
      }}
    ];
    var newTree = I.ui.Tree.create({
      skin:'Blue',
      data:d,
      onClick:function(who){
        window.alert(who.dom.li.getAttribute('gender')+','+who.dom.li.getAttribute('data-id')+','+who.dom.a.innerHTML);
      }
    });
  });
});
</script>
</body>
</html>