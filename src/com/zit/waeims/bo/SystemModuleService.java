package com.zit.waeims.bo;

import java.util.List;

import com.zit.core.PaginationResult;
import com.zit.waeims.vo.SystemModuleVO;

public interface SystemModuleService {
	/**
	 * 通过id查找系统功能模块
	 * @param id 系统功能模块id
	 * @return 系统功能模块对象
	 */
	SystemModuleVO queryById(String id);
	/**
	 * 通过关键字查找系统功能模块
	 * @param key 查询关键字
	 * @return
	 */
	PaginationResult<List<SystemModuleVO>> queryByKey(String key,int pageSize,int pageNo);
	/**
	 * 通过id删除系统功能模块
	 * @param id 系统功能模块id
	 */
	void delSystemModule(String... ids);
	/**
	 * 修改系统功能模块
	 * @param modData 修改的字段
	 */
	SystemModuleVO modSystemModule(String id,SystemModuleVO modData);
	/**
	 * 新增系统功能模块
	 * @param vo 系统功能模块信息
	 */
	void addSystemModule(SystemModuleVO vo);
}
