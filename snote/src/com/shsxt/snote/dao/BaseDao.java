package com.shsxt.snote.dao;

import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.FileDataSource;

import com.shsxt.snote.utils.MyDbUtil;


public class BaseDao<T> {
	/**
	 * 用于查询单行单列
	 * @param sql	拼接SQL
	 * @param params	多个参数
	 * @return		最终的唯一结果
	 */
	public Object querySingleValue(String sql,Object...params){
		//获取连接
		Connection connection=null;
		//预处理
		PreparedStatement ps=null;
		//结果集
		ResultSet rs=null;
		//存放返回的结果
		Object object=null;
		try {
			connection=MyDbUtil.getConnection();
			ps=connection.prepareStatement(sql);
			//是为了填充条件
			if(params!=null && params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			rs=ps.executeQuery();
			if(rs.next()){
				object=rs.getObject(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MyDbUtil.close(rs, connection, ps);
		}
		return object;
	}
	/**
	 * 用于执行添加|修改
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeUpdate(String sql,Object...params){
		//获取连接
		Connection connection=null;
		//预处理
		PreparedStatement ps=null;
		//存放结果的int
		int result=0;
		try {
			connection=MyDbUtil.getConnection();
			//关闭自动提交事务
			connection.setAutoCommit(false);
			ps=connection.prepareStatement(sql);
			if(null!=params && params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
		 result=ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result=0;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				connection.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MyDbUtil.close(connection, ps);
		}
		return result;		
	}
	/**
	 * 查询多行数据
	 * @param sql
	 * @param clazz
	 * @param params
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public List<T> queryRows(String sql,Class<T>clazz,Object...params) {
		//获取连接
		Connection connection=null;
		//预处理
		PreparedStatement ps=null;
		//结果集
		ResultSet rs=null;
		List<T>results=new ArrayList<T>();
		try {
			connection=MyDbUtil.getConnection();
			ps=connection.prepareStatement(sql);
			if(null!=params && params.length>0){
				for(int i=0;i<params.length;i++){
					ps.setObject(i+1, params[i]);
				}
			}
			rs=ps.executeQuery();
			ResultSetMetaData rsm=rs.getMetaData();
//			if(null!=rs){
				while(rs.next()){
					T obj=clazz.newInstance();
					for(int i=0;i<rsm.getColumnCount();i++){
						String columnName=rsm.getColumnLabel(i+1);//columnLable:获取的是别名
						Field field=getField(clazz,columnName);
						//字段操作打开
						field.setAccessible(true);
						field.set(obj, rs.getObject(i+1));
					}
					results.add(obj);
				}
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			MyDbUtil.close(rs, connection, ps);
		}
		return results;
	}
	public T querySingleRow(String sql,Class<T>clazz,Object...params){
		List<T> result=queryRows(sql, clazz, params);
		return result.size()==0 ? null : result.get(0);
	}
	public int executeUpdateBatch(List<SqlParam> list){
		Connection connection=null;
		PreparedStatement ps=null;
		int result=0;
		try {
			connection=MyDbUtil.getConnection();
			connection.setAutoCommit(false);
			if(list!=null && list.size()>0){
				for(SqlParam sqlParam:list){
					String sql=sqlParam.getSql();
					ps=connection.prepareStatement(sql);
					Object[] params=sqlParam.getParam();
					if(null!=params && params.length>0){
						for(int i=0;i<params.length;i++){
							ps.setObject(i+1, params[i]);
						}
					}
					result=ps.executeUpdate();
					if(result<1){
						throw new Exception();
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			result=0;
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				connection.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			MyDbUtil.close(connection, ps);
		}
		return result;
	}
	/**
	 * 获取对象中的字段
	 * @param clazz
	 * @param columnName
	 * @return
	 */
	public Field getField(Class<T> clazz,String columnName){
		for(Class<T> cc=clazz;cc!=Object.class;cc=(Class<T>)cc.getSuperclass()){
			try {
				return cc.getDeclaredField(columnName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		return null;
	}
}
