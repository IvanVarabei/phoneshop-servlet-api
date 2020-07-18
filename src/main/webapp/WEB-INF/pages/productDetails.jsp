<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.entity.Product" scope="request"/>
<tags:master pageTitle='${product.description}'>
    <table>
        <caption>Product details</caption>
        <tr>
            <td colspan="2"><img
                    src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"
                    alt="image can not be found"></td>
        </tr>
        <tr>
            <td>Price</td>
            <td><fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/></td>
        </tr>
        <tr>
            <td>Description</td>
            <td>${product.description}</td>
        </tr>
        <tr>
            <td>Code</td>
            <td>${product.code}</td>
        </tr>
        <tr>
            <td>Stock</td>
            <td>${product.stock}</td>
        </tr>
    </table>
</tags:master>
