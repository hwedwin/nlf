package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IExecuter;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;

/**
 * CSVÖ´ÐÐÆ÷
 * @author 6tail
 *
 */
public abstract class CsvExecuter implements ICsv,IExecuter{

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

	protected void initTable(String tableName){
		tableName = tableName.toUpperCase();
		this.tableName = tableName;
		File dir = new File(template.getConnVar().getSetting().getDbName());
		File file = new File(dir,tableName+".csv");
		if(!file.exists()){
			try{
				file.createNewFile();
			}catch(IOException e){
				throw new DaoException(L.get("sql.file_write_error")+file.getAbsolutePath(),e);
			}
		}
	}
	
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
	
}
