<%@page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>唯爱·员工信息管理系统</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/common.css">
		<link rel="stylesheet" type="text/css" href="css/index.css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/index.js"></script>
	</head>
	<body>
		<div class="head">

			<div class="logo">
				<h1>
					EIMS
				</h1>
				<h2>
					唯爱·员工信息管理系统
				</h2>
			</div>
			<div class="nav">
				欢迎您
				<a href="#"><b>
				${sessionScope.currentUser.name}
				</b></a>
				<span>┇</span>
				<a href="javascript:fModPassword()">修改密码</a>
				<span>┇</span>
				<a href="javascript:">帮助</a>
				<span>┇</span>
				<a href="user/logout.do">退出</a>
			</div>
		</div>
		<div class="body">
			<div class="menu">
				<ul id="menu">
					<li url="user/manage.do" >
						<strong>✧</strong>用户管理
					</li>
					<li url="role/manage.do">
						<strong>✧</strong>角色管理
					</li>
					<li url="systemModule/manage.do">
						<strong>✧</strong>功能模块管理
					</li>
				</ul>
			</div>
			<div class="container">
				<div class="frameBox">
					<iframe id="mainFrame" width="100%" height="100%" src="jsp/welcome.jsp" frameborder="0"  name="mainFrame"></iframe>
				</div>
			</div>
			
		</div>

	</body>
</html>