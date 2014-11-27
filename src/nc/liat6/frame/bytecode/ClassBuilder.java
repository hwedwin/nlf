package nc.liat6.frame.bytecode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.bytecode.constant.IConstant;
import nc.liat6.frame.util.IOHelper;
import nc.liat6.frame.util.Mather;

public class ClassBuilder{
  private Klass klass;
  private List<String> interfaces = new ArrayList<String>();

  public ClassBuilder(Klass klass){
    this.klass = klass;
  }

  public Klass getKlass(){
    return klass;
  }

  public void setKlass(Klass klass){
    this.klass = klass;
  }
  
  public void addInterface(String interfaceName){
    if(klass.getInterfaces().contains(interfaceName)){
      return;
    }
    interfaces.add(interfaceName);
  }
  
  public byte[] generate() throws IOException{
    ByteArrayInputStream ins = null;
    DataInputStream in = null;
    ByteArrayOutputStream outs = null;
    DataOutputStream out = null;
    try{
      ins = new ByteArrayInputStream(klass.getByteCodes());
      in = new DataInputStream(ins);
      outs = new ByteArrayOutputStream();
      out = new DataOutputStream(outs);
      byte[] b = new byte[4];// magic
      in.read(b);
      out.write(b);
      b = new byte[2];// minor_version
      in.read(b);
      out.write(b);
      b = new byte[2];// major_version
      in.read(b);
      out.write(b);
      b = new byte[2];//constant count
      in.read(b);
      int count = Mather.toInt(b);
      out.write(Mather.toByte(count+interfaces.size()*2,2));
      for(int i = 1;i<count;i++){
        byte tag = in.readByte();
        out.write(tag);
        byte[] data;
        switch(tag){
          case IConstant.TYPE_CLASS:
          case IConstant.TYPE_STRING:
            data = new byte[2];
            in.read(data);
            out.write(data);
            break;
          case IConstant.TYPE_INT:
          case IConstant.TYPE_FLOAT:
            data = new byte[4];
            in.read(data);
            out.write(data);
            break;
          case IConstant.TYPE_LONG:
          case IConstant.TYPE_DOUBLE:
            data = new byte[8];
            in.read(data);
            out.write(data);
            i++;
            break;
          case IConstant.TYPE_FIELD:
          case IConstant.TYPE_METHOD:
          case IConstant.TYPE_INTERFACE_METHOD:
          case IConstant.TYPE_NAME_AND_TYPE:
            data = new byte[4];
            in.read(data);
            out.write(data);
            break;
          case IConstant.TYPE_UTF:
            data = new byte[2];
            in.read(data);
            out.write(data);
            int len = Mather.toInt(data);
            data = new byte[len];
            in.read(data);
            out.write(data);
            break;
        }
      }
      for(int i=0;i<interfaces.size();i++){
        out.write(Mather.toByte(IConstant.TYPE_CLASS,1));
        out.write(Mather.toByte(count+2*i+1,2));
        out.write(Mather.toByte(IConstant.TYPE_UTF,1));
        byte[] data = interfaces.get(i).replace(".","/").getBytes(Klass.encode);
        out.write(Mather.toByte(data.length,2));
        out.write(data);
      }
      b = new byte[2];//access
      in.read(b);
      out.write(b);
      b = new byte[2];//class
      in.read(b);
      out.write(b);
      
      b = new byte[2];//super class
      in.read(b);
      out.write(b);
      
      b = new byte[2];//interface count
      in.read(b);
      int interfaceCount = Mather.toInt(b);
      out.write(Mather.toByte(interfaceCount+interfaces.size(),2));
      for(int i = 0;i<interfaceCount;i++){
        b = new byte[2];
        in.read(b);
        out.write(b);
      }
      for(int i=0;i<interfaces.size();i++){
        out.write(Mather.toByte(count+i*2,2));
      }
      
      b = new byte[2];//field count
      in.read(b);
      out.write(b);
      int fieldCount = Mather.toInt(b);
      for(int i = 0;i<fieldCount;i++){
        b = new byte[2];//access
        in.read(b);
        out.write(b);
        //name index
        b = new byte[2];
        in.read(b);
        out.write(b);
        
        //desc index
        b = new byte[2];
        in.read(b);
        out.write(b);

        //attribute count
        b = new byte[2];
        in.read(b);
        out.write(b);
        int attributeCount = Mather.toInt(b);
        for(int j = 0;j<attributeCount;j++){
          //name index
          b = new byte[2];
          in.read(b);
          out.write(b);
          //data length
          b = new byte[4];
          in.read(b);
          out.write(b);
          int length = Mather.toInt(b);
          //value index
          b = new byte[length];
          in.read(b);
          out.write(b);
        }
      }
      // method count
      b = new byte[2];
      in.read(b);
      out.write(b);
      int methodCount = Mather.toInt(b);
      for(int i = 0;i<methodCount;i++){
        //access
        b = new byte[2];
        in.read(b);
        out.write(b);
        //name index
        b = new byte[2];
        in.read(b);
        out.write(b);
        //desc index
        b = new byte[2];
        in.read(b);
        out.write(b);
        //attribute count
        b = new byte[2];
        in.read(b);
        out.write(b);
        int attributeCount = Mather.toInt(b);
        for(int j = 0;j<attributeCount;j++){
          //name index
          b= new byte[2];
          in.read(b);
          out.write(b);
          //data length
          b = new byte[4];
          in.read(b);
          out.write(b);
          int length = Mather.toInt(b);
          b = new byte[length];
          in.read(b);
          out.write(b);
        }
      }
      // class attributes
      b = new byte[2];
      in.read(b);
      out.write(b);
      int attributeCount = Mather.toInt(b);
      for(int i = 0;i<attributeCount;i++){
        //name index
        b = new byte[2];
        in.read(b);
        out.write(b);
        //data length
        b = new byte[4];
        in.read(b);
        out.write(b);
        int length = Mather.toInt(b);
        b = new byte[length];
        in.read(b);
        out.write(b);
      }
      out.flush();
      return outs.toByteArray();
    }finally{
      IOHelper.closeQuietly(in);
      IOHelper.closeQuietly(ins);
      IOHelper.closeQuietly(out);
      IOHelper.closeQuietly(outs);
    }
  }
}