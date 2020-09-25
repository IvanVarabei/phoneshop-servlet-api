<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Login">
	<form method="post" action="${pageContext.request.contextPath}/login">
		<p>${requestScope.error}</p>
		<div>Enter login:<input name="login"></div>
		<div>Enter password:<input name="password"></div>
		<button>LOGIN</button>
	</form>
	<br>
	<div><a href="${pageContext.request.contextPath}/register">REGISTER</a></div>
</tags:master>
