package nc.liat6.frame.bytecode;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import nc.liat6.frame.bytecode.attribute.DefaultAttribute;
import nc.liat6.frame.bytecode.attribute.IAttribute;
import nc.liat6.frame.bytecode.attribute.InnerClass;
import nc.liat6.frame.bytecode.attribute.InnerClassAttribute;
import nc.liat6.frame.bytecode.attribute.SourceFileAttribute;
import nc.liat6.frame.bytecode.constant.ClassConstant;
import nc.liat6.frame.bytecode.constant.DefaultConstant;
import nc.liat6.frame.bytecode.constant.FieldConstant;
import nc.liat6.frame.bytecode.constant.IConstant;
import nc.liat6.frame.bytecode.constant.InterfaceMethodConstant;
import nc.liat6.frame.bytecode.constant.MethodConstant;
import nc.liat6.frame.bytecode.constant.NameAndTypeConstant;
import nc.liat6.frame.bytecode.constant.StringConstant;
import nc.liat6.frame.bytecode.constant.UTFConstant;
import nc.liat6.frame.util.IOHelper;
import nc.liat6.frame.util.Mather;

public class Klass{

  private byte[] magic;
  public static String encode = "utf-8";
  private byte[] byteCodes;
  private int minorVersion;
  private int majorVersion;
  private int access;
  private String superClass;
  private String name;
  private List<IConstant> constants = new ArrayList<IConstant>();
  private Set<String> interfaces = new LinkedHashSet<String>();
  private List<Field> fields = new ArrayList<Field>();
  private List<Method> methods = new ArrayList<Method>();
  private List<IAttribute> attributes = new ArrayList<IAttribute>();

  public Klass(byte[] byteCodes) throws IOException{
    this.byteCodes = byteCodes;
    decode();
  }

  public byte[] getByteCodes(){
    return byteCodes;
  }

  public List<IConstant> getConstants(){
    return constants;
  }

  public List<Field> getFields(){
    return fields;
  }

  public List<Method> getMethods(){
    return methods;
  }

  public List<IAttribute> getAttributes(){
    return attributes;
  }


  public IConstant getConstant(int index){
    return constants.get(index);
  }

  public Set<String> getInterfaces(){
    return interfaces;
  }

  public String getSuperClass(){
    return superClass;
  }

  public String getName(){
    return name;
  }

  public boolean isAbstract(){
    return Modifier.isAbstract(access);
  }

  public boolean isInterface(){
    return Modifier.isInterface(access);
  }

  public int getAccess(){
    return access;
  }

  public int getMinorVersion(){
    return minorVersion;
  }

  public int getMajorVersion(){
    return majorVersion;
  }

  public byte[] getMagic(){
    return magic;
  }

  private void decode() throws IOException{
    ByteArrayInputStream stream = null;
    DataInputStream in = null;
    try{
      stream = new ByteArrayInputStream(byteCodes);
      in = new DataInputStream(stream);
      byte[] b = new byte[4];
      in.read(b);// magic
      magic = b;
      b = new byte[2];
      in.read(b);// minor_version
      minorVersion = Mather.toInt(b);
      b = new byte[2];
      in.read(b);// major_version
      majorVersion = Mather.toInt(b);
      b = new byte[2];
      in.read(b);
      int count = Mather.toInt(b);
      constants.add(null);
      for(int i = 1;i<count;i++){
        byte tag = in.readByte();
        IConstant c = new DefaultConstant();
        switch(tag){
          case IConstant.TYPE_CLASS:
            b = new byte[2];
            in.read(b);
            ClassConstant cc = new ClassConstant();
            cc.setData(b);
            cc.setNameIndex(Mather.toInt(b));
            c = cc;
            break;
          case IConstant.TYPE_STRING:
            b = new byte[2];
            in.read(b);
            StringConstant sc = new StringConstant();
            sc.setData(b);
            sc.setStringIndex(Mather.toInt(b));
            c = sc;
            break;
          case IConstant.TYPE_FIELD:
            b = new byte[4];
            in.read(b);
            FieldConstant fc = new FieldConstant();
            fc.setData(b);
            fc.setClassIndex(Mather.toInt(Mather.sub(b,0,1)));
            fc.setNameAndTypeIndex(Mather.toInt(Mather.sub(b,2,3)));
            c = fc;
            break;
          case IConstant.TYPE_METHOD:
            b = new byte[4];
            in.read(b);
            MethodConstant mc = new MethodConstant();
            mc.setData(b);
            mc.setClassIndex(Mather.toInt(Mather.sub(b,0,1)));
            mc.setNameAndTypeIndex(Mather.toInt(Mather.sub(b,2,3)));
            c = mc;
            break;
          case IConstant.TYPE_INTERFACE_METHOD:
            b = new byte[4];
            in.read(b);
            InterfaceMethodConstant ic = new InterfaceMethodConstant();
            ic.setData(b);
            ic.setClassIndex(Mather.toInt(Mather.sub(b,0,1)));
            ic.setNameAndTypeIndex(Mather.toInt(Mather.sub(b,2,3)));
            c = ic;
            break;
          case IConstant.TYPE_NAME_AND_TYPE:
            b = new byte[4];
            in.read(b);
            NameAndTypeConstant nc = new NameAndTypeConstant();
            nc.setData(b);
            nc.setNameIndex(Mather.toInt(Mather.sub(b,0,1)));
            nc.setDescriptorIndex(Mather.toInt(Mather.sub(b,2,3)));
            c = nc;
            break;
          case IConstant.TYPE_UTF:
            b = new byte[2];// data length
            in.read(b);
            int length = Mather.toInt(b);
            b = new byte[length];// data
            in.read(b);
            UTFConstant uc = new UTFConstant();
            uc.setData(b);
            uc.setContent(new String(b,Klass.encode));
            c = uc;
            break;
          case IConstant.TYPE_INT:
          case IConstant.TYPE_FLOAT:
            b = new byte[4];
            in.read(b);
            c.setData(b);
            break;
          case IConstant.TYPE_LONG:
            b = new byte[8];
            in.read(b);
            c.setData(b);
            break;
          case IConstant.TYPE_DOUBLE:
            b = new byte[8];
            in.read(b);
            c.setData(b);
            break;
        }
        c.setIndex(i);
        c.setType(tag);
        constants.add(c);
        if(IConstant.TYPE_LONG==c.getType()||IConstant.TYPE_DOUBLE==c.getType()){
          constants.add(null);
          i++;
        }
      }
      b = new byte[2];// access flags
      in.read(b);
      access = Mather.toInt(b);
      b = new byte[2];// this class
      in.read(b);
      name = getConstant(getConstant(Mather.toInt(b)).toClassConstant().getNameIndex()).toUTFConstant().getContent();
      name = name.replace("/",".");
      b = new byte[2];// super class
      in.read(b);
      superClass = getConstant(getConstant(Mather.toInt(b)).toClassConstant().getNameIndex()).toUTFConstant().getContent();
      superClass = superClass.replace("/",".");
      b = new byte[2];// interface count
      in.read(b);
      int interfaceCount = Mather.toInt(b);
      for(int i = 0;i<interfaceCount;i++){
        b = new byte[2];
        in.read(b);
        String interfaceClass = getConstant(getConstant(Mather.toInt(b)).toClassConstant().getNameIndex()).toUTFConstant().getContent();
        interfaceClass = interfaceClass.replace("/",".");
        interfaces.add(interfaceClass);
      }
      b = new byte[2];// field count
      in.read(b);
      int fieldCount = Mather.toInt(b);
      for(int i = 0;i<fieldCount;i++){
        b = new byte[2];// access
        in.read(b);
        int access = Mather.toInt(b);
        Field f = new Field(this);
        f.setAccess(access);
        b = new byte[2];// name index
        in.read(b);
        f.setNameIndex(Mather.toInt(b));
        b = new byte[2];// desc index
        in.read(b);
        f.setDescripterIndex(Mather.toInt(b));
        b = new byte[2];// attribute count
        in.read(b);
        int attributeCount = Mather.toInt(b);
        for(int j = 0;j<attributeCount;j++){
          IAttribute attr = new DefaultAttribute();
          b = new byte[2];// name index
          in.read(b);
          attr.setNameIndex(Mather.toInt(b));
          b = new byte[4];// data length
          in.read(b);
          int length = Mather.toInt(b);
          b = new byte[length];// data
          in.read(b);
          attr.setData(b);
          f.addAttribute(attr);
        }
        fields.add(f);
      }
      // methods
      b = new byte[2];// method count
      in.read(b);
      int methodCount = Mather.toInt(b);
      for(int i = 0;i<methodCount;i++){
        Method f = new Method(this);
        b = new byte[2];// access
        in.read(b);
        f.setAccess(Mather.toInt(b));
        b = new byte[2];// name index
        in.read(b);
        f.setNameIndex(Mather.toInt(b));
        b = new byte[2];// descriptor index
        in.read(b);
        f.setDescriptorIndex(Mather.toInt(b));
        b = new byte[2];// attribute count
        in.read(b);
        int attributeCount = Mather.toInt(b);
        for(int j = 0;j<attributeCount;j++){
          IAttribute attr = new DefaultAttribute();
          b = new byte[2];// name index
          in.read(b);
          attr.setNameIndex(Mather.toInt(b));
          b = new byte[4];// data length
          in.read(b);
          int length = Mather.toInt(b);
          b = new byte[length];// data
          in.read(b);
          attr.setData(b);
          f.addAttribute(attr);
        }
        methods.add(f);
      }
      // class attributes
      b = new byte[2];// attribute count
      in.read(b);
      int attributeCount = Mather.toInt(b);
      for(int i = 0;i<attributeCount;i++){
        IAttribute attr = new DefaultAttribute();
        b = new byte[2];// name index
        in.read(b);
        attr.setNameIndex(Mather.toInt(b));
        b = new byte[4];// data length
        in.read(b);
        int length = Mather.toInt(b);
        b = new byte[length];// data
        in.read(b);
        attr.setData(b);
        IConstant c = getConstant(attr.getNameIndex());
        switch(c.getType()){
          case IConstant.TYPE_UTF:
            attr.setName(c.toUTFConstant().getContent());
            break;
          case IConstant.TYPE_CLASS:
            attr.setName(getConstant(c.toClassConstant().getIndex()).toUTFConstant().getContent());
            break;
        }
        if("SourceFile".equals(attr.getName())){
          SourceFileAttribute sa = new SourceFileAttribute();
          sa.setNameIndex(attr.getNameIndex());
          sa.setName(attr.getName());
          sa.setData(attr.getData());
          sa.setSourceFileIndex(Mather.toInt(sa.getData()));
          sa.setSourceFileName(getConstant(sa.getSourceFileIndex()).toUTFConstant().getContent());
          attr = sa;
        }else if("InnerClasses".equals(attr.getName())){
          InnerClassAttribute sa = new InnerClassAttribute();
          sa.setNameIndex(attr.getNameIndex());
          sa.setName(attr.getName());
          sa.setData(attr.getData());
          b = Mather.sub(sa.getData(),0,1);
          int innerClassCount = Mather.toInt(b);
          int idx = 2;
          for(int j=0;j<innerClassCount;j++){
            InnerClass ic = new InnerClass();
            b = Mather.sub(sa.getData(),idx,idx+1);
            ic.setInnerClassIndex(Mather.toInt(b));
            idx+=2;
            b = Mather.sub(sa.getData(),idx,idx+1);
            ic.setOuterClassIndex(Mather.toInt(b));
            idx+=2;
            b = Mather.sub(sa.getData(),idx,idx+1);
            ic.setInnerNameIndex(Mather.toInt(b));
            idx+=2;
            b = Mather.sub(sa.getData(),idx,idx+1);
            ic.setInnerAccess(Mather.toInt(b));
            idx+=2;
            ic.setInnerClassName(getConstant(getConstant(ic.getInnerClassIndex()).toClassConstant().getNameIndex()).toUTFConstant().getContent());
            sa.addInnerClass(ic);
          }
          attr = sa;
        }
        attributes.add(attr);
      }
    }finally{
      IOHelper.closeQuietly(in);
      IOHelper.closeQuietly(stream);
    }
  }
}