<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
		<base href="<%=basePath%>">
		<title>唯爱·员工信息管理系统</title>
		<link rel="stylesheet" type="text/css" href="css/common.css">
		<link rel="stylesheet" type="text/css" href="css/login.css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript">
	window.onload = function() {
		$("@account")[0].focus();
		ifLoginFailed();
	}
	/**
	 * void
	 * 如果登录失败，则摇晃登录表单
	 */
	var ifLoginFailed = function() {
		if ($("messageBox")) {//消息box存在，则表示登录失败，摇晃form表单
			$.shake($("formSpan"));
		}

	}
</script>
	</head>

	<body>
		<div class="top">
			<h1 class="enName">
				EIMS
			</h1>
			<h2 class="cnName">
				<span class="a">唯爱</span><span class="b">●</span><span class="c">员工信息管理系统</span>
			</h2>
		</div>
		<div class="bottom">
			<form class="form" action="user/login.do" method="post">
				<c:if test="${sessionScope.loginMessage!=null}">
					<strong id="messageBox"> ${sessionScope.loginMessage} </strong>
					<c:remove var="loginMessage" scope="session"/>
				</c:if>



				<div class="inputs">
					<span id="formSpan"> <label>
							账号：
						</label> <input name="account" class="field"> <label class="ml2">
							密码：
						</label> <input type="password" name="password" class="field"> </span>
				</div>
				<button class="btn loginBtn" type="submit">
					登 录
				</button>
			</form>
		</div>

		<div class="copyright">
			@2000-2013 中天IT学院
		</div>
	</body>
</html>
