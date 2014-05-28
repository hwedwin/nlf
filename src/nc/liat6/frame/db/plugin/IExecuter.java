package nc.liat6.frame.db.plugin;

import nc.liat6.frame.db.sql.ITemplate;

/**
 * 数据执行接口
 * @author 6tail
 *
 */
public interface IExecuter{
	
	/**
	 * 获取SQL语句，注意只有在提交前才能获取到
	 * @return 执行的SQL语句
	 */
	public String getSql();
	
	/**
	 * 获取绑定变量，注意只有在提交前才能获取到
	 * @return 绑定变量
	 */
	public Object[] getParam();
	
	public void setTemplate(ITemplate template);
	
	public ITemplate getTemplate();

}
