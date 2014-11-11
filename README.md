NLF框架
===

#简介
NLF框架是六特尔独自开发的轻量级java框架，它只有1个jar包，含源代码在内只有600+KB的大小。它无侵入，无依赖，零配置，无注解，支持AOP，自动IOC，支持自动分页，class动态加载，原生支持Oracle、SQLSERVER、MYSQL、MONGO和独创的CSV数据库。使用它你将会体验到前所未有的开发效率，并且你会发现，它支持javase和javaee应用，你甚至不需要启动Server就可以对web应用后台代码进行调试。

#更新日志
##2014-11-11
1.增加CookieFetcher。
* 大幅改动常量，建议不要替换原有项目中的jar。

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

#其他
WebContent下有六特尔开发的i.js框架，通过一个核心js文件自动按需加载需要的组件，示例地址：http://6tail.cn/npress/api.html

#更多
    NLF框架官方QQ群：281471914
    NLF框架介绍网址：http://6tail.cn