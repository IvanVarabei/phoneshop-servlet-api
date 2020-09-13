<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="create product">
	<div class="editing">
		<div class="container">
			<div class="title">Admin page. Create product.</div>
			<form method="post">
				<table>
					<tr>
						<td>ImageUrl 235px :</td>
						<td><input class="checkoutInput" name="imageUrl" value="${not empty param.imageUrl ? param.imageUrl : ''}"/>
							<c:if test="${not empty requestScope.errors['imageUrlError']}">
							<p class="error">${requestScope.errors.imageUrlError}</p>
							</c:if>
						<td>
					</tr>
					<tr>
						<td>Product code :</td>
						<td><input class="checkoutInput" name="productCode"
											 value="${not empty param.productCode ? param.productCode : ''}"/>
							<c:if test="${not empty requestScope.errors['productCodeError']}">
								<p class="error">${requestScope.errors.productCodeError}</p>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>Description :</td>
						<td><input class="checkoutInput" name="description" value="${not empty param.description ? param.description : ''}"/>
							<c:if test="${not empty requestScope.errors['descriptionError']}">
							<p class="error">${requestScope.errors.descriptionError}</p>
							</c:if>
						<td>
					</tr>
					<tr>
						<td>Price :</td>
						<td><input class="checkoutInput" name="price" value="${not empty param.price ? param.price : ''}"/>
							<c:if test="${not empty requestScope.errors['priceError']}">
								<p class="error">${requestScope.errors.priceError}</p>
							</c:if>
						</td>
					</tr>
					<tr>
						<td>Stock :</td>
						<td><input class="checkoutInput" name="stock" value="${not empty param.stock ? param.stock : ''}"/>
							<c:if test="${not empty requestScope.errors['stockError']}">
							<p class="error">${requestScope.errors.stockError}</p>
							</c:if>
						<td>
					</tr>
				</table>
					<button class="editingSearchBtn">create</button>
			</form>
		</div>
	</div>
</tags:master>