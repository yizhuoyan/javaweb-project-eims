package com.zit.waeims.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.zit.waeims.dao.UserDao;
import com.zit.waeims.dao.jdbc.JDBCUtil;
import com.zit.waeims.vo.UserVO;

/**
 * 用户相关数据访问对象接口实现类
 * 
 * @author ben
 * 
 */
public class ImplUserDao implements UserDao {

	
	
	public void addOrMod(UserVO vo)throws SQLException {
		StringBuilder sql=new StringBuilder();
		String id=vo.getId();
		if(id==null){//add
			sql.append("INSERT INTO SYS_USER (ID, ACCOUNT, CREATETIME, LASTMODPASSTIME, LASTMODTIME, NAME, PASSWORD, REMARK, READONLY, STATE)VALUES(?,?,?,?,?, ?,?,?,?,?)");
		}else{
			sql.append("UPDATE  (SELECT * FROM SYS_USER WHERE ID=?)  SET ACCOUNT=?,CREATETIME=?,LASTMODPASSTIME=?, LASTMODTIME=?, NAME=?, PASSWORD=?, REMARK=?, READONLY=?, STATE=?");
		}
		
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection();
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			int i=1;
			if(id==null){
				id=JDBCUtil.generateGUID(connection);
			}
			statement.setString(i++, id);
			statement.setString(i++, vo.getAccount());
			statement.setTimestamp(i++, new Timestamp(vo.getCreateTime().getTime()));
			statement.setTimestamp(i++, new Timestamp(vo.getLastModTime().getTime()));
			statement.setTimestamp(i++,	new Timestamp(vo.getLastModPassTime().getTime()));
			statement.setString(i++, vo.getName());
			statement.setString(i++, vo.getPassword());
			statement.setString(i++, vo.getRemark());
			statement.setBoolean(i++, vo.isReadOnly());
			statement.setInt(i++, vo.getState());
			statement.executeUpdate();
			connection.commit();
		} finally {
			JDBCUtil.close(connection);
		}
	}
	
	/**
	 * 通过id删除用户
	 */
	public void delById(String id) throws SQLException {
		Connection connection = null;
		try {
			if(id==null){
				return;
			}
			connection = JDBCUtil.getConnection();
			String sql="delete sys_user where id='"+id+"'";
			Statement statement=connection.createStatement();
			statement.executeUpdate(sql);
			connection.commit();
		} finally {
			JDBCUtil.close(connection);
		}
	}
	/**
	 * 通过账户查询用户
	 */
	public UserVO qryByAccount(String account)throws SQLException {
		UserVO vo = null;
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection();
			String sql = "select * from sys_user where account=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, account);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				vo=resultset2UserVO(resultSet);
			}
		} finally {
			JDBCUtil.close(connection);
		}
		return vo;
	}
	
	/**
	 * 判断账号是否已存在
	 */
	public boolean existAccount(String account)throws SQLException {
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection();
			String sql = "select count(*) from sys_user where account=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, account);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt(1)!=0;
		} finally {
			JDBCUtil.close(connection);
		}
	}
	/**
	 * 通过id查找用户
	 */
	public UserVO qryById(String id)throws SQLException {
		UserVO vo = null;
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection();
			String sql = "select * from sys_user where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				vo=resultset2UserVO(resultSet);
			}
		} finally {
			JDBCUtil.close(connection);
		}
		return vo;
	}
	/**
	 * 通过关键字模糊分页查询用户
	 */
	public long qryByKey(String key, int pageSize, int pageNo,List<UserVO> pageData) throws SQLException {
		Connection connection = null;
		int maxRow=0;
		try {
			connection = JDBCUtil.getConnection();
			if(key==null){
				key="%";
			}else{
				key="%"+key.trim()+"%";	
			}
			//构建查询满足查询条件的总记录数的sql
			StringBuilder countSql=new StringBuilder();
			countSql.append("SELECT count(*) FROM SYS_USER WHERE ACCOUNT LIKE ? OR NAME LIKE ? OR REMARK LIKE ? ");
			PreparedStatement statement = connection.prepareStatement(countSql.toString());
			statement.setString(1, key);
			statement.setString(2, key);
			statement.setString(3, key);
			ResultSet resultSet=statement.executeQuery();
			resultSet.next();
			maxRow=resultSet.getInt(1);
			//如果结果是0，则直接返回
			if(maxRow==0){
				return 0; 
			}
			//执行分页查询
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT Y.* FROM (")
					.append("SELECT X.*, ROWNUM RN	FROM (")
						.append("SELECT * FROM SYS_USER WHERE ACCOUNT LIKE ? OR NAME LIKE ? OR REMARK LIKE ? ORDER BY ACCOUNT")
					.append(") X");
			int beginCount=(pageNo-1)*pageSize+1;
			int endCount=pageNo*pageSize;
			sql.append(") Y").append(" WHERE RN BETWEEN ").append(beginCount).append(" AND ").append(endCount);
			statement = connection.prepareStatement(sql.toString());
			
			statement.setString(1, key);
			statement.setString(2, key);
			statement.setString(3, key);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				
				//新增到结果集中
				pageData.add(resultset2UserVO(resultSet));
			}
		}  finally {
			JDBCUtil.close(connection);
		}
		
		return maxRow;
	}
	
	private UserVO resultset2UserVO(ResultSet resultSet)throws SQLException{
		
		UserVO vo = new UserVO();
		String id = resultSet.getString("id");
		vo.setId(id);
		String account = resultSet.getString("account");
		vo.setAccount(account);
		String password = resultSet.getString("password");
		vo.setPassword(password);
		Date createtime = resultSet.getTimestamp("createtime");
		vo.setCreateTime(createtime);
		Date lastModPassTime = resultSet.getTimestamp("lastModPassTime");
		vo.setLastModPassTime(lastModPassTime);
		Date lastModTime = resultSet.getTimestamp("lastModTime");
		vo.setLastModTime(lastModTime);
		String name = resultSet.getString("name");
		vo.setName(name);
		int readonly = resultSet.getInt("readonly");
		vo.setReadOnly(readonly == 1 ? true : false);
		String remark = resultSet.getString("remark");
		vo.setRemark(remark);
		int state = resultSet.getInt("state");
		vo.setState(state);
		return vo;
	}
}
