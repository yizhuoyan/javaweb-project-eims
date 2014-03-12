package com.zit.waeims.dao;

import java.util.List;

import com.zit.waeims.vo.RoleVO;
/**
 * 用户角色相关数据访问对象接口
 * @author ben
 *
 */
public interface RoleDao {
	/**
	 * 通过用户角色id查找用户角色对象
	 * @param id 用户角色id
	 * @return 用户角色对象
	 */
	RoleVO qryById(String id)throws Exception;
	/**
	 * 新增用户角色
	 * @param userVO 用户角色对象
	 * @return 新增的用户角色对象
	 */
	void addOrMod(RoleVO userVO)throws Exception;
	/**
	 * 通过用户角色id删除用户角色
	 * @param id 用户角色id
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
	long qryByKey(String key,int pageSize,int pageNo,List<RoleVO> pageData)throws Exception;
	
	
}
