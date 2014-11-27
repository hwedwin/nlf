package nc.liat6.frame.bytecode.constant;

public class NameAndTypeConstant extends AbstractConstant{

  private int nameIndex;
  private int descriptorIndex;

  public int getNameIndex(){
    return nameIndex;
  }

  public void setNameIndex(int nameIndex){
    this.nameIndex = nameIndex;
  }

  public int getDescriptorIndex(){
    return descriptorIndex;
  }

  public void setDescriptorIndex(int descriptorIndex){
    this.descriptorIndex = descriptorIndex;
  }

  @Override
  public NameAndTypeConstant toNameAndTypeConstant(){
    return this;
  }
}