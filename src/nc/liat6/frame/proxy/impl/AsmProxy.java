package nc.liat6.frame.proxy.impl;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nc.liat6.frame.Factory;
import nc.liat6.frame.aop.IAopInterceptor;
import nc.liat6.frame.exception.NlfException;
import nc.liat6.frame.klass.ByteCodeReader;
import nc.liat6.frame.klass.ClassInfo;
import nc.liat6.frame.lib.org.objectweb.asm.ClassAdapter;
import nc.liat6.frame.lib.org.objectweb.asm.ClassReader;
import nc.liat6.frame.lib.org.objectweb.asm.ClassWriter;
import nc.liat6.frame.lib.org.objectweb.asm.MethodVisitor;
import nc.liat6.frame.lib.org.objectweb.asm.Opcodes;
import nc.liat6.frame.lib.org.objectweb.asm.Type;
import nc.liat6.frame.proxy.IProxy;

abstract class NormalMethodAdapter{

  final String desc;
  final int access;
  final MethodVisitor mv;
  final Type[] argumentTypes;

  NormalMethodAdapter(MethodVisitor mv,String desc,int access){
    this.mv = mv;
    this.desc = desc;
    this.access = access;
    argumentTypes = Type.getArgumentTypes(this.desc);
  }

  abstract void visitCode();

  void loadArgs(){
    loadArgs(0,argumentTypes.length);
  }

  void loadArgs(final int arg,final int count){
    int index = 1;
    for(int i = 0;i<count;++i){
      Type t = argumentTypes[arg+i];
      loadInsn(t,index);
      index += t.getSize();
    }
  }

  void loadInsn(final Type type,final int index){
    mv.visitVarInsn(type.getOpcode(Opcodes.ILOAD),index);
  }
}

class ChangeToChildConstructorAdapter extends NormalMethodAdapter{

  private String superClassName;

  ChangeToChildConstructorAdapter(MethodVisitor mv,String desc,int access,String superClassName){
    super(mv,desc,access);
    this.superClassName = superClassName;
  }

  void visitCode(){
    mv.visitCode();
    mv.visitVarInsn(Opcodes.ALOAD,0);
    loadArgs();
    mv.visitMethodInsn(Opcodes.INVOKESPECIAL,superClassName,"<init>",desc);
    mv.visitInsn(Opcodes.RETURN);
    mv.visitMaxs(2,2);
    mv.visitEnd();
  }
}

/**
 * ASM代理
 * 
 * @author 6tail
 * 
 */
public class AsmProxy implements IProxy,Opcodes{

  /** 代理类缓存 */
  private static final Map<String,Class<?>> CLASS_POOL = new HashMap<String,Class<?>>();
  private static long id = 0;
  /** AOP拦截器列表 */
  private static final List<IAopInterceptor> ais = new ArrayList<IAopInterceptor>();
  /** 原始对象 */
  private Object oBean;
  private ClassWriter cw;

  public synchronized static long nextId(){
    return id++;
  }

  public void addAopInterceptor(IAopInterceptor ai){
    ais.add(ai);
  }

  private Constructor<?>[] getEffectiveConstructors(Class<?> klass){
    Constructor<?>[] constructors = klass.getDeclaredConstructors();
    List<Constructor<?>> cList = new ArrayList<Constructor<?>>();
    for(int i = 0;i<constructors.length;i++){
      Constructor<?> constructor = constructors[i];
      if(Modifier.isPrivate(constructor.getModifiers()))
        continue;
      cList.add(constructor);
    }
    return cList.toArray(new Constructor[cList.size()]);
  }

  private String convert(Class<?> klass){
    return klass.getName().replace(".","/");
  }

  private String[] convert(Class<?>[] klass){
    String[] s = new String[klass.length];
    for(int i = 0;i<klass.length;i++){
      s[i] = convert(klass[i]);
    }
    return s;
  }

  private String[] getParentInterfaces(Class<?> klass,String add){
    List<String> itfs = new ArrayList<String>();
    Class<?> cls = klass;
    while(Object.class!=cls){
      Class<?>[] its = cls.getInterfaces();
      for(Class<?> it:its){
        String c = convert(it);
        if(!itfs.contains(c)){
          itfs.add(c);
        }
      }
      cls = cls.getSuperclass();
    }
    if(!itfs.contains(add)){
      itfs.add(add);
    }
    String[] r = new String[itfs.size()];
    for(int i = 0;i<itfs.size();i++){
      r[i] = itfs.get(i);
    }
    return r;
  }

  private int getAccess(int modify){
    if(Modifier.isProtected(modify))
      return ACC_PROTECTED;
    if(Modifier.isPublic(modify))
      return ACC_PUBLIC;
    return 0x00;
  }

  private void addField(){
    cw.visitField(ACC_PRIVATE+ACC_STATIC,AOP_INTERCEPTOR_LIST_NAME,"[Ljava/util/List;","[Ljava/util/List<L"+convert(IAopInterceptor.class)+";>;",null).visitEnd();
  }

  private void addConstructor(Class<?> superClass){
    Constructor<?>[] cs = getEffectiveConstructors(superClass);
    for(Constructor<?> constructor:cs){
      String[] expClasses = convert(constructor.getExceptionTypes());
      String desc = Type.getConstructorDescriptor(constructor);
      int access = getAccess(constructor.getModifiers());
      MethodVisitor mv = cw.visitMethod(access,"<init>",desc,null,expClasses);
      new ChangeToChildConstructorAdapter(mv,desc,access,convert(superClass)).visitCode();
    }
  }

  private Class<?> reloadClass(Class<?> cls){
    Class<?> superClass = cls;
    ClassLoader cLoader = Thread.currentThread().getContextClassLoader();
    ProxyClassLoader loader = new ProxyClassLoader(cLoader);
    ByteCodeReader bcr = new ByteCodeReader();
    boolean reload = false;
    byte[] codes = null;
    String className = cls.getName();
    if(Factory.contains(className)){
      ClassInfo ci = Factory.getClass(className);
      if(null!=ci){
        long tm = bcr.lastModified(ci);
        if(bcr.lastModified(ci)!=ci.getLastModify()){
          codes = bcr.readClass(ci);
          ci.setLastModify(tm);
          reload = true;
        }
      }
    }
    if(reload){
      superClass = loader.load(cls.getCanonicalName(),codes);
    }else{
      if(CLASS_POOL.containsKey(className)){
        return CLASS_POOL.get(className);
      }
    }
    Class<?> c = null;
    String suffix = SUFFIX+nextId();
    final ClassWriter ccw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    ccw.visit(V1_5,Opcodes.ACC_PUBLIC|Opcodes.ACC_INTERFACE,"nlfi"+convert(superClass)+suffix,null,"java/lang/Object",null);
    try{
      ClassReader cr = new ClassReader(superClass.getResourceAsStream(superClass.getSimpleName()+".class"));
      ClassWriter qcw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
      cr.accept(new ClassAdapter(qcw){

        @Override
        public MethodVisitor visitMethod(int access,String name,String desc,String signature,String[] exceptions){
          if(!"<init>".equals(name)&&Opcodes.ACC_PUBLIC==access){
            MethodVisitor mmv = ccw.visitMethod(Opcodes.ACC_PUBLIC|Opcodes.ACC_ABSTRACT,name,desc,signature,exceptions);
            mmv.visitCode();
            mmv.visitEnd();
          }
          return super.visitMethod(access,name,desc,signature,exceptions);
        }
      },0);
    }catch(IOException e){
      throw new NlfException(e);
    }
    ccw.visitEnd();
    loader.load("nlfi"+className+suffix,ccw.toByteArray());
    cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    cw.visit(V1_5,ACC_PUBLIC,convert(superClass)+suffix,null,convert(superClass),getParentInterfaces(superClass,"nlfi"+convert(superClass)+suffix));
    addField();
    addConstructor(superClass);
    cw.visitEnd();
    byte[] bytes = cw.toByteArray();
    c = loader.load(className+suffix,bytes);
    CLASS_POOL.put(className,c);
    return c;
  }

  @SuppressWarnings("unchecked")
  public <T>T create(Class<?> superClass){
    Class<?> c = reloadClass(superClass);
    try{
      oBean = c.newInstance();
    }catch(InstantiationException e){
      throw new NlfException(e);
    }catch(IllegalAccessException e){
      throw new NlfException(e);
    }
    return (T)oBean;
  }

  @SuppressWarnings("unchecked")
  public <T>T getOBean(){
    return (T)oBean;
  }
}
