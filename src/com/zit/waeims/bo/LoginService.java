package com.zit.waeims.bo;


import com.zit.waeims.vo.UserVO;
/**
 * 登录相关服务
 * @author ben
 *
 */
public interface LoginService {
	/**
	 * 用户登录
	 * @param account 用户账户
	 * @param password 登录密码
	 * @return 用户对象
	 * @throws LoginException 登录异常
	 */
	UserVO userLogin(String account,String password) ;
	/**
	 * 退出登录
	 *@author 易君
	 * @param userId 用户退出登录
	 */
	void userLogout(String userId);
}
