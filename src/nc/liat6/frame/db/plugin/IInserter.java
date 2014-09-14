package nc.liat6.frame.db.plugin;

import nc.liat6.frame.db.entity.Bean;

/**
 * 数据插入接口
 * 
 * @author 6tail
 * 
 */
public interface IInserter extends IExecuter{

  /**
   * 设置要插入数据的表
   * 
   * @param tableName 表名
   * @return 数据插入接口
   */
  public IInserter table(String tableName);

  /**
   * 设置列的值
   * 
   * @param column 列名
   * @param value 绑定变量
   * @return 数据插入接口
   */
  public IInserter set(String column,Object value);

  /**
   * 通过SQL直接设置列的值
   * 
   * @param column 列名
   * @param valueSql 值，仅用于纯SQL，如:sysdate
   * @return 数据插入接口
   */
  public IInserter setSql(String column,String valueSql);

  /**
   * 通过SQL设置带变量的值
   * 
   * @param column 列名
   * @param valueSql 值，仅用于纯SQL，如:to_date(?,'yyyy-mm-dd')
   * @param values 绑定变量
   * @return 数据插入接口
   */
  public IInserter setSql(String column,String valueSql,Object[] values);

  /**
   * 插入操作，最后调用
   * 
   * @return 插入的记录数
   */
  public int insert();

  /**
   * 设置要插入的Bean
   * 
   * @param bean
   * @return 数据插入接口
   */
  public IInserter setBean(Bean bean);
}
