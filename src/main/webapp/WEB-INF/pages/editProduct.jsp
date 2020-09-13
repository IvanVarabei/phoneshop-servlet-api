<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Advanced search">
	<c:if test="${requestScope.products ne null}">
		<jsp:useBean id="products" type="java.util.List" scope="request"/>
		<div class="add_info add_success">
			<div class="container">
				<div class="add_message">${products.size()} products found.</div>
			</div>
		</div>
	</c:if>
	<div class="editing">
		<div class="container">
			<div class="title">Admin page. Edit products.</div>
			<form>
				<table>
					<tr>
						<td>Product code :</td>
						<td><input class="checkoutInput" name="productCode"
											 value="${not empty param.productCode ? param.productCode : ''}"/></td>
					</tr>
					<tr>
						<td>Min price :</td>
						<td><input class="checkoutInput" name="minPrice" value="${not empty param.minPrice ? param.minPrice : ''}"/>
							<c:if test="${not empty requestScope.searchErrors['minPriceError']}">
								<p class="error">${requestScope.searchErrors.minPriceError}</p>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>Max price :</td>
						<td><input class="checkoutInput" name="maxPrice" value="${not empty param.maxPrice ? param.maxPrice : ''}"/>
							<c:if test="${not empty requestScope.searchErrors['maxPriceError']}">
							<p class="error">${requestScope.searchErrors.maxPriceError}</p>
							</c:if>
						<td>
					</tr>
					<tr>
						<td>Min stock :</td>
						<td><input class="checkoutInput" name="minStock" value="${not empty param.minStock ? param.minStock : ''}"/>
							<c:if test="${not empty requestScope.searchErrors['minStockError']}">
							<p class="error">${requestScope.searchErrors.minStockError}</p>
							</c:if>
						<td>
					</tr>
				</table>
				<button class="editingSearchBtn">Search</button>
			</form>


			<c:if test="${not empty requestScope.products}">

				<table class="editingTable">
					<thead>
					<td>imageUrl 235px</td>
					<td>code</td>
					<td>description</td>
					<td>price</td>
					<td>stock</td>
					</thead>
					<form method="post">
						<c:forEach var="product" items="${products}" varStatus="status">
							<c:set var="currentProductError" value="${requestScope.editError[product.id]}"/>
							<c:set var="imageUrlError" value="${currentProductError.imageUrlError}"/>
							<c:set var="codeError" value="${currentProductError.codeError}"/>
							<c:set var="descriptionError" value="${currentProductError.descriptionError}"/>
							<c:set var="priceError" value="${currentProductError.priceError}"/>
							<c:set var="stockError" value="${currentProductError.stockError}"/>
							<tr>
								<td><img class="editingImg" src="${product.imageUrl}">
									<p><input class="editingInput" name="imageUrl"
														value="${not empty imageUrlError ? paramValues['imageUrl'][status.index] : product.imageUrl}"/>
									</p>
									<p>${imageUrlError}</p>
								</td>
								<td>
									<input class="editingInput" name="code"
												 value="${not empty codeError ? paramValues['code'][status.index] : product.code}"/>
									<p>${codeError}</p>
								</td>
								<td><input class="editingInput" name="description"
													 value="${not empty descriptionError ? paramValues['description'][status.index] : product.description}"/>
								</td>
								<p>${descriptionError}</p>
								<td>
									<input class="editingInput" name="price"
												 value="${not empty priceError ? paramValues['price'][status.index] : product.price}"/>${product.currency.symbol}
									<p>${priceError}</p>
								</td>
								<td><input class="editingInput" name="stock"
													 value="${not empty stockError ? paramValues['stock'][status.index] : product.stock}"/>
									<p>${stockError}</p>
								</td>
								<td>
									<button class="details_add" formmethod="post" formaction="
									${pageContext.servletContext.contextPath}/deleteProduct/${product.id}?productCode=${param.productCode}&minPrice=${param.minPrice}&maxPrice=${param.maxPrice}&minStock=${param.minStock}">
										delete
									</button>
								</td>
								<input name="productId" type="hidden" value="${product.id}">
							</tr>
						</c:forEach>
						<tr><td><button class="editingSearchBtn">Edit products</button></td></tr>
					</form>
				</table>
			</c:if>
		</div>
	</div>
</tags:master>