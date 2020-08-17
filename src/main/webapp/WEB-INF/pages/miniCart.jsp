<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" class="com.es.phoneshop.model.entity.Cart" scope="request"/>
<div class="header_cart">
    <a href="${pageContext.request.contextPath}/cart">
        <img src="${pageContext.request.contextPath}/images/icon.png" alt="">
        <c:if test="${not empty cart.cartItemList}">
            ${cart.cartItemList.size()} -
            <fmt:formatNumber value="${cart.totalCost}" type="currency"
                              currencySymbol="${cart.cartItemList.get(0).product.currency.symbol}"/>
        </c:if>
    </a>
</div>
