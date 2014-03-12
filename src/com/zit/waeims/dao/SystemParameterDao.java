package com.zit.waeims.dao;

import java.util.List;

import com.zit.waeims.vo.SystemParameterVO;
/**
 * 系统参数相关数据访问对象接口
 * @author ben
 *
 */
public interface SystemParameterDao {
	/**
	 * 通过系统参数id查找系统参数对象
	 * @param id 系统参数id
	 * @return 系统参数对象
	 */
	SystemParameterVO qryById(String id)throws Exception;
	/**
	 * 新增系统参数或修改系统参数信息
	 * @param vo 系统参数对象
	 * @return 系统参数对象
	 */
	void addOrMod(SystemParameterVO vo)throws Exception;
	/**
	 * 通过系统参数id删除系统参数
	 * @param id 系统参数id
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
	long qryByKey(String key,int pageSize,int pageNo,List<SystemParameterVO> pageData)throws Exception;
	
	/**
	 * 检查系统参数名称是否存在
	 * @param paramName 目标名称
	 * @return 是否存在
	 * @throws Exception 
	 */
	boolean existParameter(String paramName)throws Exception;
	
}
