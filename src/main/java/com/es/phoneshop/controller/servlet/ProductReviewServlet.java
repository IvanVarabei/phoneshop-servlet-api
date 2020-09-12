package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.ProductDao;
import com.es.phoneshop.model.dao.ProductReviewDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductReviewDao;
import com.es.phoneshop.model.entity.ProductReview;
import com.es.phoneshop.model.exception.ItemNotFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductReviewServlet extends HttpServlet {
    private ProductReviewDao productReviewDao = ArrayListProductReviewDao.getInstance();
    private ProductDao productDao = ArrayListProductDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        long productId = Long.parseLong(req.getPathInfo().substring(1));
        String name = req.getParameter("name");
        int rating = Integer.parseInt(req.getParameter("rating"));
        String comment = req.getParameter("comment");
        Map<String, String> reviewErrors = new HashMap<>();
        if (name == null || name.isEmpty()) {
            reviewErrors.put("nameError", "Name is required.");
        }
        if (comment == null || comment.isEmpty()) {
            reviewErrors.put("commentError", "Comment is required.");
        }
        if (reviewErrors.isEmpty()) {
            ProductReview productReview = new ProductReview(name, rating, comment, productId);
            productReviewDao.save(productReview);
            resp.sendRedirect(req.getContextPath() + "/products/" + productId);
        } else {
            try {
                req.setAttribute("reviewErrors", reviewErrors);
                req.setAttribute("product", productDao.find(productId));
                List<ProductReview> productReviews = productReviewDao.findApprovedProductReviews(productId);
                req.setAttribute("productReviews", productReviews);
                req.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(req, resp);
            } catch (ItemNotFoundException ignored) {
            }
        }
    }
}
