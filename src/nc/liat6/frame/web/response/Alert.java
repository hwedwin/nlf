package nc.liat6.frame.web.response;

/**
 * 返回 - 弹出框
 * 
 * @author liat6
 * 
 */
public class Alert extends AbstractType{

  private String title = null;
  private String content = null;
  private Integer width = null;
  private Integer height = null;

  public Alert(){
    super("z.Alert");
  }

  public Alert(String content){
    this();
    setContent(content);
  }

  public String getTitle(){
    return title;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public String getContent(){
    return content;
  }

  public void setContent(String content){
    this.content = content;
  }

  public Integer getWidth(){
    return width;
  }

  public void setWidth(Integer width){
    this.width = width;
  }

  public Integer getHeight(){
    return height;
  }

  public void setHeight(Integer height){
    this.height = height;
  }
}
