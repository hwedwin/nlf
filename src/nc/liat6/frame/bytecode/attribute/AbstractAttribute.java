package nc.liat6.frame.bytecode.attribute;

public abstract class AbstractAttribute implements IAttribute{

  protected int nameIndex;
  protected String name;
  protected byte[] data;

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public int getNameIndex(){
    return nameIndex;
  }

  public void setNameIndex(int nameIndex){
    this.nameIndex = nameIndex;
  }

  public byte[] getData(){
    return data;
  }

  public void setData(byte[] data){
    this.data = data;
  }
  
  public SourceFileAttribute toSourceFileAttribute(){
    return null;
  }
  
  public InnerClassAttribute toInnerClassAttribute(){
    return null;
  }
}