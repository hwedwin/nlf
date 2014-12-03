NLF框架
===

#简介
NLF框架是六特尔独自开发的轻量级java框架，它只有1个jar包，含源代码在内只有500+KB的大小。它无侵入，无依赖，零配置，无注解，支持AOP，自动IOC，原生支持Oracle、SQLSERVER、MYSQL和独创的CSV数据库。使用它你将会体验到前所未有的开发效率，并且你会发现，它支持javase和javaee应用，你甚至不需要启动Server就可以对web应用后台代码进行调试。

#使用示例
假如数据库有1张表TABLE_USER，3个字段USER_ID，USER_NAME，USER_AGE。我们需要一个控制器，接收1个参数age，返回所有USER_AGE=age的记录的json格式。<br />
结果示例：{success:true,data:[{id:1,name:"张三",age:30},{id:2,name:"李四",age:30}]}


##学前班
```Java
public class UserAction(){
  public Object jsonUsers(){
    Request r = Context.get(Statics.REQUEST);
    int age = r.getInt("age");
    ITrans t = TransFactory.getTrans();
    List<Bean> users = t.getSelecter().column("USER_ID id","USER_NAME name","USER_AGE age").table("TABLE_USER").where("USER_AGE",age).select();
    t.rollback();
    t.close();
    return new Json(users);
  }
}
```

##幼儿园
```Java
public class User{
  private long id;
  private String name;
  private int age;
  public void setId(long id){this.id = id;}
  public long getId(){return id;}
  public void setName(String name){this.name = name;}
  public String getName(){return name;}
  public void setAge(int age){this.age = age;}
  public String getAge(){return age;}
}
public class UserAction(){
  public Object jsonUsers(){
    Request r = Context.get(Statics.REQUEST);
    int age = r.getInt("age");
    ITrans t = TransFactory.getTrans();
    List<Bean> users = t.getSelecter().table("TABLE_USER").where("USER_AGE",age).select();
    t.rollback();
    t.close();
    List<User> result = new ArrayList<User>();
    for(Bean o:users){
      User user = new User();
      user.setId(o.getLong("USER_ID",-1));
      user.setName(o.getString("USER_NAME"));
      user.setAge(o.getInt("USER_AGE",-1));
      result.add(user);
    }
    return new Json(result);
  }
}
```

##一年级
```Java
public class UserAction(){
  public Object jsonUsers(){
    Request r = Context.get(Statics.REQUEST);
    int age = r.getInt("age");
    ITrans t = TransFactory.getTrans();
    List<Bean> users = t.getSelecter().table("TABLE_USER").where("USER_AGE",age).select();
    t.rollback();
    t.close();
    List<User> result = new ArrayList<User>();
    for(Bean o:users){
      User user = o.toObject(User.class,new BeanRuleAdapter(){
        @Override
        public String getKey(String property){
          if("id".equals(property)){
            return "USER_ID";
          }else if("name".equals(property)){
            return "USER_NAME";
          }else if("age".equals(property)){
            return "USER_AGE";
          }
          return null;
        }
      });
      result.add(user);
    }
    return new Json(result);
  }
}
```

##二年级
```Java
public class UserAdapter implements IBeanRule{
  private static final Map<String,String> map = new HashMap<String,String>();
  static{
    map.put("id","USER_ID");
    map.put("name","USER_NAME");
    map.put("age","USER_AGE");
  }
  @Override
  public String getKey(String property){
    return map.get(property);
  }
}
public class UserAction(){
  private static IBeanRule userAdapter = new UserAdapter();
  public Object jsonUsers(){
    Request r = Context.get(Statics.REQUEST);
    int age = r.getInt("age");
    ITrans t = TransFactory.getTrans();
    List<Bean> users = t.getSelecter().table("TABLE_USER").where("USER_AGE",age).select();
    t.rollback();
    t.close();
    List<User> result = new ArrayList<User>();
    for(Bean o:users){
      User user = o.toObject(User.class,userAdapter);
      result.add(user);
    }
    return new Json(result);
  }
}
```

##三年级
```Java
public class UserAction(){
  private static IBeanRule userAdapter = new UserAdapter();
  public Object jsonUsers(){
    Request r = Context.get(Statics.REQUEST);
    int age = r.getInt("age");
    List<Bean> users = Dao.list(new DaoAdapter(){
      @Override
      public List<Bean> list(ITrans t){
        return t.getSelecter().table("TABLE_USER").where("USER_AGE",age).select();
      }
    });
    List<User> result = new ArrayList<User>();
    for(Bean o:users){
      User user = o.toObject(User.class,userAdapter);
      result.add(user);
    }
    return new Json(result);
  }
}
```

##四年级
```Java
public class UserAction(){
  private static IBeanRule userAdapter = new UserAdapter();
  public Object jsonUsers(){
    Request r = Context.get(Statics.REQUEST);
    int age = r.getInt("age");
    List<User> users = Dao.list(User.class,new DaoAdapter(){
      @Override
      public List<Bean> list(ITrans t){
        return t.getSelecter().table("TABLE_USER").where("USER_AGE",age).select();
      }
      @Override
      public IBeanRule rule(){
        return userAdapter;
      }
    });
    return new Json(users);
  }
}
```

##博士后
```Java
public interface UserService{
  List<User> listByAge(int age);
}
public class UserServiceImpl implements UserService{
  public List<User> listByAge(final int age){
    List<User> users = Dao.list(User.class,new DaoAdapter(){
      @Override
      public List<Bean> list(ITrans t){
        return t.getSelecter().table("TABLE_USER").where("USER_AGE",age).select();
      }
      @Override
      public IBeanRule rule(){
        return userAdapter;
      }
    });
    return users;
  }
}
public class UserAction(){
  private static IBeanRule userAdapter = new UserAdapter();
  private UserService userService;
  public void setUserService(UserService userService){
    this.userService = userService;
  }
  public Object jsonUsers(){
    Request r = Context.get(Statics.REQUEST);
    int age = r.getInt("age");
    List<User> users = userService.listByAge(age);
    return new Json(users);
  }
}
```

##圣斗士
```Java
//你猜会肿么写？
```

#更新日志
##2014-12-03
1. 修复文件上传进度相关问题。
* 增加IFind接口，用于支持Request的find自定义。

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

#其他
WebContent下有六特尔开发的i.js框架，通过一个核心js文件自动按需加载需要的组件，示例地址：http://6tail.cn/npress/api.html

#更多
    NLF框架官方QQ群：281471914
    NLF框架用户手册：http://6tail.cn/npress/demo/jsp