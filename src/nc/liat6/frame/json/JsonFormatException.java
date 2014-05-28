package nc.liat6.frame.json;

import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;

/**
 * 不支持的JSON格式
 * @author 6tail
 *
 */
public class JsonFormatException extends NlfException {

	private static final long serialVersionUID = 3932096485367560097L;
	private static final String MESSAGE = "json.not_support";

	public JsonFormatException() {
		super(L.get(MESSAGE));
	}

	public JsonFormatException(String message) {
		super(L.get(MESSAGE)+" : "+message);
	}

	public JsonFormatException(Throwable cause) {
		super(L.get(MESSAGE),cause);
	}
	
	public JsonFormatException(String message,Throwable cause) {
		super(L.get(MESSAGE)+" : "+message,cause);
	}

}