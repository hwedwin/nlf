package nc.liat6.frame.db.transaction.impl;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.context.Context;
import nc.liat6.frame.context.Statics;
import nc.liat6.frame.db.connection.ConnVar;
import nc.liat6.frame.db.connection.ConnVarFactory;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.plugin.ICounter;
import nc.liat6.frame.db.plugin.IDeleter;
import nc.liat6.frame.db.plugin.IExecuter;
import nc.liat6.frame.db.plugin.IInserter;
import nc.liat6.frame.db.plugin.ISelecter;
import nc.liat6.frame.db.plugin.IUpdater;
import nc.liat6.frame.db.sql.ITemplate;
import nc.liat6.frame.db.transaction.ITrans;
import nc.liat6.frame.db.transaction.TransFactory;

/**
 * 事务接口的默认实现
 */
public class Trans implements ITrans{

	private String dbType;
	private ITemplate template;
	private String alias;

	public void init(String alias){
		this.alias = alias;
		List<ConnVar> l = Context.get(Statics.CONNECTIONS);
		if(null == l){
			l = new ArrayList<ConnVar>();
			Context.set(Statics.CONNECTIONS,l);
		}
		ConnVar cv = null;
		for(ConnVar n:l){
			if(alias.equals(n.getAlias())){
				cv = n;
				break;
			}
		}
		if(null == cv){
			cv = ConnVarFactory.getConnVar(alias);
			l.add(cv);
		}
		cv.setLevel(cv.getLevel() + 1);
		this.dbType = cv.getDbType();
		template = createTemplate();
	}

	public ITemplate getTemplate(){
		return template;
	}
	
	public void commit(){
		template.flush();
		template.getConnVar().getConnection().commit();
	}

	public void rollback(){
		template.getConnVar().getConnection().rollback();
	}

	public void close(){
		ConnVar cv = template.getConnVar();
		cv.setLevel(cv.getLevel() - 1);
		if(cv.getLevel() < 1){
			cv.getConnection().close();
			List<ConnVar> l = Context.get(Statics.CONNECTIONS);
			if(null != l){
				l.remove(cv);
			}
		}
	}

	public IUpdater getUpdater(){
		return (IUpdater)createExecuter(IUpdater.class);
	}

	public IInserter getInserter(){
		return (IInserter)createExecuter(IInserter.class);
	}

	public ISelecter getSelecter(){
		return (ISelecter)createExecuter(ISelecter.class);
	}

	public ICounter getCounter(){
		return (ICounter)createExecuter(ICounter.class);
	}

	public IDeleter getDeleter(){
		return (IDeleter)createExecuter(IDeleter.class);
	}

	private IExecuter createExecuter(Class<?> interfaceClass){
		IExecuter o = null;
		Class<?> c = TransFactory.getImplClass(dbType,interfaceClass);
		try{
			o = (IExecuter)c.newInstance();
		}catch(Exception e){
			throw new DaoException(e);
		}
		o.setTemplate(template);
		return o;
	}

	private ITemplate createTemplate(){
		ITemplate o = null;
		Class<?> c = TransFactory.getImplClass(dbType,ITemplate.class);
		try{
			o = (ITemplate)c.newInstance();
		}catch(Exception e){
			throw new DaoException(e);
		}
		o.setAlias(alias);
		return o;
	}

	public String getDbType(){
		return dbType;
	}

}
