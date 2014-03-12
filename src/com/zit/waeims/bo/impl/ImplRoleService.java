package com.zit.waeims.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.zit.core.PaginationResult;
import com.zit.core.exception.ThisSystemException;
import com.zit.util.ThisSystemUtil;
import com.zit.waeims.bo.RoleService;
import com.zit.waeims.dao.RoleDao;
import com.zit.waeims.dao.hibernate.HibernateUtil;
import com.zit.waeims.dao.hibernate.impl.HibernateImplRoleDao;
import com.zit.waeims.vo.RoleVO;

public class ImplRoleService implements RoleService {

	/**
	 * 通过id查询用户角色
	 */
	public RoleVO queryById(String id) {
		try{
			if(ThisSystemUtil.isNone(id)){
				throw new ThisSystemException("id参数不能为空"); 
			}
			RoleDao roleDao=new HibernateImplRoleDao();
			RoleVO vo=roleDao.qryById(id);
			return vo;
		}catch (Throwable e) {
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 分页模糊查询
	 */
	public PaginationResult<List<RoleVO>> queryByKey(String key,int pageSize,int pageNo) {
		try{
			//创建数据访问层对象
			RoleDao roleDao=new HibernateImplRoleDao();
			//构建分页结果
			PaginationResult<List<RoleVO>> paginationResult=new PaginationResult<List<RoleVO>>();
			//把当前页码保存到分页结果
			paginationResult.setCurrentPageNo(pageNo);
			//把每页大小保存到分页结果中
			paginationResult.setPageSize(pageSize);
			//创建存放每页数据的容器
			List<RoleVO> pageDate=new ArrayList<RoleVO>(pageSize);
			//把查询关键字，每页大小，当前页码，每页数据容器传递给dao，并保存满足查询条件的记录数
			long maxRow=roleDao.qryByKey(key, pageSize, pageNo,pageDate);
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
	 * 通过id删除用户角色
	 */
	public void delRole(String... ids) {
		try{
			RoleDao roleDao=new HibernateImplRoleDao();
			HibernateUtil.beginTranscation();
			for (int i = 0; i < ids.length; i++) {
				//通过id找到此用户角色对象
				RoleVO vo=roleDao.qryById(ids[i]);
				//如果用户角色为null，说明用户角色已经被其他用户角色删除掉了,那我们就不删除了
				if(vo!=null){
						roleDao.delById(ids[i]);			
				}
			}
			HibernateUtil.commitTranscation();
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 修改用户角色
	 */
	public RoleVO modRole(String id,RoleVO modVO) {
		try{
			if(ThisSystemUtil.isNone(id)){
				throw new ThisSystemException("id不能为空"); 
			}
			if(modVO==null){
				throw new ThisSystemException("modUserVO不能为空");
			}
			//创建数据访问层对象
			RoleDao roleDao=new HibernateImplRoleDao();
			//找到旧用户角色对象
			RoleVO oldVO=roleDao.qryById(id);
			//如果旧对象不存在，则表示用户角色已被其他用户角色删除
			if(oldVO==null){
				throw new ThisSystemException("对不起,用户角色已被删除！");
			}
			if(ThisSystemUtil.isNone(modVO.getName())){
				throw new ThisSystemException("用户角色名称不能为空");
			}
			//设置用户角色姓名
			oldVO.setName(modVO.getName());
			oldVO.setIcon(modVO.getIcon());
			//设置用户角色状态
			oldVO.setState(modVO.getState());
			//设置备注
			oldVO.setRemark(modVO.getRemark());
			//调用数据访问层执行更新
			HibernateUtil.beginTranscation();
			roleDao.addOrMod(oldVO);
			HibernateUtil.commitTranscation();
			return oldVO;
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 新增用户角色
	 */
	public void addRole(RoleVO vo) {
		try{
			if(vo==null){
				throw new ThisSystemException("userVO不能为空"); 
			}
			if(ThisSystemUtil.isNone(vo.getName())){
				throw new ThisSystemException("用户角色名称不能为空");
			}
			RoleDao roleDao=new HibernateImplRoleDao();
			HibernateUtil.beginTranscation();
			roleDao.addOrMod(vo);
			HibernateUtil.commitTranscation();
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
}
