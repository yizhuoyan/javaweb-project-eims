<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

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
		<title>新增用户</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/common.css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/user/modPassword.js"></script>
	</head>

	<body>
		<div >
				<form action="user/modPassword.do" method="post"  >
				<table class="form">
					<thead>
						<tr>
							<th colspan="99">修改密码 </th>
						</tr>
					</thead>
					<tbody>
					<tr>
						<td class="ar">
							旧密码：
						</td>
						<td>
							<input name="oldPassword" type="password"  class="field"><b class="em">*</b>
						</td>
					</tr>
					<tr>
						<td class="ar">
							新密码：
						</td>
						<td>
						<input name="newPassword"  type="password" class="field"><b class="em">*</b>
						</td>
					</tr>
					<tr>
						<td class="ar">
							确认新密码：
						</td>
						<td>
						<input name="confirmNewPassword" type="password"  class="field"><b class="em">*</b>
						</td>
					</tr>
						
					</tbody>
					<tfoot>
					<tr>
						<td colspan="2" >
							<button type="button" onclick="modPasswordFormSubmit(this.form)" >确定</button>
							<button onclick="fCloseWindow(false)" type="button" >关闭</button>		
						</td>
					</tr>
					</tfoot>
				</table>
			</form>
		</div>
	</body>
</html>
