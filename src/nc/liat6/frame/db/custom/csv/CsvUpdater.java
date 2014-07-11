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
  protected List<Rule> conds = new ArrayList<Rule>();
  protected List<Object> paramCols = new ArrayList<Object>();

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
    conds.add(r);
    params.add(value);
    return this;
  }

  public IUpdater whereLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("like");
    r.setOpEnd("");
    r.setTag("");
    conds.add(r);
    params.add(value);
    return this;
  }

  public IUpdater whereLeftLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("left_like");
    r.setOpEnd("");
    r.setTag("");
    conds.add(r);
    params.add(value);
    return this;
  }

  public IUpdater whereRightLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("right_like");
    r.setOpEnd("");
    r.setTag("");
    conds.add(r);
    params.add(value);
    return this;
  }

  public IUpdater whereNq(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("!=");
    r.setOpEnd("");
    r.setTag("");
    conds.add(r);
    params.add(value);
    return this;
  }

  public IUpdater whereIn(String column,Object... value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("in");
    r.setOpEnd("");
    r.setTag("");
    conds.add(r);
    List<Object> l = objectsToList(value);
    params.add(l);
    return this;
  }

  public IUpdater whereNotIn(String column,Object... value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("not_in");
    r.setOpEnd("");
    r.setTag("");
    conds.add(r);
    List<Object> l = objectsToList(value);
    params.add(l);
    return this;
  }

  public IUpdater set(String column,Object value){
    // 如果有重复的，替换值
    for(int i = 0;i<cols.size();i++){
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
    File file = getTableFile();
    CSVFileReader cr = new CSVFileReader(file);
    int updated = 0;
    try{
      String[] head = readHead(cr,file);
      File f = new File(file.getAbsolutePath()+".tmp");
      CSVWriter cw = new CSVWriter(f);
      cw.writeLine(head);
      if(conds.size()>0){
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
          // 不满足条件的，直接写入
          for(int j = 0;j<conds.size();j++){
            Rule r = conds.get(j);
            // 操作类型
            String op = r.getOpStart();
            // 结果
            String v = o.getString(r.getColumn().toUpperCase(),"");
            // 参数
            String p = params.get(j)+"";
            if("=".equals(op)){
              if(!v.equals(p)){
                cw.writeLine(data);
                continue outer;
              }
            }else if("!=".equals(op)){
              if(v.equals(p)){
                cw.writeLine(data);
                continue outer;
              }
            }else if("like".equalsIgnoreCase(op)){
              if(v.indexOf(p)<0){
                cw.writeLine(data);
                continue outer;
              }
            }else if("left_like".equalsIgnoreCase(op)){
              if(!v.startsWith(p)){
                cw.writeLine(data);
                continue outer;
              }
            }else if("right_like".equalsIgnoreCase(op)){
              if(!v.endsWith(p)){
                cw.writeLine(data);
                continue outer;
              }
            }else if("in".equalsIgnoreCase(op)){
              List<?> in = (List<?>)params.get(j);
              boolean isIn = false;
              in:for(Object m:in){
                if(v.equals(m+"")){
                  isIn = true;
                  break in;
                }
              }
              if(!isIn){
                cw.writeLine(data);
                continue outer;
              }
            }else if("not_in".equalsIgnoreCase(op)){
              List<?> in = (List<?>)params.get(j);
              boolean isIn = false;
              in:for(Object m:in){
                if(v.equals(m+"")){
                  isIn = true;
                  break in;
                }
              }
              if(isIn){
                cw.writeLine(data);
                continue outer;
              }
            }
          }
          for(int j = 0;j<cols.size();j++){
            o.set(cols.get(j).getColumn().toUpperCase(),paramCols.get(j));
          }
          for(int j = 0;j<head.length;j++){
            data[j] = o.getString(head[j].toUpperCase(),"");
          }
          cw.writeLine(data);
        }
      }else{
        for(int i = 1;i<cr.getLineCount();i++){
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
          for(int j = 0;j<cols.size();j++){
            o.set(cols.get(j).getColumn().toUpperCase(),paramCols.get(j));
          }
          for(int j = 0;j<head.length;j++){
            data[j] = o.getString(head[j].toUpperCase(),"");
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
    return updated;
  }

  public void reset(){
    cols.clear();
    conds.clear();
    paramCols.clear();
    params.clear();
  }

  public IUpdater setBean(Bean bean){
    for(String col:bean.keySet()){
      set(col,bean.get(col));
    }
    return this;
  }
}
