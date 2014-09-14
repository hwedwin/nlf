package nc.liat6.frame.json.element;

import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.locale.L;

/**
 * 抽象JSON节点
 * 
 * @author 6tail
 * 
 */
public abstract class AbstractElement implements IJsonElement{

  private String note;
  private static final long serialVersionUID = 1435775670247849314L;

  public JsonMap toJsonMap(){
    throw new NlfException(L.get("json.no_map"));
  }

  public JsonList toJsonList(){
    throw new NlfException(L.get("json.no_list"));
  }

  public JsonBool toJsonBool(){
    throw new NlfException(L.get("json.no_bool"));
  }

  public JsonNumber toJsonNumber(){
    throw new NlfException(L.get("json.no_number"));
  }

  public JsonString toJsonString(){
    throw new NlfException(L.get("json.no_string"));
  }

  public IJsonElement select(String path){
    return null;
  }

  public String getNote(){
    return note;
  }

  public void setNote(String note){
    this.note = note;
  }
}
