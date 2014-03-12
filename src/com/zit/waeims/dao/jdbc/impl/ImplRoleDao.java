package com.zit.waeims.dao.jdbc.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.zit.waeims.dao.RoleDao;
import com.zit.waeims.dao.jdbc.JDBCUtil;
import com.zit.waeims.vo.RoleVO;

/**
 * 用户角色相关数据访问对象接口实现类
 * 
 * @author ben
 * 
 */
public class ImplRoleDao implements RoleDao {

	/**
	 * @see com.zit.waeims.dao.RoleDao#qryById(java.lang.String)
	 */
	public RoleVO qryById(String id) throws SQLException {
		RoleVO vo = null;
		Connection connection = null;
		try {
			connection = JDBCUtil.getConnection();
			String sql = "select * from sys_role where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				vo=resultset2RoleVO(resultSet);
			}
		} finally {
			JDBCUtil.close(connection);
		}
		return vo;
	}
private RoleVO resultset2RoleVO(ResultSet resultSet)throws SQLException{
		
		RoleVO vo = new RoleVO();
		String id = resultSet.getString("id");
		vo.setId(id);
		String name = resultSet.getString("name");
		vo.setName(name);
		String icon = resultSet.getString("ICON");
		vo.setIcon(icon);
		String remark = resultSet.getString("remark");
		vo.setRemark(remark);
		int state = resultSet.getInt("state");
		vo.setState(state);
		return vo;
	} 

	/**
	 * @see com.zit.waeims.dao.RoleDao#addOrMod(com.zit.waeims.vo.RoleVO)
	 */
	public void addOrMod(RoleVO vo) throws SQLException {
		StringBuilder sql=new StringBuilder();
		String id=vo.getId();
		if(id==null){//add
			
			sql.append("INSERT INTO SYS_ROLE (ID, NAME,ICON,STATE,REMARK)VALUES(?,?,?,?,?)");
		}else{
			sql.append("UPDATE  (SELECT * FROM SYS_ROLE WHERE ID=?)  SET NAME=?,ICON=?,STATE=?, REMARK=?");
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
			statement.setString(i++, vo.getIcon());
			statement.setInt(i++, vo.getState());
			statement.setString(i++, vo.getRemark());
			statement.executeUpdate();
			connection.commit();
		} finally {
			JDBCUtil.close(connection);
		}
	}

	/**
	 * @see com.zit.waeims.dao.RoleDao#delById(java.lang.String)
	 */
	public void delById(String id) throws SQLException {
		Connection connection = null;
		try {
			if(id==null){
				return;
			}
			connection = JDBCUtil.getConnection();
			String sql="delete sys_role where id='"+id+"'";
			Statement statement=connection.createStatement();
			statement.executeUpdate(sql);
			connection.commit();
		} finally {
			JDBCUtil.close(connection);
		}
	}

	/**
	 * @see com.zit.waeims.dao.RoleDao#qryByKey(java.lang.String, int, int, java.util.List)
	 */
	public long qryByKey(String key, int pageSize, int pageNo,
			List<RoleVO> pageData) throws SQLException {
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
			countSql.append("SELECT count(*) FROM SYS_ROLE WHERE NAME LIKE ? OR REMARK LIKE ? ");
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
						.append("SELECT * FROM SYS_ROLE WHERE NAME LIKE ? OR REMARK LIKE ? ORDER BY NAME")
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
				pageData.add(resultset2RoleVO(resultSet));
			}
		}  finally {
			JDBCUtil.close(connection);
		}
		
		return maxRow;
	}

	
}
