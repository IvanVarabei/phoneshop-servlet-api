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
    <c:if test="${requestScope.show != false and not empty param.message}">
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
                    <img class="details_img" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    <div class="details_price"><fmt:formatNumber value="${product.price}" type="currency"
                                                                 currencySymbol="${product.currency.symbol}"/></div>
                </div>
                <div class="details_info2">
                    <div class="details_text">
                        <p>Code: ${product.code}</p>
                        <p>Stock: ${product.stock}</p>
                        <p>Price history</p>
                        <c:forEach var="entry" items="${product.priceHistory}">
                            ${entry.key} -
                            <fmt:formatNumber value="${entry.value}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/><br>
                        </c:forEach>
                    </div>
                    <form class="details_form" method="post" >
                        <button class="details_add">add to cart</button>
                        <p class="details_invitation">Enter quantity:</p>
                        <input class="details_quantity" type="text" name="quantity">
                    </form>
                </div>
            </div>
        </div>
    </div>
</tags:master>
