package nc.liat6.frame.csv;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * CSV文件读取
 * 
 * @author 6tail
 * 
 */
public class CSVFileReader{

	/** 文件行数，是按CSV格式解析后的行数，不一定是文本行数 */
	private int lineCount = -1;

	/** 解析到的当前行数 */
	private int lineNumber = 0;

	/** 读取的文件 */
	private File file;

	/** CSV输入流读取 */
	private CSVReader reader = null;

	public CSVFileReader(File file){
		this.file = file;
	}

	/**
	 * 获取CSV文件中的数据行数，因数据可能有换行，故这里的行数不一定与文本行数相同
	 * @return 行数
	 * @throws IOException
	 */
	public int getLineCount() throws IOException{
		if(-1 != lineCount){
			return lineCount;
		}
		int n = lineNumber;
		if(null == reader){
			n = 0;
			reader = new CSVReader(new FileReader(file));
		}
		try{
			while(null != reader.readLine()){
				n++;
			}
			lineCount = n;
			return n;
		}finally{
			if(null != reader){
				try{
					reader.close();
				}catch(Exception e){}
				reader = null;
			}
			lineNumber = 0;
		}
	}

	/**
	 * 读取指定行的数据，已优化续读算法以提高效率
	 * @param index 行数，从0开始
	 * @return 行数据
	 * @throws IOException
	 */
	public String[] getLine(int index) throws IOException{
		if(null != reader && index < lineNumber){
			reader.close();
			reader = null;
		}
		if(null == reader){
			lineNumber = 0;
			reader = new CSVReader(new FileReader(file));
		}
		String[] line;
		while(null != (line = reader.readLine())){
			if(lineNumber == index){
				lineNumber++;
				return line;
			}
			lineNumber++;
		}
		return null;
	}
	
	public void close() throws IOException{
		if(null!=reader){
			reader.close();
			reader =  null;
		}
	}

}
