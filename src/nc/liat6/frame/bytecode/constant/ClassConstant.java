package nc.liat6.frame.bytecode.constant;

public class ClassConstant extends AbstractConstant{

  private int nameIndex;

  public int getNameIndex(){
    return nameIndex;
  }

  public void setNameIndex(int nameIndex){
    this.nameIndex = nameIndex;
  }
  
  @Override
  public ClassConstant toClassConstant(){
    return this;
  }
}