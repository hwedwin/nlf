package nc.liat6.frame.db.sql;

import java.util.List;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.entity.Bean;
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
   * 查询实体列表
   *
   * @param sql SQL语句
   * @param param 绑定变量，可以是数组，也可以是单值
   * @return 包含Bean的列表
   */
  List<Bean> queryEntity(String sql,Object param);

  /**
   * 查询单个实体
   *
   * @param sql SQL语句
   * @return Bean
   */
  Bean oneEntity(String sql);

  /**
   * 带参数查询单个实体
   *
   * @param sql SQL语句
   * @param param 绑定变量
   * @return Bean
   */
  Bean oneEntity(String sql,Object param);

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
}