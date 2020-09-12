<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Overview">
	<div class="cart">
		<div class="container">
			<table class="cart_table">
				<caption>Order overview</caption>
				<thead>
				<tr>
					<td>Image</td>
					<td>Description</td>
					<td>Quantity</td>
					<td>Price</td>
				</tr>
				</thead>
				<c:forEach var="item" items="${requestScope.order.cartItemList}">
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
				<p>Total quantity : ${requestScope.order.cartItemList.size()}</p>
				<p>Cart subtotal : <fmt:formatNumber value="${requestScope.order.subtotal}" type="currency"
																						 currencySymbol="${requestScope.order.cartItemList.get(0)
                                                      .product.currency.symbol}"/></p>
				<p>Delivery costs : <fmt:formatNumber value="${requestScope.order.deliveryCost}" type="currency"
																							currencySymbol="${requestScope.order.cartItemList.get(0)
                                                      .product.currency.symbol}"/></p>
				<p>Cart total : <fmt:formatNumber value="${requestScope.order.totalCost}" type="currency"
																					currencySymbol="${requestScope.order.cartItemList.get(0)
                                                      .product.currency.symbol}"/></p>
			</div>
			<div class="overviewTable">
				<table class="cart_table">
					<caption>Your details</caption>
					<tags:orderOverviewRow name="firstName" label="First Name" order="${requestScope.order}"/>
					<tags:orderOverviewRow name="lastName" label="Last Name" order="${requestScope.order}"/>
					<tags:orderOverviewRow name="phone" label="Phone" order="${requestScope.order}"/>
					<tags:orderOverviewRow name="deliveryDate" label="Delivery Date" order="${requestScope.order}"/>
					<tags:orderOverviewRow name="deliveryAddress" label="Delivery Address" order="${requestScope.order}"/>
					<tags:orderOverviewRow name="paymentMethod" label="Payment method" order="${requestScope.order}"/>
				</table>
			</div>
		</div>
	</div>
</tags:master>
