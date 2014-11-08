package nc.liat6.frame.json.wrapper;

/**
 * 字符串包装器
 *
 * @author 6tail
 *
 */
public class StringWrapper extends AbstractJsonWrapper{

  /**
   * 构造默认字符串包装器
   */
  public StringWrapper(){
    super();
  }

  /**
   * 构造字符串包装器
   *
   * @param tiny 是否极简（不缩进不换行）
   * @param quote 字符串首尾的引号
   * @param numberQuoted 数字是否使用引号
   */
  public StringWrapper(boolean tiny,String quote,boolean numberQuoted){
    super(tiny,quote,numberQuoted);
  }

  public String wrap(Object obj,int level){
    String s = obj+"";
    s = s.replace("\\","\\\\");
    s = s.replace("\b","\\b");
    s = s.replace("\t","\\t");
    s = s.replace("\n","\\n");
    s = s.replace("\f","\\f");
    s = s.replace("\r","\\r");
    s = s.replace("\'","\\\'");
    s = s.replace("\"","\\\"");
    return quote+s+quote;
  }
}