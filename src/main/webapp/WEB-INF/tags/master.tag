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
            <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
            PhoneShop
        </a>
    </header>
    <main>
        <jsp:doBody/>
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