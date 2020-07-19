<%
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
    StackTraceElement[] stackTrace = pageContext.getException().getStackTrace();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>500</title>
</head>
<body>
<h2>Exception occurred while processing the request</h2>
<p>Type: <%= exception%></p>
<p>Message: <%= message %></p>
<%
    for (StackTraceElement stackTraceElement : stackTrace) {
        out.println(stackTraceElement);
    }
%>
</body>
</html>