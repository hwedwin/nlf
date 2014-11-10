package nc.liat6.frame.db.plugin;

import nc.liat6.frame.db.entity.Bean;

/**
 * 数据更新接口
 *
 * @author 6tail
 *
 */
public interface IUpdater extends IExecuter{

  /**
   * 设置要更新的表名
   *
   * @param tableName 表名
   * @return 数据更新接口
   */
  IUpdater table(String tableName);

  /**
   * SQL语句形式的条件
   *
   * @param sql SQL语句
   * @return 数据更新接口
   */
  IUpdater where(String sql);

  /**
   * SQL语句与绑定变量形式的条件
   *
   * @param sql SQL语句
   * @param values 绑定变量
   * @return 数据更新接口
   */
  IUpdater whereSql(String sql,Object[] values);

  /**
   * 列=值 形式的条件
   *
   * @param column 列名
   * @param value 绑定变量
   * @return 数据更新接口
   */
  IUpdater where(String column,Object value);

  /**
   * 列 like %值% 形式的条件
   *
   * @param column 列名
   * @param value 绑定变量
   * @return 数据更新接口
   */
  IUpdater whereLike(String column,Object value);

  /**
   * 列 like 值% 形式的条件
   *
   * @param column 列名
   * @param value 绑定变量
   * @return 数据更新接口
   */
  IUpdater whereLeftLike(String column,Object value);

  /**
   * 列 like %值 形式的条件
   *
   * @param column 列名
   * @param value 绑定变量
   * @return 数据更新接口
   */
  IUpdater whereRightLike(String column,Object value);

  /**
   * 列 != 值 形式的条件
   *
   * @param column 列名
   * @param value 绑定变量
   * @return 数据更新接口
   */
  IUpdater whereNq(String column,Object value);

  /**
   * 列 in(...) 形式的条件
   *
   * @param column 列名
   * @param value 绑定变量
   * @return 数据更新接口
   */
  IUpdater whereIn(String column,Object... value);

  /**
   * 列 not in(...) 形式的条件
   *
   * @param column 列名
   * @param value 绑定变量
   * @return 数据更新接口
   */
  IUpdater whereNotIn(String column,Object... value);

  /**
   * 列赋值
   *
   * @param column 列名
   * @param value 绑定变量
   * @return 数据更新接口
   */
  IUpdater set(String column,Object value);

  /**
   * SQL语句形式的列赋值
   *
   * @param sql SQL语句，如：col=sysdate
   * @return 数据更新接口
   */
  IUpdater set(String sql);

  /**
   * 带绑定变量的SQL语句形式的列复制
   *
   * @param sql SQL语句
   * @param values 绑定变量
   * @return 数据更新接口
   */
  IUpdater setSql(String sql,Object[] values);

  /**
   * 更新操作，最后调用
   *
   * @return 更新记录数
   */
  int update();

  /**
   * 设置要更新的Bean
   *
   * @param bean
   * @return 数据更新接口
   */
  IUpdater setBean(Bean bean);
}