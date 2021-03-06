<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html xmlns:jsp="http://java.sun.com/JSP/Page">
<head>
	<title>${pageTitle}</title>
	<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
	<script src="${pageContext.servletContext.contextPath}/js/popupProcessing.js"></script>
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@500&display=swap" rel="stylesheet">
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico"/>
</head>
<body>
<div class="wrapper">
	<div class="content">
		<header class="header">
			<div class="container">
				<div class="header_row">

					<div class="header_name">
						<a href="${pageContext.request.contextPath}/">phone shop</a>
					</div>

					<form class="header_search" action="${pageContext.request.contextPath}/products">
						<input class="header_input" name="query" value="${param.query}" placeholder="Search goods" maxlength="30">
						<button class="header_search_button">
							<img src="${pageContext.request.contextPath}/images/search_icon.png" alt="">
						</button>
					</form>

					<div class="header__auth">
						<c:if test="${not empty sessionScope.login}">
							<div class="header__auth_elem"><a href="${pageContext.request.contextPath}/logout">Logout</a></div>
						</c:if>
						<c:if test="${empty sessionScope.login}">
							<div class="header__auth_elem"><a href="${pageContext.request.contextPath}/login">Login</a></div>
							<div class="header__auth_elem"><a href="${pageContext.request.contextPath}/register">Register</a></div>
						</c:if>
					</div>

					<jsp:include page="/cart/miniCart"/>
				</div>
			</div>
		</header>
		<jsp:doBody/>
	</div>
	<jsp:include page="/recentlyViewed"/>
	<footer class="footer">
		<div class="container">
			<div class="footer_row">
				<div class="footer_text">&copy; copyrighting 2020. Expert-Soft.</div>
				<div><a href="${pageContext.request.contextPath}/adminMenu">admin menu</a></div>
				<div class="footer_pay">
					<div class="footer_text">Accepted payment methods</div>
					<img src="${pageContext.request.contextPath}/images/9-layers.png" alt="">
				</div>
			</div>
		</div>
	</footer>
</div>
</body>
</html>