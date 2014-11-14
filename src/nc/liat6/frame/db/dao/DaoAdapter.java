package nc.liat6.frame.db.dao;

import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.paging.PageData;

public abstract class DaoAdapter implements IDaoRule{

  protected Object[] params;

  public DaoAdapter(){}

  public DaoAdapter(Object... params){
    this.params = params;
  }

  public String alias(){
    return null;
  }

  public IBeanRule rule(){
    return null;
  }

  public Bean one(ITrans t){
    throw new DaoException();
  }

  public List<Bean> list(ITrans t){
    throw new DaoException();
  }

  public void insert(ITrans t,Bean bean){
    throw new DaoException();
  }

  public void delete(ITrans t){
    throw new DaoException();
  }

  public int count(ITrans t){
    throw new DaoException();
  }

  public void update(ITrans t,Bean bean){
    throw new DaoException();
  }

  public PageData page(ITrans t){
    throw new DaoException();
  }
}
