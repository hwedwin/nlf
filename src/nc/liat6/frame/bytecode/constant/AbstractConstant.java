package nc.liat6.frame.bytecode.constant;

public abstract class AbstractConstant implements IConstant{

  protected int index;
  protected int type;
  protected byte[] data = new byte[0];

  public int getIndex(){
    return index;
  }

  public void setIndex(int index){
    this.index = index;
  }

  public int getType(){
    return type;
  }

  public void setType(int type){
    this.type = type;
  }

  public byte[] getData(){
    return data;
  }

  public void setData(byte[] data){
    this.data = data;
  }
  
  public ClassConstant toClassConstant(){
    return null;
  }
  
  public UTFConstant toUTFConstant(){
    return null;
  }
  
  public FieldConstant toFieldConstant(){
    return null;
  }
  
  public MethodConstant toMethodConstant(){
    return null;
  }
  
  public InterfaceMethodConstant toInterfaceMethodConstant(){
    return null;
  }
  
  public NameAndTypeConstant toNameAndTypeConstant(){
    return null;
  }
  
  public StringConstant toStringConstant(){
    return null;
  }
}