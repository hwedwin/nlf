package nc.liat6.frame.bytecode.constant;

public class UTFConstant extends AbstractConstant{

  private String content;

  public String getContent(){
    return content;
  }

  public void setContent(String content){
    this.content = content;
  }

  @Override
  public UTFConstant toUTFConstant(){
    return this;
  }
}