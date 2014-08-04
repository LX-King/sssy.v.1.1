package com.tyut.sssy.utils.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class LoginControlC3P0Util {
	/** 
     * 数据库连接池 使用C3P0数据库连接池 避免大量的数据库连接影响数据库性能 
     */  
    private static ComboPooledDataSource ds = new ComboPooledDataSource();  
    /** 
     * 数据库连接池初始化 
     */  
    static {  
        ds.setJdbcUrl("jdbc:microsoft:sqlserver://localhost:1433;DatabaseName=zdsy");
//    	ds.setJdbcUrl("jdbc:microsoft:sqlserver://134.16.22.189:1433;DatabaseName=sssy214040000");
        ds.setUser("sssy_user");
        ds.setPassword("Sssy-2012Server");
        
        // 配置
//        ds.setAcquireIncrement(15);  	// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
//        
//        ds.setAcquireRetryAttempts(5);	// 定义在从数据库获取新连接失败后重复尝试的次数。Default: 30
//        
//        ds.setAcquireRetryDelay(1000);	// 两次连接中间隔时间，单位毫秒。Default: 1000
//        
//        ds.setAutoCommitOnClose(false);	// 连接关闭时默认将所有未提交的操作回滚。Default: false
//        
//        ds.setBreakAfterAcquireFailure(false);	// 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效
//        										// 保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试
//        										// 获取连接失败后该数据源将申明已断开并永久关闭。Default: false
//        
//        ds.setCheckoutTimeout(0);  		// 当连接池用完时客户端调用getConnection()后等待获取新连接的时间，超时后将抛出
//        								// SQLException,如设为0则无限期等待。单位毫秒。Default: 0
//        
//        ds.setIdleConnectionTestPeriod(0);	// 每60秒检查所有连接池中的空闲连接。Default: 0 
//        
//        ds.setMaxIdleTime(60);  		// 最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0
        
        ds.setInitialPoolSize(20);  		// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3 
        
        ds.setMaxPoolSize(500);			// 连接池中保留的最大连接数。Default: 15 
        
        ds.setMinPoolSize(10);  
         
        try {  
            ds.setDriverClass("com.microsoft.jdbc.sqlserver.SQLServerDriver");  
        } catch (PropertyVetoException e) {  
  
        }  
        ds.setMaxStatements(0);  
    }  
    
    // 创建数据库连接
    public static Connection getConnection(){  
    	Connection conn = null;  
        try {  
            conn = ds.getConnection();  
        } catch (Exception e) {  
            e.printStackTrace();  
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
