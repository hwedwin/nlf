package nc.liat6.frame.db.connection.impl;

import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.connection.IConnVarProvider;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.util.Stringer;

/**
 * 连接提供器父类
 * @author 6tail
 *
 */
public abstract class SuperConnVarProvider implements IConnVarProvider {

	/** 已注册驱动 */
	private static final List<String> REGIST_DRIVERS = new ArrayList<String>();

	/**
	 * 驱动注册
	 * 
	 * @param driver
	 */
	protected void registDriver(String driver) {
		synchronized (this) {
			if (REGIST_DRIVERS.contains(driver)) {
				return;
			}
			try {
				Class.forName(driver);
				REGIST_DRIVERS.add(driver);
			} catch (ClassNotFoundException e) {
				throw new DaoException(L.get("db.driver_not_found")+driver, e);
			}
			Logger.getLog().debug(Stringer.print("??",L.get(LocaleFactory.locale,"db.regist_driver"),driver));
		}
	}

}
