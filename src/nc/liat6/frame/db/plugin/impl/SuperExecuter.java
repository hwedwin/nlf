package nc.liat6.frame.db.plugin.impl;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.sql.ITemplate;

/**
 * 执行器超类
 * @author 6tail
 *
 */
public abstract class SuperExecuter{
	
	protected ITemplate template = null;
	protected StringBuffer s = new StringBuffer();
	protected List<Object> params = new ArrayList<Object>();
	
	public SuperExecuter(){}
	
	public Object[] getParam(){
		return params.toArray();
	}

	public ITemplate getTemplate(){
		return template;
	}

	public void setTemplate(ITemplate template){
		this.template = template;
	}
	
	/**
	 * 全部重置，包括SQL语句和绑定变量
	 */
	protected void reset(){
		resetSql();
		resetParams();
	}
	
	/**
	 * 重置SQL
	 */
	protected void resetSql(){
		s = new StringBuffer();
	}
	
	/**
	 * 重置绑定变量
	 */
	protected void resetParams(){
		params.clear();
	}
	
	/**
	 * 可变长参数转List，Object数组也会转入
	 * @param value Object或者Object数组
	 * @return List
	 */
	protected List<Object> objectsToList(Object... value){
		List<Object> l = new ArrayList<Object>();
		for(int i = 0;i < value.length;i++){
			if (value[i] instanceof Object[]) {
				Object[] ps = (Object[]) value[i];
				for(Object p:ps){
					l.add(p);
				}
			}else{
				l.add(value[i]);
			}
		}
		return l;
	}

}
