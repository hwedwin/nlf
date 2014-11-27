package nc.liat6.frame.bytecode;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.bytecode.attribute.IAttribute;

public class Method{

  private int access;
  private Klass klass;
  private int nameIndex;
  private int descriptorIndex;
  private List<IAttribute> attributes = new ArrayList<IAttribute>();

  public Method(Klass klass){
    this.klass = klass;
  }

  public boolean isInit(){
    return "<init>".equals(getName());
  }
  
  public boolean isClInit(){
    return "<clinit>".equals(getName());
  }

  public String getName(){
    return klass.getConstant(nameIndex).toUTFConstant().getContent();
  }

  public String getDescripter(){
    return klass.getConstant(descriptorIndex).toUTFConstant().getContent();
  }

  public int getAccess(){
    return access;
  }

  public void setAccess(int access){
    this.access = access;
  }

  public Klass getKlass(){
    return klass;
  }

  public void setKlass(Klass klass){
    this.klass = klass;
  }

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

  public List<IAttribute> getAttributes(){
    return attributes;
  }

  public void setAttributes(List<IAttribute> attributes){
    this.attributes = attributes;
  }

  public void addAttribute(IAttribute attribute){
    attributes.add(attribute);
  }
}
