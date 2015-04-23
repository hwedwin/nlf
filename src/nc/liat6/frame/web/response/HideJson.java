package nc.liat6.frame.web.response;

import nc.liat6.frame.json.JSON;

/**
 * 隐藏json
 * 
 * @author 6tail
 *
 */
public class HideJson{
  private Object data;

  public HideJson(){}

  public HideJson(Object data){
    setData(data);
  }

  /**
   * 获取隐藏后的数据
   * 
   * @return 隐藏后的数据
   */
  public String getData(){
    String s = toBinaryString();
    s = s.replace("0","\u200c");
    s = s.replace("1","\u200d");
    return s;
  }

  /**
   * 获取原始数据
   * 
   * @return 原始数据
   */
  public Object getOldData(){
    return data;
  }

  public String toBinaryString(){
    String d = JSON.toJson(new Json(data));
    StringBuffer sb = new StringBuffer();
    char[] cs = d.toCharArray();
    for(char c:cs){
      String s = Integer.toBinaryString(c);
      int l = s.length();
      for(int i = 0;i<16-l;i++){
        s = "0"+s;
      }
      sb.append(s);
    }
    return sb.toString();
  }

  public void setData(Object data){
    this.data = data;
  }

  public String toString(){
    return getData();
  }
}