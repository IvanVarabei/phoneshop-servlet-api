<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <script src="${pageContext.servletContext.contextPath}/js/popupProcessing.js"></script>
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form>
        <label>
            <input name="query" value="${param.query}">
        </label>
        <input type="submit" value="Search">
    </form>
    <table>
        <caption>Product list</caption>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <tags:sortLink sortField="description" sortOrder="asc" direction="up"></tags:sortLink>
                <tags:sortLink sortField="description" sortOrder="desc"></tags:sortLink>
            </td>
            <td>Price
                <tags:sortLink sortField="price" sortOrder="asc" direction="up"></tags:sortLink>
                <tags:sortLink sortField="price" sortOrder="desc"></tags:sortLink>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                        <img width="64px"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </a>
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
                </td>
                <td>
                    <span class="popup" onclick="changePopup(${product.id})">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                        <div class="popupText" id="${product.id}">
                            Price history<br>
                            ${product.description}
                            <table>
                                <tr>
                                    <td>Start date</td>
                                    <td>Price</td>
                                </tr>
                                <c:forEach var="entry" items="${product.priceHistory}">
                                    <tr>
                                        <td>${entry.key}</td>
                                        <td>
                                            <fmt:formatNumber value="${entry.value}" type="currency"
                                                              currencySymbol="${product.currency.symbol}"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </div>
                    </span>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>