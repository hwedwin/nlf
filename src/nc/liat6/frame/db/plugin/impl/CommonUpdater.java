package nc.liat6.frame.db.plugin.impl;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.plugin.Rule;

/**
 * 通用更新器
 * 
 * @author 6tail
 * 
 */
public class CommonUpdater extends SuperExecuter implements IUpdater{
  public IUpdater table(String tableName){
    tables.add(tableName);
    return this;
  }

  public IUpdater where(String sql){
    Rule r = new Rule();
    r.setColumn(sql);
    // 空字符串替换占位符
    r.setTag("");
    wheres.add(r);
    return this;
  }

  public IUpdater whereSql(String sql,Object[] values){
    Rule r = new Rule();
    r.setColumn(sql);
    // 空字符串替换占位符
    r.setTag("");
    wheres.add(r);
    for(Object v:values){
      paramWheres.add(v);
    }
    return this;
  }

  public IUpdater where(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    if(null!=value){
      r.setOpStart("=");
      paramWheres.add(value);
    }else{
      r.setOpStart(" IS NULL");
      r.setTag("");
    }
    wheres.add(r);
    return this;
  }

  public IUpdater set(String column,Object value){
    // 如果有重复的，替换值
    for(int i = 0,n = cols.size();i<n;i++){
      if(cols.get(i).getColumn().equalsIgnoreCase(column)){
        paramCols.set(i,value);
        return this;
      }
    }
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart("=");
    cols.add(r);
    paramCols.add(value);
    return this;
  }

  public IUpdater set(String sql){
    Rule r = new Rule();
    r.setColumn(sql);
    // 空字符串替换占位符
    r.setTag("");
    cols.add(r);
    return this;
  }

  public IUpdater setSql(String sql,Object[] values){
    Rule r = new Rule();
    r.setColumn(sql);
    // 空字符串替换占位符
    r.setTag("");
    cols.add(r);
    for(Object v:values){
      paramCols.add(v);
    }
    return this;
  }

  public int update(){
    String sql = getSql();
    int n = template.update(sql,getParam());
    reset();
    return n;
  }

  public String getSql(){
    // 重置SQL
    resetSql();
    // 重新构造
    sql.append(" UPDATE");
    int l = tables.size();
    for(int i = 0;i<l;i++){
      if(i>0){
        sql.append(",");
      }
      sql.append(" ");
      sql.append(tables.get(i));
    }
    sql.append(" SET");
    l = cols.size();
    for(int i = 0;i<l;i++){
      if(i>0){
        sql.append(",");
      }
      sql.append(" ");
      Rule r = cols.get(i);
      sql.append(r.getColumn());
      sql.append(r.getOpStart());
      sql.append(r.getTag());
      sql.append(r.getOpEnd());
    }
    sql.append(getWhereSql());
    return sql.toString();
  }

  public IUpdater whereLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart(" like ");
    wheres.add(r);
    paramWheres.add("%"+value+"%");
    return this;
  }

  public IUpdater whereLeftLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart(" like ");
    wheres.add(r);
    paramWheres.add(value+"%");
    return this;
  }

  public IUpdater whereRightLike(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    r.setOpStart(" like ");
    wheres.add(r);
    paramWheres.add("%"+value);
    return this;
  }

  public IUpdater whereIn(String column,Object... value){
    super.whereIn(column,value);
    return this;
  }

  public IUpdater whereNotIn(String column,Object... value){
    super.whereNotIn(column,value);
    return this;
  }

  public IUpdater whereNq(String column,Object value){
    Rule r = new Rule();
    r.setColumn(column);
    if(null!=value){
      r.setOpStart("!=");
      paramWheres.add(value);
    }else{
      r.setOpStart(" IS NOT NULL");
      r.setTag("");
    }
    wheres.add(r);
    return this;
  }

  public IUpdater setBean(Bean bean){
    for(String col:bean.keySet()){
      set(col,bean.get(col));
    }
    return this;
  }
}