package nc.liat6.frame.db.custom.oracle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nc.liat6.frame.db.entity.Bean;
import nc.liat6.frame.db.exception.DaoException;
import nc.liat6.frame.db.sql.impl.CommonTemplate;
import nc.liat6.frame.locale.L;
import nc.liat6.frame.locale.LocaleFactory;
import nc.liat6.frame.log.ILog;
import nc.liat6.frame.log.Logger;
import nc.liat6.frame.paging.PageData;
import nc.liat6.frame.util.Stringer;

/**
 * SQL执行模板的Oracle实现
 * @author 6tail
 *
 */
public class OracleTemplate extends CommonTemplate implements IOracle{

	private static ILog log = Logger.getLog();
	
	public int count(String sql, Object param) {
		flush();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String nsql = sql;
		String upsql = sql.toUpperCase();
		int index = upsql.indexOf(" ORDER BY ");
		if (index > -1) {
			nsql = sql.substring(0, index);
		}
		nsql = "SELECT COUNT(*) FROM (" + nsql + ") NLFTABLE_";
		try {
			stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
			List<Object> pl = processParams(stmt, param);
			StringBuilder s = new StringBuilder();
			for(Object o:pl){
				s.append("\t");
				s.append(o);
				s.append("\r\n");
			}
			log.debug(Stringer.print("??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.count"),nsql,L.get(LocaleFactory.locale,"sql.var"),s.toString()));
			rs = stmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			finalize(stmt, rs);
		}
	}

	public int count(String sql) {
		return count(sql, null);
	}

	public PageData query(String sql, int pageNumber, int pageSize) {
		return query(sql,pageNumber,pageSize,null);
	}

	public PageData query(String sql, int pageNumber, int pageSize, Object param) {
		flush();
		PageData d = new PageData();
		d.setRecordCount(count(sql, param));
		d.setPageSize(pageSize);
		int pageCount = d.getPageCount();
		d.setPageNumber(pageNumber > pageCount ? pageCount : pageNumber);

		String nsql = "SELECT * FROM (SELECT NLFTABLE_.*,ROWNUM RN_ FROM ("
				+ sql + ") NLFTABLE_ WHERE ROWNUM <= "
				+ (d.getPageNumber() * d.getPageSize()) + ") WHERE RN_ > "
				+ ((d.getPageNumber() - 1) * d.getPageSize());

		List<Object[]> l = new ArrayList<Object[]>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
			List<Object> pl = processParams(stmt, param);
			StringBuilder s = new StringBuilder();
			for(Object o:pl){
				s.append("\t");
				s.append(o);
				s.append("\r\n");
			}
			log.debug(Stringer.print("??\r\n??\r\n??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.query_page"),nsql,L.get(LocaleFactory.locale,"sql.query_page_num"),pageNumber,L.get(LocaleFactory.locale,"sql.query_page_size"),pageSize,L.get(LocaleFactory.locale,"sql.var"),s.toString()));
			rs = stmt.executeQuery();
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				Object[] o = new Object[columnCount];
				for (int i = 0; i < columnCount; i++) {
					o[i] = rs.getObject(i + 1);
				}
				l.add(o);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			finalize(stmt, rs);
		}
		d.setData(l);
		return d;
	}
	
	public PageData queryEntity(String sql,int pageNumber,int pageSize){
		return queryEntity(sql,pageNumber,pageSize,null);
	}

	public PageData queryEntity(String sql,int pageNumber,int pageSize,Object param){
		flush();
		PageData d = new PageData();
		d.setRecordCount(count(sql, param));
		d.setPageSize(pageSize);
		int pageCount = d.getPageCount();
		d.setPageNumber(pageNumber > pageCount ? pageCount : pageNumber);

		String nsql = "SELECT * FROM (SELECT NLFTABLE_.*,ROWNUM RN_ FROM ("
				+ sql + ") NLFTABLE_ WHERE ROWNUM <= "
				+ (d.getPageNumber() * d.getPageSize()) + ") WHERE RN_ > "
				+ ((d.getPageNumber() - 1) * d.getPageSize());
		List<Bean> l = new ArrayList<Bean>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = cv.getConnection().getSqlConnection().prepareStatement(nsql);
			List<Object> pl = processParams(stmt, param);
			StringBuilder s = new StringBuilder();
			for(Object o:pl){
				s.append("\t");
				s.append(o);
				s.append("\r\n");
			}
			log.debug(Stringer.print("??\r\n?\r\n?",L.get(LocaleFactory.locale,"sql.query_entity"),sql,L.get(LocaleFactory.locale,"sql.var"),s.toString()));
			rs = stmt.executeQuery();
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnCount = rsmd.getColumnCount();
				Bean o = new Bean();
				for (int i = 0; i < columnCount; i++) {
					o.set(rsmd.getColumnName(i+1),rs.getObject(i + 1));
				}
				l.add(o);
			}
		} catch (SQLException e) {
			throw new DaoException(e);
		} finally {
			finalize(stmt, rs);
		}
		d.setData(l);
		return d;
	}

}
