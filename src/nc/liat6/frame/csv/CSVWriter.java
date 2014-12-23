package nc.liat6.frame.csv;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.util.IOHelper;

/**
 * CSV格式写入，支持流输出及文件输出
 * 
 * @author 6tail
 * 
 */
public class CSVWriter implements Closeable{
  /** 回车符 */
  public static String CR = "\r";
  /** 换行符 */
  public static String LF = "\n";
  /** 列间隔符 */
  public static final String SPACE = ",";
  /** 双引号 */
  public static final String QUOTE = "\"";
  /** 是否追加 */
  public static boolean APPEND = false;
  private Writer writer;

  public CSVWriter(File file) throws IOException{
    this(file,APPEND);
  }

  public CSVWriter(File file,boolean append) throws IOException{
    this(file,append,Statics.ENCODE);
  }

  public CSVWriter(File file,boolean append,String encode) throws IOException{
    this(new FileOutputStream(file,append),encode);
  }

  public CSVWriter(OutputStream outputStream,String encode) throws IOException{
    this.writer = new BufferedWriter(new OutputStreamWriter(outputStream,encode));
  }

  /**
   * flush
   * 
   * @throws IOException
   */
  public void flush() throws IOException{
    writer.flush();
  }

  /**
   * close
   * 
   */
  public void close(){
    IOHelper.closeQuietly(writer);
  }

  /**
   * 写入一行数据
   * 
   * @param cols 一行数据
   * @throws IOException
   */
  public void writeLine(String[] cols) throws IOException{
    StringBuffer s = new StringBuffer();
    for(int i = 0;i<cols.length;i++){
      String o = cols[i];
      String ro = o;
      if(null==o){
        ro = "";
      }else{
        boolean needQuote = false;
        if(o.contains(QUOTE)){
          ro = o.replace(QUOTE,QUOTE+QUOTE);
          needQuote = true;
        }
        if(o.contains(CR)||o.contains(LF)||o.contains(SPACE)){
          needQuote = true;
        }
        if(needQuote){
          ro = QUOTE+ro+QUOTE;
        }
      }
      s.append(ro);
      if(i<cols.length-1){
        s.append(SPACE);
      }
    }
    s.append(CR);
    s.append(LF);
    writer.write(s.toString());
  }

  /**
   * 写入一行数据
   * 
   * @param cols 一行数据
   * @throws IOException
   */
  public void writeLine(List<String> cols) throws IOException{
    int n = cols.size();
    String[] line = new String[n];
    for(int i = 0;i<n;i++){
      line[i] = cols.get(i);
    }
    writeLine(line);
  }
}