package nc.liat6.frame.db.dao;

import java.util.List;
import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.entity.IBeanRule;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.paging.PageData;

public interface IDaoRule{

  /**
   * 获取数据源别名
   *
   * @return 数据源别名
   */
  public String alias();

  /**
   * 获取Bean转换规则接口
   *
   * @return Bean转换规则接口
   */
  public IBeanRule rule();

  /**
   * 查询一条记录
   *
   * @param t 事务接口
   * @return Bean
   */
  public Bean one(ITrans t);

  /**
   * 查询列表
   *
   * @param t 事务接口
   * @return 列表
   */
  public List<Bean> list(ITrans t);

  public void insert(ITrans t,Bean bean);

  public void delete(ITrans t);

  public int count(ITrans t);

  public void update(ITrans t,Bean bean);

  public PageData page(ITrans t);

}
