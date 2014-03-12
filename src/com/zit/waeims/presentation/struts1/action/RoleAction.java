/**
 * Oct 23, 2013
 * UserAction.java
 * 
 */
package com.zit.waeims.presentation.struts1.action;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.MappingDispatchAction;

import com.zit.core.PaginationResult;
import com.zit.core.exception.ThisSystemException;
import com.zit.util.ThisSystemUtil;
import com.zit.waeims.bo.RoleService;
import com.zit.waeims.bo.impl.ImplRoleService;
import com.zit.waeims.vo.RoleVO;

/**
 * @author 易君
 *
 */
public class RoleAction extends MappingDispatchAction {

	
	
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
			RoleService service = new ImplRoleService();
			//传入参数，调用业务层分页查询方法，获取分页查询结果
			PaginationResult<List<RoleVO>> paginationResult = service.queryByKey(key, pageSize, pageNo);
			if(!paginationResult.getHaveData()){
				request.setAttribute("message", "对不起，无法找到相关数据。");		
			}
			//保存分页数据到request域
			request.setAttribute("result", paginationResult);
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
			String icon=request.getParameter("icon");
			if(icon!=null){
				icon=URLDecoder.decode(icon,"utf-8");
			}
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
			RoleVO vo=new RoleVO();
			vo.setName(name);
			vo.setState(state);
			vo.setIcon(icon);
			vo.setRemark(remark);
			//调用业务层方法
			RoleService service=new ImplRoleService();
			service.addRole(vo);
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
			String icon=request.getParameter("icon");
			if(icon!=null){
				icon=URLDecoder.decode(icon,"utf-8");
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
			RoleVO vo = new RoleVO();
			vo.setName(name);
			vo.setState(state);
			vo.setIcon(icon);
			vo.setRemark(remark);
			// 调用业务层方法
			RoleService service = new ImplRoleService();
			vo=service.modRole(id,vo);
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
			RoleService service = new ImplRoleService();
			RoleVO vo = service.queryById(id);
			if(vo==null){
				throw new ThisSystemException("用户已被删除");
			}
			request.setAttribute("roleVO",vo);
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
			RoleService service=new ImplRoleService();
			service.delRole(ids);
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
