package nc.liat6.frame.web.response;

/**
 * 返回 - TIP消息
 * 
 * @author liat6
 * 
 */
public class Tip extends AbstractType{

	public static final int LONG = 0;
	public static final int NORMAL = 1;
	public static final int SHORT = 2;

	/** 消息类型 */
	private int type = NORMAL;

	public Tip(){
		super("ui.Tip");
	}
	
	public Tip(String msg){
		this();
		setMsg(msg);
	}
	
	public Tip(String msg,int type){
		this();
		setMsg(msg);
		setType(type);
	}
	
	public Tip(Object data,String msg){
		this();
		setData(data);
		setMsg(msg);
	}
	
	public Tip(Object data,String msg,int type){
		this();
		setData(data);
		setMsg(msg);
		setType(type);
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

}
