package com.zit.waeims.bo;

import java.util.List;

import com.zit.core.PaginationResult;
import com.zit.waeims.vo.UserVO;

public interface UserService {
	
	void modPassword(String  userId,String oldPassword,String newPassword,String confirmNewPassword);
	/**
	 * 通过id查找用户
	 * @param id 用户id
	 * @return 用户对象
	 */
	UserVO queryById(String id);
	/**
	 * 通过关键字查找用户
	 * @param key 查询关键字
	 * @return
	 */
	PaginationResult<List<UserVO>> queryByKey(String key,int pageSize,int pageNo);
	/**
	 * 通过id删除用户
	 * @param id 用户id
	 */
	void delUser(String... ids);
	/**
	 * 修改用户
	 * @param modData 修改的字段
	 */
	UserVO modUser(String id,UserVO modData);
	/**
	 * 新增用户
	 * @param userVO 用户信息
	 */
	void addUser(UserVO userVO);
}
