package nc.liat6.frame.db.entity;

import java.sql.ResultSet;
import java.sql.Statement;

public class StatementAndResultSet{
  private Statement statement;
  private ResultSet resultSet;
  
  public StatementAndResultSet(){}
  public StatementAndResultSet(Statement statement,ResultSet resultSet){
    setStatement(statement);
    setResultSet(resultSet);
  }

  public Statement getStatement(){
    return statement;
  }

  public void setStatement(Statement statement){
    this.statement = statement;
  }

  public ResultSet getResultSet(){
    return resultSet;
  }

  public void setResultSet(ResultSet resultSet){
    this.resultSet = resultSet;
  }
}