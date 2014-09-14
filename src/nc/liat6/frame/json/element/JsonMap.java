package nc.liat6.frame.json.element;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * JSON Map对象类型节点
 * 
 * @author 6tail
 * 
 */
public class JsonMap extends AbstractElement{

  private static final long serialVersionUID = 6628903377372659244L;
  private Map<String,IJsonElement> o = new HashMap<String,IJsonElement>();

  public IJsonElement get(String key){
    return o.get(key);
  }

  public void set(String key,IJsonElement value){
    o.put(key,value);
  }

  public Set<String> keySet(){
    return o.keySet();
  }

  public JsonType type(){
    return JsonType.MAP;
  }

  public JsonMap toJsonMap(){
    return this;
  }

  public String toString(){
    return o.toString();
  }

  public IJsonElement select(String path){
    if(null==path){
      return null;
    }
    path = path.trim();
    if(!path.contains(".")){
      return get(path);
    }
    String[] k = path.split("\\.");
    IJsonElement je = get(k[0]);
    if(JsonType.MAP!=je.type()){
      return null;
    }
    for(int i = 1;i<k.length;i++){
      if(JsonType.MAP!=je.type()){
        return null;
      }
      je = je.toJsonMap().get(k[i]);
    }
    return je;
  }
}
