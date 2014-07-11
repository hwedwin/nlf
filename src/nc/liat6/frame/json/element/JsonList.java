package nc.liat6.frame.json.element;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON List类型节点
 * 
 * @author 6tail
 * 
 */
public class JsonList extends AbstractElement{

  private static final long serialVersionUID = 1608642326897226739L;
  private List<IJsonElement> l = new ArrayList<IJsonElement>();

  public int size(){
    return l.size();
  }

  public IJsonElement get(int index){
    return l.get(index);
  }

  public JsonType type(){
    return JsonType.LIST;
  }

  public void add(IJsonElement o){
    l.add(o);
  }

  public JsonList toJsonList(){
    return this;
  }

  public String toString(){
    return l.toString();
  }

  public IJsonElement select(String path){
    if(null==path){
      return null;
    }
    path = path.trim();
    return get(Integer.parseInt(path));
  }
}
