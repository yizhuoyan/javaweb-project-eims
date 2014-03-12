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
		<title>新增用户角色</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/common.css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/role/add.js"></script>
	</head>

	<body>
	
		<div >
			<form action="role/add.do" method="post">
				<table class="form">
					<thead>
						<tr>
							<th colspan="99">新增用户角色 </th>
						</tr>
					</thead>
					<tbody>
					
					<tr>
						<td class="ar">
							角色名称：
						</td>
						<td>
						<input name="name" value="${role.name}" class="field"><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="ar">
							图标：
						</td>
						<td>
							<input name="icon" value="${role.icon}" class="field">
						</td>
					</tr>
					<tr>
						<td class="ar">
							状态：
						</td>
						<td>
							<input id="nomalStateRadio" name="state" value="1" type="radio"  checked="checked">
							<label for="nomalStateRadio">正常</label>
							<input id="unUserStateRadio" name="state" value="0" type="radio">
							<label for="unUserStateRadio" >停用</label>
						</td>
					</tr>
						<tr>
						<td class="ar">
							备注：
						</td>
						<td>
						<textarea name="remark" rows="3" cols="16">${role.remark}</textarea>
						</td>
					</tr>
					</tbody>
					<tfoot>
					<tr>
						<td colspan="2" >
								<button type="button" onclick="roleFormSubmit(this.form)" >确定</button>
							<button onclick="fCloseWindow(false)" type="button" >关闭</button>		
						</td>
					</tr>
					</tfoot>
				</table>
			</form>
		</div>
	</body>
</html>
