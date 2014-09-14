package nc.liat6.frame.db.plugin;

/**
 * 数据计数接口
 * 
 * @author 6tail
 * 
 */
public interface ICounter extends IExecuter{

  /**
   * 设置表
   * 
   * @param tableName 表名
   * @return 数据计数接口
   */
  public ICounter table(String tableName);

  /**
   * count列
   * 
   * @param column 列
   * @return 数据计数接口
   */
  public ICounter column(String... column);

  /**
   * SQL条件
   * 
   * @param sql SQL语句
   * @return 数据计数接口
   */
  public ICounter where(String sql);

  /**
   * 带参数的SQL条件
   * 
   * @param sql SQL语句
   * @param values 绑定变量
   * @return 数据计数接口
   */
  public ICounter whereSql(String sql,Object[] values);

  /**
   * 条件
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据计数接口
   */
  public ICounter where(String column,Object value);

  /**
   * 模糊条件 like %value%
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据计数接口
   */
  public ICounter whereLike(String column,Object value);

  /**
   * 模糊条件 like value%
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据计数接口
   */
  public ICounter whereLeftLike(String column,Object value);

  /**
   * 模糊条件 like %value
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据计数接口
   */
  public ICounter whereRightLike(String column,Object value);

  /**
   * 条件 col!=value
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据计数接口
   */
  public ICounter whereNq(String column,Object value);

  /**
   * 条件 col in (...)
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据计数接口
   */
  public ICounter whereIn(String column,Object... value);

  /**
   * 条件 col not in (...)
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据计数接口
   */
  public ICounter whereNotIn(String column,Object... value);

  /**
   * 计数
   * 
   * @return 记录数
   */
  public int count();
}
