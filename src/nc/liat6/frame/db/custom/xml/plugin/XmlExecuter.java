package nc.liat6.frame.db.custom.xml.plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import nc.liat6.frame.db.IXml;
import nc.liat6.frame.db.custom.bean.Table;
import nc.liat6.frame.db.custom.bean.TableInfo;
import nc.liat6.frame.db.custom.xml.XmlCache;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IExecuter;
import nc.liat6.frame.db.setting.ISetting;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.util.Stringer;
import nc.liat6.frame.xml.XML;

public abstract class XmlExecuter implements IXml,IExecuter{

	protected String tableName;
	protected ITemplate template;

	public String getSql(){
		return null;
	}

	public Object[] getParam(){
		return null;
	}

	public void setTemplate(ITemplate template){
		this.template = template;
	}

	public ITemplate getTemplate(){
		return template;
	}

	protected void initTable(String tableName){
		this.tableName = tableName.toUpperCase();
		String alias = template.getConnVar().getAlias();
		ISetting setting = template.getConnVar().getSetting();
		File dir = new File(setting.getDbName());
		if(!dir.exists() || !dir.isDirectory()){
			dir.mkdirs();
		}
		String dbFileName = tableName + ".xml";
		File file = null;
		File[] l = dir.listFiles();
		for(File f:l){
			if(f.getName().equalsIgnoreCase(dbFileName)){
				file = f;
				break;
			}
		}
		if(null == file){
			throw new DaoException(Stringer.print(L.get("sql.table_file_not_found")+"?",new File(dir,dbFileName).getAbsolutePath()));
		}
		long lastModified = file.lastModified();
		boolean needRead = false;
		if(XmlCache.contains(alias,tableName)){
			Table t = XmlCache.getTable(alias,tableName);
			if(lastModified != t.getTable().getLastModified()){
				needRead = true;
			}
		}else{
			needRead = true;
		}

		if(needRead){
			String s = null;
			try{
				s = Stringer.readFromFile(file,"UTF-8");
			}catch(IOException e){
				throw new DaoException(Stringer.print(L.get("sql.file_read_error")+"?",file.getAbsolutePath()),e);
			}

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
			XmlCache.update(alias,t);
		}
	}
}
