package com.zit.waeims.dao;

import java.util.List;

import com.zit.waeims.vo.SystemModuleVO;
/**
 * 系统功能模块相关数据访问对象接口
 * @author ben
 *
 */
public interface SystemModuleDao {
	/**
	 * 通过系统功能模块id查找系统功能模块对象
	 * @param id 系统功能模块id
	 * @return 系统功能模块对象
	 */
	SystemModuleVO qryById(String id)throws Exception;
	/**
	 * 新增系统功能模块
	 * @param userVO 系统功能模块对象
	 * @return 新增的系统功能模块对象
	 */
	void addOrMod(SystemModuleVO userVO)throws Exception;
	/**
	 * 通过系统功能模块id删除系统功能模块
	 * @param id 系统功能模块id
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
	long qryByKey(String key,int pageSize,int pageNo,List<SystemModuleVO> pageData)throws Exception;
	
	
}
