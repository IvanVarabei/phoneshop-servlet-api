<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortField" required="true" %>
<%@ attribute name="sortOrder" required="true" %>
<%@ attribute name="direction" %>

<c:set var="categories" value=""/>
<c:forEach var="category" items="${paramValues.productCode}">
    <c:set var="categories" value="${categories}&productCode=${category}"/>
</c:forEach>
<a href="?query=${param.query}&sortField=${sortField}&sortOrder=${sortOrder}&minPrice=${param.minPrice}
&maxPrice=${param.maxPrice}&minStock=${param.minStock}${categories}"
   class="${sortField eq param.sortField and sortOrder eq param.sortOrder ? 'activeRef' : ''}">
    ${direction eq 'up'? '&uArr;' : '&dArr;'}</a>