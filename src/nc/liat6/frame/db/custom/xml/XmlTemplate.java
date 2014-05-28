package nc.liat6.frame.db.custom.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.IXml;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.custom.bean.Table;
import nc.liat6.frame.db.custom.bean.TableInfo;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.ISetting;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.xml.XML;

/**
 * XML执行模板
 * @author 6tail
 *
 */
public class XmlTemplate implements ITemplate,IXml{
	
	/** 当前连接变量 */
	protected ConnVar cv;

	public Object[] one(String sql){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public Object[] one(String sql,Object param){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public List<Object[]> query(String sql){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public List<Object[]> query(String sql,Object param){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public List<Bean> queryEntity(String sql){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public List<Bean> queryEntity(String sql,Object param){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public Bean oneEntity(String sql){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public Bean oneEntity(String sql,Object param){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public int count(String sql){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public int count(String sql,Object param){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public int update(String sql){
		//create table xxx(A,B)
		String s = sql.trim().toUpperCase();
		if(s.startsWith("CREATE ")){
			String q = Stringer.cut(s," ").trim();//table xxx(A,B)
			if(q.startsWith("TABLE")){
				q = Stringer.cut(q," ").trim();//xxx(A,B)
				String tableName = Stringer.cut(q,"","(");//xxx
				
				ISetting setting = getConnVar().getSetting();
				File dir = new File(setting.getDbName());
				if(!dir.exists() || !dir.isDirectory()){
					dir.mkdirs();
				}
				File dbFile = new File(dir,tableName + ".xml");
				if(dbFile.exists()){
					throw new DaoException(L.get("sql.table_exists")+tableName);
				}
				
				q = Stringer.cut(q,"(",")").trim();//A,B
				String[] cs = q.split(",");
				List<String> cols = new ArrayList<String>();
				for(String c:cs){
					c = c.trim().toUpperCase();
					if(c.length()>0){
						cols.add(c);
					}
				}
				
				Table t = new Table();
				t.setCols(cols);
				t.setRows(new ArrayList<Bean>());
				TableInfo ti = new TableInfo();
				ti.setName(tableName.toUpperCase());
				ti.setLastModified(System.currentTimeMillis());
				t.setTable(ti);
				String rs = XML.toXML(t,false);
				
				try{
					Stringer.writeToFile(rs,dbFile,"UTF-8");
				}catch(IOException e){
					throw new DaoException(Stringer.print("??",L.get("sql.file_write_error"),dbFile.getAbsolutePath()),e);
				}
				Logger.getLog().debug(L.get(LocaleFactory.locale,"sql.table_created")+tableName);
			}
		}else if(s.startsWith("DROP ")){
			String q = Stringer.cut(s," ").trim();//table xxx
			if(q.startsWith("TABLE")){
				String tableName = Stringer.cut(q," ");
				tableName = tableName.replace(";","").trim();
				ISetting setting = getConnVar().getSetting();
				File dir = new File(setting.getDbName());
				if(!dir.exists() || !dir.isDirectory()){
					dir.mkdirs();
				}
				String dbFileName = tableName + ".xml";
				File[] l = dir.listFiles();
				for(File f:l){
					if(f.getName().equalsIgnoreCase(dbFileName)){
						f.delete();
						Logger.getLog().debug(L.get(LocaleFactory.locale,"sql.table_dropped")+tableName);
						break;
					}
				}
			}
		}else{
			throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
		}
		return 0;
	}

	public int update(String sql,Object param){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public PageData query(String sql,int pageNumber,int pageSize){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public PageData query(String sql,int pageNumber,int pageSize,Object param){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public PageData queryEntity(String sql,int pageNumber,int pageSize){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public PageData queryEntity(String sql,int pageNumber,int pageSize,Object param){
		throw new DaoException(L.get("sql.sql_not_support")+cv.getDbType());
	}

	public ConnVar getConnVar(){
		return cv;
	}

	public int[] flush(){
		return new int[]{};
	}

	public void setAlias(String alias){
		List<ConnVar> l = Context.get(Statics.CONNECTIONS);
		for(ConnVar n:l){
			if(n.getAlias().equals(alias)){
				cv = n;
				break;
			}
		}
	}

	public void call(String procName,Object param){
		throw new DaoException(L.get("sql.proc_not_support")+cv.getDbType());
	}

	public void call(String procName){
		throw new DaoException(L.get("sql.proc_not_support")+cv.getDbType());
	}

}
