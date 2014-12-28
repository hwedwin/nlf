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

a.ball{
  position:fixed;
  right:1em;
  bottom:4em;
  width:4em;
  height:4em;
  font-size:1em;
  text-align:center;
  line-height:4em;
  border-radius:2em;
  -webkit-border-radius:2em;
  -moz-border-radius:2em;
  color:#FFF;
  background-color:#0079FF;
  border:1px solid #0079FF;
  -webkit-box-shadow: #666 0px 0px 10px;
  -moz-box-shadow: #666 0px 0px 10px;
  box-shadow: #666 0px 0px 10px;
}
a.ball:active{
  background-color:#FF6906;
  border:1px solid #FF6906;
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
  
  -webkit-animation-duration:.3s;
  -webkit-animation-timing-function: ease;
  -webkit-animation-fill-mode: both;
  
  -moz-animation-duration:.3s;
  -moz-animation-timing-function: ease;
  -moz-animation-fill-mode: both;
  
  -o-animation-duration:.3s;
  -o-animation-timing-function: ease;
  -o-animation-fill-mode: both;
  
  -ms-animation-duration:.3s;
  -ms-animation-timing-function: ease;
  -ms-animation-fill-mode: both;
  
  animation-duration:.3s;
  animation-timing-function: ease;
  animation-fill-mode: both;
  
  -webkit-animation-name: fadeInLeft;
  -moz-animation-name: fadeInLeft;
  -ms-animation-name: fadeInLeft;
  -o-animation-name: fadeInLeft;
  animation-name: fadeInLeft;
  -webkit-animation-delay: .1s;
  -moz-animation-delay: .1s;
  -o-animation-delay: .1s;
  -ms-animation-delay: .1s;
  animation-delay: .1s;
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
  display:block;
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
footer.float{
  position:fixed;
  left:0;
  bottom:0;
  width:100%;
}

footer nav{
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

.group{
  background-color:#FCFCFC;
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
}
.group a{
  position:relative;
  text-align:center;
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
  display:block;
  text-align:center;
  border:1px solid #CCC;
  color:#333;
  height:2em;
  line-height:2em;
  background-color:#FFF;
  border-radius:0;
  -webkit-border-radius:0;
  -moz-border-radius:0;
  padding-left:0.6em;
  padding-right:0.6em;
}
.group a.active{
  color:#FFF;
  background-color:#007AFF;
  border:1px solid #006EE6;
}
.group a:last-child{
  border-radius:4px;
  -webkit-border-radius:4px;
  -moz-border-radius:4px;
  border-left:0;
  border-bottom-left-radius:0;
  -webkit-border-bottom-left-radius:0;
  -moz-border-radius-bottomleft:0;
  border-top-left-radius:0;
  -webkit-border-top-left-radius:0;
  -moz-border-radius-topleft:0;
}
.group a:first-child{
  border-radius:4px;
  -webkit-border-radius:4px;
  -moz-border-radius:4px;
  border-right:0;
  border-bottom-right-radius:0;
  -webkit-border-bottom-right-radius:0;
  -moz-border-radius-bottomright:0;
  border-top-right-radius:0;
  -webkit-border-top-right-radius:0;
  -moz-border-radius-topright:0;
}

.i-mobile-Mask-${skin}{z-index:902;position:fixed;margin:0;padding:0;font-size:0;left:0;top:0;width:100%;height:100%;border:0;}
.i-mobile-Win-${skin}{z-index:992;position:fixed;left:1em;top:1em;right:1em;padding:0;overflow:hidden;border:0;border-radius:0.5em;-webkit-border-radius:0.5em;-moz-border-radius:0.5em;-webkit-box-shadow: #666 0px 0px 10px;-moz-box-shadow: #666 0px 0px 10px;box-shadow: #666 0px 0px 10px;}
.i-mobile-Win-${skin} i{position:relative;display:block;width:100%;font-weight;normal;font-style:normal;}
.i-mobile-Win-${skin} .i-title{-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;-o-user-select:none;margin:0;padding:0;overflow:hidden;padding:0.5em;padding-bottom:0.2em;font-size:1.2em;font-weight:bold;}
.i-mobile-Win-${skin} .i-line{margin:0;margin-top:0.3em;height:0;border:0;border-bottom:1px solid #EEE;}
.i-mobile-Win-${skin} .i-content{margin:0;padding:0.5em;border:0;overflow:auto;}
.i-mobile-Button-${skin}{display:block;-webkit-border-radius:6px;-moz-border-radius:6px;-ms-border-radius:6px;-o-border-radius:6px;border-radius:6px;height:2em;line-height:2em;font-size:1em;text-align:center;}
.i-mobile-Toast-${skin}{z-index:2147483647;position:fixed;margin:0;padding:0.5em 1em;font-size:1em;left:0;bottom:3em;-webkit-border-radius:6px;-moz-border-radius:6px;-ms-border-radius:6px;-o-border-radius:6px;border-radius:6px;}
  */}+'';
  return {
    getCss:function(){return CSS;}
  };
}+'');