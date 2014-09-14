package nc.liat6.frame.json.wrapper;

import nc.liat6.frame.json.JSON;

/**
 * 字符串包装器
 * 
 * @author 6tail
 * 
 */
public class StringWrapper implements IWrapper{

  private String quote = JSON.QUOTE_DEFAULT;

  public StringWrapper(){}

  public StringWrapper(String quote){
    this.quote = quote;
  }

  public String wrap(Object os,int level){
    String ss = (os+"");
    ss = ss.replace("\\","\\\\");
    ss = ss.replace("\b","\\b");
    ss = ss.replace("\t","\\t");
    ss = ss.replace("\n","\\n");
    ss = ss.replace("\f","\\f");
    ss = ss.replace("\r","\\r");
    ss = ss.replace("\'","\\\'");
    ss = ss.replace("\"","\\\"");
    return quote+ss+quote;
  }
}
