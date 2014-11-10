package nc.liat6.frame.db.transaction;

import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.sql.ITemplate;

/**
 * 事务接口
 *
 * @author 6tail
 *
 */
public interface ITrans{

  /**
   * 初始化
   *
   * @param alias 连接别名
   */
  void init(String alias);

  /**
   * 获取执行器
   *
   * @return 执行器
   */
  IUpdater getUpdater();

  /**
   * 获取删除器
   *
   * @return 删除器
   */
  IDeleter getDeleter();

  /**
   * 获取插入器
   *
   * @return 插入器
   */
  IInserter getInserter();

  /**
   * 获取查询器
   *
   * @return 查询器
   */
  ISelecter getSelecter();

  /**
   * 获取计数器
   *
   * @return 计数器
   */
  ICounter getCounter();

  /**
   * 获取SQL执行模板
   *
   * @return SQL执行模板
   */
  ITemplate getTemplate();

  /**
   * 提交事务
   */
  void commit();

  /**
   * 回滚事务
   */
  void rollback();

  /**
   * 关闭连接
   */
  void close();

  /**
   * 获取数据库类型
   *
   * @return 数据库类型
   */
  String getDbType();

  /**
   * 关闭批量更新功能
   */
  void disableBatch();

  /**
   * 开启批量更新功能
   */
  void enableBatch();

  /**
   * 是否开启批量更新功能
   *
   * @return true/false
   */
  boolean isBatchEnabled();
}