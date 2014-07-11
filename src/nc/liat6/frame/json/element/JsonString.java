package nc.liat6.frame.json.element;

import java.math.BigDecimal;

/**
 * JSON字符串类型节点
 * 
 * @author 6tail
 * 
 */
public class JsonString extends AbstractElement{

  private static final long serialVersionUID = -1054998272922284563L;
  private String o;

  public JsonString(String o){
    this.o = o;
  }

  public String toString(){
    return o;
  }

  public JsonType type(){
    return JsonType.STRING;
  }

  public JsonString toJsonString(){
    return this;
  }

  public JsonNumber toJsonNumber(){
    JsonNumber n = new JsonNumber(new BigDecimal(o));
    return n;
  }
}
