package nc.liat6.frame.db.plugin;

import java.util.Iterator;
import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.paging.PageData;

/**
 * 查询接口
 *
 * @author 6tail
 *
 */
public interface ISelecter extends IExecuter{
  /**
   * 设置表名
   *
   * @param tableName 表名
   * @return 当前查询接口
   */
  ISelecter table(String tableName);

  /**
   * 要返回的列名
   *
   * @param column 列
   * @return 查询接口
   */
  ISelecter column(String... column);

  /**
   * SQL条件
   *
   * @param sql SQL语句
   * @return 查询接口
   */
  ISelecter where(String sql);

  /**
   * 带参数的SQL条件
   *
   * @param sql SQL语句
   * @param values 绑定变量
   * @return 查询接口
   */
  ISelecter whereSql(String sql,Object[] values);

  /**
   * 条件
   *
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  ISelecter where(String column,Object value);

  /**
   * 模糊查询 like %value%
   *
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  ISelecter whereLike(String column,Object value);

  /**
   * 模糊查询 like value%
   *
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  ISelecter whereLeftLike(String column,Object value);

  /**
   * 模糊查询 like %value
   *
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  ISelecter whereRightLike(String column,Object value);

  /**
   * 条件column != value
   *
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  ISelecter whereNq(String column,Object value);

  /**
   * 条件 column in (...)
   *
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  ISelecter whereIn(String column,Object... value);

  /**
   * 条件 column not in (...)
   *
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  ISelecter whereNotIn(String column,Object... value);

  /**
   * 结果升序
   *
   * @param column 列
   * @return 查询接口
   */
  ISelecter asc(String... column);

  /**
   * 结果降序
   *
   * @param column 列
   * @return 查询接口
   */
  ISelecter desc(String... column);

  /**
   * 返回列表
   *
   * @return 列表
   */
  List<Bean> select();
  
  /**
   * 返回自动转换后的对象列表
   * @param klass 转换的类
   * @return 对象列表
   */
  <T>List<T> select(Class<?> klass);
  
  /**
   * 返回自动转换后的对象列表
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 对象列表
   */
  <T>List<T> select(Class<?> klass,IBeanRule rule);

  /**
   * 分页查询
   *
   * @param pageNumber 页码
   * @param pageSize 每页记录数
   * @return 分页数据
   */
  PageData page(int pageNumber,int pageSize);
  
  /**
   * 自动转换对象的分页查询
   *
   * @param pageNumber 页码
   * @param pageSize 每页记录数
   * @param klass 转换的类
   * @return 分页数据
   */
  PageData page(int pageNumber,int pageSize,Class<?> klass);
  
  /**
   * 自动转换对象的分页查询
   *
   * @param pageNumber 页码
   * @param pageSize 每页记录数
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 分页数据
   */
  PageData page(int pageNumber,int pageSize,Class<?> klass,IBeanRule rule);

  /**
   * 获取单个记录
   *
   * @return 单记录对象
   */
  Bean one();
  
  /**
   * 获取自动转换对象的单个记录
   * @param klass 转换的类
   * @return 单记录对象
   */
  <T>T one(Class<?> klass);
  
  /**
   * 获取自动转换对象的单个记录
   * @param klass 转换的类
   * @param rule 转换规则
   * @return 单记录对象
   */
  <T>T one(Class<?> klass,IBeanRule rule);

  /**
   * 迭代结果集
   * 
   * @return
   */
  Iterator<Bean> iterator();
  
  /**
   * 自动转换对象的迭代结果集
   * @param klass 转换的类
   * @return
   */
  <T>Iterator<T> iterator(Class<?> klass);
  
  /**
   * 自动转换对象的迭代结果集
   * @param klass 转换的类
   * @param rule 转换规则
   * @return
   */
  <T>Iterator<T> iterator(Class<?> klass,IBeanRule rule);
  
  /**
   * 最前面的几个实体
   * @param n 数量
   * @return 实体列表
   */
  List<Bean> top(int n);
  
  /**
   * 最前面的几个对象
   * @param n 数量
   * @param klass 要转换的类名
   * @return 对象列表
   */
  <T>List<T> top(int n,Class<?> klass);
  
  /**
   * 最前面的几个对象
   * @param n 数量
   * @param klass 要转换的类名
   * @param rule 转换规则
   * @return 对象列表
   */
  <T>List<T> top(int n,Class<?> klass,IBeanRule rule);
}