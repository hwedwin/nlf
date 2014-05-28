package nc.liat6.frame.validate.rule;

import java.text.ParseException;

import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.Dater;

/**
 * 年-月-日形式
 * @author 6tail
 *
 */
public class RuleDate extends AbstractRule{
	
	public RuleDate(String item){
		setErrorMessage(item+L.get("reg.date_suffix"));
	}
	
	public RuleDate(){
		setErrorMessage(L.get("reg.date"));
	}

	public boolean validate(String key){
		if(null==key) return false;
		try{
			Dater.ymd2Date(key);
		}catch(ParseException e){
			return false;
		}
		return true;
	}

}
