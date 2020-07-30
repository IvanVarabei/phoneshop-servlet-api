<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <div class="photo">
        <img src="${pageContext.request.contextPath}/images/image.png" alt="">
    </div>
    <div class="product">
        <div class="container">
            <div class="product_body">
                <div class="info_colomn">
                    <nav class="product_nav">
                        <div class="type">
                            <img src="${pageContext.request.contextPath}/images/phone_icon.png" alt="" class="type_img">
                            <div class="type_text">Cell Phones</div>
                        </div>
                        <div class="type">
                            <img src="${pageContext.request.contextPath}/images/phone_icon.png" alt="" class="type_img">
                            <div class="type_text">Cell Phones</div>
                        </div>
                        <div class="type">
                            <img src="${pageContext.request.contextPath}/images/phone_icon.png" alt="" class="type_img">
                            <div class="type_text">Cell Phones</div>
                        </div>
                        <div class="type">
                            <img src="${pageContext.request.contextPath}/images/phone_icon.png" alt="" class="type_img">
                            <div class="type_text">Cell Phones</div>
                        </div>
                        <div class="type">
                            <img src="${pageContext.request.contextPath}/images/phone_icon.png" alt="" class="type_img">
                            <div class="type_text">Cell Phones</div>
                        </div>
                    </nav>
                    <div class="news">
                        <div class="news_title">News</div>
                        <div class="news_item">
                            <div class="news_body">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Totam rerum non molestias et unde animi eligendi, harum ipsum debitis earum vel architecto obcaecati praesentium iure quam sint, corporis! Vero, voluptatibus.</div>
                        </div>
                        <div class="news_item">
                            <div class="news_body">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Totam rerum non molestias et unde animi eligendi, harum ipsum debitis earum vel architecto obcaecati praesentium iure quam sint, corporis! Vero, voluptatibus.</div>
                        </div>
                    </div>
                </div>
                <div class="product_items">
                    <div class="product_panel">
                        <div class="panel_sort">Sort by name
                            <tags:sortLink sortField="description" sortOrder="asc" direction="up"></tags:sortLink>
                            <tags:sortLink sortField="description" sortOrder="desc"></tags:sortLink>
                        </div>
                        <div class="panel_sort">
                            Sort by price
                            <tags:sortLink sortField="price" sortOrder="asc" direction="up"></tags:sortLink>
                            <tags:sortLink sortField="price" sortOrder="desc"></tags:sortLink>
                        </div>
                    </div>
                    <c:forEach var="product" items="${products}">
                        <div class="product_product">
                            <div class="product_img">
                                <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                                </a>
                            </div>
                            <div class="product_name">
                                <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                        ${product.description}
                                </a>
                            </div>
                            <div class="product_cost popup" onclick="changePopup(${product.id})">
                                <fmt:formatNumber value="${product.price}" type="currency"
                                                  currencySymbol="${product.currency.symbol}"/>
                                <div class="popupText" id="${product.id}">
                                    Price history<br>
                                        ${product.description}
                                    <table>
                                        <tr>
                                            <td>Start date</td>
                                            <td>Price</td>
                                        </tr>
                                        <c:forEach var="entry" items="${product.priceHistory}">
                                            <tr>
                                                <td>${entry.key}</td>
                                                <td>
                                                    <fmt:formatNumber value="${entry.value}" type="currency"
                                                                      currencySymbol="${product.currency.symbol}"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>


    <%--    <form>--%>
    <%--        <label>--%>
    <%--            <input name="query" value="${param.query}">--%>
    <%--        </label>--%>
    <%--        <input type="submit" value="Search">--%>
    <%--    </form>--%>
    <%--    <table>--%>
    <%--        <thead>--%>
    <%--        <tr>--%>
    <%--            <td>Image</td>--%>
    <%--            <td>Description--%>
    <%--                <tags:sortLink sortField="description" sortOrder="asc" direction="up"></tags:sortLink>--%>
    <%--                <tags:sortLink sortField="description" sortOrder="desc"></tags:sortLink>--%>
    <%--            </td>--%>
    <%--            <td>Price--%>
    <%--                <tags:sortLink sortField="price" sortOrder="asc" direction="up"></tags:sortLink>--%>
    <%--                <tags:sortLink sortField="price" sortOrder="desc"></tags:sortLink>--%>
    <%--            </td>--%>
    <%--        </tr>--%>
    <%--        </thead>--%>
    <%--        <c:forEach var="product" items="${products}">--%>
    <%--            <tr>--%>
    <%--                <td>--%>
    <%--                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">--%>
    <%--                        <img width="64px"--%>
    <%--                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">--%>
    <%--                    </a>--%>
    <%--                </td>--%>
    <%--                <td>--%>
    <%--                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>--%>
    <%--                </td>--%>
    <%--                <td>--%>
    <%--                    <span class="popup" onclick="changePopup(${product.id})">--%>
    <%--                        <fmt:formatNumber value="${product.price}" type="currency"--%>
    <%--                                          currencySymbol="${product.currency.symbol}"/>--%>
    <%--                        <div class="popupText" id="${product.id}">--%>
    <%--                            Price history<br>--%>
    <%--                            ${product.description}--%>
    <%--                            <table>--%>
    <%--                                <tr>--%>
    <%--                                    <td>Start date</td>--%>
    <%--                                    <td>Price</td>--%>
    <%--                                </tr>--%>
    <%--                                <c:forEach var="entry" items="${product.priceHistory}">--%>
    <%--                                    <tr>--%>
    <%--                                               <td>${entry.key}</td>--%>
    <%--                                        <td>--%>
    <%--                                            <fmt:formatNumber value="${entry.value}" type="currency"--%>
    <%--                                                              currencySymbol="${product.currency.symbol}"/>--%>
    <%--                                        </td>--%>
    <%--                                    </tr>--%>
    <%--                                </c:forEach>--%>
    <%--                            </table>--%>
    <%--                        </div>--%>
    <%--                    </span>--%>
    <%--                </td>--%>
    <%--            </tr>--%>
    <%--        </c:forEach>--%>
    <%--    </table>--%>
</tags:master>