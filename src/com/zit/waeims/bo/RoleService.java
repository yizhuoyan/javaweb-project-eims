package com.zit.waeims.bo;

import java.util.List;

import com.zit.core.PaginationResult;
import com.zit.waeims.vo.RoleVO;

public interface RoleService {
	/**
	 * 通过id查找用户角色
	 * @param id 用户角色id
	 * @return 用户角色对象
	 */
	RoleVO queryById(String id);
	/**
	 * 通过关键字查找用户角色
	 * @param key 查询关键字
	 * @return
	 */
	PaginationResult<List<RoleVO>> queryByKey(String key,int pageSize,int pageNo);
	/**
	 * 通过id删除用户角色
	 * @param id 用户角色id
	 */
	void delRole(String... ids);
	/**
	 * 修改用户角色
	 * @param modData 修改的字段
	 */
	RoleVO modRole(String id,RoleVO modData);
	/**
	 * 新增用户角色
	 * @param vo 用户角色信息
	 */
	void addRole(RoleVO vo);
}
