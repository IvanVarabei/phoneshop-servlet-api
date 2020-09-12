<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.entity.Order" scope="request"/>
<tags:master pageTitle="Checkout">
	<div class="cart">
		<div class="container">
			<table class="cart_table">
				<caption>Checkout</caption>
				<thead>
				<tr>
					<td>Image</td>
					<td>Description</td>
					<td>Quantity</td>
					<td>Price</td>
				</tr>
				</thead>
				<c:forEach var="item" items="${order.cartItemList}">
					<tr>
						<td><a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
							<img width="64px" src="${item.product.imageUrl}">
						</a>
						</td>
						<td><a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
								${item.product.description}</a>
						</td>
						<td>
							<span>${item.quantity}</span>
						</td>
						<td><fmt:formatNumber value="${item.product.price}" type="currency"
																	currencySymbol="${item.product.currency.symbol}"/></td>
					</tr>
				</c:forEach>
			</table>
			<div class="cart_info">
				<p>Total quantity : ${order.cartItemList.size()}</p>
				<p>Cart subtotal : <fmt:formatNumber value="${order.subtotal}" type="currency"
																						 currencySymbol="${order.cartItemList.get(0)
                                                      .product.currency.symbol}"/></p>
				<p>Delivery costs : <fmt:formatNumber value="${order.deliveryCost}" type="currency"
																							currencySymbol="${order.cartItemList.get(0)
                                                      .product.currency.symbol}"/></p>
				<p>Cart total : <fmt:formatNumber value="${order.totalCost}" type="currency"
																					currencySymbol="${order.cartItemList.get(0)
                                                      .product.currency.symbol}"/></p>
			</div>
			<div>
				<form method="post">
					<table class="cart_table">
						<caption>Your details</caption>
						<tags:orderFormRow name="firstName" label="First Name" order="${order}"
															 errors="${requestScope.errors}"/>
						<tags:orderFormRow name="lastName" label="Last Name" order="${order}"
															 errors="${requestScope.errors}"/>
						<tags:orderFormRow name="phone" label="Phone" order="${order}"
															 errors="${requestScope.errors}"/>
						<tags:orderFormRow name="deliveryDate" label="Delivery Date (yyyy-MM-dd)" order="${order}"
															 errors="${requestScope.errors}"/>
						<tags:orderFormRow name="deliveryAddress" label="Delivery Address" order="${order}"
															 errors="${requestScope.errors}"/>
						<tr>
							<td>Payment method<span style="color:red">*</span></td>
							<td>
								<select class="select" name="paymentMethod">
									<option>${param['paymentMethod']}</option>
									<c:forEach var="paymentMethod" items="${requestScope.paymentMethods}">
										<c:if test="${paymentMethod != param['paymentMethod']}">
											<option>${paymentMethod}</option>
										</c:if>
									</c:forEach>
								</select>
								<c:set var="error" value="${requestScope.errors['paymentMethod']}"/>
								<c:if test="${not empty error}">
									<div class="error">${error}</div>
								</c:if>
							</td>
						</tr>
					</table>
					<button class="checkoutButton">Place order</button>
				</form>
			</div>
		</div>
	</div>
</tags:master>