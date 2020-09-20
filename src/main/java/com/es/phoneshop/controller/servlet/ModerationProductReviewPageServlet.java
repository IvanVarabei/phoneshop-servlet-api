package com.es.phoneshop.controller.servlet;

import com.es.phoneshop.model.entity.ProductReview;
import com.es.phoneshop.model.service.ProductReviewService;
import com.es.phoneshop.model.service.impl.DefaultProductReviewService;
import com.es.phoneshop.value.Const;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ModerationProductReviewPageServlet extends HttpServlet {
    private static final String PRODUCT_REVIEWS_MODERATION_JSP = "WEB-INF/pages/productReviewsModeration.jsp";
    private static final String REDIRECT_AFTER_APPROVE = "%s%s?message=Review approved successfully";
    private static final String REDIRECT_AFTER_REJECT = "%s%s?message=Review rejected successfully";
    private static final String ACTION_VALUE_APPROVE = "approve";
    private ProductReviewService productReviewService = DefaultProductReviewService.getInstance();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProductReview> productReviews = productReviewService.findNotApprovedProductReviews();
        req.setAttribute(Const.AttributeKey.PRODUCT_REVIEWS, productReviews);
        req.getRequestDispatcher(PRODUCT_REVIEWS_MODERATION_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long productReviewId = Long.parseLong(req.getPathInfo().substring(1));
        String action = req.getParameter(Const.RequestParam.ACTION);
        if (action != null && action.equals(ACTION_VALUE_APPROVE)) {
            productReviewService.approve(productReviewId);
            resp.sendRedirect(String.format(REDIRECT_AFTER_APPROVE, req.getContextPath(), req.getServletPath()));
        } else {
            productReviewService.delete(productReviewId);
            resp.sendRedirect(String.format(REDIRECT_AFTER_REJECT, req.getContextPath(), req.getServletPath()));
        }
    }
}
