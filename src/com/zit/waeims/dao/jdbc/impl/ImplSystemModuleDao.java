package com.zit.waeims.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.zit.waeims.dao.SystemModuleDao;
import com.zit.waeims.dao.jdbc.JDBCUtil;
import com.zit.waeims.vo.SystemModuleVO;

/**
 * 系统功能模块相关数据访问对象接口实现类
 * 
 * @author ben
 * 
 */
public class ImplSystemModuleDao implements SystemModuleDao {

	
	public SystemModuleVO qryById(String id) throws SQLException {
		SystemModuleVO roleVO = null;
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection();
			String sql = "select * from sys_module where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				roleVO=resultset2SystemModuleVO(resultSet);
			}
		} finally {
			JDBCUtil.close(connection);
		}
		return roleVO;
	}
	private SystemModuleVO resultset2SystemModuleVO(ResultSet resultSet)throws SQLException{
		
		SystemModuleVO vo = new SystemModuleVO();
		String id = resultSet.getString("id");
		vo.setId(id);
		String name = resultSet.getString("name");
		vo.setName(name);
		String url = resultSet.getString("url");
		vo.setUrl(url);
		String icon = resultSet.getString("ICON");
		vo.setIcon(icon);
		String remark = resultSet.getString("remark");
		vo.setRemark(remark);
		int state = resultSet.getInt("state");
		vo.setState(state);
		return vo;
	} 

	public void addOrMod(SystemModuleVO vo) throws SQLException {
		StringBuilder sql=new StringBuilder();
		String id=vo.getId();
		if(id==null){//add
			
			sql.append("INSERT INTO sys_module (ID, NAME,URL,ICON,STATE,REMARK)VALUES(?,?,?,?,?)");
		}else{
			sql.append("UPDATE  (SELECT * FROM sys_module WHERE ID=?)  SET NAME=?,ICON=?,URL=?,STATE=?, REMARK=?");
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
			statement.setString(i++, vo.getName());
			statement.setString(i++, vo.getUrl());
			statement.setString(i++, vo.getIcon());
			statement.setInt(i++, vo.getState());
			statement.setString(i++, vo.getRemark());
			statement.executeUpdate();
			connection.commit();
		} finally {
			JDBCUtil.close(connection);
		}
	}

	public void delById(String id) throws SQLException {
		Connection connection = null;
		try {
			if(id==null){
				return;
			}
			connection = JDBCUtil.getConnection();
			String sql="delete sys_module where id='"+id+"'";
			Statement statement=connection.createStatement();
			statement.executeUpdate(sql);
			connection.commit();
		} finally {
			JDBCUtil.close(connection);
		}
	}

	public long qryByKey(String key, int pageSize, int pageNo,
			List<SystemModuleVO> pageData) throws SQLException {
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
			countSql.append("SELECT count(*) FROM sys_module WHERE NAME LIKE ? OR REMARK LIKE ? ");
			PreparedStatement statement = connection.prepareStatement(countSql.toString());
			statement.setString(1, key);
			statement.setString(2, key);
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
						.append("SELECT * FROM sys_module WHERE NAME LIKE ? OR REMARK LIKE ? ORDER BY NAME")
					.append(") X");
			int beginCount=(pageNo-1)*pageSize+1;
			int endCount=pageNo*pageSize;
			sql.append(") Y").append(" WHERE RN BETWEEN ").append(beginCount).append(" AND ").append(endCount);
			statement = connection.prepareStatement(sql.toString());
			
			statement.setString(1, key);
			statement.setString(2, key);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				//新增到结果集中
				pageData.add(resultset2SystemModuleVO(resultSet));
			}
		}  finally {
			JDBCUtil.close(connection);
		}
		
		return maxRow;
	}

	
}
