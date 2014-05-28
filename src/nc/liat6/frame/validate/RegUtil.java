package nc.liat6.frame.validate;

/**
 * 正则工具
 * 
 * @author 6tail
 * 
 */
public class RegUtil{

	/** 0到9的数字 */
	public static final String NUMBER = "^[0-9]*$";

	/** 正整数 */
	public static final String INTEGER_POSITIVE = "^[1-9]\\d*$";

	/** 负整数 */
	public static final String INTEGER_NEGTIVE = "^-[1-9]\\d*$";

	/** 整数 */
	public static final String INTEGER = "^-?[1-9]\\d*$";

	/** 非负整数 */
	public static final String INTEGER_NOT_NEGTIVE = "^[1-9]\\d*|0$";

	/** 非正整数 */
	public static final String INTEGER_NOT_POSITIVE = "^-[1-9]\\d*|0$";

	/** 正浮点数 */
	public static final String FLOAT_POSITIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

	/** 负浮点数 */
	public static final String FLOAT_NEGTIVE = "^-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)$";

	/** 浮点数 */
	public static final String FLOAT = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";

	/** 非负浮点数 */
	public static final String FLOAT_NOT_NEGTIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";

	/** 非正浮点数 */
	public static final String FLOAT_NOT_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";

	/** 字母 */
	public static final String LETTER = "^[A-Za-z]+$";

	/** 大写字母 */
	public static final String LETTER_UPPERCASE = "^[A-Z]+$";

	/** 小写字母 */
	public static final String LETTER_LOWERCASE = "^[a-z]+$";

	/** 电子邮件 */
	public static final String EMAIL = "^\\w+([-+\\.]\\w+)*@\\w+([-\\.]\\w+)*\\.\\w+([-\\.]\\w+)*$";
	
	/** 手机号码 */
	public static final String MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
}
