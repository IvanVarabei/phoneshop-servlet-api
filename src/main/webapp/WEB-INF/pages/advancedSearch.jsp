<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Advanced search">
	<div class="cart">
		<div class="container">
			<form >
				<p>Product code :<input class="checkoutInput" name="productCode" value="${not empty param.productCode ? param.productCode : ''}"/></p>
				<p>Min price :<input class="checkoutInput" name="minPrice" value="${not empty param.minPrice ? param.minPrice : ''}"/></p>
				<c:if test="${not empty requestScope.searchErrors['minPriceError']}">
					<p class="error">${requestScope.searchErrors.minPriceError}</p>
				</c:if>
				<p>Max price :<input class="checkoutInput" name="maxPrice" value="${not empty param.maxPrice ? param.maxPrice : ''}"/></p>
				<c:if test="${not empty requestScope.searchErrors['maxPriceError']}">
					<p class="error">${requestScope.searchErrors.maxPriceError}</p>
				</c:if>
				<p>Min stock :<input class="checkoutInput" name="minStock" value="${not empty param.minStock ? param.minStock : ''}"/></p>
				<c:if test="${not empty requestScope.searchErrors['minStockError']}">
					<p class="error">${requestScope.searchErrors.minStockError}</p>
				</c:if>
				<button class="deleteCartItem">Search</button>
			</form>
			<c:if test="${not empty requestScope.products}">
			<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
				<div>${products.size()} products found.</div>
			<c:forEach var="product" items="${products}" varStatus="status">
				<div class="product_product">
					<div class="product_img">
						<a href="${pageContext.servletContext.contextPath}/products/${product.id}">
							<img src="${product.imageUrl}">
						</a>
					</div>
					<div class="product_name">
						<a href="${pageContext.servletContext.contextPath}/products/${product.id}">
								${product.description}
						</a>
					</div>
					<div class="product_cost popup" onclick="changePopup(${product.id})">
						<fmt:formatNumber value="${product.price}" type="currency"
															currencySymbol="${product.currency.symbol}"/>
						<div class="popupText" id="${product.id}">
							Price history<br>${product.description}
							<table>
								<tr>
									<td>Start date</td>
									<td>Price</td>
								</tr>
								<c:forEach var="entry" items="${product.priceHistory}">
									<tr>
										<td>${entry.key}</td>
										<td><fmt:formatNumber value="${entry.value}" type="currency"
																					currencySymbol="${product.currency.symbol}"/></td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
					<div class="product_adding_from_plp">
						<form method="post" action="${pageContext.servletContext.contextPath}/products?query=${
                                    not empty param.query ? param.query : ''}&sortField=${
                                    not empty param.sortField ? param.sortField : ''}&sortOrder=${
                                    not empty param.sortOrder ? param.sortOrder : ''}">
							<div class="product_add_row">
								<div class="product_quantity">
									<input class="product_quantity_input" name="quantity" value="${
                                            not empty requestScope.error and product.id ==param.productId ?
                                             param.quantity : 1}"/>
									<input type="hidden" name="productId" value="${product.id}">
								</div>
								<div class="product_button">
									<button class="product_quantity_button">Add to cart</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</c:forEach>
			</c:if>
		</div>
	</div>
</tags:master>