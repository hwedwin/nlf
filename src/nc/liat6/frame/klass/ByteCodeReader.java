package nc.liat6.frame.klass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import nc.liat6.frame.Factory;
import nc.liat6.frame.bytecode.Klass;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.util.IOHelper;


public class ByteCodeReader{

  /**
   * 从输入流读取字节码
   * 
   * @param in 输入流
   * @return 字节码
   * @throws IOException
   */
  private byte[] readClassFromStream(InputStream in) throws IOException{
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try{
      byte[] buffer = new byte[1024];
      int l = 0;
      while(-1!=(l = in.read(buffer))){
        out.write(buffer,0,l);
      }
      out.flush();
      return out.toByteArray();
    }finally{
      IOHelper.closeQuietly(in);
      IOHelper.closeQuietly(out);
    }
  }

  /**
   * 读取类字节码
   * 
   * @param ci 类信息
   * @return 字节码
   */
  public byte[] readClass(ClassInfo ci){
    ZipFile zip = null;
    try{
      String className = ci.getClassName();
      InputStream in = null;
      if(ci.isInJar()){
        zip = new ZipFile(ci.getHome());
        ZipEntry en = zip.getEntry(className.replace(".","/")+".class");
        in = zip.getInputStream(en);
      }else{
        File classFile = new File(ci.getHome()+File.separator+className.replace(".",File.separator)+".class");
        in = new FileInputStream(classFile);
      }
      return readClassFromStream(in);
    }catch(IOException e){
      throw new NlfException(ci+"",e);
    }finally{
      IOHelper.closeQuietly(zip);
    }
  }

  public List<String> getInterfaces(ClassInfo ci){
    List<String> l = new ArrayList<String>();
    try{
      byte[] byteCodes = readClass(ci);
      Klass klass = new Klass(byteCodes);
      if(klass.isAbstract()){
        ci.setAbstractClass(true);
      }
      Set<String> interfaces = klass.getInterfaces();
      String superClass = klass.getSuperClass();
      if(!"java.lang.Object".equals(superClass)){
        ClassInfo c = Factory.getClass(superClass);
        if(null!=c){
          interfaces.addAll(getInterfaces(c));
        }
      }
      l.addAll(interfaces);
    }catch(IOException e){
      throw new RuntimeException(e);
    }
    return l;
  }
}