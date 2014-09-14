package nc.liat6.frame.db.connection;

public abstract class SuperConnection implements IConnection{

  /** 连接变量 */
  protected ConnVar connVar;

  public ConnVar getConnVar(){
    return connVar;
  }

  public void setConnVar(ConnVar connVar){
    this.connVar = connVar;
  }
}
