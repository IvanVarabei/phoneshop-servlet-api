<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.entity.Product" scope="request"/>
<tags:master pageTitle='${product.description}'>
	<c:if test="${not empty requestScope.error}">
		<div class="add_info add_error">
			<div class="container">
				<div class="add_message">${requestScope.error}</div>
			</div>
		</div>
	</c:if>
	<c:if test="${not empty param.message}">
		<div class="add_info add_success">
			<div class="container">
				<div class="add_message">${param.message}</div>
			</div>
		</div>
	</c:if>
	<div class="details">
		<div class="container">
			<div class="details_name">${product.description}</div>
			<div class="details_row">
				<div class="details_info1">
					<img class="details_img" src="${product.imageUrl}">
					<div class="details_price"><fmt:formatNumber value="${product.price}" type="currency"
																											 currencySymbol="${product.currency.symbol}"/></div>
				</div>
				<div class="details_info2">
					<div class="details_text">
						<p>Tag: ${product.tag}</p>
						<p>Stock: ${product.stock}</p>
						<p>Price history</p>
						<c:forEach var="entry" items="${product.priceHistory}">${entry.key} - <fmt:formatNumber
										value="${entry.value}" type="currency" currencySymbol="${product.currency.symbol}"/><br>
						</c:forEach>
					</div>
					<form class="details_form" method="post"
								action="${pageContext.servletContext.contextPath}/products/${product.id}">
						<button class="details_add">add to cart</button>
						<p class="details_invitation">Enter quantity:</p>
						<input class="details_quantity" name="quantity"
									 value="${not empty requestScope.error? param.quantity : 1}">
					</form>
				</div>
			</div>
			<div class="comments">
				<p class="comments__title">Product reviews:</p>
				<jsp:useBean id="productReviews" type="java.util.List" scope="request"/>
				<c:if test="${productReviews.isEmpty()}"><p>There are no reviews!</p></c:if>
				<c:if test="${not productReviews.isEmpty()}"><hr></c:if>
				<c:forEach var="review" items="${productReviews}">
					<div class="single__comment">
						<p><b>${review.author}</b>  Rating: ${review.rating}</p>
						<div class="comment__body">${review.comment}</div>
					</div>
					<hr>
				</c:forEach>
				<c:if test="${sessionScope.login != null}">
					<form method="post" action="${pageContext.servletContext.contextPath}/productReview/${product.id}">
						<div class="comments__title">Post a review:</div>
						<label>Rating:</label>
						<input type="radio" name="rating" value="1" ${param.rating == 1 ? 'checked' : ''}/>1
						<input type="radio" name="rating" value="2" ${param.rating == 2 ? 'checked' : ''}/>2
						<input type="radio" name="rating" value="3" ${param.rating == 3 ? 'checked' : ''}/>3
						<input type="radio" name="rating" value="4" ${param.rating == 4 ? 'checked' : ''}/>4
						<input type="radio" name="rating"
									 value="5" ${param.rating == 5 ? 'checked' : empty param.rating ? 'checked' : ''}/>5
						<p>Comment:<input class="checkoutInput" name="comment"
															value="${not empty param.comment ? param.comment : ''}"/></p>
						<c:if test="${not empty requestScope.commentError}">
							<p class="error">${requestScope.commentError}</p>
						</c:if>
						<button class="details_add">Leave a review</button>
					</form>
				</c:if>
			</div>
		</div>
	</div>
</tags:master>
