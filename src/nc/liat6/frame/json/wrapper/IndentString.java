package nc.liat6.frame.json.wrapper;

/**
 * 带缩进处理的字符串
 * @author 6tail
 *
 */
public class IndentString{

  /** 换行符 */
  private static final String CRLF = System.getProperty("line.separator");
  private StringBuilder s = new StringBuilder();
  private boolean tiny;

  public IndentString(boolean tiny){
    this.tiny = tiny;
  }

  public void add(String str){
    s.append(str);
  }

  public void add(char c){
    s.append(c);
  }

  public void addSpace(int level){
    if(!tiny){
      for(int j = 0;j<2*level;j++){
        s.append(' ');
      }
    }
  }

  public void addLine(){
    if(!tiny){
      s.append(CRLF);
    }
  }

  public String toString(){
    return s.toString();
  }
}