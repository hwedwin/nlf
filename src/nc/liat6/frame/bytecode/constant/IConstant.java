package nc.liat6.frame.bytecode.constant;

public interface IConstant{

  public static final int TYPE_CLASS = 7;
  public static final int TYPE_INT = 3;
  public static final int TYPE_LONG = 5;
  public static final int TYPE_FLOAT = 4;
  public static final int TYPE_DOUBLE = 6;
  public static final int TYPE_STRING = 8;
  public static final int TYPE_FIELD = 9;
  public static final int TYPE_METHOD = 10;
  public static final int TYPE_INTERFACE_METHOD = 11;
  public static final int TYPE_NAME_AND_TYPE = 12;
  public static final int TYPE_UTF = 1;
  
  int getIndex();
  
  void setIndex(int index);
  
  int getType();
  
  void setType(int type);
  
  byte[] getData();
  
  void setData(byte[] data);
  
  ClassConstant toClassConstant();
  
  UTFConstant toUTFConstant();
  
  FieldConstant toFieldConstant();
  
  MethodConstant toMethodConstant();
  
  InterfaceMethodConstant toInterfaceMethodConstant();
  
  NameAndTypeConstant toNameAndTypeConstant();
  
  StringConstant toStringConstant();
}