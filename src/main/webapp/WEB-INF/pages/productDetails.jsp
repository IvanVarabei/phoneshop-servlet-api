<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.entity.Product" scope="request"/>
<tags:master pageTitle='${product.description}'>
    <p class="success">
        <c:if test="${empty requestScope.show}">
            ${param.message}
        </c:if>
    </p>
    <p class="error">${requestScope.error}</p>
    <form method="post">
        <table>
            <caption>Product details</caption>
            <tr>
                <td colspan="2"><img
                        src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"
                        alt="image can not be found"></td>
            </tr>
            <tr>
                <td>Price</td>
                <td class="details"><fmt:formatNumber value="${product.price}" type="currency"
                                                      currencySymbol="${product.currency.symbol}"/></td>
            </tr>
            <tr>
                <td>Price history</td>
                <td class="details">
                    <c:forEach var="entry" items="${product.priceHistory}">
                        ${entry.key} -
                        <fmt:formatNumber value="${entry.value}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/><br>
                    </c:forEach>
                </td>
            </tr>
            <tr>
                <td>Description</td>
                <td class="details">${product.description}</td>
            </tr>
            <tr>
                <td>Code</td>
                <td class="details">${product.code}</td>
            </tr>
            <tr>
                <td>Stock</td>
                <td class="details">${product.stock}</td>
            </tr>
            <tr>
                <td>Quantity</td>
                <td>
                    <label>
                        <input class="details" type="text" name="quantity"
                               value="${not empty requestScope.error? param.quantity : 1}">
                    </label>
                    <p class="details error">${requestScope.error}</p>
                </td>
            </tr>
        </table>
        <button>Add to cart!</button>
    </form>
</tags:master>
