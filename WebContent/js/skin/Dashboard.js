I.regist('skin.Dashboard',function(W,D){
var CSS = function(){/*
*{
 margin:0;
 padding:0;
 font-size:12px;
 font-family:"Microsoft Yahei","Helvetica Neue",Helvetica,Arial,sans-serif;
 font-style:normal;
 -webkit-box-sizing:border-box;
 -moz-box-sizing:border-box;
 -ms-box-sizing:border-box;
 -o-box-sizing:border-box;
 box-sizing:border-box;
}
html,body{
 overflow:hidden;
 height:100%;
}
.header-${skin}{
 position:absolute;
 z-index:2;
 left:0;
 top:0;
 width:100%;
 overflow:hidden;
}
.title-bar-${skin}{
 overflow:hidden;
}
.tool-bar-${skin}{
 background-color:#418EC6;
 border-bottom:4px solid #FC9431;
 overflow:hidden;
}
.body-${skin}{
 position:absolute;
 z-index:1;
 left:0;
 _top:0;
 right:0;
 _bottom:0;
 overflow:hidden;
 width:100%;
 _height:100%;
}
.footer-${skin}{
 position:absolute;
 z-index:2;
 left:0;
 bottom:0;
 width:100%;
 background-color:#F9F9F9;
 border-top:1px solid #EDEDED;
 overflow:hidden;
}
.menu-bar-${skin}{
 position:absolute;
 left:0;
 top:0;
 bottom:0;
 overflow:hidden;
 _height:100%;
 background-color:#FFF;
 border-right:1px solid #EDEDED;
}
.head-${skin}{
 position:absolute;
 top:0;
 right:0;
 overflow:hidden;
 _width:100%;
 background-color:#F5F5F5;
 border-bottom:1px solid #E5E5E5;
}
.content-${skin}{
 position:absolute;
 _position:static;
 right:0;
 overflow:auto;
 _overflow:hidden;
 _width:100%;
 _height:100%;
 background-color:#FFF;
}
.foot-${skin}{
 position:absolute;
 bottom:0;
 right:0;
 overflow:hidden;
 _width:100%;
 background-color:#F5F5F5;
 border-top:1px solid #EDEDED;
}
.tool-${skin},.tool-${skin}-active{
 float:left;
 display:block;
 cursor:pointer;
 -webkit-user-select:none;
 -moz-user-select:none;
 -ms-user-select:none;
 -o-user-select:none;
 -khtml-user-select:none;
 user-select:none;
 overflow:hidden;
 padding-top:10px;
 padding-bottom:10px;
}
.tool-${skin} i,.tool-${skin}-active i{
 display:block;
 width:100%;
 text-align:center;
 color:#DDD;
 font-size:1.4em;
 font-style:normal;
}
.tool-${skin} b,.tool-${skin}-active b{
 display:block;
 width:100%;
 text-align:center;
 color:#DDD;
 font-weight:normal;
}
.tool-${skin}-active i{
 color:#FFF;
}
.tool-${skin}-active b{
 color:#FFF;
}
.tool-${skin}:hover i,.tool-${skin}-active:hover i{
 color:#FFF;
}
.tool-${skin}:hover b,.tool-${skin}-active:hover b{
 color:#FFF;
}
.menu-${skin},.menu-${skin}-active{
 display:block;
 height:40px;
 background-color:#F9F9F9;
 border-bottom:1px solid #EDEDED;
 cursor:pointer;
 clear:both;
 -webkit-user-select:none;
 -moz-user-select:none;
 -ms-user-select:none;
 -o-user-select:none;
 -khtml-user-select:none;
 user-select:none;
 white-space:nowrap;
 text-overflow:ellipsis;
}
.menu-${skin}-active{
 background-color:#F6F6F6;
}
.menu-${skin}:hover{
 background-color:#F6F6F6;
}
.menu-${skin} i,.menu-${skin}-active i{
 display:block;
 float:left;
 line-height:40px;
 margin-left:8px;
 text-align:center;
 color:#9CA5B6;
 font-size:1.333em;
}
.menu-${skin} b,.menu-${skin}-active b{
 display:block;
 float:left;
 line-height:40px;
 margin-left:8px;
 color:#475B76;
 font-weight:bold;
 font-size:1.2em;
 letter-spacing:1px;
 white-space:nowrap;
 text-overflow:ellipsis;
}
.menu-${skin} u,.menu-${skin}-active u{
 display:block;
 float:right;
 line-height:40px;
 margin-right:8px;
 text-align:center;
 color:#DDD;
 font-size:1.33em;
 text-decoration:none;
}
.menu-${skin}-active i{
  color:#FA9231;
}
.menu-${skin}-active b{
  color:#FA9231;
}
.menu-${skin}:hover i{
  color:#FA9231;
}
.menu-${skin}:hover b{
  color:#FA9231;
}
.menu-item-${skin}{
 display:block;
 background-color:#FFF;
 overflow:hidden;
 height:0;
 cursor:pointer;
 -webkit-user-select:none;
 -moz-user-select:none;
 -ms-user-select:none;
 -o-user-select:none;
 -khtml-user-select:none;
 user-select:none;
}
.menu-item-${skin} li{
 display:block;
 height:40px;
 border-top:1px solid #FFF;
 border-bottom:1px solid #EDEDED;
}
.menu-item-${skin} a{
 display:block;
 height:40px;
 line-height:40px;
}
.menu-item-${skin} i{
 display:block;
 float:left;
 line-height:40px;
 margin-left:28px;
 text-align:center;
 color:#DDD;
 font-size:1.33em;
}
.menu-item-${skin} b{
 display:block;
 float:left;
 line-height:40px;
 margin-left:8px;
 color:#2C3C56;
 font-weight:normal;
 white-space:nowrap;
 text-overflow:ellipsis;
}
.menu-item-${skin} li.active i{
 color:#FA9231;
}
.menu-item-${skin} li.active b{
 color:#FA9231;
}
.menu-item-${skin} a:hover i{
 color:#FA9231;
}
.menu-item-${skin} a:hover b{
 color:#FA9231;
}
.tool-bar-${skin} ul.user-${skin}{
 list-style:none;
 float:right;
 margin-right:10px;
}
.tool-bar-${skin} ul.user-${skin} li{
 float:left;
 display:block;
 color:#FFF;
 margin-right:5px;
}
.title-bar-${skin} ul.user-${skin}{
 list-style:none;
 float:right;
 margin-right:10px;
}
.title-bar-${skin} ul.user-${skin} li{
 float:left;
 display:block;
 color:#000;
 margin-right:5px;
}
.head-${skin} ul.user-${skin}{
 list-style:none;
 float:right;
 margin-right:10px;
}
.head-${skin} ul.user-${skin} li{
 float:left;
 display:block;
 color:#000;
 margin-right:5px;
}
.footer-${skin} ul.user-${skin}{
 list-style:none;
 float:left;
 margin-left:10px;
}
.footer-${skin} ul.user-${skin} li{
 float:left;
 display:block;
 color:#000;
 margin-left:5px;
}
.foot-${skin} ul.user-${skin}{
 list-style:none;
 float:left;
 margin-left:10px;
}
.foot-${skin} ul.user-${skin} li{
 float:left;
 display:block;
 color:#000;
 margin-left:5px;
}
ul.bread-${skin}{
 list-style:none;
 float:left;
}
ul.bread-${skin} li{
 float:left;
 display:block;
 color:#999;
 margin-left:5px;
}
ul.bread-${skin} li.arrow{
 font-family:Webdings;
 color:#CCC;
}
*/}+'';
return {
 getCss:function(){return CSS;}
};
}+'');