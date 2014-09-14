package nc.liat6.frame.validate;

import java.util.HashMap;
import java.util.Map;
import nc.liat6.frame.locale.L;

/**
 * 匹配结论
 * 
 * @author 6tail
 * 
 */
public class RegPool{

  private static final Map<String,String> M = new HashMap<String,String>();
  static{
    M.put(RegUtil.NUMBER,"reg.number");
    M.put(RegUtil.INTEGER,"reg.integer");
    M.put(RegUtil.INTEGER_POSITIVE,"reg.integer_positive");
    M.put(RegUtil.INTEGER_NEGTIVE,"reg.integer_negtive");
    M.put(RegUtil.INTEGER_NOT_POSITIVE,"reg.integer_not_positive");
    M.put(RegUtil.INTEGER_NOT_NEGTIVE,"reg.integer_not_negtive");
    M.put(RegUtil.FLOAT,"reg.float");
    M.put(RegUtil.FLOAT_POSITIVE,"reg.float_positive");
    M.put(RegUtil.FLOAT_NEGTIVE,"reg.float_negtive");
    M.put(RegUtil.FLOAT_NOT_POSITIVE,"reg.float_not_positive");
    M.put(RegUtil.FLOAT_NOT_NEGTIVE,"reg.float_not_negtive");
    M.put(RegUtil.LETTER,"reg.letter");
    M.put(RegUtil.LETTER_LOWERCASE,"reg.letter_lowercase");
    M.put(RegUtil.LETTER_UPPERCASE,"reg.letter_uppercase");
    M.put(RegUtil.EMAIL,"reg.email");
    M.put(RegUtil.MOBILE,"reg.mobile");
  }

  private RegPool(){}

  public static String getMsg(String reg){
    String s = M.get(reg);
    return null==s?L.get("reg.error"):L.get(s);
  }
}
