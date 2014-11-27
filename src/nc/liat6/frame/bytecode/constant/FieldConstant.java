package nc.liat6.frame.bytecode.constant;

public class FieldConstant extends AbstractConstant{

  private int classIndex;
  private int nameAndTypeIndex;

  public int getClassIndex(){
    return classIndex;
  }

  public void setClassIndex(int classIndex){
    this.classIndex = classIndex;
  }

  public int getNameAndTypeIndex(){
    return nameAndTypeIndex;
  }

  public void setNameAndTypeIndex(int nameAndTypeIndex){
    this.nameAndTypeIndex = nameAndTypeIndex;
  }

  @Override
  public FieldConstant toFieldConstant(){
    return this;
  }
}