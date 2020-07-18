<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
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
          <tags:sortLink sortField="Description" sortOrder="asc" direction="up"></tags:sortLink>
          <tags:sortLink sortField="Description" sortOrder="desc"></tags:sortLink>
        </td>
        <td>Price
          <tags:sortLink sortField="Price" sortOrder="asc" direction="up"></tags:sortLink>
          <tags:sortLink sortField="Price" sortOrder="desc"></tags:sortLink>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
          <img class="product-tile"
               src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"
               alt="image can not be found">
          </a>
        </td>
        <td><a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a></td>
        <td>
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>