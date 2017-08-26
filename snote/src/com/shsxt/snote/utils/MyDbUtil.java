package com.shsxt.snote.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MyDbUtil {
	//只在初始化类时，加载一次
	static Properties p=new Properties();
	static{
		try {
			p.load(MyDbUtil.class.getClassLoader().getResourceAsStream("db.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取数据库连接
	 * @return	连接
	 */
	public static Connection getConnection(){
		Connection connection=null;
		try {
			Class.forName(p.getProperty("jdbc.driver"));
			connection=DriverManager.getConnection(p.getProperty("jdbc.url"), p.getProperty("jdbc.user"),
					p.getProperty("jdbc.password"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	/**
	 * 关闭连接
	 * @param connection
	 * @param ps
	 */
	public static void close(Connection connection,PreparedStatement ps){
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(connection!=null){
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 利用重载:关闭资源
	 * @param rs
	 * @param connection
	 * @param ps
	 */
	public static void close(ResultSet rs,Connection connection,PreparedStatement ps){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		close(connection, ps);
	}
}
