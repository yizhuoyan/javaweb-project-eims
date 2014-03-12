/**
 * Oct 23, 2013
 * UserAction.java
 * 
 */
package com.zit.waeims.presentation.struts1.action;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.zit.core.PaginationResult;
import com.zit.core.exception.ThisSystemException;
import com.zit.util.ThisSystemUtil;
import com.zit.waeims.bo.LoginService;
import com.zit.waeims.bo.UserService;
import com.zit.waeims.bo.impl.ImplLoginService;
import com.zit.waeims.bo.impl.ImplUserService;
import com.zit.waeims.vo.UserVO;

/**
 * @author 易君
 *
 */
public class UserAction extends MappingDispatchAction {

	
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			// 获取会话
		HttpSession httpSession = request.getSession();
		try {
			
			String account = request.getParameter("account");
			String password = request.getParameter("password");
			if (ThisSystemUtil.isNone(account, password)) {
				throw new ThisSystemException("账户或密码不能为空");
			}
			LoginService loginService = new ImplLoginService();
			UserVO userVO = loginService.userLogin(account, password);
			// 保存当前用户账户信息到httpSession域，用于用户会话期间。
			httpSession.setAttribute("currentUser", userVO);
			// 用户名密码正确，重定向到主页面
			return mapping.findForward("loginSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			// 把登录失败的消息保存到httpSession域，用于在jsp页面给用户提示
			httpSession.setAttribute("loginMessage", e.getMessage());
			
		}
		// 重定向到login.jsp
		return mapping.findForward("loginFailed");
	}
	/**
	 * 退出登录
	 *@author 易君
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public synchronized ActionForward  logout(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			// 获取会话
		HttpSession httpSession = request.getSession();
		try {
			UserVO currentUser=(UserVO)httpSession.getAttribute("currentUser");
			if(currentUser!=null){//==null 说明session已经失效，不做处理
				// 在httpSession域删除当前用户账户。
				httpSession.removeAttribute("currentUser");
				LoginService loginService = new ImplLoginService();
				loginService.userLogout(currentUser.getId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 重定向到login.jsp
		return mapping.findForward("logout");
	}
	public ActionForward manage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return query(mapping, form, request, response);
		
	}
	
	public ActionForward query(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
		try {
			//获取查询关键字
			String key = request.getParameter("key");
			//默认为空，且去除空白字符
			key=key==null?"":key.trim();
			//保存查询关键字到request域，用于页面展示。
			request.setAttribute("key", key);
			//获取分页大小参数
			String pageSizeStr=request.getParameter("pageSize");
			//默认为10
			if(ThisSystemUtil.isNone(pageSizeStr)){
				pageSizeStr="10";
			}
			//转换为int型
			int pageSize=Integer.parseInt(pageSizeStr);
			//获取查询页码
			String pageNoStr=request.getParameter("pageNo");
			//默认为第一页
			if(ThisSystemUtil.isNone(pageNoStr)){
				pageNoStr="1";
			}
			//转换为int型
			int pageNo=Integer.parseInt(pageNoStr);
			//创建业务层对象
			UserService service = new ImplUserService();
			//传入参数，调用业务层分页查询方法，获取分页查询结果
			PaginationResult<List<UserVO>> paginationResult = service.queryByKey(key, pageSize, pageNo);
			if(!paginationResult.getHaveData()){
				request.setAttribute("message", "对不起，无法找到相关数据。");		
			}
			//保存分页数据到request域
			request.setAttribute("result", paginationResult);
			//请求内部跳转到jsp，进行数据展示。
			
		} catch (Exception e) {
			request.setAttribute("message", e.getMessage());
		}
		return mapping.findForward("querySuccess");
		
	}

	
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out=null;
		try {
			out=response.getWriter();
			//获取参数
			String account=request.getParameter("account");
			
			String name=request.getParameter("name");
			if(name!=null){
				name=URLDecoder.decode(name,"utf-8");
			}
			
			String stateStr=request.getParameter("state");
			//格式化参数
			int state=0;
			if(stateStr.equals("1")){
				state=1;
			}
			String remark=request.getParameter("remark");
			if(remark!=null){
				remark=URLDecoder.decode(remark,"utf-8");
			}
			UserVO userVO=new UserVO();
			userVO.setAccount(account);
			userVO.setName(name);
			userVO.setState(state);
			userVO.setRemark(remark);
			//调用业务层方法
			UserService service=new ImplUserService();
			service.addUser(userVO);
			out=response.getWriter();
			out.append("{state:true,message:\"新增成功！\"}");
			//保存新增用户信息到request域中，便于jsp页面显示处理。
		} catch (Exception e) {
			out.append("{state:false,message:\"").append(e.getMessage())
			.append("\"}");
		}finally{
			out.close();
		}
			return null;
	}
	public ActionForward modPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out=null;
		try {
			out=response.getWriter();
			//获取参数
			String oldPassword=request.getParameter("oldPassword");
			
			String newPassword=request.getParameter("newPassword");
			
			String confirmNewPassword=request.getParameter("confirmNewPassword");
			
			HttpSession httpSession=request.getSession();
			
			UserVO currentUser=(UserVO)httpSession.getAttribute("currentUser");
			//调用业务层方法
			UserService service=new ImplUserService();
			service.modPassword(currentUser.getId(), oldPassword, newPassword, confirmNewPassword);
			out=response.getWriter();
			out.append("{state:true,message:\"修改成功，请重新登录！\"}");
			//保存新增用户信息到request域中，便于jsp页面显示处理。
		} catch (Exception e) {
			out.append("{state:false,message:\"").append(e.getMessage())
			.append("\"}");
		}finally{
			out.close();
		}
			return null;
	}
	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out=null;
		try {
			out=response.getWriter();
			// 获取参数
			String id=request.getParameter("id");
			if(ThisSystemUtil.isNone(id)){
				throw new ThisSystemException("id不能为空");
			}
			
			String account = request.getParameter("account");
			String name = request.getParameter("name");
			if(name!=null){
				name=URLDecoder.decode(name,"utf-8");
			}
			String stateStr = request.getParameter("state");
			// 格式化参数
			int state = 0;
			if (stateStr.equals("1")) {
				state = 1;
			}
			String remark = request.getParameter("remark");
			if(remark!=null){
				remark=URLDecoder.decode(remark,"utf-8");
			}
			UserVO userVO = new UserVO();
			userVO.setAccount(account);
			userVO.setName(name);
			userVO.setState(state);
			userVO.setRemark(remark);
			// 调用业务层方法
			UserService service = new ImplUserService();
			userVO=service.modUser(id,userVO);
			out=response.getWriter();
			out.append("{state:true,message:\"修改成功！\"}");
			//保存新增用户信息到request域中，便于jsp页面显示处理。
		} catch (Exception e) {
			out.append("{state:false,message:\"").append(e.getMessage())
			.append("\"}");
		}finally{
			out.close();
		}
		return null;
	}

	public ActionForward show(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String id = request.getParameter("id");
			if (ThisSystemUtil.isNone(id)) {
				throw new ThisSystemException("id参数不能为空");
			}
			UserService service = new ImplUserService();
			UserVO userVO = service.queryById(id);
			if(userVO==null){
				throw new ThisSystemException("用户已被删除");
			}
			request.setAttribute("userVO",userVO);
		} catch (Exception e) {
			request.setAttribute("message", e.getMessage());
		}
		return mapping.findForward("show");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
	PrintWriter out=null;
	try {
			out=response.getWriter();
			//获取参数
			
			String idstr=request.getParameter("ids");
			if(idstr==null){
				throw new IllegalArgumentException("ids参数不能为空");
			}
			String[] ids=idstr.split(",");
			//调用业务层方法
			UserService service=new ImplUserService();
			service.delUser(ids);
			out=response.getWriter();
			out.append("{state:true,message:\"删除成功！\"}");
			//保存新增用户信息到request域中，便于jsp页面显示处理。
		} catch (Exception e) {
			out.append("{state:false,message:\"").append(e.getMessage())
			.append("\"}");
		}finally{
			out.close();
		}
		return null;
	
	}
	
	
	
	
	
	
}
