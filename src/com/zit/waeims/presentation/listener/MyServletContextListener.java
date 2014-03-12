package com.zit.waeims.presentation.listener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zit.waeims.dao.hibernate.HibernateUtil;

/**
 * web服务器启动销毁监听
 * @author 易君
 *
 */
public class MyServletContextListener implements ServletContextListener {
	// 日志记录器
	private static Log log = LogFactory.getLog(MyServletContextListener.class);
	/**销毁
	 * 
	 */
	public void contextDestroyed(ServletContextEvent event) {
		//销毁hiberate
		HibernateUtil.getSessionFactory().close();
		//释放其他资源，代码写在这
	}

	/**
	 * 启动
	 * 如果有其他需要在web服务器启动的工作，可以把代码写在这处理。
	 */
	public void contextInitialized(ServletContextEvent event) {
		try{
		//初始化hibernate
		log.debug("------------初始化hibernate------------");
		HibernateUtil.init();
		log.debug("------------hibernate初始化完成------------");
		//。。。。其他初始化工作
		log.info("-----------系统初始化工作完毕，系统成功启动----------");
		}catch (Throwable e) {
			log.error("系统初始化工作过程中发生异常",e);
		}
		
	}

}
