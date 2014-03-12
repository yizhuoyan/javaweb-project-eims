<%@page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Pagging -->
<div class="box-foot">
	<div class="total">
		<span>每页<input id="pageSizeInput" class="smallInput"
				value="${result.pageSize }">条</span>
		<span>共<big>${result.maxPageNo}</big>页</span>
		<span>跳到<input id="jumpPageNoInput"
				onblur="checkValid(this,${result.maxPageNo})" class="smallInput">页</span>
		<span><a class="btn" href="javascript:fJumpPage()">GO</a> </span>
	</div>
	<div class="jumping">

		<a
			<c:if test="${result.currentPageNo!=1 }">
						class="active" href="javascript:fJumpPage(1)"
					</c:if>>第一页</a>
		<a
			<c:if test="${result.currentPageNo!=1 }">
						class="active"
						href="javascript:fJumpPage(${result.currentPageNo-1})"
					</c:if>>上一页</a>
		<span> ${result.currentPageNo} </span>
		<a
			<c:if test="${result.currentPageNo!=result.maxPageNo }">
					class="active"
						href="javascript:fJumpPage(${result.currentPageNo+1 })"
					
					</c:if>>下一页</a>

		<a
			<c:if test="${result.currentPageNo!=result.maxPageNo }">
					class="active"
						href="javascript:fJumpPage(${result.maxPageNo})" 
						
						

					</c:if>>最末页</a>
	</div>
</div>
<!-- Pagging end-->
