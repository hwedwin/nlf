package nc.liat6.frame.db.setting.impl;

/**
 * PROXOOL连接池配置
 * 
 * @author 6tail
 * 
 */
public class ProxoolSetting extends SuperDbSetting{

  private static final long serialVersionUID = -1965324522415398901L;
  /** 默认连接类型 */
  public static final String DEFAULT_TYPE = "proxool";
  private int prototypeCount = -1;
  private long houseKeepingSleepTime = -1;
  private long maximumActiveTime = -1;
  private int maximumConnectionLifeTime = -1;
  private int maximumConnectionCount = -1;
  private int minimumConnectionCount = -1;
  private int simultaneousBuildThrottle = -1;
  private boolean testBeforeUse = true;
  private boolean testAfterUse = true;

  public ProxoolSetting(){
    type = DEFAULT_TYPE;
  }

  public int getPrototypeCount(){
    return prototypeCount;
  }

  public void setPrototypeCount(int prototypeCount){
    this.prototypeCount = prototypeCount;
  }

  public long getHouseKeepingSleepTime(){
    return houseKeepingSleepTime;
  }

  public void setHouseKeepingSleepTime(long houseKeepingSleepTime){
    this.houseKeepingSleepTime = houseKeepingSleepTime;
  }

  public long getMaximumActiveTime(){
    return maximumActiveTime;
  }

  public void setMaximumActiveTime(long maximumActiveTime){
    this.maximumActiveTime = maximumActiveTime;
  }

  public int getMaximumConnectionLifeTime(){
    return maximumConnectionLifeTime;
  }

  public void setMaximumConnectionLifeTime(int maximumConnectionLifeTime){
    this.maximumConnectionLifeTime = maximumConnectionLifeTime;
  }

  public int getMaximumConnectionCount(){
    return maximumConnectionCount;
  }

  public void setMaximumConnectionCount(int maximumConnectionCount){
    this.maximumConnectionCount = maximumConnectionCount;
  }

  public int getMinimumConnectionCount(){
    return minimumConnectionCount;
  }

  public void setMinimumConnectionCount(int minimumConnectionCount){
    this.minimumConnectionCount = minimumConnectionCount;
  }

  public int getSimultaneousBuildThrottle(){
    return simultaneousBuildThrottle;
  }

  public void setSimultaneousBuildThrottle(int simultaneousBuildThrottle){
    this.simultaneousBuildThrottle = simultaneousBuildThrottle;
  }

  public boolean isTestBeforeUse(){
    return testBeforeUse;
  }

  public void setTestBeforeUse(boolean testBeforeUse){
    this.testBeforeUse = testBeforeUse;
  }

  public boolean isTestAfterUse(){
    return testAfterUse;
  }

  public void setTestAfterUse(boolean testAfterUse){
    this.testAfterUse = testAfterUse;
  }
}
