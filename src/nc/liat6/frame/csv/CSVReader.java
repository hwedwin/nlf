package nc.liat6.frame.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV输入流读取
 * @author 6tail
 *
 */
public class CSVReader{

	/** 回车符 */
	public static String CR = "\r";

	/** 换行符 */
	public static String LF = "\n";

	/** 列间隔符 */
	public static final String SPACE = ",";

	/** 双引号 */
	public static final String QUOTE = "\"";

	private BufferedReader reader;

	/** 缓存 */
	private StringBuffer buffer = new StringBuffer();
	
	/** 标识数据内容是否包含在引号之间 */
	private boolean quoted = false;
	
	public CSVReader(InputStreamReader reader){
		this.reader = new BufferedReader(reader);
	}

	/**
	 * close
	 * @throws IOException
	 */
	public void close() throws IOException{
		reader.close();
	}

	/**
	 * 按间隔符拆分字符串
	 * @param s 字符串
	 * @param sp 间隔符
	 * @return 拆分后的列表
	 */
	private List<String> split(String s,String sp){
		List<String> l = new ArrayList<String>();
		String r = s;
		while(r.contains(sp)){
			int space = r.indexOf(sp);
			l.add(r.substring(0,space));
			r = r.substring(space + sp.length());
		}
		l.add(r);
		return l;
	}

	/**
	 * 按照CSV格式规范将拆散的本来是一列的数据合并
	 * @param segs 拆散的列
	 * @return 合并后的列
	 */
	private List<String> combin(List<String> segs){
		List<String> l = new ArrayList<String>();
		for(String o:segs){
			String t = o.replace(QUOTE + QUOTE,"");
			if(t.startsWith(QUOTE)){
				if(!quoted){
					quoted = true;
					buffer.append(o);
					if(t.endsWith(QUOTE)){
						if(!t.equals(QUOTE)){
							l.add(buffer.toString());
							buffer.delete(0,buffer.length());
							quoted = false;
						}
					}
				}else{
					if(t.equals(QUOTE)){
						buffer.append(SPACE);
						buffer.append(o);
						l.add(buffer.toString());
						buffer.delete(0,buffer.length());
						quoted = false;
					}else{
						l.add(buffer.toString());
						buffer.delete(0,buffer.length());
						buffer.append(o);
						quoted = true;
					}
				}
			}else if(t.endsWith(QUOTE)){
				if(quoted){
					buffer.append(SPACE);
					buffer.append(o);
					l.add(buffer.toString());
					buffer.delete(0,buffer.length());
					quoted = false;
				}else{
					l.add(o);
				}
			}else{
				if(quoted){
					buffer.append(SPACE);
					buffer.append(o);
				}else{
					l.add(o);
				}
			}
		}
		return l;
	}

	/**
	 * 读取下一行
	 * @return 一行数据，如果没有下一行，返回null
	 * @throws IOException
	 */
	public String[] readLine() throws IOException{
		buffer.delete(0,buffer.length());
		quoted = false;
		String line = reader.readLine();
		if(null == line){
			return null;
		}
		List<String> l = new ArrayList<String>();
		String r = line;
		if(!r.contains(QUOTE)){
			l.addAll(split(r,SPACE));
		}else{
			String t = r.replace(QUOTE + QUOTE,"");
			int count = t.length()-t.replace(QUOTE,"").length();
			while(count%2==1){
				String nextLine = reader.readLine();
				if(null==nextLine){
					nextLine = "\"";
				}
				r = r +CR+LF+nextLine;
				String nt = nextLine.replace(QUOTE + QUOTE,"");
				int ncount = nt.length()-nt.replace(QUOTE,"").length();
				count += ncount;
			}
			
			List<String> segs = split(r,SPACE);
			l.addAll(combin(segs));
		}
		String[] cols = new String[l.size()];
		for(int i = 0;i < l.size();i++){
			String col = l.get(i);
			if(col.equals(QUOTE)){
				col = "";
			}else if(col.equals(QUOTE+QUOTE)){
				col = "";
			}else	if(col.startsWith(QUOTE) && col.endsWith(QUOTE)){
				col = col.replace(QUOTE + QUOTE,QUOTE);
				col = col.substring(QUOTE.length());
				col = col.substring(0,col.length() - QUOTE.length());
			}
			cols[i] = col;
		}
		return cols;
	}

}
