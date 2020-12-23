<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Register">
	<div class="cart">
		<div class="container">
			<form class="auth__form" method="post" action="${pageContext.request.contextPath}/register">
				<p class="error">${requestScope.error}</p>
				<div>Enter login:<input class="header_input" name="login" minlength="3" maxlength="20" required
																pattern="^[a-zA-Z0-9_]{3,20}$"></div>
				<div>Enter password:<input class="header_input" id="password" name="password" type="password" pattern=".{3,20}"
																	 minlength="3"
																	 maxlength="20" required oninvalid="this.setCustomValidity('Passwords are different')"
																	 onchange="this.setCustomValidity('')"></div>
				<div>Repeat password:<input class="header_input" id="password-check" name="repeatPassword"
																		type="password" minlength="3"
																		maxlength="20" title=""></div>

					<%--				<div>Enter login:<input class="header_input" id="password" name="login" minlength="3" maxlength="20" required></div>--%>
					<%--				<div>Enter password:<input class="header_input" id="password-check" name="password" minlength="3" maxlength="20" required></div>--%>
					<%--				<div>Enter password one more time:<input class="header_input" name="repeatPassword" minlength="3" maxlength="20" required></div>--%>
				<button>REGISTER</button>
			</form>
		</div>
	</div>
	<script src="${pageContext.servletContext.contextPath}/js/passwordConformity.js"></script>
	<script>checkPasswordsConformity('#password', '#password-check', 'Passwords are different')</script>
</tags:master>