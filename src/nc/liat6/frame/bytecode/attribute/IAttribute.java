package nc.liat6.frame.bytecode.attribute;


public interface IAttribute{
  
  void setNameIndex(int index);
  
  int getNameIndex();
  
  void setName(String name);
  
  String getName();
  
  void setData(byte[] data);
  
  byte[] getData();
  
  SourceFileAttribute toSourceFileAttribute();
  
  InnerClassAttribute toInnerClassAttribute();
}
