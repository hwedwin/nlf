package nc.liat6.frame.json.element;

/**
 * JSON布尔类型节点
 * @author 6tail
 *
 */
public class JsonBool extends AbstractElement{
	
	private static final long serialVersionUID = -4184611655089576407L;
	private boolean o;
	
	public JsonBool(boolean o){
		this.o = o;
	}
	
	public String toString(){
		return o?"true":"false";
	}

	public JsonType type() {
		return JsonType.BOOL;
	}
	
	public JsonBool toJsonBool(){
		return this;
	}
	
	public boolean value(){
		return o;
	}

}
