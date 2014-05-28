package nc.liat6.frame.exception;

import nc.liat6.frame.locale.L;

/**
 * 找不到接口的实现异常
 * @author 6tail
 *
 */
public class ImplNotFoundException extends NlfException {

	private static final long serialVersionUID = 3932096485467560097L;
	private static final String MESSAGE = "exception.impl_not_found";

	public ImplNotFoundException() {
		super(L.get(MESSAGE));
	}

	public ImplNotFoundException(String message) {
		super(L.get(MESSAGE)+" : "+message);
	}

	public ImplNotFoundException(Throwable cause) {
		super(L.get(MESSAGE),cause);
	}
	
	public ImplNotFoundException(String message,Throwable cause) {
		super(message,cause);
	}

}