package nc.liat6.frame.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * CSV格式写入，支持流输出及文件输出
 * @author 6tail
 *
 */
public class CSVWriter{

	/** 回车符 */
	public static String CR = "\r";

	/** 换行符 */
	public static String LF = "\n";

	/** 列间隔符 */
	public static final String SPACE = ",";

	/** 双引号 */
	public static final String QUOTE = "\"";

	private BufferedWriter writer;

	public CSVWriter(OutputStreamWriter writer){
		this.writer = new BufferedWriter(writer);
	}

	public CSVWriter(File file) throws IOException{
		this(new FileWriter(file));
	}

	/**
	 * flush
	 * @throws IOException
	 */
	public void flush() throws IOException{
		writer.flush();
	}

	/**
	 * close
	 * @throws IOException
	 */
	public void close() throws IOException{
		writer.close();
	}

	/**
	 * 写入一行数据
	 * @param cols 一行数据
	 * @throws IOException
	 */
	public void writeLine(String[] cols) throws IOException{
		StringBuffer s = new StringBuffer();
		for(int i = 0;i < cols.length;i++){
			String o = cols[i];
			String ro = o;
			if(null == o){
				ro = "";
			}else{
				boolean needQuote = false;
				if(o.contains(QUOTE)){
					ro = o.replace(QUOTE,QUOTE + QUOTE);
					needQuote = true;
				}
				if(o.contains(CR) || o.contains(LF) || o.contains(SPACE)){
					needQuote = true;
				}
				if(needQuote){
					ro = QUOTE + ro + QUOTE;
				}
			}

			s.append(ro);
			if(i < cols.length - 1){
				s.append(SPACE);
			}
		}
		s.append(CR);
		s.append(LF);
		writer.write(s.toString());
	}

	/**
	 * 写入一行数据
	 * @param cols 一行数据
	 * @throws IOException
	 */
	public void writeLine(List<String> cols) throws IOException{
		String[] line = new String[cols.size()];
		for(int i = 0;i < cols.size();i++){
			line[i] = cols.get(i);
		}
		writeLine(line);
	}

}
