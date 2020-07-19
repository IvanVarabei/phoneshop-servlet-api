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
    private static ArrayListProductDao arrayListProductDao = ArrayListProductDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        SortField sortField;
        SortOrder sortOrder;
        try {
            sortField = SortField.valueOf(request.getParameter("sortField").toUpperCase());
            sortOrder = SortOrder.valueOf(request.getParameter("sortOrder").toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            sortField = SortField.DEFAULT;
            sortOrder = SortOrder.DEFAULT;
        }
        request.setAttribute("products", arrayListProductDao.findProducts(query, sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
