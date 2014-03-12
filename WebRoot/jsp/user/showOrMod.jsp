<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>查看/修改用户</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/common.css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/user/showOrMod.js"></script>

	</head>

	<body>
		<div>
			<c:if test="${userVO!=null}" var="exsitVO" scope="page">
				<form action="user/modify.do" method="post"
					onsubmit="return userFormSubmit(this),false">
					<input name="id" type="hidden" value="${userVO.id}">
					<table class="form">
						<thead>
							<tr>
								<th colspan="99">
									查看/编辑用户
								</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="ar">
									账号：
								</td>
								<td>
									<input name="account" value="${userVO.account}" class="field">
									<b>*</b>
								</td>
							</tr>
							<tr>
								<td class="ar">
									姓名：
								</td>
								<td>
									<input name="name" value="${userVO.name }" class="field">
									<b>*</b>
								</td>
							</tr>
							<tr>
								<td class="ar">
									状态：
								</td>
								<td>
									<input name="state" value="1" type="radio"
										<c:if test="${userVO.state==1}">
							checked="checked"
						</c:if>>
									<label>
										正常
									</label>
									<input name="state" value="0" type="radio"
										<c:if test="${userVO.state==0}">
							checked="checked"
						</c:if>>
									<label>
										停用
									</label>
								</td>
							</tr>
							<tr>
								<td class="ar">
									备注：
								</td>
								<td>
									<textarea name="remark" rows="3" cols="16">${userVO.remark}</textarea>
								</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<td colspan="2">
									<button type="submit">
										修改
									</button>
									<button onclick="window.close();" type="button">
										关闭
									</button>
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
