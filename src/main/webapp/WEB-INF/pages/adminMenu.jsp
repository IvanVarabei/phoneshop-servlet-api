<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="admin menu">
	<div class="cart">
		<div class="container">
			<div class="title" align="center"><a href="${pageContext.request.contextPath}/advancedSearch">edit products</a></div>
			<div class="title" align="center"><a href="${pageContext.request.contextPath}/createProduct">create product</a></div>
			<div class="title" align="center"><a href="${pageContext.request.contextPath}/productReviewModeration">moderate comments</a></div>
		</div>
	</div>
</tags:master>