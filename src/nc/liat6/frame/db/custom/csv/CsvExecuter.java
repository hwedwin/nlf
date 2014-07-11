package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.impl.SuperExecuter;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * CSV执行器
 * 
 * @author 6tail
 * 
 */
public abstract class CsvExecuter extends SuperExecuter implements ICsv{

  /** 表名 */
  protected String tableName;

  public String getSql(){
    return null;
  }
  
  @Override
  protected void reset(){
    super.reset();
    tableName = null;
  }

  /**
   * 初始化表
   * 
   * @param tableName 表名
   */
  protected void initTable(String tableName){
    tableName = tableName.toUpperCase();
    this.tableName = tableName;
    getTableFile();
  }

  /**
   * 获取表文件，如果不存在，自动创建
   * 
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
   * 读取列名
   * 
   * @param cr
   * @return 列名
   * @throws IOException
   */
  protected String[] readHead(CSVFileReader cr,File file) throws IOException{
    if(null==tableName){
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
