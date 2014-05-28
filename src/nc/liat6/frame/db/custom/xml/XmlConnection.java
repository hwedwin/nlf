package nc.liat6.frame.db.custom.xml;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.IConnection;
import nc.liat6.frame.db.custom.bean.Table;
import nc.liat6.frame.db.custom.bean.TableInfo;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.setting.ISetting;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.xml.XML;

/**
 * JSON数据库连接
 * @author 6tail
 *
 */
public class XmlConnection implements IConnection{

	/** 连接变量 */
	private ConnVar connVar;

	public ConnVar getConnVar(){
		return connVar;
	}

	public void setConnVar(ConnVar connVar){
		this.connVar = connVar;
	}

	public void close(){

	}

	public void rollback(){
		List<String> l = XmlCache.getTableNames(connVar.getAlias());
		for(String tableName:l){
			ISetting setting = getConnVar().getSetting();
			File dir = new File(setting.getDbName());
			if(!dir.exists() || !dir.isDirectory()){
				dir.mkdirs();
			}
			File file = new File(dir,tableName + ".xml");
			String s = null;
			try{
				s = Stringer.readFromFile(file,"UTF-8");
			}catch(IOException e){
				throw new DaoException(Stringer.print("??",L.get("sql.file_read_error"),file.getAbsolutePath()),e);
			}

			long lastModified = file.lastModified();
			Bean o = XML.toBean(s);
			Table t = new Table();
			List<Bean> rows = o.get("rows");
			t.setRows(rows);
			List<String> cols = o.get("cols");
			t.setCols(cols);
			TableInfo ti = new TableInfo();
			Bean b = o.get("table");
			ti.setName(b.getString("name"));
			ti.setLastModified(lastModified);
			t.setTable(ti);
			XmlCache.update(getConnVar().getAlias(),t);
		}
	}

	public Connection getSqlConnection(){
		throw new DaoException(L.get("sql.conn_not_support"));
	}

	public boolean isSupportsBatchUpdates(){
		return false;
	}

	public void commit(){
		List<String> l = XmlCache.getTableNames(connVar.getAlias());
		for(String tableName:l){
			Logger.getLog().debug(L.get(LocaleFactory.locale,"sql.update_file")+tableName);
			Table t = XmlCache.getTable(connVar.getAlias(),tableName);
			t.getTable().setLastModified(System.currentTimeMillis());
			String rs = XML.toXML(t,false);
			ISetting setting = getConnVar().getSetting();
			File dir = new File(setting.getDbName());
			if(!dir.exists() || !dir.isDirectory()){
				dir.mkdirs();
			}
			File dbFile = new File(dir,tableName + ".xml");
			try{
				Stringer.writeToFile(rs,dbFile,"UTF-8");
			}catch(IOException e){
				throw new DaoException(Stringer.print("??",L.get("sql.file_write_error"),dbFile.getAbsolutePath()),e);
			}
		}
	}

	public boolean isClosed(){
		return false;
	}

}
