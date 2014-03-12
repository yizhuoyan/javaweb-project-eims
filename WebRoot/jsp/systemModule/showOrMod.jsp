<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>" target="_self">
		<title>查看/修改系统功能模块</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/common.css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/systemModule/showOrMod.js"></script>
		
	</head>

	<body>
		<div>
			<c:if var="exsitVO" test="${requestScope.systemModuleVO!=null}" scope="page"  >
			
			
			<form action="systemModule/modify.do" method="post" onsubmit="return systemModuleFormSubmit(this),false" >
			<input name="id" type="hidden" value="${systemModuleVO.id}">
				<table class="form">
					<thead>
						<tr>
							<th colspan="99">查看/编辑系统功能模块 </th>
						</tr>
					</thead>
					<tbody>
					
					<tr>
						<td class="ar">
							系统功能模块名称：
						</td>
						<td>
							<input name="name" value="${systemModuleVO.name }" class="field"><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="ar">
							地址：
						</td>
						<td>
							<input name="url" value="${systemModuleVO.url }" class="field">
						</td>
					</tr>
					<tr>
						<td class="ar">
							图标：
						</td>
						<td>
							<input name="icon" value="${systemModuleVO.icon}" class="field">
						</td>
					</tr>
					<tr>
						<td class="ar">
							状态：
						</td>
						<td>
						<input name="state" value="1" type="radio" 
						
						<c:if test="${systemModuleVO.state==1}">
							checked="checked"
						</c:if>
						><label >正常</label>
						<input name="state" value="0" type="radio" 
						
						<c:if test="${systemModuleVO.state==0}">
							checked="checked"
						</c:if>
						
						><label >停用</label>
						</td>
					</tr>
						<tr>
						<td class="ar">
							备注：
						</td>
						<td>
						<textarea name="remark" rows="3" cols="16">${systemModuleVO.remark}</textarea>
						</td>
					</tr>
					</tbody>
					<tfoot>
					<tr>
						<td colspan="2" >
							<button type="submit" >修改</button>
							<button onclick="window.close();" type="button" >关闭</button>		
						</td>
					</tr>
					</tfoot>
				</table>
			</form>
			</c:if>
			<c:if test="${!exsitVO}">
				${message}
			</c:if>
		</div>
	</body>
</html>
