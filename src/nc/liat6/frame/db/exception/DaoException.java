package nc.liat6.frame.db.exception;

import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;

/**
 * 数据访问异常
 * 
 * @author 六特尔
 */
public class DaoException extends NlfException {

	private static final long serialVersionUID = -1384642399300500862L;
	private static final String MESSAGE = "exception.dao";

	public DaoException() {
		super(L.get(MESSAGE));
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		this(L.get(MESSAGE),cause);
	}
	
	public DaoException(String message,Throwable cause) {
		super(message,cause);
	}

}
