package nc.liat6.frame.db.custom.csv;

import java.io.File;
import java.io.IOException;
import java.util.List;
import nc.liat6.frame.csv.CSVFileReader;
import nc.liat6.frame.csv.CSVWriter;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.Rule;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * CSV删除器
 * 
 * @author 6tail
 * 
 */
public class CsvDeleter extends CsvExecuter implements IDeleter{

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
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public IDeleter whereIn(String column,Object... value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("in");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(objectsToList(value));
    return this;
  }

  public IDeleter whereNotIn(String column,Object... value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("not_in");
    r.setOpEnd("");
    r.setTag("");
    wheres.add(r);
    paramWheres.add(objectsToList(value));
    return this;
  }

  public int delete(){
    File file = getTableFile();
    CSVFileReader cr = new CSVFileReader(file);
    int deleted = 0;
    try{
      String[] head = readHead(cr,file);
      File f = new File(file.getAbsolutePath()+".tmp");
      CSVWriter cw = new CSVWriter(f);
      cw.writeLine(head);
      if(wheres.size()>0){
        outer:for(int i = 1;i<cr.getLineCount();i++){
          String[] data = cr.getLine(i);
          Bean o = new Bean();
          for(int j = 0;j<head.length;j++){
            String s = head[j].toUpperCase();
            if(data.length>=j){
              o.set(s,data[j]);
            }else{
              o.set(s,"");
            }
          }
          // 满足条件的跳过，即不写入文件
          for(int j = 0;j<wheres.size();j++){
            Rule r = wheres.get(j);
            // 操作类型
            String op = r.getOpStart();
            // 结果
            String v = o.getString(r.getColumn().toUpperCase(),"");
            // 参数
            String p = paramWheres.get(j)+"";
            if("=".equals(op)){
              if(v.equals(p)){
                continue outer;
              }
            }else if("!=".equals(op)){
              if(!v.equals(p)){
                continue outer;
              }
            }else if("like".equalsIgnoreCase(op)){
              if(v.indexOf(p)>-1){
                continue outer;
              }
            }else if("left_like".equalsIgnoreCase(op)){
              if(v.startsWith(p)){
                continue outer;
              }
            }else if("right_like".equalsIgnoreCase(op)){
              if(v.endsWith(p)){
                continue outer;
              }
            }else if("in".equalsIgnoreCase(op)){
              List<?> in = (List<?>)paramWheres.get(j);
              boolean isIn = false;
              in:for(Object m:in){
                if(v.equals(m+"")){
                  isIn = true;
                  break in;
                }
              }
              if(isIn){
                continue outer;
              }
            }else if("not_in".equalsIgnoreCase(op)){
              List<?> in = (List<?>)paramWheres.get(j);
              boolean isIn = false;
              in:for(Object m:in){
                if(v.equals(m+"")){
                  isIn = true;
                  break in;
                }
              }
              if(!isIn){
                continue outer;
              }
            }
          }
          cw.writeLine(data);
        }
      }
      cr.close();
      cw.flush();
      cw.close();
      cw = new CSVWriter(file);
      cr = new CSVFileReader(f);
      for(int i = 0;i<cr.getLineCount();i++){
        String[] data = cr.getLine(i);
        cw.writeLine(data);
      }
      cr.close();
      cw.flush();
      cw.close();
      f.delete();
    }catch(IOException e){
      throw new DaoException(L.get("sql.file_write_error")+file.getAbsolutePath(),e);
    }
    reset();
    return deleted;
  }

}
