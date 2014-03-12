package com.zit.waeims.dao.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * hiberate工具类，提供hibernate初始化和获取/关闭session的工具方法
 * 
 * @author 易君
 * 
 */
public class HibernateUtil {
	// 日志记录器
	private static Log log = LogFactory.getLog(HibernateUtil.class);
	// hiberante配置文件路径
	private static String CONFIG_FILE_LOCATION = "/hibernate.cfg.xml";
	// 线程变量，用于保存session，使每个线程仅适用一个session
	private static final ThreadLocal<Session> THREAD_LOCAL = new ThreadLocal<Session>();
	// sessionFactory对象
	private static org.hibernate.SessionFactory sessionFactory;

	/**
	 * 获取session，每个线程一个
	 * 
	 * @author 易君
	 * @return
	 * @throws HibernateException
	 */
	public static Session getSession() throws HibernateException {
		Session session = THREAD_LOCAL.get();
		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				throw new HibernateException("hiberante还未初始化");
			}
			session = sessionFactory.openSession();
			THREAD_LOCAL.set(session);
		}
		return session;
	}
	/**
	 * 工具方法，开启事务
	 *@author 易君
	 */
	public static Transaction beginTranscation() {
		return getSession().beginTransaction();
	}
	/**
	 * 工具方法，提交当前线程中的session的事务
	 *@author 易君
	 */
	public static void commitTranscation() {
		Session session = THREAD_LOCAL.get();
		if (session != null) {
			Transaction transaction=session.getTransaction();
			if(transaction!=null&&transaction.isActive()){
				transaction.commit();
			}
		}
	}
	/**
	 * 工具方法，回滚当前线程中的session的事务
	 *@author 易君
	 */
	public static void rollbackTranscation() {
		Session session = THREAD_LOCAL.get();
		if (session != null) {
			Transaction transaction=session.getTransaction();
			if(transaction!=null&&transaction.isActive()){
				transaction.rollback();
			}
		}
	}
	/**
	 * 初始化hibernate
	 * 
	 */
	public static void init() {
		try {
			if (sessionFactory != null) {
				return;
			}
			Configuration configuration = new Configuration();
			configuration.configure(CONFIG_FILE_LOCATION);
			sessionFactory = configuration.buildSessionFactory();
		} catch (Throwable e) {
			log.error("初始化hibernate出现异常", e);
		}
	}

	/**
	 * 关闭当前线程中的session
	 * 
	 * @throws HibernateException
	 */
	public static void closeSession() throws HibernateException {
		Session session = THREAD_LOCAL.get();
		THREAD_LOCAL.set(null);
		if (session != null) {
			session.close();
		}
	}

	/**
	 * return session factory
	 */
	public static org.hibernate.SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}