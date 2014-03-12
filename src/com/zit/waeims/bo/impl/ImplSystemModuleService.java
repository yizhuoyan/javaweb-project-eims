package com.zit.waeims.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.zit.core.PaginationResult;
import com.zit.core.exception.ThisSystemException;
import com.zit.util.ThisSystemUtil;
import com.zit.waeims.bo.SystemModuleService;
import com.zit.waeims.dao.SystemModuleDao;
import com.zit.waeims.dao.hibernate.HibernateUtil;
import com.zit.waeims.dao.hibernate.impl.HibernateImplSystemModuleDao;
import com.zit.waeims.vo.SystemModuleVO;

public class ImplSystemModuleService implements SystemModuleService {

	/**
	 * 通过id查询系统功能模块
	 */
	public SystemModuleVO queryById(String id) {
		try{
			if(ThisSystemUtil.isNone(id)){
				throw new ThisSystemException("id参数不能为空"); 
			}
			SystemModuleDao dao=new HibernateImplSystemModuleDao();
			SystemModuleVO vo=dao.qryById(id);
			return vo;
		}catch (Throwable e) {
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 分页模糊查询
	 */
	public PaginationResult<List<SystemModuleVO>> queryByKey(String key,int pageSize,int pageNo) {
		try{
			//创建数据访问层对象
			SystemModuleDao dao=new HibernateImplSystemModuleDao();
			//构建分页结果
			PaginationResult<List<SystemModuleVO>> paginationResult=new PaginationResult<List<SystemModuleVO>>();
			//把当前页码保存到分页结果
			paginationResult.setCurrentPageNo(pageNo);
			//把每页大小保存到分页结果中
			paginationResult.setPageSize(pageSize);
			//创建存放每页数据的容器
			List<SystemModuleVO> pageDate=new ArrayList<SystemModuleVO>(pageSize);
			//把查询关键字，每页大小，当前页码，每页数据容器传递给dao，并保存满足查询条件的记录数
			long maxRow=dao.qryByKey(key, pageSize, pageNo,pageDate);
			//保存每页数据到分页结果中
			paginationResult.setData(pageDate);
			//把满足查询条件记录数保存到分页结果中
			paginationResult.setMaxRow(maxRow);
			//返回分页结果
			return paginationResult;
		}catch (Throwable e) {
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 通过id删除系统功能模块
	 */
	public void delSystemModule(String... ids) {
		try{
			SystemModuleDao dao=new HibernateImplSystemModuleDao();
			HibernateUtil.beginTranscation();
			for (int i = 0; i < ids.length; i++) {
				//通过id找到此系统功能模块对象
				SystemModuleVO vo=dao.qryById(ids[i]);
				//如果系统功能模块为null，说明系统功能模块已经被删除掉了,那我们就不删除了
				if(vo!=null){
						dao.delById(ids[i]);			
				}
			}
			HibernateUtil.commitTranscation();
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 修改系统功能模块
	 */
	public SystemModuleVO modSystemModule(String id,SystemModuleVO modVO) {
		try{
			if(ThisSystemUtil.isNone(id)){
				throw new ThisSystemException("id不能为空"); 
			}
			if(modVO==null){
				throw new ThisSystemException("modUserVO不能为空");
			}
			//创建数据访问层对象
			SystemModuleDao dao=new HibernateImplSystemModuleDao();
			//找到旧系统功能模块对象
			SystemModuleVO oldVO=dao.qryById(id);
			//如果旧对象不存在，则表示系统功能模块已被其他系统功能模块删除
			if(oldVO==null){
				throw new ThisSystemException("对不起,系统功能模块已被删除！");
			}
			if(ThisSystemUtil.isNone(modVO.getName())){
				throw new ThisSystemException("系统功能模块名称不能为空");
			}
			//设置系统功能模块姓名
			oldVO.setName(modVO.getName());
			oldVO.setIcon(modVO.getIcon());
			oldVO.setUrl(modVO.getUrl());
			//设置系统功能模块状态
			oldVO.setState(modVO.getState());
			//设置备注
			oldVO.setRemark(modVO.getRemark());
			//调用数据访问层执行更新
			HibernateUtil.beginTranscation();
			dao.addOrMod(oldVO);
			HibernateUtil.commitTranscation();
			return oldVO;
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 新增系统功能模块
	 */
	public void addSystemModule(SystemModuleVO vo) {
		try{
			if(vo==null){
				throw new ThisSystemException("userVO不能为空"); 
			}
			if(ThisSystemUtil.isNone(vo.getName())){
				throw new ThisSystemException("系统功能模块名称不能为空");
			}
			
			SystemModuleDao dao=new HibernateImplSystemModuleDao();
			HibernateUtil.beginTranscation();
			dao.addOrMod(vo);
			HibernateUtil.commitTranscation();
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
}
