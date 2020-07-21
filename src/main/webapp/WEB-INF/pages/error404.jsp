<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="message" class="java.lang.String" scope="request"/>
<tags:master pageTitle="404">
    <p> ${(empty message) ? 'Such a page does not exist.' : message}</p> Go to the
    <a href="${pageContext.servletContext.contextPath}/products">product list!</a>.</p>
</tags:master>
