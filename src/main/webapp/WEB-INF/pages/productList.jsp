<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<jsp:useBean id="products" type="java.util.List" scope="request"/>
<tags:master pageTitle="Product List">
	<div class="photo"><img src="${pageContext.request.contextPath}/images/image.png" alt=""></div>
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
	<div class="product">
		<div class="container">
			<div class="product_body">
				<jsp:useBean id="tags" type="java.util.List" scope="request"/>
				<div class="filter">
					<div class="type">
						<form>
							<div class="filter_name">Categories</div>
							<c:forEach var="category" items="${tags}" varStatus="status">
								<c:set var="flag" value="${true}"/>
								<c:forEach var='value' items='${paramValues.tag}'>
									<c:if test="${value eq category}">
										<c:set var="isActive" value="${true}"/>
										<c:set var="flag" value="${false}"/>
									</c:if>
									<c:if test="${flag eq true and value ne category}">
										<c:set var="isActive" value="${false}"/>
									</c:if>
								</c:forEach>
								<div class="filter_tag"><input ${isActive ? 'checked' : ''} type="checkbox" name="tag"
																																						value="${category}">${category}</div>
							</c:forEach>
							<div class="filter_name">Cost</div>
							<div class="filter_cost">
								<input class="filter_cost_input" placeholder="min" type="text" name="minPrice"
											 value="${not empty param.minPrice and empty requestScope.errors['minPriceError'] ? param.minPrice : ''}">
								<input class="filter_cost_input" placeholder="max" type="text" name="maxPrice"
											 value="${not empty param.maxPrice and empty requestScope.errors['maxPriceError'] ? param.maxPrice : ''}">
							</div>
							<div class="filter_name">Min stock amount</div>
							<input class="filter_stock_input" type="text" name="stock"
										 value="${not empty param.stock and empty requestScope.errors['stockError'] ? param.stock : ''}">
							<input type="hidden" name="query" value="${param.query}">
							<button class="filter_btn editingSearchBtn">apply filters</button>
						</form>
					</div>
				</div>
			<div class="product_items">
				<div class="product_panel">
					<div class="panel_sort">Sort by name
						<tags:sortLink sortField="description" sortOrder="asc" direction="up"></tags:sortLink>
						<tags:sortLink sortField="description" sortOrder="desc"></tags:sortLink>
					</div>
					<div class="panel_sort">
						Sort by price
						<tags:sortLink sortField="price" sortOrder="asc" direction="up"></tags:sortLink>
						<tags:sortLink sortField="price" sortOrder="desc"></tags:sortLink>
					</div>
				</div>
				<c:forEach var="product" items="${products}" varStatus="status">
					<div class="product_product">
						<div class="product_img">
							<a href="${pageContext.servletContext.contextPath}/products/${product.id}">
								<img width="235" height="235" src="${product.imageUrl}">
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
							<c:set var="categories" value=""/>
							<c:forEach var="category" items="${paramValues.tag}">
								<c:set var="categories" value="${categories}&tag=${category}"/>
							</c:forEach>
							<form method="post" action="${pageContext.servletContext.contextPath}/products?query=${
                                    not empty param.query ? param.query : ''}&sortField=${
                                    not empty param.sortField ? param.sortField : ''}&sortOrder=${
                                    not empty param.sortOrder ? param.sortOrder : ''}&minPrice=${
                                    not empty param.minPrice ? param.minPrice : ''}&maxPrice=${
                                    not empty param.maxPrice ? param.maxPrice : ''}&stock=${
                                    not empty param.stock ? param.stock : ''}${categories}">
								<div class="product_add_row">
									<div class="product_quantity">
										<input class="product_quantity_input" name="quantity" value="${
                                            not empty requestScope.error and product.id == param.productId ?
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
			</div>
		</div>
	</div>
	</div>
</tags:master>