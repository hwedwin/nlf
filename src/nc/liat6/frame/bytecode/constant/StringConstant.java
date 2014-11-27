package nc.liat6.frame.bytecode.constant;

public class StringConstant extends AbstractConstant{

  private int stringIndex;

  public int getStringIndex(){
    return stringIndex;
  }

  public void setStringIndex(int stringIndex){
    this.stringIndex = stringIndex;
  }

  @Override
  public StringConstant toStringConstant(){
    return this;
  }
}