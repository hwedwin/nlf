package nc.liat6.frame.klass;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.json.JSON;

/**
 * 类信息封装
 * 
 * @author 6tail
 * 
 */
public class ClassInfo{

  private boolean inJar;
  private boolean abstractClass;
  private String className;
  private String home;
  private String fileName;
  private long lastModify;
  private List<String> interfaces = new ArrayList<String>();

  public long getLastModify(){
    return lastModify;
  }

  public void setLastModify(long lastModify){
    this.lastModify = lastModify;
  }

  public String getClassName(){
    return className;
  }

  public void setClassName(String className){
    this.className = className;
  }

  public boolean isInJar(){
    return inJar;
  }

  public void setInJar(boolean inJar){
    this.inJar = inJar;
  }

  public String getHome(){
    return home;
  }

  public void setHome(String home){
    this.home = home;
  }

  public String getFileName(){
    return fileName;
  }

  public void setFileName(String fileName){
    this.fileName = fileName;
  }

  public List<String> getInterfaces(){
    return interfaces;
  }

  public void setInterfaces(List<String> interfaces){
    this.interfaces = interfaces;
  }

  public void addInterface(String s){
    interfaces.add(s);
  }

  @Override
  public String toString(){
    return JSON.toJson(this,false);
  }

  public boolean isAbstractClass(){
    return abstractClass;
  }

  public void setAbstractClass(boolean abstractClass){
    this.abstractClass = abstractClass;
  }
}
