package nc.liat6.frame.bytecode.attribute;

public class SourceFileAttribute extends AbstractAttribute{

  private int sourceFileIndex;
  private String sourceFileName;

  public String getSourceFileName(){
    return sourceFileName;
  }

  public void setSourceFileName(String sourceFileName){
    this.sourceFileName = sourceFileName;
  }

  public int getSourceFileIndex(){
    return sourceFileIndex;
  }

  public void setSourceFileIndex(int sourceFileIndex){
    this.sourceFileIndex = sourceFileIndex;
  }
  
  @Override
  public SourceFileAttribute toSourceFileAttribute(){
    return this;
  }
}