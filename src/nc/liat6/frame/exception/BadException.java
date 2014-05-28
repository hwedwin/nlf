package nc.liat6.frame.exception;

import nc.liat6.frame.locale.L;

/**
 * ø…∫ˆ¬‘“Ï≥£
 * @author 6tail
 *
 */
public class BadException extends NlfException {

	private static final long serialVersionUID = -5122157914640946740L;
	private static final String MESSAGE = "exception.bad";
	private Object data = null;

	public BadException() {
		super(L.get(MESSAGE));
	}

	public BadException(String message) {
		super(message);
	}
	
	public BadException(Throwable cause) {
		super(L.get(MESSAGE),cause);
	}
	
	public BadException(String message,Throwable cause) {
		super(message,cause);
	}

	public Object getData(){
		return data;
	}

	public void setData(Object data){
		this.data = data;
	}

}