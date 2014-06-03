package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.csv.CSVWriter;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * CSV更新器
 * 
 * @author 6tail
 * 
 */
public class CsvUpdater extends CsvExecuter implements IUpdater{

	protected List<Rule> cols = new ArrayList<Rule>();
	protected List<Rule> wheres = new ArrayList<Rule>();
	protected List<Object> paramCols = new ArrayList<Object>();
	protected List<Object> paramWheres = new ArrayList<Object>();

	public IUpdater table(String tableName){
		initTable(tableName);
		return this;
	}

	public IUpdater where(String sql){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public IUpdater whereSql(String sql,Object[] values){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.cond_not_support"),sql));
		return this;
	}

	public IUpdater where(String column,Object value){
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("=");
		r.setOpEnd("");
		r.setTag("");
		wheres.add(r);
		paramWheres.add(value);
		return this;
	}

	public IUpdater whereLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLike"));
		return where(column,value);
	}

	public IUpdater whereLeftLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereLeftLike"));
		return where(column,value);
	}

	public IUpdater whereRightLike(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereRightLike"));
		return where(column,value);
	}

	public IUpdater whereNq(String column,Object value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNq"));
		return where(column,value);
	}

	public IUpdater whereIn(String column,Object... value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereIn"));
		return where(column,value);
	}

	public IUpdater whereNotIn(String column,Object... value){
		Logger.getLog().warn(Stringer.print("?? ?",L.get(LocaleFactory.locale,"sql.cond_not_support"),column,"whereNotIn"));
		return where(column,value);
	}

	public IUpdater set(String column,Object value){
		// 如果有重复的，替换值
		for(int i = 0;i < cols.size();i++){
			if(cols.get(i).getColumn().equals(column)){
				paramCols.set(i,value);
				return this;
			}
		}
		Rule r = new Rule();
		r.setColumn(column);
		r.setOpStart("");
		r.setOpEnd("");
		r.setTag("");
		cols.add(r);
		paramCols.add(value);
		return this;
	}

	public IUpdater set(String sql){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.update_not_support"),sql));
		return this;
	}

	public IUpdater setSql(String sql,Object[] values){
		Logger.getLog().warn(Stringer.print("??",L.get(LocaleFactory.locale,"sql.update_not_support"),sql));
		return this;
	}

	public int update(){
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
		int updated = 0;
		try{
			File f = new File(file.getAbsolutePath() + ".tmp");
			CSVWriter cw = new CSVWriter(f);
			cw.writeLine(head);
			if(wheres.size() > 0){
				for(int i = 1;i < cr.getLineCount();i++){
					String[] data = cr.getLine(i);
					boolean cond = true;
					//条件
					outer:for(int j = 0;j < wheres.size();j++){
						Rule r = wheres.get(j);
						for(int k=0;k<head.length;k++){
							if(head[k].equalsIgnoreCase(r.getColumn())){
								if("=".equals(r.getOpStart())){
									if(!(data[k] + "").equals("" + paramWheres.get(j))){
										cond = false;
										break outer;
									}
								}
							}
						}
					}
					if(cond){
						for(int j = 0;j < cols.size();j++){
							Rule r = cols.get(j);
							for(int k=0;k<head.length;k++){
								if(head[k].equalsIgnoreCase(r.getColumn())){
									data[k] = paramCols.get(j)+"";
								}
							}
						}
						updated++;
					}
					cw.writeLine(data);
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
		return updated;
	}

	public void reset(){
		cols.clear();
		wheres.clear();
		paramCols.clear();
		paramWheres.clear();
		params.clear();
	}

	public IUpdater setBean(Bean bean){
		for(String col:bean.keySet()){
			set(col,bean.get(col));
		}
		return this;
	}

}
