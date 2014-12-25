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
}
html{
  height:100%;
  overflow:hidden;
}
a{
  text-decoration:none;
  color:#333;
  cursor:pointer;
}
a.button{
  display:block;
  text-align:center;
  border:1px solid #0079FF;
  height:2.5em;
  line-height:2.5em;
  background-color:#0079FF;
  color:#FFF;
  border-radius:6px;
  -webkit-border-radius:6px;
  -moz-border-radius:6px;
}

a.button:active{
  background-color:#FF6600;
  border:1px solid #FF6600;
}
a.orange{
  background-color:#FF6906;
  border:1px solid #FF6906;
}
a.orange:active{
  background-color:#0079FF;
  border:1px solid #0079FF;
}
i{
  font-style:normal;
}
.fa:first-letter {
  margin-right:.2em;
}
body{
  -webkit-text-size-adjust:none;
  background-color:#F2F2F2;
  color:#4B4B4B;
  font:16px/1.25 "Microsoft Yahei","Helvetica Neue",Helvetica,Arial,sans-serif;
  font-weight:normal;
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
  height:100%;
  overflow:hidden;
}

header{
  background-color:#00AFE3;
  line-height:2em;
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
  -webkit-box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  -moz-box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  -ms-box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  -o-box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  box-shadow:inset 0 -1px #009ECC, 0 1px rgba(0,0,0,0.1);
  -moz-user-select:none;
  -webkit-user-select:none;
  -ms-user-select:none;
  -khtml-user-select:none;
  user-select:none;
  -webkit-text-shadow:0 .1em #009ECC;
  -moz-text-shadow:0 .1em #009ECC;
  -ms-text-shadow:0 .1em #009ECC;
  -o-text-shadow:0 .1em #009ECC;
  text-shadow:0 .1em #009ECC;
}
header i{
  overflow:hidden;
  white-space:nowrap;
  text-overflow:ellipsis;
  color:#FFF;
  display:-webkit-box;
  display:-moz-box;
  display:-ms-box;
  display:-o-box;
  display:box;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  -ms-box-flex: 1;
  -o-box-flex: 1;
  box-flex: 1;
  -moz-box-pack:center;
  -webkit-box-pack:center;
  -o-box-pack:center;
  box-pack:center;
}
header a{
  color:#FFF;
  font-size:1.2em;
  padding-left:0.32em;
  padding-right:0.32em;
}
article{
  color:#333;
  overflow-x:hidden;
  overflow-y:auto;
  -webkit-overflow-scrolling:touch;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  -ms-box-flex: 1;
  -o-box-flex: 1;
  box-flex: 1;
}
article ul{
  clear:both;
  background-color:#FFF;
  border-top:solid 1px #DEDEDE;
}
article ul li{
  position:relative;
  border-bottom:solid 1px #DEDEDE;
  overflow:hidden;
  padding:.8em;
}
article ul li:last-child{
  border-bottom:0;
}
article ul li.link:after{
  z-index: 1;
  position: absolute;
  top: 0;
  right:0;
  padding:.8em;
  text-align: center;
  border-radius:0;
  border:0;
  border-color:transparent;
  box-shadow:none;
  -webkit-transform:none;
  transform:none;
  font-family:FontAwesome;
  speak:none;
  font-style:normal;
  font-weight:normal;
  font-variant:normal;
  text-transform:none;
  -webkit-font-smoothing:antialiased;
  content:"\f054";
  background-color:transparent;
}
label{
  line-height:2em;
  padding-left:0.8em;
}
footer{
  -moz-user-select:none;
  -webkit-user-select:none;
  -ms-user-select:none;
  -khtml-user-select:none;
  user-select:none;
}

footer nav{
  background-color:#FCFCFC;
  border-top:1px solid #D7D7D7;
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
  padding:0.6em;
}
footer nav a{
  position:relative;
  text-align:center;
  color:#999;
  display:-webkit-box;
  display:-moz-box;
  display:-ms-box;
  display:-o-box;
  display:box;
  -webkit-box-flex:1;
  -moz-box-flex:1;
  -ms-box-flex:1;
  -o-box-flex:1;
  box-flex:1;
  -moz-box-pack:center;
  -webkit-box-pack:center;
  -o-box-pack:center;
  box-pack:center;
  -moz-box-align:center;
  -webkit-box-align:center;
  -o-box-align:center;
  box-align:center;
  -webkit-box-orient:vertical;
  -moz-box-orient:vertical;
  -ms-box-orient:vertical;
  -o-box-orient:vertical;
  box-orient:vertical;
}

li.input{
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
  line-height:2em;
}
li.input > *{
  display:-webkit-box;
  display:-moz-box;
  display:-ms-box;
  display:-o-box;
  display:box;
  -webkit-box-flex: 1;
  -moz-box-flex: 1;
  -ms-box-flex: 1;
  -o-box-flex: 1;
  box-flex: 1;
}
li.input i{
  -webkit-box-flex: 0;
  -moz-box-flex: 0;
  -ms-box-flex: 0;
  -o-box-flex: 0;
  box-flex: 0;
  padding-right:0.5em;
}

input[type=text]{
  outline:none;
  width:100%;
  padding-left:0.5em;
  padding-right:0.5em;
  font-size:1em;
  height:2em;
  background-color: #FFF;
  border: 1px solid #CCC;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
  box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
}
input[type=text]:focus{
  border:1px solid #FF6600;
}
select{
  outline:none;
  width:100%;
  padding-left:0.5em;
  padding-right:0.5em;
  font-size:1em;
  height:2em;
  background-color: #FFF;
  border: 1px solid #CCC;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
  box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
}
textarea{
  outline:none;
  width:100%;
  padding-left:0.5em;
  padding-right:0.5em;
  font-size:1em;
  line-height:1.2em;
  background-color: #FFF;
  border: 1px solid #CCC;
  border-radius: 4px;
  -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
  box-shadow: inset 0 1px 1px rgba(0,0,0,0.075);
}
  */}+'';
  return {
    getCss:function(){return CSS;}
  };
}+'');