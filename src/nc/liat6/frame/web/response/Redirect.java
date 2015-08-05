package nc.liat6.frame.web.response;

/**
 * 重定向
 * 
 * @author 6tail
 *
 */
public class Redirect{
  /** 页面地址 */
  private String uri;

  public Redirect(){}

  public Redirect(String uri){
    this.uri = uri;
  }

  public String getUri(){
    return uri;
  }

  public void setUri(String uri){
    this.uri = uri;
  }
}