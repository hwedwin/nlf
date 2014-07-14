package test;

import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.db.transaction.TransFactory;
import nc.liat6.frame.execute.Request;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.web.response.Paging;


public class Action{
  public Object paging(){
    Request r = Context.get(Statics.REQUEST);
    ITrans t = TransFactory.getTrans();
    PageData pd = t.getSelecter().table("T006_CITY").page(r.getPageNumber(),r.getPageSize());
    Paging p = new Paging();
    p.setPageData(pd);
    p.setUri("/demo/paging.jsp");
    return p;
  }
}
