package com.zit.waeims.dao;

import java.util.List;

import com.zit.waeims.vo.UserVO;
/**
 * 用户相关数据访问对象接口
 * @author ben
 *
 */
public interface UserDao {
	/**
	 * 通过账户查找用户对象
	 * @param account 用户账户
	 * @return 用户对象
	 */
	UserVO qryByAccount(String account)throws Exception;
	/**
	 * 通过用户id查找用户对象
	 * @param id 用户id
	 * @return 用户对象
	 */
	UserVO qryById(String id)throws Exception;
	/**
	 * 新增用户或修改用户信息
	 * @param userVO 用户对象
	 * @return 用户对象
	 */
	void addOrMod(UserVO userVO)throws Exception;
	/**
	 * 通过用户id删除用户
	 * @param id 用户id
	 * @return
	 */
	void delById(String id)throws Exception;
	/**
	 * 通过关键字模糊分页查询
	 * @param key 查询关键字
	 * @param pageSize 每页大小
	 * @param pageNo 页码
	 * @param pageData 存放每页数据的容器
	 * @return 查询结果的最大记录数
	 * @throws Exception
	 */
	long qryByKey(String key,int pageSize,int pageNo,List<UserVO> pageData)throws Exception;
	
	/**
	 * 检查用户账户是否存在
	 * @param account 目标账户
	 * @return 是否存在
	 * @throws Exception 
	 */
	boolean existAccount(String account)throws Exception;
	
}
