package nc.liat6.frame.json.element;

/**
 * JSON数字类型节点
 * 
 * @author 6tail
 * 
 */
public class JsonNumber extends AbstractElement{

  private static final long serialVersionUID = -2260717882558566579L;
  private Number n;

  public JsonNumber(Number n){
    this.n = n;
  }

  public JsonType type(){
    return JsonType.NUMBER;
  }

  public String toString(){
    return n+"";
  }

  public Number value(){
    return n;
  }

  public JsonNumber toJsonNumber(){
    return this;
  }
}
