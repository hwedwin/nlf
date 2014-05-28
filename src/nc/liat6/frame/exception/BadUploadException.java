package nc.liat6.frame.exception;

import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;

/**
 * 文件上传异常
 * @author 6tail
 *
 */
public class BadUploadException extends NlfException {

	private static final long serialVersionUID = -2382517006519673215L;
	private static final String MESSAGE = "exception.upload";

	public BadUploadException() {
		super(L.get(MESSAGE));
	}

	public BadUploadException(String message) {
		super(L.get(MESSAGE)+" : "+message);
	}

	public BadUploadException(Throwable cause) {
		this(null==cause?null:cause.getMessage());
	}
	
	public BadUploadException(String message,Throwable cause) {
		this(null==cause?null:cause.getMessage());
	}

}