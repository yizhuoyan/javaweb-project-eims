/**
 * Oct 23, 2013
 * MyActionServlet.java
 * 
 */
package com.zit.waeims.presentation.struts1;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionServlet;

import com.zit.waeims.dao.hibernate.HibernateUtil;

/**
 * @author 易君
 *
 */
public class MyActionServlet extends ActionServlet {
	//统一字符编码
	private static String CHARACTERE_NCODING;
	@Override
	public void init() throws ServletException {
		super.init();
		CHARACTERE_NCODING=this.getInitParameter("characterEncoding");
		
	}

	protected void process(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		try {
			request.setCharacterEncoding(CHARACTERE_NCODING);
			response.setCharacterEncoding(CHARACTERE_NCODING);

			super.process(request, response);
		} finally {
			HibernateUtil.closeSession();
		}
	}

}
