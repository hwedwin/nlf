NLF框架
===

#简介
NLF框架是六特尔独自开发的轻量级java框架，它只有1个jar包，含源代码在内只有500+KB的大小。它无侵入，无依赖，零配置，无注解，支持AOP，自动IOC，原生支持Oracle、SQLSERVER、MYSQL和独创的CSV数据库。使用它你将会体验到前所未有的开发效率，并且你会发现，它支持javase和javaee应用，你甚至不需要启动Server就可以对web应用后台代码进行调试。

#文档
NLF框架用户手册：http://6tail.cn/npress/demo/jsp  
相关插件源码：https://github.com/6tail/nlf-plugin
    
    NLF框架官方QQ群：281471914

#其他
WebContent下有六特尔开发的i.js框架，通过一个核心js文件自动按需加载需要的组件，示例地址：http://6tail.cn/npress/api.html

#更新日志
##2015-09-03
1. 修正proxool连接池houseKeepingSleepTime参数错误。

##2015-08-25
1. 优化db模块代码及sql日志

##2015-08-10
1. 增强logger扩展性，从此可以愉快的使用slf4j+logback了(需要nlf-plugin-slf4j.jar)。

##2015-08-05
1. 增加请求重定向（返回Redirect）。

##2015-06-17
1. 对象和Bean互转映射失败时能准确定位失败的属性名。
2. 前端UI：增加字号选择组件；富文本编辑器增加字号选择；移动端增加侧边栏。

##2015-05-25
1. 更新ImageHelper的缩略图算法。
2. 优化部分前端UI。

##2015-04-30
1. 增加top查询，如List<Bean> users = Dao.getSelecter().table("user").asc("id").top(5);

##2015-04-23
1. 增加Dao自动映射，如User user = Dao.getSelecter().table("user").where("id",1).one(User.class);

##2015-04-21
1. 增加Dao快捷操作，如List<Bean> users = Dao.getSelecter().table("user").select();

##2015-04-17
1. ISelecter增加Iterator返回以支持大数据量的查询。

##2015-03-27
1. 修正rmi的中文乱码问题。

##2015-03-19
1. 减少is null条件的冗余代码。
* 自增ID增加分布式应用支持。
* 优化UI。

##2015-03-16
1. 修正多数据库支持问题。

##2015-03-09
1. mysql增加is null条件的支持。

##2015-03-03
1. Stringer.md5(String)方法不再抛出显式异常。

##2015-02-28
1. 修正请求禁止访问的地址时报错的问题。

##2015-01-17
1. 修正MD5中文编码引起的问题。
* 增加手机号等的正则。

##2015-01-17
1. 支持一次上传多个文件。
* 优化框架自定义异常。

##2015-01-12
1. 修正获取Cookie偶尔报错的问题。

##2015-01-08
1. 优化404的处理。
* 修正mobile.Toast和mobile.Alert无效的问题。

##2015-01-07
1. 修正某次代码优化造成XML解析失败的bug。

##2015-01-05
1. 增加针对移动端的返回类型：Alert和Toast。

##2014-12-25
1. 修改移动端html5 ui。
* Request获取客户端类型调整。

##2014-12-23
1. 代码优化。
* 插件转移到https://github.com/6tail/nlf-plugin

##2014-12-22
1. 微信公众号插件重构（支持接收所有消息，支持发送被动响应消息，支持自定义菜单创建、查询、删除，支持获取access_token）
* 增加微信公众号插件使用示例

##2014-12-21
1. 增加微信公众号开发插件nlf-plugin-weixin.jar

##2014-12-03
1. 修复文件上传进度相关问题。
* 增加IFind接口，用于支持Request的find自定义。
* Bean增加getList方法。
* XMLtoBean支持自闭合标签的解析。

##2014-11-27 1.6.0版本重大更新
1. 从框架里彻底删除asm字节码框架，自行实现字节码操作，框架得以大幅瘦身，至此100%的代码由六特尔编写。
* class文件扫描速度有了质的飞跃。
* 不再支持热部署功能。
* 不再支持注入对象的属性自动赋值（因为连我都没用过这个功能）。

##2014-11-24
1. 优化代码。

##2014-11-23
1. db配置文件过滤以.开头的文件名。
* 实现IDbSettingFileFilter接口，可对db配置文件过滤器进行扩展。

##2014-11-20
1. 单纯返回字符串时设置编码以防止浏览器显示乱码。

##2014-11-19
1. 不再扫描manifest.mf中Created-By包含Sun、Oracle、Apache、Apple、IBM、Signtool的jar
* WebContext可获取lib路径，classes路径。
* 404及错误提示页面可按需配置。
* 删除response下的Bad类，统一使用非受检异常BadException来替代。

##2014-11-14
1. 增加Dao相关封装。
* 优化前端js。

##2014-11-11
1. 增加CookieFetcher。
* 大幅改动常量，建议不要替换原有项目中的jar。
* 修正Object to Bean属性映射失败的问题。

##2014-11-10
1. 删除BeanUtil，删除nlfe.tld，以后${nlfe:bean(obj,'keyName')}不再使用，直接使用${obj.keyName}。
* 调整demo页面。
* 删除接口的public修饰符，基于jdk1.5重新编译。

##2014-11-08
1. 重构json包。
* 将tld文件打包到jar中，web.xml不再需要引入相关tld。
* 已支持内嵌jetty。
* 少量其他优化。

##2014-10-19
1. 优化扫描逻辑，不再扫描manifest.mf中Created-By包含Sun Microsystems Inc.的jar文件；不再重复扫描已扫描过的路径。
* web应用中禁止访问的路径和允许访问的路径去重。

##2014-10-14
1. 修复外部locale不加载的问题。
* 修复某些jar包扫描报错的问题。