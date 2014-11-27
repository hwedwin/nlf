package nc.liat6.frame.bytecode.attribute;

import java.util.ArrayList;
import java.util.List;

public class InnerClassAttribute extends AbstractAttribute{

  private List<InnerClass> innerClasses = new ArrayList<InnerClass>();

  public List<InnerClass> getInnerClasses(){
    return innerClasses;
  }

  public void setInnerClasses(List<InnerClass> innerClasses){
    this.innerClasses = innerClasses;
  }
  
  public void addInnerClass(InnerClass innerClass){
    innerClasses.add(innerClass);
  }
  
  @Override
  public InnerClassAttribute toInnerClassAttribute(){
    return this;
  }
}
