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
<a href="${PATH}/">返回首页</a>

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
<a id="btnA" class="i-ui-Button-Default" href="javascript:void(0);">获取选中的</a>
<a id="btnB" class="i-ui-Button-Default" href="javascript:void(0);">添加子节点</a>
<a id="btnG" class="i-ui-Button-Default" href="javascript:void(0);">来点暴力的添加1000个子节点</a>
<a id="btnD" class="i-ui-Button-Default" href="javascript:void(0);">删除选中节点</a>
<a id="btnE" class="i-ui-Button-Default" href="javascript:void(0);">全部展开</a>
<a id="btnF" class="i-ui-Button-Default" href="javascript:void(0);">全部收缩</a>
<br />
<a id="btnC" class="i-ui-Button-Default" href="javascript:void(0);">通过json创建一颗新的树</a>
<script type="text/javascript">
I.want(function(){
  //渲染树到id为tree的ul上
  var tree = I.ui.Tree.render('tree',{
    //当点击text的事件响应，who指点击的节点对象
    onClick:function(who){
      //让点击text的时候，也切换展开和收缩
      if('folder'==who.type){
        if(who.expand){
          who.close();
        }else{
          who.open();
        }
      }else{
        window.alert(who.dom.a.innerHTML);
      }
    },
    //当点击checkbox的时候的事件响应，who指点击的节点对象
    onCheck:function(who){
      //获取子节点
      var chd = who.getChildren();
      for(var i=0;i<chd.length;i++){
        //子节点选中状态保持与父节点一致
        chd[i].checked = who.checked;
        //别忘了更新
        chd[i].repaint();
      }
    }
  });
  //按钮事件，弹出选中节点的text
  I.listen('btnA','click',function(m,e){
    var text = [];
    var selected = tree.getSelected();
    for(var i=0;i<selected.length;i++){
      text.push(selected[i].dom.a.innerHTML);
    }
    window.alert(text.join(','));
  });
  //按钮事件，全部展开
  I.listen('btnE','click',function(m,e){
    tree.open();
  });
  //按钮事件，全部收缩
  I.listen('btnF','click',function(m,e){
    tree.close();
  });
  
  //按钮事件，添加子节点，如果有选中的，在选中的下边添加子节点，否则在根节点下添加子节点
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
  //按钮事件，添加1000个子节点
  I.listen('btnG','click',function(m,e){
    var selected = tree.getSelected();
    if(selected.length<1){
      for(var i=0;i<1000;i++){
        tree.add('<b></b><i></i><input type="checkbox" /><a>新添加的节点'+i+'</a>');
      }
    }else{
      for(var j=0;j<1000;j++){
        for(var i=0;i<selected.length;i++){
          selected[i].add('<b></b><i></i><input type="checkbox" /><a>新添加的节点'+j+'</a>');
        }
      }
    }
  });
  
  //按钮事件，删除选中节点
  I.listen('btnD','click',function(m,e){
    var selected = tree.getSelected();
    for(var i=0;i<selected.length;i++){
      tree.remove(selected[i].uuid);
    }
  });
  
  //按钮事件，通过json渲染树
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
      //dom:document.body,
      skin:'Blue',//皮肤名
      folder_open_icon:'fa fa-car',//文件夹打开的图标
      data:d,//数据
      //点击text事件
      onClick:function(who){
        window.alert(who.dom.li.getAttribute('gender')+','+who.dom.li.getAttribute('data-id')+','+who.dom.a.innerHTML);
      }
    });
  });
});
</script>
</body>
</html>