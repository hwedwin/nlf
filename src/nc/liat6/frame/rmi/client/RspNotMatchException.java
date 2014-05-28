package nc.liat6.frame.rmi.client;

import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;


/**
 * œÏ”¶≤ª∆•≈‰“Ï≥£
 * @author 6tail
 *
 */
public class RspNotMatchException extends NlfException {

	private static final long serialVersionUID = 4291160896339314388L;
	private static final String MESSAGE = "exception.rmi_req_not_match";

	public RspNotMatchException() {
		super(L.get(MESSAGE));
	}

	public RspNotMatchException(String message) {
		super(L.get(MESSAGE)+" : "+message);
	}

	public RspNotMatchException(Throwable cause) {
		super(L.get(MESSAGE),cause);
	}
	
	public RspNotMatchException(String message,Throwable cause) {
		super(message,cause);
	}

}