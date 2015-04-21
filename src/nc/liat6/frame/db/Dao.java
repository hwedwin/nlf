package nc.liat6.frame.db;

import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.plugin.impl.AutoCloseCounter;
import nc.liat6.frame.db.plugin.impl.AutoCloseDeleter;
import nc.liat6.frame.db.plugin.impl.AutoCloseInserter;
import nc.liat6.frame.db.plugin.impl.AutoCloseSelecter;
import nc.liat6.frame.db.plugin.impl.AutoCloseUpdater;
import nc.liat6.frame.db.transaction.TransFactory;

/**
 * 适用于单语句的快捷操作
 * @author 6tail
 *
 */
public class Dao{
  protected Dao(){}

  public static ISelecter getSelecter(){
    return getSelecter(null);
  }

  public static ISelecter getSelecter(String alias){
    return new AutoCloseSelecter(TransFactory.getTrans(alias));
  }

  public static ICounter getCounter(){
    return getCounter(null);
  }

  public static ICounter getCounter(String alias){
    return new AutoCloseCounter(TransFactory.getTrans(alias));
  }

  public static IInserter getInserter(){
    return getInserter(null);
  }

  public static IInserter getInserter(String alias){
    return new AutoCloseInserter(TransFactory.getTrans(alias));
  }
  
  public static IDeleter getDeleter(){
    return getDeleter(null);
  }

  public static IDeleter getDeleter(String alias){
    return new AutoCloseDeleter(TransFactory.getTrans(alias));
  }
  
  public static IUpdater getUpdater(){
    return getUpdater(null);
  }

  public static IUpdater getUpdater(String alias){
    return new AutoCloseUpdater(TransFactory.getTrans(alias));
  }

}