package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.dao.ProductReviewDao;
import com.es.phoneshop.model.dao.impl.ArrayListProductReviewDao;
import com.es.phoneshop.model.entity.ProductReview;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ModerationProductReviewPageServlet extends HttpServlet {
    private ProductReviewDao productReviewDao = ArrayListProductReviewDao.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductReview> productReviews = productReviewDao.findNotApprovedProductReviews();
        req.setAttribute("productReviews", productReviews);
        req.getRequestDispatcher("/WEB-INF/pages/productReviewsModeration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productReviewId = Long.parseLong(req.getPathInfo().substring(1));
        String action = req.getParameter("action");
        if (action != null && action.equals("approve")) {
            productReviewDao.approve(productReviewId);
            resp.sendRedirect(req.getContextPath() + "/productReviewModeration?message=Review approved successfully");
        } else {
            productReviewDao.delete(productReviewId);
            resp.sendRedirect(req.getContextPath() + "/productReviewModeration?message=Review rejected successfully");
        }
    }
}
