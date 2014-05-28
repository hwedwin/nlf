package nc.liat6.frame.validate.rule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nc.liat6.frame.validate.RegPool;

/**
 * ’˝‘Ú∆•≈‰
 * 
 * @author 6tail
 * 
 */
public class RuleRegex extends AbstractRule{
	
	private String reg;

	public RuleRegex(String item,String reg){
		this.reg = reg;
		setErrorMessage(item + RegPool.getMsg(reg));
	}
	
	public RuleRegex(String reg){
		this("",reg);
	}

	public boolean validate(String key){
		if(null == key){
			return false;
		}
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(key);
		return m.matches();
	}

}
