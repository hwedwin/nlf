package nc.liat6.frame.db.transaction;

import nc.liat6.frame.db.DbType;
import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.sql.ITemplate;

/**
 * 事务接口
 * @author 6tail
 *
 */
public interface ITrans {
	
	/**
	 * 初始化
	 * @param alias 连接别名
	 */
	public void init(String alias);
	
	/**
	 * 获取执行器
	 * @return 执行器
	 */
	public IUpdater getUpdater();
	
	/**
	 * 获取删除器
	 * @return 删除器
	 */
	public IDeleter getDeleter();
	
	/**
	 * 获取插入器
	 * @return 插入器
	 */
	public IInserter getInserter();
	
	/**
	 * 获取查询器
	 * @return 查询器
	 */
	public ISelecter getSelecter();
	
	/**
	 * 获取计数器
	 * @return 计数器
	 */
	public ICounter getCounter();

	/**
	 * 获取模板
	 * 
	 * @return 模板
	 */
	public ITemplate getTemplate();
	
	/**
	 * 提交事务
	 */
	public void commit();

	/**
	 * 回滚事务
	 */
	public void rollback();

	/**
	 * 关闭连接
	 */
	public void close();
	
	/**
	 * 获取数据库类型
	 * @return 数据库类型
	 * @see DbType
	 */
	public DbType getDbType();

}
