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
  String alias();

  /**
   * 获取Bean转换规则接口
   *
   * @return Bean转换规则接口
   */
  IBeanRule rule();

  /**
   * 查询一条记录
   *
   * @param t 事务接口
   * @return Bean
   */
  Bean one(ITrans t);

  /**
   * 查询列表
   *
   * @param t 事务接口
   * @return 列表
   */
  List<Bean> list(ITrans t);

  void insert(ITrans t,Bean bean);

  void delete(ITrans t);

  int count(ITrans t);

  void update(ITrans t,Bean bean);

  PageData page(ITrans t);
}