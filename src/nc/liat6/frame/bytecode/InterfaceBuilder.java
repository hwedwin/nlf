package nc.liat6.frame.bytecode;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.bytecode.constant.ClassConstant;
import nc.liat6.frame.bytecode.constant.IConstant;
import nc.liat6.frame.bytecode.constant.UTFConstant;
import nc.liat6.frame.util.IOHelper;
import nc.liat6.frame.util.Mather;

public class InterfaceBuilder{
  
  private Klass klass;
  private List<IConstant> constants = new ArrayList<IConstant>();

  public InterfaceBuilder(Klass klass){
    this.klass = klass;
  }

  public Klass getKlass(){
    return klass;
  }

  public void setKlass(Klass klass){
    this.klass = klass;
  }
  
  private void generateConstants(String interfaceName) throws UnsupportedEncodingException{
    int index = 0;
    constants.add(null);
    index++;
    IConstant c = new ClassConstant();
    c.setType(IConstant.TYPE_CLASS);
    c.setIndex(index);
    c.setData(Mather.toByte(index+1,2));
    constants.add(c);
    index++;
    
    c = new UTFConstant();
    c.setType(IConstant.TYPE_UTF);
    c.setIndex(index);
    byte[] b = interfaceName.replace(".","/").getBytes(Klass.encode);
    c.setData(b);
    constants.add(c);
    index++;
    
    c = new ClassConstant();
    c.setType(IConstant.TYPE_CLASS);
    c.setIndex(index);
    c.setData(Mather.toByte(index+1,2));
    constants.add(c);
    index++;
    
    c = new UTFConstant();
    c.setType(IConstant.TYPE_UTF);
    c.setIndex(index);
    b = "java/lang/Object".getBytes(Klass.encode);
    c.setData(b);
    constants.add(c);
    index++;
    
    for(Method m:klass.getMethods()){
      if(m.isInit()||m.isClInit()){
        continue;
      }
      IConstant old = klass.getConstant(m.getNameIndex());
      c = new UTFConstant();
      c.setType(IConstant.TYPE_UTF);
      c.setIndex(index);
      c.setData(old.getData());
      constants.add(c);
      index++;
      
      old = klass.getConstant(m.getDescriptorIndex());
      c = new UTFConstant();
      c.setType(IConstant.TYPE_UTF);
      c.setIndex(index);
      c.setData(old.getData());
      constants.add(c);
      index++;
    }
    c = new UTFConstant();
    c.setType(IConstant.TYPE_UTF);
    c.setIndex(index);
    b = "SourceFile".getBytes(Klass.encode);
    c.setData(b);
    constants.add(c);
    index++;
    
    c = new UTFConstant();
    c.setType(IConstant.TYPE_UTF);
    c.setIndex(index);
    String className = interfaceName;
    className = className.substring(klass.getName().lastIndexOf(".")+1);
    String fileName = className+".java";
    b = fileName.getBytes(Klass.encode);
    c.setData(b);
    constants.add(c);
    index++;
  }
  
  private int index(String utf) throws UnsupportedEncodingException{
    for(IConstant c:constants){
      if(null==c){
        continue;
      }
      if(IConstant.TYPE_UTF!=c.getType()){
        continue;
      }
      if(utf.equals(new String(c.getData(),Klass.encode))){
        return c.getIndex();
      }
    }
    throw new IllegalArgumentException(utf);
  }
  
  public byte[] generate(String interfaceName) throws IOException{
    generateConstants(interfaceName);
    ByteArrayOutputStream stream = null;
    DataOutputStream out = null;
    try{
      stream = new ByteArrayOutputStream();
      out = new DataOutputStream(stream);
      out.write(klass.getMagic());
      out.write(Mather.toByte(klass.getMinorVersion(),2));
      out.write(Mather.toByte(klass.getMajorVersion(),2));
      //constants size
      out.write(Mather.toByte(constants.size(),2));
      for(IConstant c:constants){
        if(null==c){
          continue;
        }
        out.write(Mather.toByte(c.getType(),1));
        if(IConstant.TYPE_UTF==c.getType()){
          out.write(Mather.toByte(c.getData().length,2));
        }
        out.write(c.getData());
      }
      //access = public|abstract|interface
      out.write(Mather.toByte(0x0001|0x0200|0x0400,2));
      //class name
      out.write(Mather.toByte(1,2));
      //super class name
      out.write(Mather.toByte(3,2));
      //interface count
      out.write(Mather.toByte(0,2));
      //field count
      out.write(Mather.toByte(0,2));
      //method count
      int methodCount = 0;
      for(Method m:klass.getMethods()){
        if(!m.isInit()&&!m.isClInit()){
          methodCount++;
        }
      }
      out.write(Mather.toByte(methodCount,2));
      
      for(Method m:klass.getMethods()){
        if(m.isInit()||m.isClInit()){
          continue;
        }
        out.write(Mather.toByte(0x0001|0x0400,2));//access public abstract
        out.write(Mather.toByte(index(m.getName()),2));//name index
        out.write(Mather.toByte(index(m.getDescripter()),2));//desc index
        out.write(Mather.toByte(0,2));//attribute count
      }
      //class attribute count
      out.write(Mather.toByte(1,2));
      out.write(Mather.toByte(index("SourceFile"),2));//name index
      String className = interfaceName;
      className = className.substring(klass.getName().lastIndexOf(".")+1);
      String fileName = className+".java";
      out.write(Mather.toByte(2,4));//length
      out.write(Mather.toByte(index(fileName),2));//source index
      out.flush();
      return stream.toByteArray();
    }finally{
      IOHelper.closeQuietly(out);
      IOHelper.closeQuietly(stream);
    }
  }
}