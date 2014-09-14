package nc.liat6.frame.db.plugin;

import java.util.List;
import nc.liat6.frame.db.entity.Bean;
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
  public ISelecter table(String tableName);

  /**
   * 要返回的列名
   * 
   * @param column 列
   * @return 查询接口
   */
  public ISelecter column(String... column);

  /**
   * SQL条件
   * 
   * @param sql SQL语句
   * @return 查询接口
   */
  public ISelecter where(String sql);

  /**
   * 带参数的SQL条件
   * 
   * @param sql SQL语句
   * @param values 绑定变量
   * @return 查询接口
   */
  public ISelecter whereSql(String sql,Object[] values);

  /**
   * 条件
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  public ISelecter where(String column,Object value);

  /**
   * 模糊查询 like %value%
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  public ISelecter whereLike(String column,Object value);

  /**
   * 模糊查询 like value%
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  public ISelecter whereLeftLike(String column,Object value);

  /**
   * 模糊查询 like %value
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  public ISelecter whereRightLike(String column,Object value);

  /**
   * 条件column != value
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  public ISelecter whereNq(String column,Object value);

  /**
   * 条件 column in (...)
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  public ISelecter whereIn(String column,Object... value);

  /**
   * 条件 column not in (...)
   * 
   * @param column 列
   * @param value 绑定变量
   * @return 查询接口
   */
  public ISelecter whereNotIn(String column,Object... value);

  /**
   * 结果升序
   * 
   * @param column 列
   * @return 查询接口
   */
  public ISelecter asc(String... column);

  /**
   * 结果降序
   * 
   * @param column 列
   * @return 查询接口
   */
  public ISelecter desc(String... column);

  /**
   * 返回列表
   * 
   * @return 列表
   */
  public List<Bean> select();

  /**
   * 分页查询
   * 
   * @param pageNumber 页码
   * @param pageSize 每页记录数
   * @return 分页数据
   */
  public PageData page(int pageNumber,int pageSize);

  /**
   * 获取单个记录
   * 
   * @return 单记录对象
   */
  public Bean one();
}
