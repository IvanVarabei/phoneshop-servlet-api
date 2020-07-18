<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="java.lang.String" scope="request"/>
<tags:master pageTitle="Product not found">
    <p>Product with identifier <strong>${product}</strong> not found. You can see
        <a href="${pageContext.servletContext.contextPath}/products">the other products</a>.</p>
</tags:master>
