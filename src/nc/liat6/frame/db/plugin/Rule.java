package nc.liat6.frame.db.plugin;

/**
 * 规则
 * @author 6tail
 *
 */
public class Rule{

	/**操作开始*/
	private String opStart = "";
	
	/**字段*/
	private String column;
	
	/**操作结束*/
	private String opEnd = "";
	
	/**赋值*/
	private String tag = "?";

	public String getOpStart(){
		return opStart;
	}

	public void setOpStart(String opStart){
		this.opStart = opStart;
	}

	public String getOpEnd(){
		return opEnd;
	}

	public void setOpEnd(String opEnd){
		this.opEnd = opEnd;
	}

	public String getColumn(){
		return column;
	}

	public void setColumn(String column){
		this.column = column;
	}

	public String getTag(){
		return tag;
	}

	public void setTag(String tag){
		this.tag = tag;
	}

}
