package test;

import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.db.transaction.TransFactory;
import nc.liat6.frame.log.Logger;

public class TestDb{
	
	public static void main(String[] args){
		ITrans t = TransFactory.getTrans("db0");
		Logger.getLog().debug(t.getSelecter().table("TAB_TEST").select());
		t.rollback();
		t.close();
	}

}
