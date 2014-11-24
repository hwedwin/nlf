package nc.liat6.frame.db;

import java.util.ArrayList;
import java.util.List;
import nc.liat6.frame.db.dao.IDaoRule;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.db.transaction.TransFactory;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.IOHelper;

public class Dao{

  protected Dao(){}

  protected static ITrans getTrans(IDaoRule daoRule){
    return TransFactory.getTrans(daoRule.alias());
  }

  protected static void rollback(ITrans t){
    if(null!=t){
      try{
        t.rollback();
      }catch(Exception e){
      }
    }
  }
  protected static void commit(ITrans t){
    if(null!=t){
      try{
        t.commit();
      }catch(Exception e){
      }
    }
  }
  protected static void close(ITrans t){
    IOHelper.closeQuietly(t);
  }

  public static Bean one(IDaoRule daoRule){
    Bean bean = null;
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      bean = daoRule.one(t);
      rollback(t);
    }finally{
      close(t);
    }
    return bean;
  }

  public static <T>T one(Class<?> entityClass,IDaoRule daoRule){
    Bean bean = one(daoRule);
    IBeanRule beanRule = daoRule.rule();
    if(null==beanRule){
      return bean.toObject(entityClass);
    }else{
      return bean.toObject(entityClass,beanRule);
    }
  }

  public static List<Bean> list(IDaoRule daoRule){
    List<Bean> l = null;
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      l = daoRule.list(t);
      rollback(t);
    }finally{
      close(t);
    }
    return l;
  }

  public static <T> List<T> list(Class<?> entityClass,IDaoRule daoRule){
    List<T> result = null;
    List<Bean> l = list(daoRule);
    result = new ArrayList<T>();
    IBeanRule beanRule = daoRule.rule();
    for(Bean bean:l){
      T o = null;
      if(null==beanRule){
        o = bean.toObject(entityClass);
      }else{
        o = bean.toObject(entityClass,beanRule);
      }
      result.add(o);
    }
    return result;
  }

  public static void insert(List<Object> objects,IDaoRule daoRule){
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      for(Object obj:objects){
        Bean bean = null;
        if(obj instanceof Bean){
          bean = (Bean)obj;
        }else{
          bean = new Bean();
          bean.fromObject(obj,daoRule.rule());
        }
        daoRule.insert(t,bean);
      }
      commit(t);
    }finally{
      close(t);
    }
  }

  public static void insert(Bean bean,IDaoRule daoRule){
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      daoRule.insert(t,bean);
      commit(t);
    }finally{
      close(t);
    }
  }

  public static void insert(Object object,IDaoRule daoRule){
    Bean bean = new Bean();
    bean.fromObject(object,daoRule.rule());
    insert(bean,daoRule);
  }

  public static void delete(IDaoRule daoRule){
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      daoRule.delete(t);
      commit(t);
    }finally{
      close(t);
    }
  }

  public static int count(IDaoRule daoRule){
    int count = 0;
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      count = daoRule.count(t);
      rollback(t);
    }finally{
      close(t);
    }
    return count;
  }

  public static void update(Object object,IDaoRule daoRule){
    Bean bean = new Bean();
    bean.fromObject(object,daoRule.rule());
    update(bean,daoRule);
  }

  public static void update(Bean bean,IDaoRule daoRule){
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      daoRule.update(t,bean);
      commit(t);
    }finally{
      close(t);
    }
  }

  public static void update(List<Object> objects,IDaoRule daoRule){
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      for(Object obj:objects){
        Bean bean = null;
        if(obj instanceof Bean){
          bean = (Bean)obj;
        }else{
          bean = new Bean();
          bean.fromObject(obj,daoRule.rule());
        }
        daoRule.update(t,bean);
      }
      commit(t);
    }finally{
      close(t);
    }
  }

  public static PageData page(IDaoRule daoRule){
    PageData pd = null;
    ITrans t = null;
    try{
      t = getTrans(daoRule);
      pd = daoRule.page(t);
      rollback(t);
    }finally{
      close(t);
    }
    return pd;
  }

  public static PageData page(Class<?> entityClass,IDaoRule daoRule){
    PageData pd = page(daoRule);
    IBeanRule beanRule = daoRule.rule();
    List<Object> l = new ArrayList<Object>();
    for(int i=0;i<pd.getSize();i++){
      Bean bean = pd.getBean(i);
      Object o = null;
      if(null==beanRule){
        o = bean.toObject(entityClass);
      }else{
        o = bean.toObject(entityClass,beanRule);
      }
      l.add(o);
    }
    pd.setData(l);
    return pd;
  }
}
