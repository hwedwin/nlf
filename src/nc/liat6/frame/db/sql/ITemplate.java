package nc.liat6.frame.db.sql;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.paging.PageData;

/**
 * SQL执行模板接口
 *
 * @author 6tail
 *
 */
public interface ITemplate{
  /**
   * 设置事务接口
   *
   * @param t 事务接口
   */
  void setTrans(ITrans t);

  /**
   * 获取事务接口
   *
   * @return 事务接口
   */
  ITrans getTrans();

  /**
   * 查询一条记录
   *
   * @param sql SQL语句
   * @return 列值数组
   */
  Object[] one(String sql);

  /**
   * 带参数的查询一条记录
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return 列值数组
   */
  Object[] one(String sql,Object param);

  /**
   * 查询列表
   *
   * @param sql SQL语句
   * @return 包含列值数组的列表
   */
  List<Object[]> query(String sql);

  /**
   * 查询列表
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return 包含列值数组的列表
   */
  List<Object[]> query(String sql,Object param);

  /**
   * 查询实体列表
   *
   * @param sql SQL语句
   * @return 包含Bean的列表
   */
  List<Bean> queryEntity(String sql);

  /**
   * 查询对象列表
   *
   * @param sql SQL语句
   * @param klass 转换的类
   * @return 包含Bean的列表
   */
  <T>List<T> queryObject(String sql,Class<?> klass);

  /**
   * 查询对象列表
   *
   * @param sql SQL语句
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 包含Bean的列表
   */
  <T>List<T> queryObject(String sql,Class<?> klass,IBeanRule rule);

  /**
   * 查询实体列表
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return 包含Bean的列表
   */
  List<Bean> queryEntity(String sql,Object param);

  /**
   * 查询对象列表
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param klass 转换的类
   * @return 包含Bean的列表
   */
  <T>List<T> queryObject(String sql,Object param,Class<?> klass);

  /**
   * 查询对象列表
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 包含Bean的列表
   */
  <T>List<T> queryObject(String sql,Object param,Class<?> klass,IBeanRule rule);

  /**
   * 查询单个实体
   *
   * @param sql SQL语句
   * @return Bean
   */
  Bean oneEntity(String sql);

  /**
   * 查询单个对象
   *
   * @param sql SQL语句
   * @param klass 转换的类
   * @return Bean
   */
  <T>T oneObject(String sql,Class<?> klass);

  /**
   * 查询单个对象
   *
   * @param sql SQL语句
   * @param klass 转换的类
   * @param rule 转换规则
   * @return Bean
   */
  <T>T oneObject(String sql,Class<?> klass,IBeanRule rule);

  /**
   * 带参数查询单个实体
   *
   * @param sql SQL语句
   * @param param 绑定变量
   * @return Bean
   */
  Bean oneEntity(String sql,Object param);

  /**
   * 带参数查询单个对象
   *
   * @param sql SQL语句
   * @param param 绑定变量
   * @param klass 转换的类
   * @return Bean
   */
  <T>T oneObject(String sql,Object param,Class<?> klass);

  /**
   * 带参数查询单个对象
   *
   * @param sql SQL语句
   * @param param 绑定变量
   * @param klass 转换的类
   * @param rule 转换规则
   * @return Bean
   */
  <T>T oneObject(String sql,Object param,Class<?> klass,IBeanRule rule);

  /**
   * 获取记录数
   *
   * @param sql SQL语句
   * @return 记录数
   */
  int count(String sql);

  /**
   * 获取记录数
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return 记录数
   */
  int count(String sql,Object param);

  /**
   * 数据更新
   *
   * @param sql SQL语句
   * @return 更新的记录数
   */
  int update(String sql);

  /**
   * 带参数的数据更新
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return 更新的记录数
   */
  int update(String sql,Object param);

  /**
   * 分页查询
   *
   * @param sql SQL语句
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @return 分页数据封装
   */
  PageData query(String sql,int pageNumber,int pageSize);

  /**
   * 带参数的分页查询
   *
   * @param sql SQL语句
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return 分页数据封装
   */
  PageData query(String sql,int pageNumber,int pageSize,Object param);

  /**
   * 实体分页查询
   *
   * @param sql SQL语句
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @return 实体分页数据封装
   */
  PageData queryEntity(String sql,int pageNumber,int pageSize);

  /**
   * 带参数的实体分页查询
   *
   * @param sql SQL语句
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return 实体分页数据封装
   */
  PageData queryEntity(String sql,int pageNumber,int pageSize,Object param);

  /**
   * 对象分页查询
   *
   * @param sql SQL语句
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @param klass 转换的类
   * @return 实体分页数据封装
   */
  PageData queryObject(String sql,int pageNumber,int pageSize,Class<?> klass);

  /**
   * 对象分页查询
   *
   * @param sql SQL语句
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 实体分页数据封装
   */
  PageData queryObject(String sql,int pageNumber,int pageSize,Class<?> klass,IBeanRule rule);

  /**
   * 带参数的对象分页查询
   *
   * @param sql SQL语句
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param klass 转换的类
   * @return 实体分页数据封装
   */
  PageData queryObject(String sql,int pageNumber,int pageSize,Object param,Class<?> klass);

  /**
   * 带参数的对象分页查询
   *
   * @param sql SQL语句
   * @param pageNumber 页码，从1开始
   * @param pageSize 每页记录数
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 实体分页数据封装
   */
  PageData queryObject(String sql,int pageNumber,int pageSize,Object param,Class<?> klass,IBeanRule rule);

  /**
   * 获取连接变量
   *
   * @return 连接变量
   */
  ConnVar getConnVar();

  /**
   * 批量提交
   */
  int[] flush();

  /**
   * 设置使用的连接别名
   *
   * @param alias 连接别名
   */
  void setAlias(String alias);

  /**
   * 调用有参存储过程，不返回结果，如需带返回的，请获取Connection执行存储过程
   *
   * @param procName 存储过程名称
   * @param param 参数，可以是单个，也可以多个，如有多个参数，传入Object[]数组
   */
  void call(String procName,Object param);

  /**
   * 调用无参存储过程，不返回结果，如需带返回的，请获取Connection执行存储过程
   *
   * @param procName 存储过程名称
   */
  void call(String procName);

  /**
   * 迭代结果集
   *
   * @param sql SQL语句
   * @return
   */
  Iterator<Bean> iterator(String sql);

  /**
   * 迭代结果集
   *
   * @param sql SQL语句
   * @param klass 转换的类
   * @return
   */
  <T>Iterator<T> iterator(String sql,Class<?> klass);

  /**
   * 迭代结果集
   *
   * @param sql SQL语句
   * @param klass 转换的类
   * @param rule 转换规则
   * @return
   */
  <T>Iterator<T> iterator(String sql,Class<?> klass,IBeanRule rule);

  /**
   * 迭代结果集
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return
   */
  Iterator<Bean> iterator(String sql,Object param);

  /**
   * 迭代结果集
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param klass 转换的类
   * @return
   */
  <T>Iterator<T> iterator(String sql,Object param,Class<?> klass);

  /**
   * 迭代结果集
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param klass 转换的类
   * @param rule 转换规则
   * @return
   */
  <T>Iterator<T> iterator(String sql,Object param,Class<?> klass,IBeanRule rule);
  
  /**
   * 查询最前面几条记录
   * @param sql SQL语句
   * @param n 条数
   * @return 包含列值数组的列表
   */
  List<Object[]> top(String sql,int n);
  
  /**
   * 查询最前面几条记录
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param n 条数
   * @return 包含列值数组的列表
   */
  List<Object[]> top(String sql,Object param,int n);
  
  /**
   * 查询最前面几个实体
   * @param sql SQL语句
   * @param n 条数
   * @return 实体列表
   */
  List<Bean> topEntity(String sql,int n);
  
  /**
   * 查询最前面几个实体
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param n 条数
   * @return 实体列表
   */
  List<Bean> topEntity(String sql,Object param,int n);
  
  /**
   * 查询最前面几个对象
   * @param sql SQL语句
   * @param n 条数
   * @param klass 转换的类
   * @return 对象列表
   */
  <T>List<T> topObject(String sql,int n,Class<?> klass);
  
  /**
   * 查询最前面几个对象
   * @param sql SQL语句
   * @param n 条数
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 对象列表
   */
  <T>List<T> topObject(String sql,int n,Class<?> klass,IBeanRule rule);
  
  /**
   * 查询最前面几个对象
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param n 条数
   * @param klass 转换的类
   * @return 对象列表
   */
  <T>List<T> topObject(String sql,Object param,int n,Class<?> klass);
  
  /**
   * 查询最前面几个对象
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @param n 条数
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 对象列表
   */
  <T>List<T> topObject(String sql,Object param,int n,Class<?> klass,IBeanRule rule);

  /**
   * 对Statement和结果集的善后处理
   * 
   * @param stmt Statement
   * @param rs 结果集
   */
  void finalize(Statement stmt,ResultSet rs);

  /**
   * 最终的善后处理
   */
  void finalizeAll();
}