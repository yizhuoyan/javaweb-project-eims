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
		<title>新增系统功能模块</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/common.css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/systemModule/add.js"></script>
	</head>

	<body>
	
		<div >
			<form action="systemModule/add.do" method="post"  >
				<table class="form">
					<thead>
						<tr>
							<th colspan="99">新增系统功能模块 </th>
						</tr>
					</thead>
					<tbody>
					
					<tr>
						<td class="ar">
							功能模块名称：
						</td>
						<td>
						<input name="name" value="${systemModule.name}" class="field"><b>*</b>
						</td>
					</tr>
					<tr>
						<td class="ar">
							地址：
						</td>
						<td>
							<input name="url" value="${systemModule.url}" class="field">
						</td>
					</tr>
					<tr>
						<td class="ar">
							图标：
						</td>
						<td>
							<input name="icon" value="${systemModule.icon}" class="field">
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
						<textarea name="remark" rows="3" cols="16">${systemModule.remark}</textarea>
						</td>
					</tr>
					</tbody>
					<tfoot>
					<tr>
						<td colspan="2" >
							<button type="button" onclick="systemModuleFormSubmit(this.form)" >确定</button>
							<button onclick="fCloseWindow(false)" type="button" >关闭</button>		
						</td>
					</tr>
					</tfoot>
				</table>
			</form>
		</div>
	</body>
</html>
