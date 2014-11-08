package nc.liat6.frame.json.wrapper;

import nc.liat6.frame.json.JSON;

public abstract class AbstractJsonWrapper implements IJsonWrapper{

  /** 是否极简（不缩进不换行） */
  protected boolean tiny;
  /** 字符串首尾的引号 */
  protected String quote;
  /** 数字是否使用引号 */
  protected boolean numberQuoted;

  protected AbstractJsonWrapper(){
    this(JSON.tiny,JSON.quote,JSON.numberQuoted);
  }

  /**
   * 构造包装器
   *
   * @param tiny 是否极简（不缩进不换行）
   * @param quote 字符串首尾的引号
   * @param numberQuoted 数字是否使用引号
   */
  protected AbstractJsonWrapper(boolean tiny,String quote,boolean numberQuoted){
    this.tiny = tiny;
    this.quote = quote;
    this.numberQuoted = numberQuoted;
  }
}