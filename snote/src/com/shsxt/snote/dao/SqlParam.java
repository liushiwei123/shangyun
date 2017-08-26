package com.shsxt.snote.dao;
/**
 * 用来封装执行的SQL
 * @author Administrator
 *
 */
public class SqlParam {
	private String sql;
	private Object[] param;
	public SqlParam() {
		// TODO Auto-generated constructor stub
	}
	public SqlParam(String sql, Object[] param) {
		super();
		this.sql = sql;
		this.param = param;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object[] getParam() {
		return param;
	}
	public void setParam(Object[] param) {
		this.param = param;
	}
	
}
