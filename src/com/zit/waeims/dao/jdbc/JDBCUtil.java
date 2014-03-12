package com.zit.waeims.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.zit.core.exception.ThisSystemException;
/**
 * 数据库帮助类
 * @author ben
 *
 */
public class JDBCUtil {
	private static final String USER_NAME="zit";
	private static final String DRIVER_CLASS="oracle.jdbc.OracleDriver";
	private  static final String URL="jdbc:oracle:thin:@127.0.0.1:1521:eims";
	private static final String PASSWORD="zit";
	
	//静态代码块用于初始化数据库连接工作
	static{
		init();
	}
	/**
	 * 数据库连接初始化工作
	 */
	private static final void init(){
		try {
			//加载数据库驱动
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			//如果没找到数据库驱动类，则抛出异常。
			throw new ThisSystemException("无法找到数据库驱动，请检查驱动jar包");
		}
	}
	/**
	 * 核心方法，获取一个数据库连接
	 * 数据库连接默认被设置为非自动提交的。
	 * @return 
	 * @throws SQLException 
	 */
	public static final Connection getConnection() throws SQLException{
		try {
			//从驱动管理类DriverManager中获取数据库连接
			Connection connection=DriverManager.getConnection(URL, USER_NAME, PASSWORD);
			//设置数据库连接为非自动提交
			connection.setAutoCommit(false);
			return connection;
		} catch (SQLException e) {
			throw new SQLException("无法获取数据库连接，请检查数据库名，用户名和密码",e);
		}
	}
	/**
	 * 核心方法，关闭数据库连接
	 * @param connection 需要关闭的连接
	 */
	public static final void close(Connection connection){
		try {
			if(connection!=null){
				connection.close();
			}
		} catch (SQLException e) {
			throw new ThisSystemException("关闭数据库连接发生异常。");
		}
	}
	/**
	 * 自动生成GUID
	 *@author 易君
	 * @return
	 */
	public static final String generateGUID(Connection connection)throws SQLException{
		PreparedStatement statement=null;
		ResultSet rs=null;
		
		try {
			String sql="select sys_guid() from dual";
			statement = connection.prepareStatement(sql);
			rs=statement.executeQuery();
			if(rs.next()){
				String guid=rs.getString(1);
				return guid;
			}
			
		}finally{
			rs.close();
			statement.close();
		}
		return null;
		
	}
	
}
