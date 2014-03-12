package com.zit.waeims.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zit.core.PaginationResult;
import com.zit.core.exception.ThisSystemException;
import com.zit.util.ThisSystemUtil;
import com.zit.waeims.bo.UserService;
import com.zit.waeims.dao.UserDao;
import com.zit.waeims.dao.hibernate.HibernateUtil;
import com.zit.waeims.dao.hibernate.impl.HibernateImplUserDao;
import com.zit.waeims.vo.UserVO;

public class ImplUserService implements UserService {

	/**
	 * @see com.zit.waeims.bo.UserService#modPassword(com.zit.waeims.vo.UserVO, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void modPassword(String  userId, String oldPassword,
			String newPassword, String confirmNewPassword) {
		try{
			if(ThisSystemUtil.isNone(oldPassword)){
				throw new ThisSystemException("旧密码不能为空");
			}
			if(ThisSystemUtil.isNone(newPassword)){
				throw new ThisSystemException("新密码不能为空");
			}
			if(!newPassword.matches("^\\w{5,20}$")){
				throw new ThisSystemException("密码长度5-20位，且只能为数字，字母");
			}
			if(!newPassword.equals(confirmNewPassword)){
				throw new ThisSystemException("两次密码不一致");
			}
			HibernateUtil.beginTranscation();
			UserDao dao=new HibernateImplUserDao();
			UserVO vo=dao.qryById(userId);
			if(!oldPassword.equals(vo.getPassword())){
				throw new ThisSystemException("旧密码不正确");
			}
			vo.setPassword(newPassword);
			HibernateUtil.commitTranscation();
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 通过id查询用户
	 */
	public UserVO queryById(String id) {
		try{
			if(ThisSystemUtil.isNone(id)){
				throw new ThisSystemException("id参数不能为空"); 
			}
			UserDao userDao=new HibernateImplUserDao();
			UserVO userVO=userDao.qryById(id);
			return userVO;
		}catch (Throwable e) {
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 分页模糊查询
	 */
	public PaginationResult<List<UserVO>> queryByKey(String key,int pageSize,int pageNo) {
		try{
			//创建数据访问层对象
			UserDao userDao=new HibernateImplUserDao();
			//构建分页结果
			PaginationResult<List<UserVO>> paginationResult=new PaginationResult<List<UserVO>>();
			//把当前页码保存到分页结果
			paginationResult.setCurrentPageNo(pageNo);
			//把每页大小保存到分页结果中
			paginationResult.setPageSize(pageSize);
			//创建存放每页数据的容器
			List<UserVO> pageDate=new ArrayList<UserVO>(pageSize);
			//把查询关键字，每页大小，当前页码，每页数据容器传递给dao，并保存满足查询条件的记录数
			long maxRow=userDao.qryByKey(key, pageSize, pageNo,pageDate);
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
	 * 通过id删除用户
	 */
	public void delUser(String... ids) {
		try{
			UserDao userDao=new HibernateImplUserDao();
			HibernateUtil.beginTranscation();
			for (int i = 0; i < ids.length; i++) {
				//通过id找到此用户对象
				UserVO userVO=userDao.qryById(ids[i]);
				//如果用户为null，说明用户已经被其他用户删除掉了,那我们就不删除了
				if(userVO!=null){
					//如果用户是只读的，我们也不删除,仅删除非只读的。
					if(!userVO.isReadOnly()){
						userDao.delById(ids[i]);			
					}
				}
			}
			HibernateUtil.commitTranscation();
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 修改用户
	 */
	public UserVO modUser(String id,UserVO modUserVO) {
		try{
			if(ThisSystemUtil.isNone(id)){
				throw new ThisSystemException("id不能为空"); 
			}
			if(modUserVO==null){
				throw new ThisSystemException("modUserVO不能为空");
			}
			//创建数据访问层对象
			UserDao userDao=new HibernateImplUserDao();
			
			//找到旧用户对象
			UserVO oldUser=userDao.qryById(id);
			//如果旧对象不存在，则表示用户已被其他用户删除
			if(oldUser==null){
				throw new ThisSystemException("对不起,用户已被删除！");
			}
			//获取新的账号
			String newAccount=modUserVO.getAccount();
			if(ThisSystemUtil.isNone(newAccount)){
				throw new ThisSystemException("用户账号不能为空");
			}
			if(ThisSystemUtil.isNone(modUserVO.getName())){
				throw new ThisSystemException("用户姓名不能为空");
			}
			//如果账号已修改
			if(!newAccount.equals(oldUser.getAccount())){
				//判断新账号是否已存在
				if(userDao.existAccount(newAccount)){
					throw new ThisSystemException("对不起,账号已存在！");	
				}
				//设置新账号
				oldUser.setAccount(modUserVO.getAccount());
			}
			//设置用户最好修改时间为当前时间
			oldUser.setLastModTime(new Date());
			//设置用户姓名
			oldUser.setName(modUserVO.getName());
			//设置用户状态
			oldUser.setState(modUserVO.getState());
			//设置备注
			oldUser.setRemark(modUserVO.getRemark());
			//调用数据访问层执行更新
			HibernateUtil.beginTranscation();
			userDao.addOrMod(oldUser);
			HibernateUtil.commitTranscation();
			return oldUser;
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			throw ThisSystemException.create(e);
		}
	}
	/**
	 * 新增用户
	 */
	public void addUser(UserVO userVO) {
		try{
			if(userVO==null){
				throw new ThisSystemException("userVO不能为空"); 
			}
			
			String account=userVO.getAccount();
			if(ThisSystemUtil.isNone(account)){
				throw new ThisSystemException("用户账号不能为空");
			}
			UserDao userDao=new HibernateImplUserDao();
			if(userDao.existAccount(account)){
				throw new ThisSystemException("账号【"+account+"】已存在");
			}
			if(ThisSystemUtil.isNone(userVO.getName())){
				throw new ThisSystemException("用户姓名不能为空");
			}
			userVO.setPassword("123456");
			Date now=new Date();
			userVO.setCreateTime(now);
			userVO.setLastModPassTime(now);
			userVO.setLastModTime(now);
			userVO.setReadOnly(false);
			HibernateUtil.beginTranscation();
			userDao.addOrMod(userVO);
			HibernateUtil.commitTranscation();
		}catch (Throwable e) {
			HibernateUtil.rollbackTranscation();
			e.printStackTrace();
			throw ThisSystemException.create(e);
		}
	}
}
