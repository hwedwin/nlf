package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.csv.CSVWriter;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * CSVÉ¾³ýÆ÷
 * 
 * @author 6tail
 * 
 */
public class CsvDeleter extends CsvExecuter implements IDeleter{

	protected List<Rule> conds = new ArrayList<Rule>();

	public IDeleter table(String tableName){
		initTable(tableName);
		return this;
	}

	public IDeleter where(String sql){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public IDeleter whereSql(String sql,Object[] values){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public IDeleter where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("=");
		r.setOpEnd("");
		r.setTag("");
		conds.add(r);
		params.add(value);
		return this;
	}

	public IDeleter whereIn(String column,Object... value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereIn"));
		return where(column,value);
	}

	public IDeleter whereNotIn(String column,Object... value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNotIn"));
		return where(column,value);
	}

	public int delete(){
		if(null == tableName){
			throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
		}
		File file = getTableFile();
		CSVFileReader cr = new CSVFileReader(file);
		String[] head = null;
		try{
			if(cr.getLineCount() > 0){
				head = cr.getLine(0);
			}
		}catch(IOException e){
			throw new DaoException(L.get("sql.file_read_error") + file.getAbsolutePath(),e);
		}
		if(null == head){
			throw new DaoException(L.get("sql.file_read_error") + file.getAbsolutePath());
		}
		int deleted = 0;
		try{
			File f = new File(file.getAbsolutePath() + ".tmp");
			CSVWriter cw = new CSVWriter(f);
			cw.writeLine(head);
			if(conds.size() > 0){
				for(int i = 1;i < cr.getLineCount();i++){
					String[] data = cr.getLine(i);
					boolean cond = true;
					//Ìõ¼þ
					outer:for(int j = 0;j < conds.size();j++){
						Rule r = conds.get(j);
						for(int k=0;k<head.length;k++){
							if(head[k].equalsIgnoreCase(r.getColumn())){
								if("=".equals(r.getOpStart())){
									if(!(data[k] + "").equals("" + params.get(j))){
										cond = false;
										break outer;
									}
								}
							}
						}
					}
					if(!cond){
						cw.writeLine(data);
					}
				}
			}
			cr.close();
			cw.flush();
			cw.close();
			
			cw = new CSVWriter(file);
			cr = new CSVFileReader(f);
			for(int i = 0;i < cr.getLineCount();i++){
				String[] data = cr.getLine(i);
				cw.writeLine(data);
			}
			cr.close();
			cw.flush();
			cw.close();
			f.delete();
		}catch(IOException e){
			throw new DaoException(L.get("sql.file_write_error") + file.getAbsolutePath(),e);
		}
		reset();
		return deleted;
	}

	public void reset(){
		conds.clear();
		params.clear();
	}

}
