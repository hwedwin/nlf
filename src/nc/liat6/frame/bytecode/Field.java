package nc.liat6.frame.bytecode;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.bytecode.attribute.IAttribute;

public class Field{

  private int access;
  private Klass klass;
  private int nameIndex;
  private int descripterIndex;
  private List<IAttribute> attributes = new ArrayList<IAttribute>();

  public Field(Klass klass){
    this.klass = klass;
  }

  public Klass getKlass(){
    return klass;
  }

  public void setKlass(Klass klass){
    this.klass = klass;
  }

  public int getAccess(){
    return access;
  }

  public void setAccess(int access){
    this.access = access;
  }

  public int getNameIndex(){
    return nameIndex;
  }

  public void setNameIndex(int nameIndex){
    this.nameIndex = nameIndex;
  }

  public int getDescripterIndex(){
    return descripterIndex;
  }

  public void setDescripterIndex(int descripterIndex){
    this.descripterIndex = descripterIndex;
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