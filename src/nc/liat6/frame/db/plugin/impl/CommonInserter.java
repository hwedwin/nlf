package nc.liat6.frame.db.plugin.impl;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.db.plugin.Rule;

/**
 * 通用插入器
 * 
 * @author 6tail
 * 
 */
public class CommonInserter extends SuperExecuter implements IInserter{

  public IInserter table(String tableName){
    tables.add(tableName);
    return this;
  }

  public IInserter set(String column,Object value){
    // 如果有重复的，替换值
    for(int i = 0;i<cols.size();i++){
      if(cols.get(i).getColumn().equalsIgnoreCase(column)){
        paramCols.set(i,value);
        return this;
      }
    }
    Rule r = new Rule();
    r.setColumn(column);
    cols.add(r);
    paramCols.add(value);
    return this;
  }

  public IInserter setSql(String column,String valueSql){
    Rule r = new Rule();
    r.setColumn(column);
    r.setTag(valueSql);
    cols.add(r);
    return this;
  }

  public IInserter setSql(String column,String valueSql,Object[] values){
    Rule r = new Rule();
    r.setColumn(column);
    r.setTag(valueSql);
    cols.add(r);
    for(Object v:values){
      paramCols.add(v);
    }
    return this;
  }

  public int insert(){
    String sql = getSql();
    int n = template.update(sql,getParam());
    reset();
    return n;
  }

  public String getSql(){
    // 重置SQL
    resetSql();
    // 重新构造
    sql.append(" INSERT INTO");
    int l = tables.size();
    for(int i = 0;i<l;i++){
      if(i>0){
        sql.append(",");
      }
      sql.append(" ");
      sql.append(tables.get(i));
    }
    l = cols.size();
    for(int i = 0;i<l;i++){
      if(i<1){
        sql.append("(");
      }else{
        sql.append(",");
      }
      sql.append(" ");
      Rule r = cols.get(i);
      sql.append(r.getColumn());
      if(i>l-2){
        sql.append(")");
      }
    }
    sql.append(" VALUES (");
    for(int i = 0;i<l;i++){
      if(i>0){
        sql.append(",");
      }
      sql.append(" ");
      Rule r = cols.get(i);
      sql.append(r.getTag());
    }
    sql.append(")");
    return sql.toString();
  }

  public IInserter setBean(Bean bean){
    for(String col:bean.keySet()){
      set(col,bean.get(col));
    }
    return this;
  }
}
