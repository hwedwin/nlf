NLF框架
===

NLF框架是六特尔独自开发的轻量级java框架，它只有1个jar包，含源代码在内只有600+KB的大小。它无侵入，无依赖，零配置，无注解，支持AOP，自动IOC，支持自动分页，class动态加载，原生支持Oracle、SQLSERVER、MYSQL、MONGO和独创的CSV数据库。使用它你将会体验到前所未有的开发效率，并且你会发现，它支持javase和javaee应用，你甚至不需要启动Server就可以对web应用后台代码进行调试。
<br />
<br />
六特尔并不愿意其他人来直接修改NLF框架的代码，因为六特尔致力于将它打造为完美的作品，其他人写的代码，实在没有加入进来的意义。
<br />
<br /> 
但是您可以当小白鼠，提意见，提bug，好的idea我还是愿意接受的。

===
WebContent下有六特尔开发的一个js框架，通过一个核心js文件自动按需加载需要的组件。
<br />
这是一个史上最智能的按需加载js框架，这儿有一个使用例子：
<br />
这是js文件路径：<br />
>/js<br />
>/js/icore.js<br />
>/js/test/A.js<br />
>/js/test/B.js<br />
<br /><br />
html代码：<br />
>&lt;script type="text/javascript" src="/js/icore.js"&gt;&lt;/script&gt;<br />
>&lt;script type="text/javascript"&gt;<br />
>I.want(function(){<br />
>  I.test.A.sayHello();<br />
>});<br />
>&lt;/script&gt;<br />
<br /><br />
B.js代码：<br />
>I.regist('test.B',function(W,D){<br />
>  var _say = function(){<br />
>    W.alert('Hello World!');<br />
>  };<br />
>  return {<br />
>    say:function(){_say();}<br />
>  };<br />
>}+'');<br />
<br /><br />
A.js代码：<br />
>I.regist('test.A',function(W,D){<br />
>  var _sayHello = function(){<br />
>    I.test.B.say();<br />
>  };<br />
>  return {<br />
>    sayHello:function(){_sayHello();}<br />
>  };<br />
>}+'');<br />

===

> NLF框架官方QQ群：281471914
