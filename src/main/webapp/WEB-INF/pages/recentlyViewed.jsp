<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="recent" class="java.util.LinkedList" scope="request"/>
<div class="previous">
    <div class="container">
        <div class="previous_title">${not empty recent? 'Recently viewed' : ''}</div>
        <div class="previous_row">
            <c:forEach var="product" items="${recent}">
                <div class="previous_product">
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                        <img class="previous_img" src="${product.imageUrl}">
                        <div class="previous_name">${product.description}</div>
                        <div class="previous_cost">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/></div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</div>