package nc.liat6.frame.klass;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import nc.liat6.frame.Factory;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.lib.org.objectweb.asm.AnnotationVisitor;
import nc.liat6.frame.lib.org.objectweb.asm.Attribute;
import nc.liat6.frame.lib.org.objectweb.asm.ClassReader;
import nc.liat6.frame.lib.org.objectweb.asm.ClassVisitor;
import nc.liat6.frame.lib.org.objectweb.asm.FieldVisitor;
import nc.liat6.frame.lib.org.objectweb.asm.MethodVisitor;
import nc.liat6.frame.lib.org.objectweb.asm.Opcodes;

class InterfaceVisitor implements ClassVisitor{

  private ClassInfo classInfo;
  private List<String> interfaces = new ArrayList<String>();

  public InterfaceVisitor(ClassInfo ci){
    classInfo = ci;
  }

  public List<String> getInterfaces(){
    return interfaces;
  }

  public void visit(int version,int access,String name,String signature,String superName,String[] interfaces){
    if(Modifier.isAbstract(access)){
      classInfo.setAbstractClass(true);
    }
    for(String s:interfaces){
      this.interfaces.add(s.replace("/","."));
    }
    ClassInfo ci = Factory.getClass(superName.replace("/","."));
    if(null==ci){
      return;
    }
    this.interfaces.addAll(new ByteCodeReader().getInterfaces(ci));
  }

  public void visitSource(String source,String debug){}

  public void visitOuterClass(String owner,String name,String desc){}

  public AnnotationVisitor visitAnnotation(String desc,boolean visible){
    return null;
  }

  public void visitAttribute(Attribute attr){}

  public void visitInnerClass(String name,String outerName,String innerName,int access){}

  public FieldVisitor visitField(int access,String name,String desc,String signature,Object value){
    return null;
  }

  public MethodVisitor visitMethod(int access,String name,String desc,String signature,String[] exceptions){
    return null;
  }

  public void visitEnd(){}
}

public class ByteCodeReader implements Opcodes{

  /**
   * 获取类文件的最后修改时间
   * 
   * @param ci 类信息
   * @return 最好修改时间
   */
  public long lastModified(ClassInfo ci){
    String className = ci.getClassName();
    if(ci.isInJar()){
      try{
        ZipFile zip = new ZipFile(ci.getHome());
        ZipEntry en = zip.getEntry(ci.getClassName().replace(".","/")+".class");
        return en.getTime();
      }catch(IOException e){
        throw new NlfException(ci+"",e);
      }
    }else{
      File classFile = new File(ci.getHome()+File.separator+className.replace(".",File.separator)+".class");
      return classFile.lastModified();
    }
  }

  /**
   * 从输入流读取字节码
   * 
   * @param in 输入流
   * @return 字节码
   * @throws IOException
   */
  private byte[] readClassFromStream(InputStream in) throws IOException{
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    try{
      int r = 0;
      while((r = in.read())!=-1){
        buffer.write(r);
      }
      return buffer.toByteArray();
    }finally{
      try{
        in.close();
      }catch(Exception e){}
      try{
        buffer.close();
      }catch(Exception e){}
    }
  }

  /**
   * 读取类字节码
   * 
   * @param ci 类信息
   * @return 字节码
   */
  public byte[] readClass(ClassInfo ci){
    try{
      String className = ci.getClassName();
      InputStream in = null;
      if(ci.isInJar()){
        ZipFile zip = new ZipFile(ci.getHome());
        ZipEntry en = zip.getEntry(className.replace(".","/")+".class");
        in = zip.getInputStream(en);
      }else{
        File classFile = new File(ci.getHome()+File.separator+className.replace(".",File.separator)+".class");
        in = new FileInputStream(classFile);
      }
      return readClassFromStream(in);
    }catch(IOException e){
      throw new NlfException(ci+"",e);
    }
  }

  public List<String> getInterfaces(ClassInfo ci){
    InputStream in = null;
    try{
      if(ci.isInJar()){
        ZipFile zip = new ZipFile(ci.getHome());
        ZipEntry en = zip.getEntry(ci.getClassName().replace(".","/")+".class");
        in = zip.getInputStream(en);
      }else{
        in = new FileInputStream(new File(ci.getHome(),ci.getClassName().replace(".",File.separator)+".class"));
      }
      ClassReader cr = new ClassReader(in);
      InterfaceVisitor iv = new InterfaceVisitor(ci);
      cr.accept(iv,0);
      return iv.getInterfaces();
    }catch(IOException e){
      throw new NlfException(ci+"",e);
    }finally{
      try{
        in.close();
      }catch(Exception e){}
    }
  }
}
