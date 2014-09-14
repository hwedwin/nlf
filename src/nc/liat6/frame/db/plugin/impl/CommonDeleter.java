package nc.liat6.frame.db.plugin.impl;

import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.Rule;

/**
 * 通用删除器
 * 
 * @author 6tail
 * 
 */
public class CommonDeleter extends SuperExecuter implements IDeleter{

  public IDeleter table(String tableName){
    tables.add(tableName);
    return this;
  }

  public IDeleter where(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("=");
    wheres.add(r);
    paramWheres.add(value);
    return this;
  }

  public IDeleter where(String sql){
    Rule r = new Rule();
    r.setColumn(sql);
    r.setOpStart("");
    r.setTag("");
    r.setOpEnd("");
    wheres.add(r);
    return this;
  }

  public IDeleter whereSql(String sql,Object[] values){
    super.whereSql(sql,values);
    return this;
  }

  public IDeleter whereIn(String column,Object... value){
    super.whereIn(column,value);
    return this;
  }

  public IDeleter whereNotIn(String column,Object... value){
    super.whereNotIn(column,value);
    return this;
  }

  public int delete(){
    String sql = getSql();
    int n = template.update(sql,getParam());
    reset();
    return n;
  }

  public String getSql(){
    // 重置SQL
    resetSql();
    // 重新构造
    sql.append(" DELETE FROM");
    int l = tables.size();
    for(int i = 0;i<l;i++){
      if(i>0){
        sql.append(",");
      }
      sql.append(" ");
      sql.append(tables.get(i));
    }
    sql.append(getWhereSql());
    return sql.toString();
  }
}
