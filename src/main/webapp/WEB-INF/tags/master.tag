<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body>
<div class="page">
    <header>
        <a href="${pageContext.servletContext.contextPath}">
            <img src="${pageContext.servletContext.contextPath}/images/logo.svg" alt="logo"/>
            PhoneShop
        </a>
    </header>
    <main>
        <h3>${not empty sessionScope.cart? sessionScope.cart : ''}</h3>
        <jsp:doBody/>
        <h3>${not empty sessionScope.recent? 'Recently viewed' : ''}</h3>
        <div class="products-container">
            <c:forEach var="product" items="${sessionScope.recent}">
                <div class="item">
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}" target="_blank">
                        <div class="item-img">
                            <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"
                                 alt="Phone" style="display: inline;">
                        </div>
                        <div class="item-info">
                            <div class="good">
                                <span class="good-name">${product.description}</span><br>
                                <fmt:formatNumber value="${product.price}" type="currency"
                                                  currencySymbol="${product.currency.symbol}"/>
                            </div>
                        </div>
                    </a>
                </div>
            </c:forEach>
        </div>
    </main>
    <footer>
        <p>&copy; copyrighting 2020. Expert-Soft.</p>
        <address>
            Contact us address@gmail.com
        </address>
    </footer>
</div>
</body>
</html>