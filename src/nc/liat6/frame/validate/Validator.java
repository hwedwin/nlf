package nc.liat6.frame.validate;

import nc.liat6.frame.exception.BadException;

/**
 * 验证器
 * @author 6tail
 *
 */
public class Validator{

	/**
	 * 检查，验证失败，抛出BadException
	 * @param s 检查的值
	 * @param rd 绑定数据，用于返回
	 * @param rule 验证规则，可以多个
	 */
	public static void check(String s,Object rd,IValidatorRule... rule){
		for(IValidatorRule r:rule){
			if(!r.validate(s)){
				BadException be = new BadException(r.getErrorMessage());
				be.setData(rd);
				throw be;
			}
		}
	}
	
	/**
	 * 检查，验证失败，抛出BadException
	 * @param s 检查的值
	 * @param rule 验证规则，可以多个
	 */
	public static void check(String s,IValidatorRule... rule){
		check(s,null,rule);
	}

}
