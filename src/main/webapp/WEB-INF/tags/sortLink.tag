<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="sortField" required="true" %>
<%@ attribute name="sortOrder" required="true" %>
<%@ attribute name="direction" %>

<a href="?query=${param.query}&sortField=${sortField}&sortOrder=${sortOrder}"
   class="${sortField eq param.sortField and sortOrder eq param.sortOrder ? 'activeRef' : ''}">
    ${direction eq 'up'? '&uArr;' : '&dArr;'}</a>