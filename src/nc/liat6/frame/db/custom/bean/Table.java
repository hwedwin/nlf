package nc.liat6.frame.db.custom.bean;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.entity.Bean;

/**
 * ±í
 * @author 6tail
 *
 */
public class Table{
	
	private TableInfo table;
	private List<String> cols = new ArrayList<String>();
	private List<Bean> rows = new ArrayList<Bean>();
	
	public TableInfo getTable(){
		return table;
	}
	public void setTable(TableInfo table){
		this.table = table;
	}
	public List<String> getCols(){
		return cols;
	}
	public void setCols(List<String> cols){
		this.cols = cols;
	}
	public List<Bean> getRows(){
		return rows;
	}
	public void setRows(List<Bean> rows){
		this.rows = rows;
	}
	
	public void addCol(String col){
		cols.add(col.toUpperCase());
	}
	
	public void addRow(Bean row){
		rows.add(row);
	}

}
