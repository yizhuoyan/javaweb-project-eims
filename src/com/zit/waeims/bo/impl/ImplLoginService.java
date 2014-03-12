package com.zit.waeims.bo.impl;

import com.zit.core.exception.ThisSystemException;
import com.zit.util.ThisSystemUtil;
import com.zit.waeims.bo.LoginService;
import com.zit.waeims.dao.UserDao;
import com.zit.waeims.dao.hibernate.HibernateUtil;
import com.zit.waeims.dao.hibernate.impl.HibernateImplUserDao;
import com.zit.waeims.vo.UserVO;

/**
 * 登录服务接口实现类
 * 
 * @author ben
 * 
 */
public class ImplLoginService implements LoginService {

	/**
	 * @see com.zit.waeims.bo.LoginService#userLogout(java.lang.String)
	 */
	public void userLogout(String userId) {
		try {
			
			UserDao userDao = new HibernateImplUserDao();
			UserVO userVO = userDao.qryById(userId);
			//记录日志
			
		}catch (Throwable e) {
			throw ThisSystemException.create(e);
		}
	}

	public UserVO userLogin(String account, String password) {
		try {
			if (ThisSystemUtil.isNone(account)) {
				throw new ThisSystemException("账户不能为空");
			}
			UserDao userDao = new HibernateImplUserDao();
			UserVO userVO = userDao.qryByAccount(account);
			if (userVO == null) {
				throw new ThisSystemException("账户和密码不匹配");
			}
			if (!password.equals(userVO.getPassword())) {
				throw new ThisSystemException("账户和密码不匹配");
			}
			return userVO;
		
		}catch (Throwable e) {
			throw ThisSystemException.create(e);
		}

	}

}
