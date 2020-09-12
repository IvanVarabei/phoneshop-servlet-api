<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Register">
	<form method="post" action="${pageContext.request.contextPath}/register">
		<p>${requestScope.message}</p>
		<div>Enter login:<input name="login"></div>
		<div>Enter password:<input name="password1"></div>
		<div>Enter password one more time:<input name="password2"></div>
		<button>REGISTER</button>
	</form>
</tags:master>