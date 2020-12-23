<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Login">
	<div class="cart">
		<div class="container">
			<form class="auth__form" method="post" action="${pageContext.request.contextPath}/login">
				<p class="error">${requestScope.error}</p>
				<div>Enter login:<input class="header_input" name="login" minlength="3" maxlength="20" required></div>
				<div>Enter password:<input class="header_input" name="password" minlength="3" maxlength="20" required
																	 type="password"></div>
				<button>LOGIN</button>
				<br>
				<br>
				<div><a href="${pageContext.request.contextPath}/register">REGISTER</a></div>
			</form>

		</div>
	</div>
</tags:master>
