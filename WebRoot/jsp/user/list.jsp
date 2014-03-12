<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

		<title>用户列表</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<link rel="stylesheet" type="text/css" href="css/common.css">
		<link rel="stylesheet" type="text/css" href="css/user/list.css">
		<script type="text/javascript" src="js/common.js"></script>
		<script type="text/javascript" src="js/user/list.js"></script>
	</head>

	<body>

		<div class="nav">
			<a href="jsp/welcome.jsp">首页</a><span>»</span>用户管理
		</div>
		<div class="box radius">
			<div class="box-head radius">
				<div class="left">
					<label>用户列表</label>
					<span>┇</span>
					<a href="javascript:fAddUser()">新增</a>
					<span>┇</span>
					<a href="javascript:fDelUser()">删除</a>
					<span>┇</span>
				</div>
				<div class="right">

					<form id="queryForm" action="user/query.do" method="post">
						<input type="text" name="key" value="${key}" class="field">
						<input type="hidden" name="pageSize">
						<input type="hidden" name="pageNo">
						<input type="submit" value="查询">
					</form>
				</div>
			</div>
			<div class="box-body radius">
				<table class="dataList">
					<thead>
						<tr>
							<th width="14px">
								No.
							</th>
							<th width="13px">
								<input onclick="fToggleCheckRow()" title="全选/全不选"
									type="checkbox">
							</th>
							<th width="80px">
								操作
							</th>
							<th width="80px">
								账号
							</th>
							<th width="80px">
								姓名
							</th>
							<th width="160px">
								创建时间
							</th>
							<th>
								备注
							</th>
							<th width="30px">
								状态
							</th>
						</tr>
					</thead>

					<tbody >
						<c:if var="hasDate" test="${result!=null&&result.haveData}" scope="page">
							<c:forEach var="user" items="${result.data}" varStatus="rowIndex">
						<tr
							<c:if test="${rowIndex.index%2!=0}">
							class="odd"
						</c:if>
						>
							<td>
							
								${rowIndex.count}
							</td>
							<td>
								<input name="userCheckBox" type="checkbox" value="${user.id}">
							</td>
							<td>
								<a href="javascript:fShowOrMod('${user.id }')">查看/修改</a>
							</td>
							<td>
								${user.account }
							</td>
							<td>
								${user.name }
							</td>
							<td>
								<fmt:formatDate value="${user.createTime}" pattern="EE yyyy-MM-dd hh:mm:ss"/>
							</td>
							<td class="remark">
								${user.remark }
							</td>
							<td>
								<c:choose>
									<c:when test="${user.state==1 }">
										正常
									</c:when>
									<c:when test="${user.state==0 }">
										<b class="em">停用</b>
									</c:when>
								</c:choose>
							
							</td></tr>
							</c:forEach>
						</c:if>
						
						<c:if test="${!hasDate}">
							<tr>
								<td colspan="99">
									${requestScope.message}
								</td>
							</tr>
						</c:if>

					</tbody>
				</table>
			</div>
			<jsp:include page="/jsp/core/pagination.jsp" flush="true"></jsp:include>
		</div>
	</body>
</html>
