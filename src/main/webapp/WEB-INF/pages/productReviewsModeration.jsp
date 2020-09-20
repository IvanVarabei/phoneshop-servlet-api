<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productReviews" type="java.util.List" scope="request"/>
<tags:master pageTitle="Moderation">
	<c:if test="${not empty param.message}">
		<div class="add_info add_success">
			<div class="container">
				<div class="add_message">${param.message}</div>
			</div>
		</div>
	</c:if>
	<div class="cart">
	<div class="container">
	<p class="title">Admin page. Product reviews moderation.</p>
	<div class="title"><c:if test="${productReviews.isEmpty()}">There are no reviews</c:if></div>
	<c:if test="${!productReviews.isEmpty()}">
	<form method="post">
		<table class="cart_table">
			<thead>
			<tr>
				<td>Author</td>
				<td>Rating</td>
				<td>Comment</td>
			</tr>
			</thead>
			<c:forEach var="review" items="${productReviews}">
				<tr>
					<td>${review.author}</td>
					<td> ${review.rating}</td>
					<td>${review.comment}</td>
					<td>
						<button class="details_add" formaction="${
               pageContext.servletContext.contextPath}/productReviewModeration/${review.id}?action=approve">
							Approve
						</button>
					</td>
					<td>
						<button class="details_add" formaction="${
               pageContext.servletContext.contextPath}/productReviewModeration/${review.id}">
							Reject
						</button>
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
	</c:if>
	</div>
	</div>
</tags:master>
