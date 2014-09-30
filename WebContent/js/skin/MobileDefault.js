/**
 * I.skin.MobileDefault
 * <i>移动端默认皮肤</i>
 */
I.regist('skin.MobileDefault',function(W,D){
  var CSS = function(){/*
*{
  margin:0;
  padding:0;
  -webkit-box-sizing:border-box;
  -moz-box-sizing:border-box;
  box-sizing:border-box;
  outline: none;
  overflow:hidden;
}
html{
  height:100%;
}
a{
  text-decoration:none;
  color:#333;
  cursor:pointer;
}
a:active{
  color:#006988;
}
body {
  background-color:#000;
  color:#F4F4F4;
  font:16px/1.25 "Microsoft Yahei","Helvetica Neue",Helvetica,Arial,sans-serif;
  font-weight:normal;
  letter-spacing:-0.05em;
  height:100%;
  display:-webkit-box;
  display:-moz-box;
  display:-ms-box;
  display:-o-box;
  display:box;
  -webkit-box-orient:horizontal;
  -moz-box-orient:horizontal;
  -ms-box-orient:horizontal;
  -o-box-orient:horizontal;
  box-orient:horizontal;
}

aside{
  display:none;
  position:relative;
  width:256px;
  background-color:#232323;
  -webkit-box-orient:vertical;
  -moz-box-orient:vertical;
  -ms-box-orient:vertical;
  -o-box-orient:vertical;
  box-orient:vertical;
}
aside.active{
  display:-webkit-box;
  display:-moz-box;
  display:-ms-box;
  display:-o-box;
  display:box;
}
aside,aside.left{
  -webkit-box-ordinal-group:1;
  -moz-box-ordinal-group:1;
  -ms-box-ordinal-group:1;
  -o-box-ordinal-group:1;
  box-ordinal-group:1;
}
aside.right{
  -webkit-box-ordinal-group:3;
  -moz-box-ordinal-group:3;
  -ms-box-ordinal-group:3;
  -o-box-ordinal-group:3;
  box-ordinal-group:3;
}

section{
  position:relative;
  -webkit-box-flex:1;
  display:-webkit-box;
  display:-moz-box;
  display:-ms-box;
  display:-o-box;
  display:box;
  -webkit-box-orient:vertical;
  -moz-box-orient:vertical;
  -ms-box-orient:vertical;
  -o-box-orient:vertical;
  box-orient:vertical;
  -webkit-box-ordinal-group:2;
  -moz-box-ordinal-group:2;
  -ms-box-ordinal-group:2;
  -o-box-ordinal-group:2;
  box-ordinal-group:2;
}

header{
  position:relative;
  width:100%;
  height:46px;
  line-height:46px;
  background-color:#00AFE3;
  -webkit-box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  -moz-box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  -ms-box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  -o-box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  -webkit-text-shadow:0 .1em #009ECC;
  -moz-text-shadow:0 .1em #009ECC;
  -ms-text-shadow:0 .1em #009ECC;
  -o-text-shadow:0 .1em #009ECC;
  text-shadow:0 .1em #009ECC;
  text-align:center;
  color:#FFF;
  font-size:1.2em;
  -webkit-box-ordinal-group:1;
  -moz-box-ordinal-group:1;
  -ms-box-ordinal-group:1;
  -o-box-ordinal-group:1;
  box-ordinal-group:1;
  white-space:nowrap;
  text-overflow:ellipsis;
  -moz-user-select:none;
  -webkit-user-select:none;
  -ms-user-select:none;
  -khtml-user-select:none;
  user-select:none;
}

header nav{
  float:left;
}
header nav:last-child{
  float:right;
}
header nav:first-child{
  float:left;
}
.right header nav{
  float:left;
}
.right header nav:first-child{
  float:left;
}
.right header nav:last-child{
  float:right;
}
header nav.left{
  float:left;
}
header nav.right{
  float:right;
}
header nav a{
  display:block;
  height:46px;
  padding-left:0.311em;
  padding-right:0.2em;
  padding-top:0.5em;
  color:#FFF;
  font-size:1.12em;
  overflow:hidden;
}
header nav a:hover{
  color:#006988;
}
header nav a:active{
  color:#006988;
}

.padding > * {
  padding:.5333333333333333em;
}
.padding li:not(:last-child) {
  margin-bottom: .8em;
}

article{
  position:relative;
  background-color:#E7E7E7;
  color:#333;
  overflow-x:hidden;
  overflow-y:auto;
  -webkit-overflow-scrolling:touch;
  -webkit-box-flex:1;
  -webkit-box-ordinal-group:2;
  -moz-box-ordinal-group:2;
  -ms-box-ordinal-group:2;
  -o-box-ordinal-group:2;
  box-ordinal-group:2;
}

footer{
  position:relative;
  height:46px;
  line-height:46px;
  background-color: #2f2f2f;
  -webkit-box-flex: 0;
  -moz-box-flex: 0;
  -ms-box-flex: 0;
  -o-box-flex: 0;
  box-flex: 0;
  text-align:center;
  color:#FFF;
  font-size:1.2em;
  -webkit-box-ordinal-group: 3;
  -moz-box-ordinal-group: 3;
  -ms-box-ordinal-group: 3;
  -o-box-ordinal-group: 3;
  box-ordinal-group: 3;
  -moz-user-select:none;
  -webkit-user-select:none;
  -ms-user-select:none;
  -khtml-user-select:none;
  user-select:none;
}

aside *{
  background-color:transparent;
  -webkit-box-shadow:none;
  -moz-box-shadow:none;
  -ms-box-shadow:none;
  -o-box-shadow:none;
  box-shadow:none;
  -webkit-text-shadow:none;
  -moz-text-shadow:none;
  -ms-text-shadow:none;
  -o-text-shadow:none;
  text-shadow:none;
}

ul{
  list-style:none;
}
article li{
  position: relative;
  padding: .8em;
  font-weight: 300;
  background-color:#FFF;
  border-bottom: inset 1px #CFCFCF;
}
aside li{
  position: relative;
  background-color:transparent;
  border-bottom:0;
  padding:0;
}
footer nav{
  display:-webkit-box;
  display:-moz-box;
  display:-ms-box;
  display:-o-box;
  display:box;
  -webkit-box-pack:justify;
  -moz-box-pack:justify;
  -ms-box-pack:justify;
  -o-box-pack:justify;
  box-pack:justify;
  width:100%;
  height:100%;
}
footer nav a{
  display:block;
  line-height:46px;
  text-align:center;
  color:#FFF;
  -webkit-box-flex:1;
  -moz-box-flex:1;
  -ms-box-flex:1;
  -o-box-flex:1;
  box-flex:1;
  -webkit-box-shadow: inset 0 0.3em #6F6F6F;
  -moz-box-shadow: inset 0 0.3em #6F6F6F;
  -ms-box-shadow: inset 0 0.3em #6F6F6F;
  -o-box-shadow: inset 0 0.3em #6F6F6F;
  box-shadow: inset 0 0.3em #6F6F6F;
}
footer nav a.fa{
  display:block;
  line-height:46px;
}
footer nav a.active{
  -webkit-box-shadow: inset 0 0.3em #00AFE3;
  -moz-box-shadow: inset 0 0.3em #00AFE3;
  -ms-box-shadow: inset 0 0.3em #00AFE3;
  -o-box-shadow: inset 0 0.3em #00AFE3;
  box-shadow: inset 0 0.3em #00AFE3;
}
aside footer nav a.active{
  -webkit-box-shadow: inset 0 0.3em #BFBFBF;
  -moz-box-shadow: inset 0 0.3em #BFBFBF;
  -ms-box-shadow: inset 0 0.3em #BFBFBF;
  -o-box-shadow: inset 0 0.3em #BFBFBF;
  box-shadow: inset 0 0.3em #BFBFBF;
}
aside li a{
  display:block;
  width:100%;
  height:100%;
  padding: .8em;
  color:#979797;
}

aside li a:hover{
  color:#FFF;
  background-color:#636363;
}
strong{
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  display: block;
}
small{
  font-size:.9em;
  color:#666;
  line-height:2em;
  display: block;
}
.fa:first-letter {
  margin-right:.111em;
}
  */}+'';
  return {
    getCss:function(){return CSS;}
  };
}+'');