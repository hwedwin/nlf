package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.csv.CSVWriter;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.json.JSON;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * CSV插入器
 * 
 * @author 6tail
 * 
 */
public class CsvInserter extends CsvExecuter implements IInserter{

  private Bean row = new Bean();

  public IInserter table(String tableName){
    initTable(tableName);
    return this;
  }

  public IInserter set(String column,Object value){
    row.set(column,value);
    return this;
  }

  public IInserter setSql(String column,String valueSql){
    Logger.getLog().warn(Stringer.print("??=?",L.get(LocaleFactory.locale,"sql.insert_not_support"),column,valueSql));
    return this;
  }

  public IInserter setSql(String column,String valueSql,Object[] values){
    Logger.getLog().warn(Stringer.print("??=?",L.get(LocaleFactory.locale,"sql.insert_not_support"),column,valueSql));
    return this;
  }

  public void reset(){
    row.clear();
  }

  private boolean headEquals(String[] oldHead,String[] newHead){
    if(oldHead.length!=newHead.length){
      return false;
    }
    outer:for(String a:oldHead){
      for(String b:newHead){
        if(a.equalsIgnoreCase(b)){
          continue outer;
        }
      }
      return false;
    }
    outer:for(String a:newHead){
      for(String b:oldHead){
        if(a.equalsIgnoreCase(b)){
          continue outer;
        }
      }
      return false;
    }
    return true;
  }

  private void updateOld(CSVFileReader cr,File file,String[] oldHead,List<String> heads){
    try{
      File f = new File(file.getAbsolutePath()+".tmp");
      CSVWriter cw = new CSVWriter(f,true);
      cw.writeLine(heads);
      for(int i = 1;i<cr.getLineCount();i++){
        String[] oldData = cr.getLine(i);
        String[] data = new String[heads.size()];
        outer:for(int j = 0;j<data.length;j++){
          for(int k = 0;k<oldHead.length;k++){
            if(oldHead[k].equalsIgnoreCase(heads.get(j))){
              data[j] = oldData[k];
              continue outer;
            }
          }
          data[j] = "";
        }
        cw.writeLine(data);
      }
      String[] data = new String[heads.size()];
      for(int i = 0;i<data.length;i++){
        data[i] = row.getString(heads.get(i),"");
      }
      cw.writeLine(data);
      cw.flush();
      cw.close();
      f.delete();
      cw = new CSVWriter(file);
      CSVFileReader cfr = new CSVFileReader(f);
      for(int i = 0;i<cfr.getLineCount();i++){
        cw.writeLine(cfr.getLine(i));
      }
      cfr.close();
      cw.flush();
      cw.close();
      f.delete();
    }catch(IOException e){
      throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath(),e);
    }
  }

  public int insert(){
    if(null==tableName){
      throw new DaoException(Stringer.print("??.?",L.get("sql.table_not_found"),template.getConnVar().getAlias(),tableName));
    }
    File file = getTableFile();
    CSVFileReader cr = new CSVFileReader(file);
    String[] head = null;
    try{
      if(cr.getLineCount()>0){
        head = cr.getLine(0);
      }
    }catch(IOException e){
      throw new DaoException(L.get("sql.file_read_error")+file.getAbsolutePath(),e);
    }
    try{
      if(null==head){// 第一次插入
        head = new String[row.keySet().size()];
        int idx = 0;
        for(String k:row.keySet()){
          head[idx] = k;
          idx++;
        }
        CSVWriter cw = new CSVWriter(file,true);
        cw.writeLine(head);
        String[] data = new String[head.length];
        for(int i = 0;i<head.length;i++){
          data[i] = row.getString(head[i],"");
        }
        Logger.getLog().debug(L.get("sql.update")+JSON.toJson(data));
        cw.writeLine(data);
        cw.flush();
        cw.close();
      }else{
        String[] newHead = new String[row.keySet().size()];
        int idx = 0;
        for(String k:row.keySet()){
          newHead[idx] = k;
          idx++;
        }
        if(headEquals(head,newHead)){
          String[] data = new String[head.length];
          for(int i = 0;i<head.length;i++){
            data[i] = row.getString(head[i],"");
          }
          CSVWriter cw = new CSVWriter(file,true);
          cw.writeLine(data);
          cw.flush();
          cw.close();
        }else{
          List<String> heads = new ArrayList<String>();
          outer:for(String a:head){
            for(String b:heads){
              if(a.equalsIgnoreCase(b)){
                continue outer;
              }
            }
            heads.add(a);
          }
          outer:for(String a:newHead){
            for(String b:heads){
              if(a.equalsIgnoreCase(b)){
                continue outer;
              }
            }
            heads.add(a);
          }
          updateOld(cr,file,head,heads);
        }
      }
    }catch(IOException e){
      throw new DaoException(L.get("sql.file_write_error")+file.getAbsolutePath(),e);
    }finally{
      try{
        cr.close();
      }catch(Exception e){}
    }
    reset();
    return 1;
  }

  public IInserter setBean(Bean bean){
    for(String col:bean.keySet()){
      set(col,bean.get(col));
    }
    return this;
  }
}
