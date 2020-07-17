package com.es.phoneshop.controller;

import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.initializer.ProductStockHardCodeInitializer;
import com.es.phoneshop.model.dao.sort.SortField;
import com.es.phoneshop.model.dao.sort.SortOrder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductListPageServlet extends HttpServlet {
    private ArrayListProductDao arrayListProductDao = new ArrayListProductDao();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        new ProductStockHardCodeInitializer().initProductStock();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        SortField sortField;
        SortOrder sortOrder;
        try {
            sortField = SortField.valueOf(request.getParameter("sortField").toUpperCase());
            sortOrder = SortOrder.valueOf(request.getParameter("sortOrder").toUpperCase());
        }catch(IllegalArgumentException | NullPointerException e){
            sortField = SortField.DEFAULT;
            sortOrder = SortOrder.DEFAULT;
        }
        request.setAttribute("products", arrayListProductDao.findProducts(query,sortField, sortOrder));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }
}
