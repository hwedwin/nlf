package nc.liat6.frame.bytecode.attribute;

public class InnerClass{

  private int innerClassIndex;
  private int outerClassIndex;
  private int innerNameIndex;
  private int innerAccess;
  private String innerClassName;

  public int getInnerClassIndex(){
    return innerClassIndex;
  }

  public void setInnerClassIndex(int innerClassIndex){
    this.innerClassIndex = innerClassIndex;
  }

  public int getOuterClassIndex(){
    return outerClassIndex;
  }

  public void setOuterClassIndex(int outerClassIndex){
    this.outerClassIndex = outerClassIndex;
  }

  public int getInnerNameIndex(){
    return innerNameIndex;
  }

  public void setInnerNameIndex(int innerNameIndex){
    this.innerNameIndex = innerNameIndex;
  }

  public int getInnerAccess(){
    return innerAccess;
  }

  public void setInnerAccess(int innerAccess){
    this.innerAccess = innerAccess;
  }

  public String getInnerClassName(){
    return innerClassName;
  }

  public void setInnerClassName(String innerClassName){
    this.innerClassName = innerClassName;
  }
}