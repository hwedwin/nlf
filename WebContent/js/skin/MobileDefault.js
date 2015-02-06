/**
 * I.skin.MobileDefault
 * <i>移动端默认皮肤</i>
 */
I.regist('skin.MobileDefault',function(W,D){var CSS = function(){/*
*{
 margin:0;
 padding:0;
 -webkit-box-sizing:border-box;
 -moz-box-sizing:border-box;
 -ms-box-sizing:border-box;
 -o-box-sizing:border-box;
 box-sizing:border-box;
 outline:none;
 -webkit-tap-highlight-color:rgba(0,0,0,0);
 -webkit-tap-highlight-color:transparent;
}
html{height:100%;overflow:hidden;font:normal 14px/1.5 "Hiragino Sans GB","Microsoft YaHei", "hei", Arial, "Lucida Grande", Verdana;}
body{
 -webkit-text-size-adjust:none;
 -webkit-font-smoothing:antialiased;
 background-color:#F2F2F2;
 color:#4B4B4B;
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
a{text-decoration:none;color:#333;cursor:pointer;}
a.button{
 display:block;
 text-align:center;
 height:2.5em;
 line-height:2.5em;
 color:#FFF;
 -webkit-border-radius:6px;
 -moz-border-radius:6px;
 -ms-border-radius:6px;
 -o-border-radius:6px;
 border-radius:6px;
 background-color:#0E90D2;
 border:1px solid #0E90D2;
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
 -webkit-border-radius:2em;
 -moz-border-radius:2em;
 -ms-border-radius:2em;
 -o-border-radius:2em;
 border-radius:2em;
 color:#FFF;
 -webkit-box-shadow:#666 0px 0px 10px;
 -moz-box-shadow:#666 0px 0px 10px;
 -ms-box-shadow:#666 0px 0px 10px;
 -o-box-shadow:#666 0px 0px 10px;
 box-shadow:#666 0px 0px 10px;
 background-color:#0E90D2;
 border:1px solid #0E90D2;
}
a.blue{background-color:#0E90D2;border:1px solid #0E90D2;}
a.orange{background-color:#F37B1D;border:1px solid #F37B1D;}
a.green{background-color:#5EB95E;border:1px solid #5EB95E;}
a.red{background-color:#DD514C;border:1px solid #DD514C;}
a:active{
 -webkit-text-shadow:0 0 .3em rgba(51, 50, 47, 0.8);
 -moz-text-shadow:0 0 .3em rgba(51, 50, 47, 0.8);
 -ms-text-shadow:0 0 .3em rgba(51, 50, 47, 0.8);
 -o-text-shadow:0 0 .3em rgba(51, 50, 47, 0.8);
 text-shadow:0 0 .3em rgba(51, 50, 47, 0.8);
}
a.button:active{background-color:#F37B1D;border:1px solid #F37B1D;}
a.ball:active{background-color:#F37B1D;border:1px solid #F37B1D;}
a.blue:active{background-color:#F37B1D;border:1px solid #F37B1D;}
a.orange:active{background-color:#0E90D2;border:1px solid #0E90D2;}
a.green:active{background-color:#DD514C;border:1px solid #DD514C;}
a.red:active{background-color:#5EB95E;border:1px solid #5EB95E;}
i{font-style:normal;}
.fa:first-letter{margin-right:.2em;}
header{
 background-color:#EFEFF0;
 line-height:2.8em;
 font-size:1em;
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
 -webkit-box-shadow:inset 0 -1px #CBCBCC, 0 1px rgba(0,0,0,0.1);
 -moz-box-shadow:inset 0 -1px #CBCBCC, 0 1px rgba(0,0,0,0.1);
 -ms-box-shadow:inset 0 -1px #CBCBCC, 0 1px rgba(0,0,0,0.1);
 -o-box-shadow:inset 0 -1px #CBCBCC, 0 1px rgba(0,0,0,0.1);
 box-shadow:inset 0 -1px #CBCBCC, 0 1px rgba(0,0,0,0.1);
 -webkit-user-select:none;
 -moz-user-select:none;
 -ms-user-select:none;
 -o-user-select:none;
 -khtml-user-select:none;
 user-select:none;

}
header i{
 overflow:hidden;
 white-space:nowrap;
 text-overflow:ellipsis;
 color:#000;
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
 -webkit-box-pack:center;
 -moz-box-pack:center;
 -ms-box-pack:center;
 -o-box-pack:center;
 box-pack:center;
 font-size:1.3em;
}
header a{color:#000;font-size:1.3em;padding-left:.65em;padding-right:.65em;}
header a:visited{color:#000}
header a.fa{line-height:2.153846em;font-size:1.3em;}
header a.fa-none:after{
  content:'';
  border:0;
  border-right:1.3em solid transparent;
}
article{
 color:#333;
 overflow-x:hidden;
 overflow-y:auto;
 -webkit-overflow-scrolling:touch;
 -webkit-box-flex:1;
 -moz-box-flex:1;
 -ms-box-flex:1;
 -o-box-flex:1;
 box-flex:1;
}
article ul{clear:both;background-color:#FFF;border:solid 1px #EEE;}
article ul li{position:relative;border-bottom:solid 1px #EEE;overflow:hidden;padding:.8em;}
article ul li:last-child{border-bottom:0;}
article ul li.link:after{
 z-index:1;
 position:absolute;
 top:0;
 right:0;
 padding:.8em;
 text-align:center;
 border-radius:0;
 border:0;
 border-color:transparent;
 -webkit-transform:none;
 -moz-transform:none;
 -ms-transform:none;
 -o-transform:none;
 transform:none;
 speak:none;
 font-style:normal;
 font-weight:normal;
 font-variant:normal;
 text-transform:none;
 -webkit-font-smoothing:antialiased;
 font-family:FontAwesome;
 content:"\f054";
 background-color:transparent;
 -webkit-box-shadow:none;
 -moz-box-shadow:none;
 -ms-box-shadow:none;
 -o-box-shadow:none;
 box-shadow:none
}
label{display:block;line-height:2em;padding-left:.8em;}
footer{-webkit-user-select:none;-moz-user-select:none;-ms-user-select:none;-o-user-select:none;-khtml-user-select:none;user-select:none;}
footer.float{position:fixed;left:0;bottom:0;width:100%;}
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
 padding:.6em;
}
footer nav a{
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
 -webkit-box-pack:center;
 -moz-box-pack:center;
 -ms-box-pack:center;
 -o-box-pack:center;
 box-pack:center;
 -webkit-box-align:center;
 -moz-box-align:center;
 -ms-box-align:center;
 -o-box-align:center;
 box-align:center;
 -webkit-box-orient:vertical;
 -moz-box-orient:vertical;
 -ms-box-orient:vertical;
 -o-box-orient:vertical;
 box-orient:vertical;
}
input[type=text]{
 width:100%;
 padding-left:.5em;
 padding-right:.5em;
 font-size:1em;
 height:2em;
 background-color:#FFF;
 border:0;
}
input[type=number]{
 width:100%;
 padding-left:.5em;
 padding-right:.5em;
 font-size:1em;
 height:2em;
 background-color:#FFF;
 border:0;
}
input[type=number]::-webkit-inner-spin-button,
input[type=number]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
select{
 width:100%;
 padding-left:.5em;
 padding-right:.5em;
 font-size:1em;
 height:2em;
 background-color: #FFF;
 border:0;
}
textarea{
 width:100%;
 padding-left:.5em;
 padding-right:.5em;
 font-size:1em;
 line-height:1.2em;
 background-color:#FFF;
 border:1px solid #CCC;
 -webkit-border-radius:4px;
 -moz-border-radius:4px;
 -ms-border-radius:4px;
 -o-border-radius:4px;
 border-radius:4px;
 -webkit-box-shadow:inset 0 1px 1px rgba(0,0,0,0.075);
 -moz-box-shadow:inset 0 1px 1px rgba(0,0,0,0.075);
 -ms-box-shadow:inset 0 1px 1px rgba(0,0,0,0.075);
 -o-box-shadow:inset 0 1px 1px rgba(0,0,0,0.075);
 box-shadow:inset 0 1px 1px rgba(0,0,0,0.075)
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
 -webkit-box-pack:center;
 -moz-box-pack:center;
 -ms-box-pack:center;
 -o-box-pack:center;
 box-pack:center;
 -webkit-box-align:center;
 -moz-box-align:center;
 -ms-box-align:center;
 -o-box-align:center;
 box-align:center;
 text-align:center;
 border:1px solid #CCC;
 color:#333;
 height:2em;
 line-height:2em;
 background-color:#FFF;
 -webkit-border-radius:0;
 -moz-border-radius:0;
 -ms-border-radius:0;
 -o-border-radius:0;
 border-radius:0;
 padding-left:.6em;
 padding-right:.6em;
}
.group a.active{
 color:#FFF;
 background-color:#00AFE3;
 border:1px solid #0E90D2;
}
.group a:last-child{
 -webkit-border-radius:4px;
 -moz-border-radius:4px;
 -ms-border-radius:4px;
 -o-border-radius:4px;
 border-radius:4px;
 border-left:0;
 -webkit-border-bottom-left-radius:0;
 -moz-border-radius-bottomleft:0;
 border-bottom-left-radius:0;
 -webkit-border-top-left-radius:0;
 -moz-border-radius-topleft:0;
 border-top-left-radius:0;
}
.group a:first-child{
 -webkit-border-radius:4px;
 -moz-border-radius:4px;
 -ms-border-radius:4px;
 -o-border-radius:4px;
 border-radius:4px;
 border-right:0;
 -webkit-border-bottom-right-radius:0;
 -moz-border-radius-bottomright:0;
 border-bottom-right-radius:0;
 -webkit-border-top-right-radius:0;
 -moz-border-radius-topright:0;
 border-top-right-radius:0;
}
.i-mobile-Mask-${skin}{z-index:902;position:fixed;font-size:0;left:0;top:0;width:100%;height:100%;border:0;}
.i-mobile-ScrollBar-${skin}{position:fixed;right:0;width:6px;border:1px solid #F3F3F3;background-color:#777;font-size:0;-webkit-border-radius:4px;-moz-border-radius:4px;-ms-border-radius:4px;-o-border-radius:4px;border-radius:4px;filter:alpha(opacity=50);-moz-opacity:.5;opacity:.5;}
.i-mobile-Win-${skin}{z-index:903;position:fixed;left:1em;top:1em;right:1em;overflow:hidden;-webkit-border-radius:4px;-moz-border-radius:4px;-ms-border-radius:4px;-o-border-radius:4px;border-radius:4px;-webkit-box-shadow:#333 0px 0px 8px;-moz-box-shadow:#333 0px 0px 8px;-ms-box-shadow:#333 0px 0px 8px;-o-box-shadow:#333 0px 0px 8px;box-shadow:#333 0px 0px 8px;
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
 max-height:80%;
}
.i-mobile-Loading-${skin}{
 z-index:992;
 position:fixed;
 left:0;
 top:0;
 width:100%;
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
 background:transparent;
 -webkit-box-pack:center;
 -moz-box-pack:center;
 -ms-box-pack:center;
 -o-box-pack:center;
 box-pack:center;
 -webkit-box-align:center;
 -moz-box-align:center;
 -ms-box-align:center;
 -o-box-align:center;
 box-align:center;
}
.i-mobile-Loading-${skin} div{
 width:130px;
 height:110px;
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
 -webkit-box-pack:center;
 -moz-box-pack:center;
 -ms-box-pack:center;
 -o-box-pack:center;
 box-pack:center;
 -webkit-box-align:center;
 -moz-box-align:center;
 -ms-box-align:center;
 -o-box-align:center;
 box-align:center;
 text-align:center;
 background:rgba(0,0,0,.65);
 -webkit-border-radius:6px;
 -moz-border-radius:6px;
 -ms-border-radius:6px;
 -o-border-radius:6px;
 border-radius:6px;
 color:#FFF;
}
.i-mobile-Loading-${skin} i{
 display:block;
 width:48px;
 height:48px;
 background-color:transparent;
 -webkit-box-shadow:inset 0px 0px 0px 2px #FFF;
 -moz-box-shadow:inset 0px 0px 0px 2px #FFF;
 -ms-box-shadow:inset 0px 0px 0px 2px #FFF;
 -o-box-shadow:inset 0px 0px 0px 2px #FFF;
 box-shadow:inset 0px 0px 0px 2px #FFF;
 -webkit-border-radius:50%;
 -moz-border-radius:50%;
 -ms-border-radius:50%;
 -o-border-radius:50%;
 border-radius:50%;
 position:relative;
}
.i-mobile-Loading-${skin} i:before{
 position:absolute;
 content:"";
 background-color:#FFF;
 width:14px;
 height:2px;
 top:22px;
 left:22px;
 -webkit-transform-origin:1px 1px;
 -moz-transform-origin:1px 1px;
 transform-origin:1px 1px;
 -webkit-animation:hrhand 8s linear infinite;
 -moz-animation:hrhand 8s linear infinite;
 animation:hrhand 8s linear infinite;
}
.i-mobile-Loading-${skin} i:after{
 position:absolute;
 content:"";
 background-color:#FFF;
 width:20px;
 height:2px;
 top:22px;
 left:22px;
 -webkit-transform-origin:1px 1px;
 -moz-transform-origin:1px 1px;
 transform-origin:1px 1px;
 -webkit-animation:minhand 2s linear infinite;
 -moz-animation:minhand 2s linear infinite;
 animation:minhand 2s linear infinite;
}
.i-mobile-Loading-${skin} b {
 display:block;
 margin-top:6px;
 font-weight:normal;
 font-size:.9em;
}
@-webkit-keyframes minhand{
 0%{-webkit-transform:rotate(0deg)}
 100%{-webkit-transform:rotate(360deg)}
}
@-moz-keyframes minhand{
 0%{-moz-transform:rotate(0deg)}
 100%{-moz-transform:rotate(360deg)}
}
@keyframes minhand{
 0%{transform:rotate(0deg)}
 100%{transform:rotate(360deg)}
}

@-webkit-keyframes hrhand{
 0%{-webkit-transform:rotate(0deg)}
 100%{-webkit-transform:rotate(360deg)}
}
@-moz-keyframes hrhand{
 0%{-moz-transform:rotate(0deg)}
 100%{-moz-transform:rotate(360deg)}
}
@keyframes hrhand{
 0%{transform:rotate(0deg)}
 100%{transform:rotate(360deg)}
}

.round{-webkit-border-radius:6px;-moz-border-radius:6px;-ms-border-radius:6px;-o-border-radius:6px;border-radius:6px;}
.i-mobile-Button-${skin}{display:block;height:2em;line-height:2em;font-size:1em;text-align:center;}
.i-mobile-Toast-${skin}{z-index:2147483647;position:fixed;bottom:4em;left:0;width:100%;
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
 background:transparent;
 -webkit-box-pack:center;
 -moz-box-pack:center;
 -ms-box-pack:center;
 -o-box-pack:center;
 box-pack:center;
 -webkit-box-align:center;
 -moz-box-align:center;
 -ms-box-align:center;
 -o-box-align:center;
 box-align:center;
}
.i-mobile-Toast-${skin} span{
 display:block;
 overflow:hidden;
 max-width:80%;
 white-space:nowrap;
 text-overflow:ellipsis;
 margin-left:auto;
 margin-right:auto;
 padding:.5em 1em;
 font-size:1em;
 text-align:center;
 -webkit-border-radius:6px;-moz-border-radius:6px;-ms-border-radius:6px;-o-border-radius:6px;border-radius:6px;}
.grid{
 display:-webkit-box;
 display:-moz-box;
 display:-ms-box;
 display:-o-box;
 display:box;
 -webkit-box-orient:horizontal;
 -moz-box-orient:horizontal;
 -ms-box-orient:horizontal;
 -o-box-orient:horizontal;
 box-orient:horizontal
}
.grid > *{
 display:block;
 -webkit-box-flex:1;
 -moz-box-flex:1;
 -ms-box-flex:1;
 -o-box-flex:1;
 box-flex:1;
}
.grid > *.col0{-webkit-box-flex:0;-moz-box-flex:0;-ms-box-flex:0;-o-box-flex:0;box-flex:0;}
.grid > *.col2{-webkit-box-flex:2;-moz-box-flex:2;-ms-box-flex:2;-o-box-flex:2;box-flex:2;}
.grid > *.col3{-webkit-box-flex:3;-moz-box-flex:3;-ms-box-flex:3;-o-box-flex:3;box-flex:3;}
.grid > *.col4{-webkit-box-flex:4;-moz-box-flex:4;-ms-box-flex:4;-o-box-flex:4;box-flex:4;}
.grid > *.col5{-webkit-box-flex:5;-moz-box-flex:5;-ms-box-flex:5;-o-box-flex:5;box-flex:5;}
.grid > *.col6{-webkit-box-flex:6;-moz-box-flex:6;-ms-box-flex:6;-o-box-flex:6;box-flex:6;}
.grid > *.col7{-webkit-box-flex:7;-moz-box-flex:7;-ms-box-flex:7;-o-box-flex:7;box-flex:7;}
.grid > *.col8{-webkit-box-flex:8;-moz-box-flex:8;-ms-box-flex:8;-o-box-flex:8;box-flex:8;}
.grid > *.col9{-webkit-box-flex:9;-moz-box-flex:9;-ms-box-flex:9;-o-box-flex:9;box-flex:9;}
*/}+'';return{getCss:function(){return CSS;}};}+'');