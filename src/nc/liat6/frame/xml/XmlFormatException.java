package nc.liat6.frame.xml;

import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;

/**
 * 不支持的XML格式
 * 
 * @author 6tail
 * 
 */
public class XmlFormatException extends NlfException{

	private static final long serialVersionUID = -7025417612500249343L;
	private static final String MESSAGE = "xml.not_support";

	public XmlFormatException(){
		super(L.get(MESSAGE));
	}

	public XmlFormatException(String message){
		super(L.get(MESSAGE) + " : " + message);
	}

	public XmlFormatException(Throwable cause){
		super(L.get(MESSAGE),cause);
	}

	public XmlFormatException(String message,Throwable cause){
		super(L.get(MESSAGE) + " : " + message,cause);
	}

}