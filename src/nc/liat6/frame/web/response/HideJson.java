package nc.liat6.frame.web.response;

import nc.liat6.frame.json.JSON;

public class HideJson{

  private Object data;

  public HideJson(){}

  public HideJson(Object data){
    setData(data);
  }

  public String getData(){
    String s = toBinaryString();
    s = s.replace("0","\u200c");
    s = s.replace("1","\u200d");
    return s;
  }

  public String toBinaryString(){
    if(null==data){
      return null;
    }
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
