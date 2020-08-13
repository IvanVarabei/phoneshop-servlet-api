<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
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
                        <a href="${pageContext.servletContext.contextPath}">phone shop</a></div>
                    <form class="header_search" action="${pageContext.request.contextPath}/products">
                        <input class="header_input" name="query" value="${param.query}" placeholder="Search goods">
                        <button class="header_search_button">
                            <img src="${pageContext.request.contextPath}/images/search_icon.png" alt="">
                        </button>
                    </form>
                    <jsp:include page="/cart/miniCart"/>
                </div>
            </div>
        </header>
        <jsp:doBody/>
    </div>
    <div class="previous">
        <div class="container">
            <div class="previous_title">${not empty sessionScope.recent? 'Recently viewed' : ''}</div>
            <div class="previous_row">
                <c:forEach var="product" items="${sessionScope.recent}">
                    <div class="previous_product">
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            <img class="previous_img"
                                 src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                            <div class="previous_name">${product.description}</div>
                            <div class="previous_cost">
                                <fmt:formatNumber value="${product.price}" type="currency"
                                                  currencySymbol="${product.currency.symbol}"/></div>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <footer class="footer">
        <div class="container">
            <div class="footer_row">
                <div class="footer_text">&copy; copyrighting 2020. Expert-Soft.</div>
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