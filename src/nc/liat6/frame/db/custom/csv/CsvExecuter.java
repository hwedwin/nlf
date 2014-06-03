package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IExecuter;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * CSV执行器
 * @author 6tail
 *
 */
public abstract class CsvExecuter implements ICsv,IExecuter{

	/**表名*/
	protected String tableName;
	protected ITemplate template;
	protected List<Object> params = new ArrayList<Object>();

	public Object[] getParam(){
		return params.toArray();
	}
	
	public String getSql(){
		return null;
	}

	public void setTemplate(ITemplate template){
		this.template = template;
	}

	public ITemplate getTemplate(){
		return template;
	}

	/**
	 * 初始化表
	 * @param tableName 表名
	 */
	protected void initTable(String tableName){
		tableName = tableName.toUpperCase();
		this.tableName = tableName;
		getTableFile();
	}
	
	/**
	 * 获取表文件，如果不存在，自动创建
	 * @return 表文件
	 */
	protected File getTableFile(){
		File dir = new File(template.getConnVar().getSetting().getDbName());
		File file = new File(dir,tableName+".csv");
		if(!file.exists()){
			try{
				file.createNewFile();
				Logger.getLog().debug(L.get("sql.table_created")+file.getAbsolutePath());
			}catch(IOException e){
				throw new DaoException(L.get("sql.file_write_error")+file.getAbsolutePath(),e);
			}
		}
		return file;
	}
	
	/**
	 * 可变长参数转List，Object数组也会转入
	 * @param value Object或者Object数组
	 * @return List
	 */
	protected List<Object> objectsToList(Object... value){
		List<Object> l = new ArrayList<Object>();
		for(int i = 0;i < value.length;i++){
			if (value[i] instanceof Object[]) {
				Object[] ps = (Object[]) value[i];
				for(Object p:ps){
					l.add(p);
				}
			}else{
				l.add(value[i]);
			}
		}
		return l;
	}
	
	/**
	 * 读取列名
	 * @param cr
	 * @return 列名
	 * @throws IOException 
	 */
	protected String[] readHead(CSVFileReader cr,File file) throws IOException{
		if(null == tableName){
			throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
		}
		String[] head = null;
		try{
			if(cr.getLineCount()>0){
				head = cr.getLine(0);
			}
		}catch(IOException e){
			throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath(),e);
		}
		if(null==head){
			throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath());
		}
		return head;
	}
	
}
