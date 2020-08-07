<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Cart">
    <c:if test="${empty requestScope.errors and not empty param.message}">
        <div class="add_info add_success">
            <div class="container">
                <div class="add_message">${param.message}</div>
            </div>
        </div>
    </c:if>
    <div class="cart">
        <div class="container">
            <c:if test="${empty sessionScope.cart.getCartItemList()}">
                <h2 class="title" align="center">Cart is empty</h2>
            </c:if>
            <c:if test="${not empty sessionScope.cart.getCartItemList()}">
            <form method="post" action="${pageContext.servletContext.contextPath}/cart">
                <table class="cart_table">
                    <thead>
                    <tr>
                        <td>Image</td>
                        <td>Description</td>
                        <td>Quantity</td>
                        <td>Price</td>
                    </tr>
                    </thead>
                    <c:forEach var="item" items="${sessionScope.cart.getCartItemList()}" varStatus="status">
                        <tr>
                            <td><a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                                <img width="64px"
                                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${item.product.imageUrl}">
                            </a>
                            </td>
                            <td><a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                                    ${item.product.description}</a>
                            </td>
                            <td><fmt:formatNumber value="${item.quantity}" var="quantity"/>
                                <c:set var="error" value="${requestScope.errors[item.product.id]}"/>
                                <input name="quantity"
                                       value="${not empty error ? paramValues['quantity'][status.index] : item.quantity}"/>
                                <c:if test="${not empty error}">
                                    <div class="error">${requestScope.errors[item.product.id]}</div>
                                </c:if>
                                <input name="productId" type="hidden" value="${item.product.id}">
                            </td>
                            <td><fmt:formatNumber value="${item.product.price}" type="currency"
                                                  currencySymbol="${item.product.currency.symbol}"/></td>
                            <td>
                                <button form="deleteCartItem" formaction=
                                        "${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}"
                                >delete
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div class="cart_info">
                    <p>Total quantity : ${sessionScope.cart.getCartItemList().size()}</p>
                    <p>Total cost : <fmt:formatNumber value="${sessionScope.cart.totalCost}" type="currency"
                                                      currencySymbol="${sessionScope.cart.getCartItemList().get(0)
                                                      .getProduct().currency.symbol}"/></p>
                    <p>
                        <button>Update</button>
                    </p>
                </div>
            </form>
            <form id="deleteCartItem" method="post"></form>
        </div>
        </c:if>
    </div>
</tags:master>