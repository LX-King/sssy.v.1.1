package com.tyut.sssy.utils.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcUtil {
	
	//加载驱动
	static{
		String driver = "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		try{
			Class.forName(driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	private static final String USERNAME="test";
	private static final String PASSWORD="123";
	private static final String URL="jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=sssy214040000";
	
	//连接数据库
	public static Connection getConnection(){
		Connection conn=null;
		try {	
			conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("连接错误！");
		}
		return conn;
	}
	
	//关闭数据库
	public static void close(Statement stmt,ResultSet rs,Connection conn){
		try {
			if(stmt!=null)
				stmt.close();
			if(rs!=null)
				rs.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("关闭失败！");
		}
	}
}
