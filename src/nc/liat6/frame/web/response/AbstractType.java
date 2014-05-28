package nc.liat6.frame.web.response;

/**
 * 返回 - 自定义封装，所有与前端UI有关的对象的父类
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractType extends Json{

	/** UI组件名称 */
	private String xtype;

	public AbstractType(String xtype){
		this.xtype = xtype;
	}

	public String getXtype(){
		return xtype;
	}

}
