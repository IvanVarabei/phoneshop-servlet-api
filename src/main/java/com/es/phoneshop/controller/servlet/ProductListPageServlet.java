package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private static final String SEARCH_QUERY_PARAM = "query";
    private static final String SORT_FIELD_PARAM = "sortField";
    private static final String SORT_ORDER_PARAM = "sortOrder";
    private static final String PRODUCT_LIST_ATTRIBUTE = "products";
    private static final String PRODUCT_LIST_PATH = "/WEB-INF/pages/productList.jsp";
    private ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter(SEARCH_QUERY_PARAM) != null ? request.getParameter(SEARCH_QUERY_PARAM) : "";
        String sortFieldParam = request.getParameter(SORT_FIELD_PARAM);
        String sortOrderParam = request.getParameter(SORT_ORDER_PARAM);
        SortField sortField = SortField.DEFAULT;
        SortOrder sortOrder = SortOrder.DEFAULT;
        if (sortFieldParam != null && sortOrderParam != null) {
            sortField = SortField.valueOf(sortFieldParam.toUpperCase());
            sortOrder = SortOrder.valueOf(sortOrderParam.toUpperCase());
        }
        request.setAttribute(PRODUCT_LIST_ATTRIBUTE, arrayListProductDao.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher(PRODUCT_LIST_PATH).forward(request, response);
    }
}
