package nc.liat6.frame.db.plugin;

/**
 * 数据删除接口
 * 
 * @author 6tail
 * 
 */
public interface IDeleter extends IExecuter{

  /**
   * 设置表
   * 
   * @param tableName 表名
   * @return 数据删除接口
   */
  public IDeleter table(String tableName);

  /**
   * SQL条件
   * 
   * @param sql SQL语句
   * @return 数据删除接口
   */
  public IDeleter where(String sql);

  /**
   * 带参数的SQL条件
   * 
   * @param sql SQL语句
   * @param values 绑定变量
   * @return 数据删除接口
   */
  public IDeleter whereSql(String sql,Object[] values);

  /**
   * 删除条件
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据删除接口
   */
  public IDeleter where(String column,Object value);

  /**
   * 删除条件column in (...)
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据删除接口
   */
  public IDeleter whereIn(String column,Object... value);

  /**
   * 删除条件column not in (...)
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 数据删除接口
   */
  public IDeleter whereNotIn(String column,Object... value);

  /**
   * 删除操作，最后调用
   * 
   * @return 删除的记录数
   */
  public int delete();
}
