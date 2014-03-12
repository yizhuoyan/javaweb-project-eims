/**
 * Nov 4, 2013
 * UserDaoImpl.java
 * 
 */
package com.zit.waeims.dao.hibernate.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.zit.core.exception.ThisSystemException;
import com.zit.util.ThisSystemUtil;
import com.zit.waeims.dao.UserDao;
import com.zit.waeims.dao.RoleDao;
import com.zit.waeims.dao.hibernate.HibernateUtil;
import com.zit.waeims.vo.RoleVO;

/**
 * @author 易君
 * 
 */
public class HibernateImplRoleDao implements RoleDao {

	/**
	 * HibernateException
	 * @see com.zit.waeims.dao.UserDao#qryByAccount(java.lang.String)
	 */
	public RoleVO qryByAccount(String account) {
			Session session = HibernateUtil.getSession();
			Query query = session.createQuery(
					"from " + RoleVO.class.getName() + " where account=?")
					.setString(0, account);
			return (RoleVO) query.uniqueResult();
	}

	/**
	 * @see com.zit.waeims.dao.UserDao#qryById(java.lang.String)
	 */
	public RoleVO qryById(String id) {
			Session session = HibernateUtil.getSession();
			return (RoleVO) session.load(RoleVO.class,id);
	}

	/**
	 * @see com.zit.waeims.dao.UserDao#addOrMod(com.zit.waeims.vo.RoleVO)
	 */
	public void addOrMod(RoleVO userVO) {
			Session session = HibernateUtil.getSession();
			session.saveOrUpdate(userVO);
	}

	/**
	 * @see com.zit.waeims.dao.UserDao#delById(java.lang.String)
	 */
	public void delById(String id) {
			Session session = HibernateUtil.getSession();
			RoleVO userVO=(RoleVO) session.load(RoleVO.class, id);
			session.delete(userVO);
			
	}

	/**
	 * @see com.zit.waeims.dao.UserDao#qryByKey(java.lang.String, int, int,
	 *      java.util.List)
	 */
	public long qryByKey(String key, int pageSize, int pageNo,
			List<RoleVO> pageData) {
		Session session = HibernateUtil.getSession();
		StringBuilder querySQL=new StringBuilder();
		querySQL.append("from ").append(RoleVO.class.getName());
		if(!ThisSystemUtil.isNone(key)){
			querySQL.append(" where  name like :key or remark like :key ");
		}
		//创建查询满足查询条件的hql query
		Query query=session.createQuery("select count(*) "+querySQL.toString());
		if(!ThisSystemUtil.isNone(key)){
			query.setString("key", "%"+key+"%");
		}
		long totalCount=(Long) query.uniqueResult();
		//没有满足查询条件的记录，直接返回0
		if(totalCount==0){
			return 0;
		}
		//创建查询分页数据的hql query
		query=session.createQuery(querySQL.toString());
		//设置查询参数
		if(!ThisSystemUtil.isNone(key)){
			query.setString("key", "%"+key+"%");
		}
		//设置开始序号
		query.setFirstResult((pageNo-1)*pageSize);
		//设置每页最大结果集
		query.setMaxResults(pageSize);
		//把查询的分页结果集放入业务层传过来的分页容器中
		pageData.addAll(query.list());
		return totalCount;
	}


}
